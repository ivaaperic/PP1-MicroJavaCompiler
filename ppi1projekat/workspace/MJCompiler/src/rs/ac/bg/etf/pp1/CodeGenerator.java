package rs.ac.bg.etf.pp1;


import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter; 
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPc;
	private boolean elemniza=false;
//	List<Integer> nums = new ArrayList<>();
//	boolean printTrue;
	
	public int getMainPc(){
		return mainPc;
	}
	
	public void visit(ProgName progName) {
		//ord
//		Obj ord = TabIv.find("ord");
//		ord.setAdr(Code.pc);
		TabIv.ordObj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);
		//chr
		TabIv.chrObj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);
		//len
		TabIv.lenObj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.arraylength);
		Code.put(Code.exit);
		Code.put(Code.return_);

	}
	
	public void visit(MethodTypeNameVoidIv methodTypeName){
		
		if("main".equalsIgnoreCase(methodTypeName.getMethName())){
			mainPc = Code.pc;
		}
		methodTypeName.obj.setAdr(Code.pc);
		// Collect arguments and local variables
		SyntaxNode methodNode = methodTypeName.getParent();
	
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);
		
		// Generate the entry
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(fpCnt.getCount() + varCnt.getCount());
	
	}
	
	public void visit(MethodDeclIv methodDecl){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(MatchedReturnIv returnExpr){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(MatchedPrintIv ps) {
		if(ps.getExpr().struct.getKind()==Struct.Array) {
			if (ps.getExpr().struct.getElemType() == TabIv.intType) {
				Code.loadConst(5);
				Code.put(Code.print);
			} else if(ps.getExpr().struct.getElemType() == TabIv.charType) {
				Code.loadConst(1);
				Code.put(Code.bprint);
			} else if(ps.getExpr().struct.getElemType() == TabIv.boolType){
				Code.loadConst(5);
				Code.put(Code.print);
			}
		} else {
			if (ps.getExpr().struct == TabIv.intType) {
				Code.loadConst(5);
				Code.put(Code.print);
			} else if(ps.getExpr().struct == TabIv.charType) {
				Code.loadConst(1);
				Code.put(Code.bprint);
			} else if(ps.getExpr().struct == TabIv.boolType){
				Code.loadConst(5);
				Code.put(Code.print);
			}
		}
	}
	
	public void visit(MatchedReadIv readIv) {
		Struct designator = readIv.getDesignator().obj.getType();
		if(designator == TabIv.intType) {
			Code.put(Code.read);
		}else if (designator == TabIv.charType) {
			Code.put(Code.bread);
		} else { //bool
			Code.put(Code.read);
		}
		Code.store(readIv.getDesignator().obj);
	}
	
	public void visit(MatchedPrintNumberIv ps) {
		if(ps.getExpr().struct.getKind()==Struct.Array) {
			if (ps.getExpr().struct.getElemType() == TabIv.intType) {
				Code.loadConst(ps.getPrintNumber());
				Code.put(Code.print);
			} else if(ps.getExpr().struct.getElemType() == TabIv.charType) {
				Code.loadConst(ps.getPrintNumber());
				Code.put(Code.bprint);
			} else if(ps.getExpr().struct.getElemType() == TabIv.boolType){
				Code.loadConst(ps.getPrintNumber());
				Code.put(Code.print);
			}
		} else {
			if (ps.getExpr().struct == TabIv.intType) {
				Code.loadConst(ps.getPrintNumber());
				Code.put(Code.print);
			} else if(ps.getExpr().struct == TabIv.charType) {
				Code.loadConst(ps.getPrintNumber());
				Code.put(Code.bprint);
			} else if(ps.getExpr().struct == TabIv.boolType){
				Code.loadConst(ps.getPrintNumber());
				Code.put(Code.print);
			}
		}
	}
	
	public void visit(FactorNum cnst){
		Obj con = TabIv.insert(Obj.Con, "$", cnst.struct);
		con.setLevel(0);
		con.setAdr(cnst.getNumberFactor());
		Code.load(con);
	}
	
	public void visit(ExprMinusIv minus) {
		Code.put(Code.neg);
	}
	
	public void visit(FactorBool fb) {
		Obj bool = TabIv.insert(Obj.Con, "$", fb.struct);
		bool.setLevel(0);
		int x = fb.getBoolFactor().equalsIgnoreCase("true") ? 1 : 0;
		bool.setAdr(x);
		Code.load(bool);
	}
	
	public void visit(FactorChar fc)
	{
		Obj ch = TabIv.insert(Obj.Con, "$", fc.struct);
		ch.setLevel(0);
		ch.setAdr(fc.getCharFactor().charAt(1));
		Code.load(ch);
	}
	public void visit(FactorNew fn)
	{
		if (fn.struct.getKind() == Struct.Int) {
			Code.put(Code.newarray);
			Code.put(1);
		} else if(fn.struct.getKind() == Struct.Char) {
			Code.put(Code.newarray);
			Code.put(0);
		} else  {//bool
			Code.put(Code.newarray);
			Code.put(1);
		}
	}
	
	//Designator:des1 EQUAL Designator:des2 DOT FINDANY LPAREN Expr:ex RPAREN SEMI
	public void visit(MatchedFindAnyIv findAny) {
		//stek: adr , expr
		Code.loadConst(0);
		//stek: adr, expr, 0
		Code.put(Code.dup_x2);
		Code.put(Code.pop);
		//stek: 0, adr, expr
		Code.put(Code.dup_x2);
		Code.put(Code.pop);
		//stek: expr, 0, adr
		Code.put(Code.dup_x2);
		//stek: adr, expr, 0, adr
		int forInt = Code.pc;
		
		Code.put(Code.arraylength);
		//stek: adr, expr, 0, n
		
		Code.put(Code.dup2);
		
		//stek: adr, expr, 0, n, 0, n
		
		//kad (god?) naidjem na x 
		int temp = Code.pc;
//		Code.loadConst(0);
		Code.put(Code.jcc+Code.eq);
		Code.put2(0);
		
		//stek: adr, expr, 0, n
		Code.put(Code.pop);
		//stek: adr, expr, 0
		Code.put(Code.dup_x2);
		//stek: 0, adr, exp, 0
		Code.put(Code.pop);
		//stek: 0, adr, expr
		Code.put(Code.dup_x2);
		//stek: expr, 0, adr, expr
		Code.put(Code.dup_x1);
		//stek: expr, 0, expr, adr, expr
		Code.put(Code.pop);
		//stek: expr, 0, expr, adr
		Code.put(Code.dup_x2);
		//stek: expr, adr, 0, expr, adr
		Code.put(Code.dup_x2);
		//stek:expr, adr, adr, 0, expr, adr
		Code.put(Code.pop);
		//stek:expr, adr, adr, 0, expr
		Code.put(Code.dup_x2);
		//stek: expr, adr, expr, adr, 0, expr
		Code.put(Code.pop);
		//stek:expr, adr, expr, adr, 0
		Code.put(Code.dup2);
		//stek:expr, adr, expr, adr, 0, adr, 0
		
		if(findAny.getDesignator1().obj.getType().getElemType()==TabIv.charType) {
			Code.put(Code.baload);
		} else {
			Code.put(Code.aload);
		}
		//stek: expr, adr, expr, adr, 0, adr[0]
		Code.put(Code.dup_x2);
		//stek: expr, adr, expr, adr[0], adr, 0, adr[0]
		Code.put(Code.pop);
		//stek:expr, adr, expr, adr[0], adr, 0
		Code.put(Code.dup_x2);
		//stek:expr, adr, expr, 0, adr[0], adr, 0
		Code.put(Code.pop);
		//stek:expr, adr, expr, 0, adr[0], adr
		Code.put(Code.pop);
		//stek:expr, adr, expr, 0, adr[0]
		Code.put(Code.dup_x2);
		//stek: expr, adr, adr[0], expr, 0, adr[0]
		Code.put(Code.pop);
		//stek: expr, adr, adr[0], expr, 0
		Code.put(Code.dup_x2);
		//stek: expr, adr, 0, adr[0], expr, 0
		Code.put(Code.pop);
		//stek: expr, adr, 0, adr[0], expr
		
		//kad (god?) naidjem na y 
		//int temp2 = Code.pc;
		//kad naidjem na jmp y
		int temp2 = Code.pc;
//		Code.loadConst(0);
		Code.put(Code.jcc+Code.eq);
		Code.put2(0);
		//stek: expr, adr, 0
		
		Code.loadConst(1);
		//stek: expr, adr, 0 , 1
		Code.put(Code.add);
		//stek: expr, adr, 1
		Code.put(Code.dup_x2);
		//stek:1, expr, adr, 1
		Code.put(Code.pop);
		//stek: 1, expr, adr
		Code.put(Code.dup_x2);
		//stek: adr, 1, expr, adr
		Code.put(Code.dup_x2);
		//stek: adr, adr, 1, expr, adr
		Code.put(Code.pop);
		//stek: adr, adr, 1, expr
		Code.put(Code.dup_x1);
		//stek: adr, adr, expr, 1, expr
		Code.put(Code.pop);
		//stek: adr, adr, expr, 1
		Code.put(Code.dup_x2);
		//stek: adr, 1, adr, expr,1
		Code.put(Code.pop);
		//stek: adr, 1, adr, expr
		Code.put(Code.dup_x2);
		//stek: adr, expr, 1, adr, expr
		Code.put(Code.pop);
		//stek: adr, expr, 1, adr
		Code.putJump(forInt);
		
		//kad izadjem iz for petlje:
		Code.put2(temp+1,Code.pc-temp);
		//stek: adr, expr, index, n
		Code.put(Code.pop);
		//stavim false odmah
		Code.loadConst(0);
		Code.put(Code.jmp); 
		Code.put2(4);
		Code.put2(temp2+1, Code.pc-temp2);
		Code.loadConst(1);
		
		//na kraju storujem u designator 1 ili 0 u zavisnosti je l nasao
		Code.store(findAny.getDesignator().obj);
		//expr,  adr  ,index 
		Code.put(Code.pop);
		Code.put(Code.pop);
		Code.put(Code.pop);
	}
	
	public void visit(DesignatorEqualExprIv assignment){
		if(elemniza) {
			elemniza=false;
			if (assignment.getDesignator().obj.getType().getElemType() == TabIv.charType) {
				Code.put(Code.bastore);
			} else {
				Code.put(Code.astore);
			}
		} else {
			Code.store(assignment.getDesignator().obj);
		}
	}
	
	public void visit(DesignatorIv designator){
		
		SyntaxNode parent = designator.getParent();
		if(DesignatorEqualExprIv.class != parent.getClass()
				&& FactorDesignCall.class != parent.getClass()
				&& DesignaLActRIv.class != parent.getClass()
				&& MatchedReadIv.class != parent.getClass()
				&&DesignMinusMinusIv.class != parent.getClass()
				&&DesignPlusPlusIv.class != parent.getClass()
				&&MatchedFindAnyIv.class!=parent.getClass()){
			Code.load(designator.obj);
		}
		if(DesignatorEqualExprIv.class == parent.getClass()) {
			if(designator.getDesList() instanceof DesListExprIv) {
				Code.load(designator.obj);
				Code.put(Code.dup_x1);
				Code.put(Code.pop);
				elemniza=true;
			}
		} else if(parent.getClass()==MatchedFindAnyIv.class){
			if(designator.obj.getType().getKind()==Struct.Array) {
				Code.load(designator.obj);
			} else if(designator.obj.getType()==TabIv.boolType) {
				//prvi designator
				
			}
		} else {
			if(designator.getDesList() instanceof DesListExprIv) {
				Code.put(Code.dup_x1);
				Code.put(Code.pop);
				if (designator.obj.getType().getElemType() == TabIv.charType) {
					Code.put(Code.baload);
				} else { //za int i bool
					Code.put(Code.aload);
				}
			}
		}
//		}
		
	}
	
	public void visit(FactorDesignCall funcCall){

		Obj functionObj = funcCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		
		Code.put2(offset);
		
	}
	
	public void visit(DesignaLActRIv procCall){
		Obj functionObj = procCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		if(procCall.getDesignator().obj.getType() != TabIv.noType){
			Code.put(Code.pop);
		}
	}
	
	public void visit(ExprAddopIv addExpr){
		if (addExpr.getAddop() instanceof PlusIv) {
			Code.put(Code.add);
		} else {
			Code.put(Code.sub);
		}
	}
	
	public void visit(TermMulopIv mulopExpr) {
		if (mulopExpr.getMulop() instanceof MulIv) {
			Code.put(Code.mul);
		}else if (mulopExpr.getMulop() instanceof DivIv) {
			Code.put(Code.div);
		} else { //mod
			Code.put(Code.rem);
		}
	}
	
	public void visit(DesignPlusPlusIv desplusplus) {
		Code.loadConst(1);
		Code.load(desplusplus.getDesignator().obj);
		Code.put(Code.add);
		Code.store(desplusplus.getDesignator().obj);
		
	}
	public void visit(DesignMinusMinusIv desminusminus) {
		Code.loadConst(-1);
		Code.load(desminusminus.getDesignator().obj);
		Code.put(Code.add);
		Code.store(desminusminus.getDesignator().obj);
	}
}
