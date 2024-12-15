package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticPass extends VisitorAdaptor {

	int printCallCount = 0;
	int varDeclCount = 0;
	Obj currentMethod = null;
	Obj metodaKojaSePoziva=null;
	int metodaKojaSePozivaCnt=0;
	boolean returnFound = false;
	boolean newflag=false;
	boolean errorDetected = false;
	int nVars;
//	boolean metodaJe=false;
	boolean elemnizaje=false, elemnizaje2=false;;
	boolean elemnizajePom=false, elemnizajePom2=false;;
	
	ArrayList<Integer> daLiJeElemNiza= new ArrayList<>();
	ArrayList<Struct> stvarniParametri=new ArrayList<>();
	HashMap<String, ArrayList<Obj>> metode = new HashMap<>();
	ArrayList<Obj> pomocniParametri= new ArrayList<>();
	boolean imaMain;
	Term trenExpr = null;
	int brojGlobalnihPromenljivih=0;
	int brojLokalnihPromenljivih=0;
	String currentType=null;
	TypeIv currentTypeIv;
	int trenutniNivo=0;
	boolean globFlag=false, varFlag=false, formFlag=false;
	String globName=null, varName=null, formName=null;
	Obj designatorTrenutni=null;
	boolean elemniza=false, elemniza2=false;
	boolean declGreska =false;
	boolean whileFlag=false, foreachFlag=false;
	boolean whileFlag2=false, foreachFla2=false;
	int cnt=0;
	boolean proveraRelop=false;
	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
    
    public void visit(ProgNameIv progName){
    	progName.obj = TabIv.insert(Obj.Prog, progName.getProgName(), TabIv.noType);
    	TabIv.openScope();
    	trenutniNivo=0;
    }
    
    public void visit(ProgramIv program){
    	nVars = TabIv.currentScope.getnVars();
    	TabIv.chainLocalSymbols(program.getProgName().obj);
    	TabIv.closeScope();
    	trenutniNivo=-1;
    	if(!imaMain) {
    		errorDetected=true;
    		log.error("GRESKA! U programu mora postojati main metoda tipa void bez parametara!");
    	}
    	if(brojLokalnihPromenljivih>256) {
    		errorDetected=true;
    		log.error("GRESKA! Ne sme se koristiti vise od 256 lokalnih promenljivih!");
    	}
    	if(brojGlobalnihPromenljivih>65536) {
    		errorDetected=true;
    		log.error("GRESKA! Ne sme se koristiti vise od 65536 globalnih promenljivih!");
    	}
    }
    
    public void visit(TypeIv type){
    	Obj typeNode = TabIv.find(type.getTypeName());
    	if(typeNode == TabIv.noObj){
    		report_error("GRESKA! Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", null);
    		type.struct = TabIv.noType;
    	}else{
    		if(Obj.Type == typeNode.getKind()){
    			type.struct = typeNode.getType();
    			currentType=type.getTypeName();
    			currentTypeIv=type;
    		}else{
    			report_error("GRESKA: Ime " + type.getTypeName() + " ne predstavlja tip!", type);
    			type.struct = TabIv.noType;
    		}
    	}
    }

    public void visit(NumConstIv numConstIv) {
//    	report_info("Curr type pre ulaska: "+currentType, numConstIv);
    	if(currentType.equals("int")) {
        	String imeConst=numConstIv.getConstName();
        	Obj provera=TabIv.find(imeConst);
        	if(provera==TabIv.noObj || TabIv.currentScope.findSymbol(imeConst)==null) {
        		Obj constNode = TabIv.insert(Obj.Con, numConstIv.getConstName(), dohvatiTipPomocuStringa(currentType)); 
        		constNode.setAdr(numConstIv.getNumberConst());
        		report_info("Deklarisana konstanta "+ numConstIv.getConstName() +" tipa "+currentType + " sa vrednoscu: "+ numConstIv.getNumberConst(),numConstIv);
        	}else {
        		errorDetected=true;
        		report_error("GRESKA prilikom deklaracije konstante! Ime "+ imeConst+" se vec nalazi u tabeli simbola u tom opsegu!", numConstIv);
        		return;
        	}
		} else {
			errorDetected=true;
			report_error("GRESKA prilikom deklaracije promenljive! Ne poklapaju se tipovi int i "+currentType+ "!", numConstIv);
			return;
		}

    }
    public Struct dohvatiTipPomocuStringa(String tip) {
    	
    	if(currentType.equals("char")) {
    		return TabIv.charType;
    	} else if(currentType.equals("int")) {
    		return TabIv.intType;
    	} else if(currentType.equals("bool")) {
    		return TabIv.boolType;
    	} else {
    		return TabIv.noType;
    	}
    	
    }
    public void visit(SquareBracketsIv sq) {
    	if(formFlag) {
    		Obj obj =TabIv.insert(Obj.Var, formName, new StructIv(StructIv.Array, dohvatiTipPomocuStringa(currentType)));
    		report_info("Deklarisan formalni parametar niz "+ obj.getName() +" tipa "+obj.getType().getElemType().getKind(),sq);
    		brojLokalnihPromenljivih++;
    		currentMethod.setLevel(currentMethod.getLevel()+1);
    		pomocniParametri.add(obj);
//    		report_info("BROJ: "+ pomocniParametri.size(), sq);

    		formFlag=false;
    		formName=null;
    	} else if(globFlag) {
    		Obj obj =TabIv.insert(Obj.Var, globName, new StructIv(StructIv.Array, dohvatiTipPomocuStringa(currentType)));
//    		Obj varNode = TabIv.insert(Obj.Var, globName, currentTypeIv.struct); 
    		report_info("Deklarisana globalna promenljiva niz "+ obj.getName() +" tipa "+ obj.getType().getElemType().getKind(),sq);
    		brojGlobalnihPromenljivih++;
    		globFlag=false;
    		globName=null;
    	} else if(varFlag) {
    		Obj obj = TabIv.insert(Obj.Var, varName, new StructIv(StructIv.Array, dohvatiTipPomocuStringa(currentType))); 
    		report_info("Deklarisana promenljiva niz "+ obj.getName() +" tipa "+ obj.getType().getElemType().getKind(),sq);
    		brojLokalnihPromenljivih++;
    		varFlag=false;
    		varName=null;
    	}
    }
    
    public void visit(NoSquareBracketsIv nsq) {
    	if(formFlag) {
    		Obj varNode = TabIv.insert(Obj.Var, formName, dohvatiTipPomocuStringa(currentType)); 
    		report_info("Deklarisana formalni parametar "+ varNode.getName() +" tipa "+currentType,nsq);
    		currentMethod.setLevel(currentMethod.getLevel()+1);
    		brojLokalnihPromenljivih++;
    		pomocniParametri.add(varNode);
//    		report_info("BROJ: "+ pomocniParametri.size(), nsq);
    		formFlag=false;
    		formName=null;
    	} else if(globFlag) {
    		Obj varNode = TabIv.insert(Obj.Var, globName, dohvatiTipPomocuStringa(currentType)); 
    		report_info("Deklarisana globalna promenljiva "+ varNode.getName() +" tipa "+currentType,nsq);
    		brojGlobalnihPromenljivih++;
    		globFlag=false;
    		globName=null;
    	} else if(varFlag) {
    		Obj varNode = TabIv.insert(Obj.Var, varName, dohvatiTipPomocuStringa(currentType)); 
    		report_info("Deklarisana promenljiva "+ varNode.getName() +" tipa "+currentType,nsq);
    		brojLokalnihPromenljivih++;
    		varFlag=false;
    		varName=null;
    	}
    }
    
    public void visit(KrajMetDecIv kmd) {
//    	report_info("METODA DEKLARACIJA: IME: "+ currentMethod.getName() + " BROJ PARAMETARA: "+ pomocniParametri, kmd);
    	ArrayList<Obj> arej = new ArrayList<>();
    	arej.addAll(pomocniParametri);
    	metode.put(currentMethod.getName(), arej);
    	pomocniParametri.clear();
    }
    
    public void visit(CharConstIv charConstIv) {
//    	report_info("Curr type pre ulaska: "+currentType, charConstIv);
    	if(currentType.equals("char")) {
        	String imeConst=charConstIv.getConstName();
        	Obj provera=TabIv.find(imeConst);
        	if(provera==TabIv.noObj || TabIv.currentScope.findSymbol(imeConst)==null) {
        		Obj constNode = TabIv.insert(Obj.Con, charConstIv.getConstName(), dohvatiTipPomocuStringa(currentType)); 
        		constNode.setAdr(charConstIv.getCharConst().charAt(1));
        		report_info("Deklarisana konstanta "+ charConstIv.getConstName() +" tipa "+currentType + " sa vrednoscu: "+ charConstIv.getCharConst().charAt(1),charConstIv);
        	}else {
        		errorDetected=true;
        		report_error("GRESKA prilikom deklaracije konstante! Ime "+ imeConst+" se vec nalazi u tabeli simbola u tom opsegu!", charConstIv);
        		return;
        	}
		} else {
			errorDetected=true;
			report_error("GRESKA prilikom deklaracije promenljive! Ne poklapaju se tipovi char i "+currentType+ "!", charConstIv);
			return;
		}
    }
    public void visit(BoolConstIv boolConstIv) {
//    	report_info("Curr type pre ulaska: "+currentType, boolConstIv);
    	if(currentType.equals("bool")) {
        	String imeConst=boolConstIv.getConstName();
        	Obj provera=TabIv.find(imeConst);
        	if(provera==TabIv.noObj || TabIv.currentScope.findSymbol(imeConst)==null) {
        		Obj constNode = TabIv.insert(Obj.Con, boolConstIv.getConstName(), dohvatiTipPomocuStringa(currentType)); 
        		if(boolConstIv.getBoolConst().equals("false")) {
        			constNode.setAdr(0);
        		} else if(boolConstIv.getBoolConst().equals("true")) {
        			constNode.setAdr(1);
        		} else {
        			report_error("GRESKA prosledjena vrednost kod bool konstante nije ni true ni false nego neko smece!", boolConstIv);
        		}
        		report_info("Deklarisana konstanta "+ boolConstIv.getConstName() +" tipa "+currentType + " sa vrednoscu: "+ boolConstIv.getBoolConst(),boolConstIv);
        	}else {
        		errorDetected=true;
        		report_error("GRESKA prilikom deklaracije konstante! Ime "+ imeConst+" se vec nalazi u tabeli simbola u tom opsegu!", boolConstIv);
        		return;
        	}
		} else {
			errorDetected=true;
			report_error("GRESKA prilikom deklaracije promenljive! Ne poklapaju se tipovi bool i "+currentType+ "!", boolConstIv);
			return;
		}
    }
    
    public void visit(VarIdentIv varDecl) {
    	String imeVar=varDecl.getVarName();
    	Obj provera=TabIv.find(imeVar);
    	if(provera==TabIv.noObj || TabIv.currentScope.findSymbol(imeVar)==null) {
    		varFlag=true;
    		varName=imeVar;
    	}else {
    		errorDetected=true;
    		report_error("GRESKA prilikom deklaracije promenljive! Ime "+ imeVar+" se vec nalazi u tabeli simbola u tom opsegu!", varDecl);
    		return;
    	}
    }

    public void visit(GlobIdentIv globVarDeclMoreIv) {
    	String imeVar=globVarDeclMoreIv.getGlobVarName();
    	Obj provera=TabIv.find(imeVar);
    	if(provera==TabIv.noObj || TabIv.currentScope.findSymbol(imeVar)==null) {
    		globFlag=true;
    		globName=imeVar;
    	}else {
    		errorDetected=true;
    		report_error("GRESKA prilikom deklaracije promenljive! Ime "+ imeVar+" se vec nalazi u tabeli simbola u tom opsegu!", globVarDeclMoreIv);
    		return;
    	}
    }
	public void visit(MethodTypeNameIv methodTypeName){

		if(TabIv.currentScope.findSymbol(methodTypeName.getMethName())==null) {
			currentMethod = TabIv.insert(Obj.Meth, methodTypeName.getMethName(), methodTypeName.getType().struct);
			methodTypeName.obj = currentMethod;
			
			TabIv.openScope();
			trenutniNivo++;
			report_info("Obradjuje se funkcija " + methodTypeName.getMethName(), methodTypeName);
			if(methodTypeName.getMethName().equals("main")) {
				errorDetected=true;
	    		report_error("GRESKA prilikom deklaracije metode! Metoda main mora da bude void tipa!", methodTypeName);
	    		return;
			}
		} else {
			errorDetected=true;
    		report_error("GRESKA prilikom deklaracije metode! Metoda "+ methodTypeName.getMethName()+" se vec nalazi u tabeli simbola u tom opsegu!", methodTypeName);
    		return;
    	
		}
		
	}
	
	public void visit(MethodTypeNameVoidIv methodTypeName){
		if(TabIv.currentScope.findSymbol(methodTypeName.getMethName())==null) {
			currentMethod = TabIv.insert(Obj.Meth, methodTypeName.getMethName(), TabIv.noType);
			methodTypeName.obj = currentMethod;
			if(methodTypeName.getMethName().equals("main")) {
				imaMain=true;
			}
			TabIv.openScope();
			trenutniNivo++;
			report_info("Obradjuje se funkcija " + methodTypeName.getMethName(), methodTypeName);
			
		} else {
			errorDetected=true;
    		report_error("GRESKA prilikom deklaracije metode! Metoda "+ methodTypeName.getMethName()+" se vec nalazi u tabeli simbola u tom opsegu!", methodTypeName);
    		return;
		}
	}
    
    public void visit(MethodDeclIv methodDecl){
    	if(!returnFound && currentMethod.getType() != TabIv.noType &&!currentMethod.getName().equals("main")){
			report_error("GRESKA! Semanticka greska na liniji " + methodDecl.getLine() + ": funkcija " + currentMethod.getName() + " nema return iskaz!", null);
    	}
    	TabIv.chainLocalSymbols(currentMethod);
    	TabIv.closeScope();
    	
    	returnFound = false;
    	currentMethod = null;
    }
    

    public void visit(FormParamIv formparslistiv) {
    	String imeForm=formparslistiv.getFormParamName();
    	if(currentMethod.getName().equals("main")) {
    		errorDetected=true;
    		imaMain=false;
    		report_error("GRESKA prilikom deklaracije main metode! Metoda main ne sme da ima formalne parametre", formparslistiv);
    		return;
    	}
    	Obj provera=TabIv.find(imeForm);
    	if(provera==TabIv.noObj || TabIv.currentScope.findSymbol(imeForm)==null) {
    		formFlag=true;
    		formName=imeForm;
    	}else {
    		errorDetected=true;
    		report_error("GRESKA prilikom deklaracije promenljive! Ime "+ imeForm+" se vec nalazi u tabeli simbola u tom opsegu!", formparslistiv);
    		return;
    	}
    }
    
    public void visit(DesignatorEqualExprIv designeqexpr) {
    	if(declGreska) {
    		declGreska=false;
    		return;
    	}
    	Obj designator = TabIv.find(designeqexpr.getDesignator().obj.getName()) ;
    	Struct expr = designeqexpr.getExpr().struct;
    	if(designator.getKind()==Obj.Con) {
    		errorDetected=true;
    		report_error("GRESKA prilikom dodele vrednosti! Ne sme se menjati vrednost konstante!", designeqexpr);
    		return;
    	}
    	if (designator.getKind()!=Obj.Var && designator.getType().getKind()!=Struct.Array) {
    		errorDetected=true;
    		report_error("GRESKA prilikom dodele vrednosti! Designator mora biti promenljiva ili element niza!", designeqexpr);
    		return;
    	}
    	if(designator.getType().getKind()==Struct.Array) {
//    		if(designator.getFpPos()==0) {
//    			//designator je niz, a ne elem niza
//    			errorDetected=true;
//        		report_error("GRESKA prilikom dodele vrednosti! Designator mora biti element niza, a ne niz!", designeqexpr);
//        		return;
//    		} else if(newflag) {
//    			newflag=false;
//    		}
//    	}
//    	if (designator.getType().getKind() == Struct.Array) {
    		if(expr.getKind() == Struct.Array) {
    			//expr je niz
    			if(designator.getFpPos()==0) {
        			//designator je niz, a ne elem niza
    				if(!newflag) {
    					//design ne treba da dobije vrednost novog niza
    					if(elemniza==true) {
    						errorDetected=true;
    	            		report_error("GRESKA prilikom dodele vrednosti! Expr mora biti niz kao i designator", designeqexpr);
    	            		return;
    					}
    				} else {
    					newflag=false;
    				}
//        			errorDetected=true;
//            		report_error("GRESKA prilikom dodele vrednosti! Designator mora biti element niza, a ne niz!", designeqexpr);
//            		return;
        		} else {
        			if(newflag==true) {
        				errorDetected=true;
        				newflag=false;
                		report_error("GRESKA prilikom dodele vrednosti! New ne moze da dodeli vrednost niza elementru niza!", designeqexpr);
                		return;
        			}else if(elemniza==false) {
        				//expr je niz a ne elem niza
            			errorDetected=true;
                		report_error("GRESKA prilikom dodele vrednosti! Expr mora biti element niza, a ne niz!1", designeqexpr);
                		return;
            		}
        		}
    			
    			if (!expr.getElemType().assignableTo(designator.getType().getElemType())) {
    				errorDetected=true;
    	    		report_error("GRESKA prilikom dodele vrednosti! Nekompatibilni tipovi! (Levo arr, desno arr)", designeqexpr);
    	    		return;
    			}
    		} else {
    			if (!expr.assignableTo(designator.getType().getElemType())) {
    				errorDetected=true;
    	    		report_error("GRESKA prilikom dodele vrednosti! Nekompatibilni tipovi! (Levo arr, desno var)", designeqexpr);
    	    		return;
    			}
    		}
		} else if ( expr.getKind() == Struct.Array) {
			if(newflag) {
				errorDetected=true;
				newflag=false;
        		report_error("GRESKA prilikom dodele vrednosti! NEW niz se ne moze dodeliti nenizu!", designeqexpr);
        		return;
			}else if(elemniza==false) {
    			errorDetected=true;
        		report_error("GRESKA prilikom dodele vrednosti! Expr mora biti element niza, a ne niz!2", designeqexpr);
        		return;
    		}
			if (!expr.getElemType().assignableTo(designator.getType())) {
				errorDetected=true;
	    		report_error("GRESKA prilikom dodele vrednosti! Nekompatibilni tipovi! (Levo var, desno arr)", designeqexpr);
	    		return;
			}
		} else {	
			if (!expr.assignableTo(designator.getType())) {
				errorDetected=true;
	    		report_error("GRESKA prilikom dodele vrednosti! Nekompatibilni tipovi! (Levo var, desno var)", designeqexpr);
	    		return;
			}
		} 
    	report_info("SVE OK sa dodelom vrednosti", designeqexpr);
    }
    
    public void visit(DesignPlusPlusIv desplusplus) {
    	if(declGreska) {
    		declGreska=false;
    		return;
    	}
    	Obj designator = TabIv.find(desplusplus.getDesignator().obj.getName());
    	if(designator.getKind()==Obj.Con) {
    		errorDetected=true;
    		report_error("GRESKA prilikom inkrementa! Ne sme se menjati vrednost konstante!", desplusplus);
    		return;
    	}
    	if (designator.getKind()!=Obj.Var && designator.getType().getKind()!=Struct.Array) {
    		errorDetected=true;
    		report_error("GRESKA prilikom inkrementa! Designator mora biti promenljiva ili element niza!", desplusplus);
    		return;
    	}
    	if(designator.getType().getKind()==Struct.Array) {
    		if(designator.getFpPos()==0) {
    			errorDetected=true;
        		report_error("GRESKA prilikom inkrementa! Designator mora biti element niza, a ne niz!", desplusplus);
        		return;
    		}
    		if(!designator.getType().getElemType().equals(TabIv.intType)) {
    			errorDetected=true;
        		report_error("GRESKA prilikom inkrementa! Elem niza mora biti tipa INT!", desplusplus);
        		return;
    		}
    	} else if(!designator.getType().equals(TabIv.intType)) {
    		errorDetected=true;
    		report_error("GRESKA prilikom inkrementa! Designator mora biti tipa INT!", desplusplus);
    		return;
    	}
    	report_info("SVE OK sa inkrementom", desplusplus);
    }
    public void visit(DesignMinusMinusIv desminusminus) {
    	if(declGreska) {
    		declGreska=false;
    		return;
    	}
    	Obj designator = TabIv.find(desminusminus.getDesignator().obj.getName());
    	
    	if(designator.getType()==TabIv.noType) {
    		errorDetected=true;
    		report_error("GRESKA ime "+ designator.getName() + " nije prethodno deklarisano!", desminusminus);
    		return;
    	}
    	if(designator.getKind()==Obj.Con) {
    		errorDetected=true;
    		report_error("GRESKA prilikom dekrementa! Ne sme se menjati vrednost konstante!", desminusminus);
    		return;
    	}
    	if (designator.getKind()!=Obj.Var && designator.getType().getKind()!=Struct.Array) {
    		errorDetected=true;
    		report_error("GRESKA prilikom dekrementa! Designator mora biti promenljiva ili element niza!", desminusminus);
    		return;
    	}
    	if(designator.getType().getKind()==Struct.Array) {
    		if(designator.getFpPos()==0) {
    			errorDetected=true;
        		report_error("GRESKA prilikom dekrementa! Designator mora biti element niza, a ne niz!", desminusminus);
        		return;
    		}
    		if(!designator.getType().getElemType().equals(TabIv.intType)) {
    			errorDetected=true;
        		report_error("GRESKA prilikom dekrementa! Elem niza mora biti tipa INT!", desminusminus);
        		return;
    		}
    	} else if(!designator.getType().equals(TabIv.intType)) {
    		errorDetected=true;
    		report_error("GRESKA prilikom dekrementa! Designator mora biti tipa INT!", desminusminus);
    		return;
    	}
    	report_info("SVE OK sa dekrementom", desminusminus);
    }
    
    public void visit(DesignatorIv designatorIv) {
    	String designator = designatorIv.getDesignatorIdent();
    	Obj designatorObj= TabIv.find(designator);
    	if(designatorObj==TabIv.noObj) {
    		errorDetected=true;
    		declGreska=true;
    		designatorIv.obj= TabIv.noObj;
    		report_error("GRESKA ime "+ designatorIv.getDesignatorIdent() + " nije prethodno deklarisano!", designatorIv);
    		return;
    	}
    	designatorIv.obj=designatorObj;
    	if(Obj.Meth == designatorObj.getKind()){
//    		metodaJe=true;
    		metodaKojaSePoziva=designatorObj;
    	} 
//    	else {
//    		metodaJe=false;
//    	}
    	DesList sq = designatorIv.getDesList();
    	if(sq instanceof DesListExprIv) { //ima zagrade
    		designatorObj.setFpPos(1);
			elemnizaje=true;
			if(elemnizaje) {
				elemnizaje2=true;
			}
    		if(designatorObj.getType().getKind()!=Struct.Array){
    			errorDetected=true;
//    			designatorIv.obj= TabIv.noObj;
    			designatorIv.obj=designatorObj;
        		report_error("GRESKA ime "+ designator + " nije niz!", designatorIv);
        		return;
    		} else {
    			if(((DesListExprIv) sq).getExpr().struct.getKind()!=Struct.Array) {
    				if(!((DesListExprIv) sq).getExpr().struct.assignableTo(TabIv.intType)){
        				errorDetected=true;
//            			designatorIv.obj= TabIv.noObj;
        				designatorIv.obj=designatorObj;
                		report_error("GRESKA expr niza "+ designator + " nije int!", designatorIv);
                		return;
        			} else {
        				report_info("SVE OKEJ sa nizom", sq);
        			}
    			} else {
    				Struct s = new Struct(Struct.Array, TabIv.intType);
    				if(!((DesListExprIv) sq).getExpr().struct.assignableTo(s)){
        				errorDetected=true;
//            			designatorIv.obj= TabIv.noObj;
        				designatorIv.obj=designatorObj;
                		report_error("GRESKA expr niza u nizu "+ designator + " nije int!", designatorIv);
                		return;
        			} else {
        				report_info("SVE OKEJ sa nizom u nizu", sq);
        			}
    				//niz[niz[jedan]] = niz[niz[0]] * 3;
    			}
    			
    		}
    			
    	} else { //nema zagrade
    		if(elemnizaje2) {
    			elemnizaje2=false;
    		} else {
    			elemnizaje=false;
    		}
    		
    		designatorObj.setFpPos(0);
    	}
    	
    	designatorIv.obj=designatorObj;
    }
    public void visit(TermIv term){
    	term.struct= term.getFactor().struct;
    }
    public void visit(TermMulopIv termmulop) {
    	Struct term = termmulop.getTerm().struct;
    	Struct factor = termmulop.getFactor().struct;
    	termmulop.struct=term;
    	if(term.getKind()==Struct.Array) {
    		if(!term.getElemType().assignableTo(TabIv.intType)) {
    			errorDetected=true;
//    			termmulop.struct=TabIv.noType;
        		report_error("GRESKA term "+ termmulop.getTerm() + " nije int!", termmulop);
        		return;
    		} else {
    			termmulop.struct=term;
    		}
    	} else {
    		if(term.getKind()!=Struct.Int) {
    			errorDetected=true;
//    			termmulop.struct=TabIv.noType;

        		report_error("GRESKA term "+ termmulop.getTerm() + " nije int!", termmulop);
        		return;
    		} else {
    			termmulop.struct=term;
    		}
    	}
    	if(factor.getKind()==Struct.Array) {
    		if(!factor.getElemType().assignableTo(TabIv.intType)) {
    			errorDetected=true;
//    			termmulop.struct=TabIv.noType;

    			report_error("GRESKA factor "+ termmulop.getFactor() + " nije int!", termmulop);
    			return;
    		} else {
    			termmulop.struct=factor;
    		}
    	} else {
    		if(factor.getKind()!=Struct.Int) {
    			errorDetected=true;
//    			termmulop.struct=TabIv.noType;

    			report_error("GRESKA factor "+ termmulop.getFactor() + " nije int!", termmulop);
    			return;
    		} else {
    			termmulop.struct=factor;
    		}
    	}
    	report_info("SVE OK sa mulop", termmulop);
    }
    
    public void visit(FactorNew fn) {
    	Struct type = fn.getType().struct;
    	Struct s = new Struct(Struct.Array, type);
//    	fn.obj = new Obj(Obj.Elem, "Array", new Struct(Struct.Array, type));
//    	Obj obj = TabIv.find(formName)
    	
//    	Obj obj = new Obj(Obj.Elem, "Array", new Struct(Struct.Array, type));
		Struct expr = fn.getExpr().struct;
		fn.struct = s;
		if (!expr.assignableTo(TabIv.intType)) {
			errorDetected=true;
//    		fn.struct=TabIv.noType;
    		report_error("GRESKA kod NEW! Expr mora biti tipa INT!", fn);
    		return;			
		}
		newflag=true;
//    	fn.struct=fn.getExpr().struct;
//    	if(expr.getKind()!= Struct.Int) {
//    		errorDetected=true;
////    		fn.struct=TabIv.noType;
//    		report_error("GRESKA kod NEW! Expr mora biti tipa INT!", fn);
//    		return;
//    	}
    	
    	
    }
    public void visit(FactorExpr fn) {
    	fn.struct=fn.getExpr().struct;
    }
    public void visit(FactorBool fn) {
    	fn.struct=TabIv.boolType;
    }
    public void visit(FactorChar fn) {
    	fn.struct=TabIv.charType;
    }
    public void visit(FactorNum fn) {
    	fn.struct=TabIv.intType;
    }
    public void visit(FactorDesign fn) {
    	
    	Obj design = fn.getDesignator().obj;
    	fn.struct=design.getType();
    	if(declGreska) {
    		declGreska=false;
    		return;
    	}
//    	if(metodaKojaSePoziva!=null && metodaJe) {
//			errorDetected=true;
//			report_error("GRESKA neispravan poziv metode "+ metodaKojaSePoziva.getName(), fn);
//			return;
//    	}
//    	
//		if(metodaKojaSePoziva!=null) {
		fn.struct=design.getType();
//		}
    }
    public void visit(FactorDesignCall fn) {
    	
    	Obj design = fn.getDesignator().obj;
    	fn.struct=design.getType();
    	if(declGreska) {
    		declGreska=false;
    		return;
    	}
    	
    	if(metodaKojaSePoziva==null) {
    		errorDetected=true;
    		report_error("GRESKA nemetodu pozivate kao metodu", fn);
    		return;
    	}
    	
    	if(Obj.Meth == metodaKojaSePoziva.getKind()){
			report_info("Pronadjen poziv funkcije " + metodaKojaSePoziva.getName() , fn);

    	}else{
    		errorDetected=true;
			report_error("GRESKA: ime " + metodaKojaSePoziva.getName() + " nije funkcija!", fn);
			return;
    	}
    	
		if(design.getKind()!=Obj.Meth) {
			errorDetected=true;
	//        		fn.struct=TabIv.noType;
			report_error("GRESKA kod poziva funkcije! Identifikator "+ design.getName()+" nije funkcija!", fn);
			return;
		} else {
			report_info("Pronadjen poziv funkcije " + design.getName() , fn);
			
			metodaKojaSePoziva=design;
		}
    	
    }
//    public void visit(FactorDesign nda) {
//    	if(metodaKojaSePoziva==null) {
//    		errorDetected=true;
//    		report_error("GRESKA nemetodu pozivdate kao metodu", nda);
//    		return;
//    	}
//    	
//    	if(Obj.Meth == metodaKojaSePoziva.getKind()){
//    		report_info("Pronadjen poziv funkcije " + metodaKojaSePoziva.getName() , nda);
//    		
////			if(metodaKojaSePoziva.getLevel()!=stvarniParametri.size()) {
////				errorDetected=true;
////				report_error("GRESKA Metoda "+func.getName()+" ima "+ metodaKojaSePoziva.getLevel()+ " formalna parametara, a prosledjeno joj je "+ stvarniParametri.size()+ " stvarna parametra!", desactl);
////			}
//    	}else{
//    		errorDetected=true;
//    		report_error("GRESKA: ime " + metodaKojaSePoziva.getName() + " nije funkcija!", nda);
//    		return;
//    	}
//    }
    public void visit(MatchedReturnIv fn) {
    	returnFound = true;
    	if(currentMethod == null) {
    		errorDetected=true;
			report_error("GRESKA return izraz se nalazi van bilo kakve metode!", fn);
			return;
    	}
    }
	public void visit(ExprOrEpsIv fn) {
		Struct currMethType = currentMethod.getType();
		if(fn.getExpr().struct.getKind()==Struct.Array) {
			if(elemnizaje) {
				report_info("curr meth type "+ currMethType.getKind(), fn);
				report_info("curr meth elem type "+ fn.getExpr().struct.getElemType().getKind(), fn);
				if(currMethType.getKind()!=fn.getExpr().struct.getElemType().getKind()){
		    		errorDetected=true;
					report_error("GRESKA tip izraza u return naredbi elem niza ne slaze se sa tipom povratne vrednosti funkcije " + currentMethod.getName(), fn);
					return;
		    	}
			} else {
				errorDetected=true;
				report_error("GRESKA tip izraza u return naredbi ne sme da bude niz i ne slaze se sa tipom povratne vrednosti funkcije " + currentMethod.getName(), fn);
				return;
			}
		}
		else {
			if(!currMethType.compatibleWith(fn.getExpr().struct)){
				errorDetected=true;
				report_error("GRESKA tip izraza u return naredbi ne slaze se sa tipom povratne vrednosti funkcije " + currentMethod.getName(), fn);
				return;
			}
		}
    		
	}
	public void visit(NoExprOrEpsIv fn) {
		if(!currentMethod.getType().assignableTo(TabIv.noType)) {
			errorDetected=true;
			report_error("GRESKA void metoda moze da ima samo return bez dodatka, greska kod metode " + currentMethod.getName(), fn);
			return;
		}
	}
	
	public void visit(ConditionIfIv fn) {
		fn.struct=fn.getCondition().struct;
//		Struct cond = fn.getCondition().struct;
//		if(cond==null) {
//			errorDetected=true;
//			report_error("GRESKA Tip uslovnog izraza kod IF je null!", fn);
//			return;
//		}
		if(fn.getCondition().struct!=TabIv.boolType){
			errorDetected=true;
			report_error("GRESKA Tip uslovnog izraza kod IF nije bool!", fn);
			return;
		}
	}
	public void visit(UnmatchedWhileIv fn) {
//		Struct cond = fn.getCondition().struct;
//		if(cond==null) {
//			errorDetected=true;
//			report_error("GRESKA Tip uslovnog izraza kod WHILE je null!", fn);
//			return;
//		}
		if(fn.getCondition().struct!=TabIv.boolType){
			errorDetected=true;
			report_error("GRESKA Tip uslovnog izraza kod WHILE nije bool!", fn);
			return;
		}
	}
	public void visit(MatchedWhileIv fn) {
//		Struct cond = fn.getCondition().struct;
//		if(cond==null) {
//			errorDetected=true;
//			report_error("GRESKA Tip uslovnog izraza kod WHILE je null!", fn);
//			return;
//		}
		if(fn.getCondition().struct!=TabIv.boolType){
			errorDetected=true;
			report_error("GRESKA Tip uslovnog izraza kod WHILE nije bool!", fn);
			return;
		}
	}
	
	
	public void visit(ConditionIv fn) {
		if(fn.getCondition().struct==TabIv.boolType && fn.getCondTerm().struct==TabIv.boolType) {
			fn.struct=TabIv.boolType;

		}
	}
	public void visit(CondtitionTermIv fn) {

		if(fn.getCondTerm().struct==TabIv.boolType) {
			fn.struct=TabIv.boolType;
		} 
	}
	
	public void visit(CondTermIv fn) {
		if(fn.getCondFact().struct==TabIv.boolType && fn.getCondTerm().struct==TabIv.boolType) {
			fn.struct=TabIv.boolType;

		} 
	}
	public void visit(CondTermFactIv fn) {
		if(fn.getCondFact().struct==TabIv.boolType) {
			fn.struct=TabIv.boolType;

		} 
	}
	
	public void visit(CondFactIv fn) {
		if(fn.getExpr()!=null) {
			fn.struct=TabIv.boolType;
		}
		
		report_info("USPEH condition je bool type", fn);
		if(fn.getExpr().struct.assignableTo(TabIv.noType)) {
			errorDetected=true;
    		//fn.struct = TabIv.noType;
    		report_error("GRESKA kod condition! CondFact je noType!", fn);
    		return;
		}
	}
    
    public void visit(CondFactRelopIv fn) {
    	Struct expr1 = fn.getExpr().struct;
    	Struct expr2 = fn.getExpr1().struct;
    	fn.struct= expr1;
    	report_info("elem niza 1: "+elemnizaje, fn);
    	report_info("elem niza 2: "+elemnizaje2, fn);
//    	fn.struct = TabIv.boolType;
    	if(expr1!=null && expr2!=null) {
    		if(expr1.getKind()==Struct.Array) {
				if(expr2.getKind()==Struct.Array) {
					if(elemnizaje&&elemnizaje2) {
						if(expr1.getElemType().getKind()!=expr2.getElemType().getKind()) {
							errorDetected=true;
							report_error("GRESKA prilikom relop! Nisu kompatibilni tipovi elementa niza dva expr!", fn);
				    		return;
						} else {
							fn.struct=TabIv.boolType;
						}
					}else if(elemnizaje) {
						errorDetected=true;
						report_error("GRESKA prilikom relop! Ne mogu se porediti elem niza i niz!", fn);
			    		return;
					} else {
						if (proveraRelop) {
		    				errorDetected=true;
		    	    		report_error("GRESKA prilikom relop! Za nizove smeju da se koriste samo == i !=!", fn);
		    	    		return;
		    			} else {
		    				if(expr1.getElemType().getKind()!=expr2.getElemType().getKind()) {
		    					errorDetected=true;
			    	    		report_error("GRESKA prilikom relop! Nizovi nisu istog tipa!", fn);
			    	    		return;
		    				} else {
		    					fn.struct = TabIv.boolType;
		    				}
		    				
		    			}
					}
				} else {
					if(!elemnizaje) {
						errorDetected=true;
						report_error("GRESKA prilikom relop! Ne mogu se porediti elem niza i niz!", fn);
			    		return;
					} else {
						if(expr1.getElemType().getKind()!=expr2.getKind()){
							errorDetected=true;
							report_error("GRESKA prilikom relop! Elem niza expr1 nije istog tipa kao expr2!", fn);
				    		return;
						} else {
							fn.struct=TabIv.boolType;
						}
					}
				}
			} else if(expr2.getKind()==Struct.Array) {
				if(!elemnizaje) {
					errorDetected=true;
					report_error("GRESKA prilikom relop! Ne mogu se porediti elem niza i niz!", fn);
		    		return;
				} else {
					if(!expr1.compatibleWith(expr2.getElemType())){
						errorDetected=true;
						report_error("GRESKA prilikom relop! Expr1 nije istog tipa kao elem niza expr2!", fn);
			    		return;
					} else {
						fn.struct=TabIv.boolType;
					}
				}
			} else {
				if(!expr1.compatibleWith(expr2)) {
					errorDetected=true;
					report_error("GRESKA prilikom relop! Expr1 i expr2 nisu istog tipa!", fn);
		    		return;
				} else {
					fn.struct=TabIv.boolType;
				}
			}
			
		} else {
			errorDetected=true;
			report_error("GRESKA prilikom relop! Expr1 ili expr2 su null!", fn);
    		return;
		}
    	report_info("SVE OK sa relop", fn);
    	
    }
    
    public void visit(ActParsIv fn) {
    	fn.struct=fn.getActOne().struct;
    	
    }
    
    public void visit(ActParsMoreIv fn) {
    	fn.struct=fn.getActOne().struct;
    }
    
    public void visit(ActOneIv fn) {
    	Struct actpar= fn.getExpr().struct;
    	fn.struct=actpar;
    	stvarniParametri.add(actpar);
    	
    	if(elemnizaje) {
    		daLiJeElemNiza.add(metodaKojaSePozivaCnt);
    	}
    	metodaKojaSePoziva.setFpPos(metodaKojaSePoziva.getFpPos()+1);
    	metodaKojaSePozivaCnt++;
    }
    
    public void visit(MatchedReadIv fn) {
//    	fn.obj=fn.getDesignator().obj;
    	if(declGreska) {
    		declGreska=false;
    		return;
    	}
    	Obj designator = TabIv.find(fn.getDesignator().obj.getName());
    	if (designator.getKind()!=Obj.Var && designator.getType().getKind()!=Struct.Array) {
    		errorDetected=true;
    		report_error("GRESKA kod READ! Designator mora biti promenljiva ili element niza!", fn);
    		return;
    	}
    	if(designator.getType().getKind()==Struct.Array) {
    		
    		if(designator.getFpPos()==0) {
    			errorDetected=true;
        		report_error("GRESKA kod READ! Designator mora biti element niza, a ne niz!", fn);
        		return;
    		}
    		if(!designator.getType().getElemType().equals(TabIv.intType) &&
    				!designator.getType().getElemType().equals(TabIv.charType)&&
    				!designator.getType().getElemType().equals(TabIv.boolType)) {
    			errorDetected=true;
        		report_error("GRESKA kod READ! Elem niza mora biti tipa INT, CHAR ili BOOL!", fn);
        		return;
    		}
    	} else if(!designator.getType().assignableTo(TabIv.intType)&&
    			!designator.getType().assignableTo(TabIv.charType)&&
    			!designator.getType().assignableTo(TabIv.boolType)) {
    		errorDetected=true;
    		report_error("GRESKA kod READ! Designator mora biti tipa INT, CHAR ili BOOL!", fn);
    		return;
    	}
    	report_info("SVE OK sa READ", fn);
    	
    }
    
    public void visit(ExprAddopIv termExpr){
    	Struct term =termExpr.getTerm().struct;
    	Struct expr=termExpr.getExpr().struct;
    	termExpr.struct=term;
    	if(term.getKind()==Struct.Array) {
    		if(elemniza==false) {
    			errorDetected=true;
        		report_error("GRESKA prilikom addop! Term i expr moraju biti element niza, a ne niz!", termExpr);
        		return;
    		}
    		if(expr.getKind()==Struct.Array) {
    			if(elemniza) {
    				if(elemniza2==false) {
            			errorDetected=true;
                		report_error("GRESKA prilikom addop! Term i expr moraju biti element niza, a ne niz!", termExpr);
                		return;
            		}
    			} else if(elemniza==false) {
        			errorDetected=true;
            		report_error("GRESKA prilikom addop! Term i expr moraju biti element niza, a ne niz!", termExpr);
            		return;
        		}
        		if(!expr.getElemType().assignableTo(term.getElemType())) {
        			errorDetected=true;
        			report_error("GRESKA expr i term nisu kompatibilni nizovi!", termExpr);
        			return;
        		}
        	} else {
        		if(!expr.assignableTo(term.getElemType())) {
        			errorDetected=true;

        			report_error("GRESKA expr i niz term nisu kompatibilni!", termExpr);
        			return;
        		} 
        	}
    		
    	} else {
    		if(expr.getKind()==Struct.Array) {
    			if(elemniza==false) {
        			errorDetected=true;
            		report_error("GRESKA prilikom addop! Term i expr moraju biti element niza, a ne niz!", termExpr);
            		return;
        		}
        		if(!expr.getElemType().assignableTo(term)) {
        			errorDetected=true;

        			report_error("GRESKA expr niz i term nisu kompatibilni!", termExpr);
        			return;
        		}
        	} else {
        		if(!expr.assignableTo(term)) {
        			errorDetected=true;

        			report_error("GRESKA expr i term nisu kompatibilni!", termExpr);
        			return;
        		} 
        	}
    	}
    	if(expr.getKind()==Struct.Array) {
    		if(!expr.getElemType().assignableTo(TabIv.intType)) {
    			errorDetected=true;

    			report_error("GRESKA expr "+ expr + " nije int!", termExpr);
    			return;
    		} else {
    			termExpr.struct=expr;
    		}
    	} else {
    		if(expr.getKind()!=Struct.Int) {
    			errorDetected=true;

    			report_error("GRESKA expr "+ expr + " nije int!", termExpr);
    			return;
    		} else {
    			termExpr.struct=expr;
    		}
    	}
    	//sad da proverim za int 
    	if(term.getKind()==Struct.Array) {
    		if(!term.getElemType().assignableTo(TabIv.intType)) {
    			errorDetected=true;
    			report_error("GRESKA term "+ termExpr.getTerm() + " nije int!", termExpr);
    			return;
    		} else {
    			termExpr.struct= term;
    		}
    	} else {
    		if(term.getKind()!=Struct.Int) {
    			errorDetected=true;
    			report_error("GRESKA term "+ termExpr.getTerm() + " nije int!", termExpr);
    			return;
    		} else {
    			termExpr.struct= term;
    		}
    	}
    	if(expr.getKind()==Struct.Array) {
    		if(!expr.getElemType().assignableTo(TabIv.intType)) {
    			errorDetected=true;
    			
    			report_error("GRESKA expr "+ expr + " nije int!", termExpr);
    			return;
    		} else {
    			termExpr.struct=expr;
    		}
    	} else {
    		if(expr.getKind()!=Struct.Int) {
    			errorDetected=true;
    			
    			report_error("GRESKA expr "+ expr + " nije int!", termExpr);
    			return;
    		} else {
    			termExpr.struct=expr;
    		}
    	}
    	
    	report_info("SVE OK sa addop", termExpr);
    }
    
    public void visit(ExprMinusIv exprminus){
    	Struct termstr= exprminus.getTerm().struct;
    	exprminus.struct=exprminus.getTerm().struct;
    	if(termstr.getKind()!= Struct.Int) {
    		errorDetected=true;
//    		exprminus.struct=TabIv.noType;
    		report_error("GRESKA kod '-' term! Term mora biti tipa INT!", exprminus);
    		return;
    	}
    	if(exprminus.getParent().getClass()==FactorNew.class) {
    		errorDetected=true;
    		report_error("GRESKA kod NEW! Expr ne sme da bude negativan!", exprminus);
    		return;
    	}
    	trenExpr=exprminus.getTerm();
    	
    }
    public void visit(ExprNoMinusIv exprnominus){
    	trenExpr=exprnominus.getTerm();
    	exprnominus.struct=exprnominus.getTerm().struct;
    }
    
    public void visit(DesListExprIv deslistexp) {
    	deslistexp.struct = deslistexp.getExpr().struct;
    	if(elemniza) {
    		elemniza2=true;
    	}
    	elemniza=true;
    }
    public void visit(NoDesListExprIv deslistexp) {
//    	deslistexp.struct=TabIv.noType;
    	elemniza=false;
    	if(elemniza2) {
    		elemniza2=false;
    		elemniza=true;
    	}
    }
    
    public void visit(MatchedPrintIv matchedPrintIv) {
    	Struct exprStr= matchedPrintIv.getExpr().struct;
    	if(exprStr.getKind()==Struct.Array) {
    		if(!exprStr.getElemType().equals(TabIv.intType) && !exprStr.getElemType().assignableTo(TabIv.boolType) 
        			&& !exprStr.getElemType().assignableTo(TabIv.charType)) {
        		errorDetected=true;
        		report_error("GRESKA kod PRINT stmt! Expr mora biti tipa int, char ili bool!", matchedPrintIv);
        		return;
        	}
    	} else {
    		if(!exprStr.equals(TabIv.intType) && !exprStr.assignableTo(TabIv.boolType) 
        			&& !exprStr.assignableTo(TabIv.charType)) {
        		errorDetected=true;
        		report_error("GRESKA kod PRINT stmt! Expr mora biti tipa int, char ili bool!", matchedPrintIv);
        		return;
        	}
    	}
    	report_info("SVE OK sa print", matchedPrintIv);
    	
    }
    public void visit(MatchedPrintNumberIv matchedPrintIv) {
    	Struct exprStr= matchedPrintIv.getExpr().struct;
    	if(exprStr.getKind()==Struct.Array) {
    		if(!exprStr.getElemType().equals(TabIv.intType) && !exprStr.getElemType().assignableTo(TabIv.boolType) 
    				&& !exprStr.getElemType().assignableTo(TabIv.charType)) {
    			errorDetected=true;
    			report_error("GRESKA kod PRINT stmt! Expr mora biti tipa int, char ili bool!", matchedPrintIv);
    			return;
    		}
    	} else {
    		if(!exprStr.equals(TabIv.intType) && !exprStr.assignableTo(TabIv.boolType) 
    				&& !exprStr.assignableTo(TabIv.charType)) {
    			errorDetected=true;
    			report_error("GRESKA kod PRINT stmt! Expr mora biti tipa int, char ili bool!", matchedPrintIv);
    			return;
    		}
    	}
    	report_info("SVE OK sa print", matchedPrintIv);
    	
    }
    public boolean proveriUgradjenTip(Struct type) {
    	if(type.assignableTo(TabIv.boolType) || type.assignableTo(TabIv.charType)
    			|| type.assignableTo(TabIv.intType)) {
    		return true;
    	} else return false;
    }
    
    public void visit(MatchedFindAnyIv matchedFindAnyIv) {
//    	matchedFindAnyIv.struct = matchedFindAnyIv.getExpr().struct;
    	if(declGreska) {
    		declGreska=false;
    		return;
    	}
    	Obj des1= matchedFindAnyIv.getDesignator().obj;
    	Obj des2= matchedFindAnyIv.getDesignator1().obj;
    	
    	if(!des1.getType().assignableTo(TabIv.boolType) || des1.getKind()!=Obj.Var) {
    		errorDetected=true;
    		report_error("GRESKA designator pre = kod .findAny() fje mora da bude promenljiva tipa bool!", matchedFindAnyIv);
    		return;
    	}
    	if(des2.getType().getKind()!=Struct.Array) {
    		errorDetected=true;
    		report_error("GRESKA designator pre .findAny() fje mora da bude niz!", matchedFindAnyIv);
    		return;
    	} else if(des2.getFpPos()==1) {
    		errorDetected=true;
    		report_error("GRESKA designator pre .findAny() fje mora da bude niz, a ne elem niza!", matchedFindAnyIv);
    		return;
    	}
    	
    	if(!proveriUgradjenTip(des2.getType().getElemType())) {
    		errorDetected=true;
    		report_error("GRESKA designator pre .findAny() fje mora da bude tipa char/bool/int!", matchedFindAnyIv);
    		return;
    	}
    	
    	report_info("SVE OK sa findAny", matchedFindAnyIv);
    }
    public void visit(MatchedFindAndReplaceIv matchedFindAndReplaceIv) {
    	if(declGreska) {
    		declGreska=false;
    		return;
    	}
    	String ident = matchedFindAndReplaceIv.getFindAndReplaceIdent();
//    	Struct expr1 = matchedFindAndReplaceIv.getExpr().struct;
//    	Struct expr2 = matchedFindAndReplaceIv.getExpr2().struct;
    	Obj des1= matchedFindAndReplaceIv.getDesignator().obj;
    	Obj des2= matchedFindAndReplaceIv.getDesignator1().obj;
    	
    	
    	if(des1.getType().getKind()!=Struct.Array) {
    		errorDetected=true;
    		report_error("GRESKA designator pre = kod .findAndReplace() fje mora da bude niz!", matchedFindAndReplaceIv);
    		return;
    	} else if(des1.getFpPos()==1) {
    		errorDetected=true;
    		report_error("GRESKA designator pre = kod .findAndReplace() fje mora da bude niz, a ne elem niza!", matchedFindAndReplaceIv);
    		return;
    	}
    	if(des2.getType().getKind()!=Struct.Array) {
    		errorDetected=true;
    		report_error("GRESKA designator pre .findAndReplace() fje mora da bude niz!", matchedFindAndReplaceIv);
    		return;
    	} else if(des2.getFpPos()==1) {
    		errorDetected=true;
    		report_error("GRESKA designator pre .findAndReplace() fje mora da bude niz, a ne elem niza!", matchedFindAndReplaceIv);
    		return;
    	}
    	if(!proveriUgradjenTip(des1.getType().getElemType())) {
    		errorDetected=true;
    		report_error("GRESKA designator pre = kod .findAndReplace() fje mora da bude tipa char/bool/int!", matchedFindAndReplaceIv);
    		return;
    	}
    	if(!proveriUgradjenTip(des2.getType().getElemType())) {
    		errorDetected=true;
    		report_error("GRESKA designator pre .findAndReplace() fje mora da bude tipa char/bool/int!", matchedFindAndReplaceIv);
    		return;
    	}
    	
    	Obj identObj = TabIv.find(ident);
    	if(identObj== TabIv.noObj) {
    		errorDetected=true;
    		report_error("GRESKA kod find and replace: identifikator " + ident +" nije ni globalna promenljiva ni lokalna promenljiva ove metode!", matchedFindAndReplaceIv);
    		return;
    	}
    	if(identObj.getKind()!=Obj.Var) {
    		errorDetected=true;
    		report_error("GRESKA kod find and replace: identifikator " + ident +" mora biti promenljiva!", matchedFindAndReplaceIv);
    		return;
    	}
    	if(!proveriUgradjenTip(identObj.getType())) {
    		errorDetected=true;
    		report_error("GRESKA kod find and replace: identifikator " + ident +" ne sme biti niz!", matchedFindAndReplaceIv);
    		return;
    	}
    	if(!identObj.getType().assignableTo(des2.getType().getElemType())) {
    		errorDetected=true;
    		report_error("GRESKA kod find and replace: identifikator " + ident +" mora biti istog tipa kao niz "+ des2.getName()+"!", matchedFindAndReplaceIv);
    		return;
    	}
    	report_info("SVE OK sa findAndReplace", matchedFindAndReplaceIv);
    	
    }
    
    public void visit(MatchedBreakIv fn) {
    	if(!whileFlag && !foreachFlag) {
    		errorDetected = true;
    		report_error("GRESKA break naredba se naredba se nalazi van foreach i while", fn);
    		return;
    	} 
    	if(whileFlag) {
//    		cnt--;
    		report_info("USPEH Uspesan break u while", fn);
    	} 
    	if(foreachFlag) {
    		
//    		cnt--;
    		report_info("USPEH Uspesan break u foreach", fn);
    	}
    	
    }
    public void visit(MatchedContinueIv fn) {
    	if(!whileFlag && !foreachFlag) {
    		errorDetected = true;
    		report_error("GRESKA continue naredba se nalazi van foreach i while!", fn);
    		return;
    	} 
    	if(whileFlag) {
//    		cnt--;
    		report_info("USPEH Uspesan continue u while", fn);
    	} 
    	if(foreachFlag) {
//    		cnt--;
    		report_info("USPEH Uspesan continue u foreach", fn);
    	}
    }
    
    public void visit(InWhileIv fn) {
    	if(whileFlag) {
    		whileFlag2=true;
    	}
    	whileFlag=true;
    }
    public void visit(OutWhileIv fn) {
    	whileFlag=false;
    	if(whileFlag2) {
    		whileFlag2=false;
    		whileFlag=true;
    	}
    	
    }
    public void visit(InForeachIv fn) {
    	if(foreachFlag) {
    		foreachFla2=true;
    	}
    	foreachFlag=true;
    }
    public void visit(OutForeachIv fn) {
    	foreachFlag=false;
    	if(foreachFla2) {
    		foreachFla2=false;
    		foreachFlag=true;
    	}
    }

    public void visit(MatchedForeachIv fn) {
    	if(declGreska) {
			declGreska=false;
			return;
		}
    	
		String ident = fn.getForeachIdent();
	//	Struct expr1 = matchedFindAndReplaceIv.getExpr().struct;
	//	Struct expr2 = matchedFindAndReplaceIv.getExpr2().struct;
		Obj des1= fn.getDesignator().obj;
		
		if(des1.getType().getKind()!=Struct.Array) {
			errorDetected=true;
			report_error("GRESKA designator pre .foreach() fje mora da bude niz!", fn);
			return;
		} else if(des1.getFpPos()==1) {
			errorDetected=true;
			report_error("GRESKA designator pre .foreach() fje mora da bude niz, a ne elem niza!", fn);
			return;
		}
		if(!proveriUgradjenTip(des1.getType().getElemType())) {
			errorDetected=true;
			report_error("GRESKA designator pre .foreach() fje mora da bude tipa char/bool/int!", fn);
			return;
		}
		
		Obj identObj = TabIv.find(ident);
		if(identObj== TabIv.noObj) {
			errorDetected=true;
			report_error("GRESKA kod foreach: identifikator " + ident +" nije ni globalna promenljiva ni lokalna promenljiva ove metode!", fn);
			return;
		}
		if(identObj.getKind()!=Obj.Var) {
			errorDetected=true;
			report_error("GRESKA kod foreach: identifikator " + ident +" mora biti promenljiva!", fn);
			return;
		}
		if(!proveriUgradjenTip(identObj.getType())) {
			errorDetected=true;
			report_error("GRESKA kod foreach: identifikator " + ident +" ne sme biti niz!", fn);
			return;
		}
		if(!identObj.getType().assignableTo(des1.getType().getElemType())) {
			errorDetected=true;
			report_error("GRESKA kod foreach: identifikator " + ident +" mora biti istog tipa kao niz "+ des1.getName()+"!", fn);
			return;
		}
		report_info("SVE OK sa foreach", fn);
    }
    
    public void visit(DesignaLActRIv desactl) { //poziv fje
    	if(declGreska) {
			declGreska=false;
			return;
		}
    	Obj func = desactl.getDesignator().obj;
    	
    	if(Obj.Meth == func.getKind()){
    		metodaKojaSePoziva=func;
			report_info("Pronadjen poziv funkcije " + func.getName() , desactl);
			
    	}else{
    		errorDetected=true;
			report_error("GRESKA: ime " + func.getName() + " nije funkcija!", desactl);
			return;
    	}
    	
    }
    public void visit(KrajPozivaIv kp) {
    	if(metodaKojaSePoziva!=null){
    		ArrayList<Obj> pomArray= new ArrayList<>();
    		pomArray=metode.get(metodaKojaSePoziva.getName());
    		
    		if(metodaKojaSePoziva.getName().equals("chr")) {
//    			Obj chrObj= TabIv.find("chr");
    			if(metodaKojaSePoziva.getFpPos()!=1) {
    				errorDetected=true;
    				report_error("GRESKA Metoda CHR mora da ima tacno jedan stvarni parametar prilikom poziva!", kp);
    				return;
    			} else {
    				if(!stvarniParametri.get(0).assignableTo(TabIv.intType)) {
    					errorDetected=true;
    					report_info("!!!!!!!!!!!!!!!! chr tip: "+ stvarniParametri.get(0).getKind(), kp);
        				report_error("GRESKA stvarni parametar prilikom poziva metode CHR mora da bude tipa int!", kp);
        				return;
    				}
    			}
    		} else if(metodaKojaSePoziva.getName().equals("ord")) {
//    			Obj ordObj= TabIv.find("ord");
    			if(metodaKojaSePoziva.getFpPos()!=1) {
    				errorDetected=true;
    				report_error("GRESKA Metoda ORD mora da ima tacno jedan stvarni parametar prilikom poziva!", kp);
    				return;
    			} else {
    				if(!stvarniParametri.get(0).assignableTo(TabIv.charType)) {
    					errorDetected=true;
        				report_error("GRESKA stvarni parametar prilikom poziva metode ORD mora da bude tipa char!", kp);
        				return;
    				}
    			}
    		} else if(metodaKojaSePoziva.getName().equals("len")) {
    			if(metodaKojaSePoziva.getFpPos()!=1) {
    				errorDetected=true;
    				report_error("GRESKA Metoda LEN mora da ima tacno jedan stvarni parametar prilikom poziva!", kp);
    				return;
    			} else {
    				if(!daLiJeElemNiza.isEmpty()) {
    					errorDetected=true;
        				report_error("GRESKA stvarni parametar prilikom poziva metode LEN ne sme da bude element niza nego niz!", kp);
        				return;
    				}
    				if(!(stvarniParametri.get(0).getKind()==Struct.Array && (stvarniParametri.get(0).getElemType().getKind()==Struct.Int || stvarniParametri.get(0).getElemType().getKind()==Struct.Char))) {
    					errorDetected=true;
        				report_error("GRESKA stvarni parametar prilikom poziva metode LEN mora biti niz ili znakovni niz!", kp);
        				return;
    				}
    			}
    		} else if(metodaKojaSePoziva.getName().equals("main")) {
    			if(metodaKojaSePoziva.getFpPos()!=0) {
    				errorDetected=true;
    				report_error("GRESKA Metoda MAIN ne sme da ima parametre!", kp);
    				return;
    			}
    		}
    		
    		else {
    			if(metodaKojaSePoziva.getLevel()!=metodaKojaSePoziva.getFpPos()) {
    				errorDetected=true;
    				report_error("GRESKA Metoda "+metodaKojaSePoziva.getName()+" ima "+ metodaKojaSePoziva.getLevel()+ " formalna parametara, a prosledjeno joj je "+ metodaKojaSePoziva.getFpPos()+ " stvarna parametra!", kp);
    				metodaKojaSePoziva.setFpPos(0);
    				metodaKojaSePoziva=null;
    	        	metodaKojaSePozivaCnt=0;
    	        	stvarniParametri.clear();
    				return;
        		}
    			for(int i =0; i<metodaKojaSePoziva.getFpPos(); i++) {
        			if(pomArray.get(i).getType().getKind()==3) {
        				if(stvarniParametri.get(i).getKind()!=3) {
        					errorDetected=true;
        					report_error("GRESKA kod poziva fje "+metodaKojaSePoziva.getName()+"! "+(i+1)+". parametar treba da bude niz, ali stvarni parametar nije niz.", kp);
        					return;
        				} else {
        					if(pomArray.get(i).getType().getElemType().getKind()!=stvarniParametri.get(i).getElemType().getKind()) {
        						errorDetected=true;
            					report_error("GRESKA kod poziva fje "+metodaKojaSePoziva.getName()+"! "+(i+1)+". formalni parametar (niz) nije istog tipa kao stvarni.", kp);
            					return;
        					}
        				}
        			} else {
        				if(stvarniParametri.get(i).getKind()==3) {
        					boolean hehe=false;
        					for(int j=0; j<daLiJeElemNiza.size();j++) {
        						if(daLiJeElemNiza.get(j)==i) {
        							hehe=true;
        						}
        					}
        					if(!hehe) {
        						errorDetected=true;
            					report_error("GRESKA kod poziva fje "+metodaKojaSePoziva.getName()+"! "+(i+1)+". parametar ne treba da bude niz, a jeste niz.", kp);
            					return;
        					} 
        					else {
        						if(!stvarniParametri.get(i).getElemType().assignableTo(pomArray.get(i).getType())){
        							errorDetected=true;
        	    					report_error("GRESKA kod poziva fje "+metodaKojaSePoziva.getName()+"! "+(i+1)+". formalni parametar nije istog tipa kao stvarni (element niza).", kp);
        	    					return;
        						}
        					}
        					
        				} else if(!pomArray.get(i).getType().assignableTo(stvarniParametri.get(i))) {
        					
        					errorDetected=true;
        					report_error("GRESKA kod poziva fje "+metodaKojaSePoziva.getName()+"! "+(i+1)+". formalni parametar nije istog tipa kao stvarni.", kp);
        					return;
        				}
        			}
        		}
    			
    		}
    		
    		report_info("USPEH Svi parametri metode "+metodaKojaSePoziva.getName()+ " su ispravno prosledjeni!", kp);
    		
			metodaKojaSePoziva.setFpPos(0);
			
        	metodaKojaSePoziva=null;
        	metodaKojaSePozivaCnt=0;
//        	metodaJe=false;
        	
        	stvarniParametri.clear();
        	daLiJeElemNiza.clear();
    	} 
//    	else if(metodaKojaSePoziva!=null && !metodaJe) {
//    		metodaJe=true;
//    	}

    }
    
    public void visit(NoDesignActIv nda) {
    	if(metodaKojaSePoziva!=null) {
    		if(metodaKojaSePoziva.getLevel()!=0) {
    			errorDetected=true;
    			report_error("GRESKA Metoda koja se poziva: "+metodaKojaSePoziva.getName()+ " ima "+ metodaKojaSePoziva.getLevel()+ " formalna parametara, a prosledjeno je 0.", nda);
    			return;
    		}
    	}
    }
    public void visit(NoFactorAct nda) {
//	    	report_info("ovde??", nda);
    	
    	if(metodaKojaSePoziva!=null) {
    		if(metodaKojaSePoziva.getLevel()!=0) {
    			errorDetected=true;
    			report_error("GRESKA Metoda koja se poziva: "+metodaKojaSePoziva.getName()+ " ima "+ metodaKojaSePoziva.getLevel()+ " formalna parametara, a prosledjeno je 0.", nda);
    			return;
    		}
    	}
    }
    public void visit(DesignActIv nda) {
    	if(metodaKojaSePoziva!=null) {
    		if(metodaKojaSePoziva.getLevel()==0) {
    			errorDetected=true;
    			report_error("GRESKA Metoda koja se poziva: "+metodaKojaSePoziva.getName()+ " ima "+ metodaKojaSePoziva.getLevel()+ " formalnih parametara, a prosledjeno je vise.", nda);
    			return;
    		}
    	}
    }
    public void visit(FactorAct nda) {
//    	report_info("ovde?????", nda);

    	if(metodaKojaSePoziva!=null) {
    		if(metodaKojaSePoziva.getLevel()==0) {
    			errorDetected=true;
    			report_error("GRESKA Metoda koja se poziva: "+metodaKojaSePoziva.getName()+ " ima "+ metodaKojaSePoziva.getLevel()+ " formalnih parametara, a prosledjeno je vise.", nda);
    			return;
    		}
    	}
    }
//    public void visit(NoFactorList nda) {
//    	
//    	if(metodaKojaSePoziva!=null && metodaJe) {
//			errorDetected=true;
//			report_error("GRESKA neispravan poziv metode "+ metodaKojaSePoziva.getName(), nda);
//			return;
//    	}
//    }
//    public void visit(FactorList nda) {
//    	if(metodaKojaSePoziva==null) {
//    		errorDetected=true;
//    		report_error("GRESKA nemetodu pozivatde kao metodu", nda);
//    		return;
//    	}
//    	
//    	if(Obj.Meth == metodaKojaSePoziva.getKind()){
//			report_info("Pronadjen poziv funkcije " + metodaKojaSePoziva.getName() , nda);
//
//    	}else{
//    		errorDetected=true;
//			report_error("GRESKA: ime " + metodaKojaSePoziva.getName() + " nije funkcija!", nda);
//			return;
//    	}
//    }
    
    public void visit(EqualEqualIv e) {
    	proveraRelop=false;
    }
    public void visit(NotEqualIv e) {
    	proveraRelop=false;
    }
    public void visit(GreaterIv e) {
    	proveraRelop=true;
    }
    public void visit(GreateEqualIv e) {
    	proveraRelop=true;
    }
    public void visit(LessIv e) {
    	proveraRelop=true;
    }
    public void visit(LessEqualIv e) {
    	proveraRelop=true;
    }
    
    public boolean passed(){
    	return !errorDetected;
    }
    
}
