package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class TabIv extends Tab {

	public static final Struct boolType = new Struct(StructIv.Bool);
		
	public static void init() {
		Tab.init();
		
		currentScope.addToLocals(new Obj(Obj.Type, "bool", boolType));
		
	}
	
	
}