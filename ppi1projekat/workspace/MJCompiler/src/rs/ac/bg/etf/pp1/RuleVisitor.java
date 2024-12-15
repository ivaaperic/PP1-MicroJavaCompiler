package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;

public class RuleVisitor extends VisitorAdaptor{

	int printCallCount = 0;
	int varDeclCount = 0;
	int formparams=0;
	
	Logger log = Logger.getLogger(getClass());

	public void visit(GlobVarDeclMoreIv GlobVarDeclMoreIv){
		varDeclCount++;
	}
	
	public void visit(GlobVarDeclOneIv GlobVarDeclOneIv){
		varDeclCount++;
	}
	
    public void visit(MatchedPrintIv MatchedPrintIv) {
		printCallCount++;
		log.info("prepoznata naredba print");
	}
    
    public void visit(FormParsListIv FormParsListIv) {
    	formparams++;
    }
    public void visit(FormParamOneLParenIv FormParamOneLParenIv) {
    	formparams++;
    }
}
