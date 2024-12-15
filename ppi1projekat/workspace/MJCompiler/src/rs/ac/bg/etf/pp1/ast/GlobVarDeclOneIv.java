// generated with ast extension for cup
// version 0.8
// 24/7/2023 2:19:40


package rs.ac.bg.etf.pp1.ast;

public class GlobVarDeclOneIv extends GlobVarDecl {

    private GlobVarDeclOne GlobVarDeclOne;

    public GlobVarDeclOneIv (GlobVarDeclOne GlobVarDeclOne) {
        this.GlobVarDeclOne=GlobVarDeclOne;
        if(GlobVarDeclOne!=null) GlobVarDeclOne.setParent(this);
    }

    public GlobVarDeclOne getGlobVarDeclOne() {
        return GlobVarDeclOne;
    }

    public void setGlobVarDeclOne(GlobVarDeclOne GlobVarDeclOne) {
        this.GlobVarDeclOne=GlobVarDeclOne;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(GlobVarDeclOne!=null) GlobVarDeclOne.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(GlobVarDeclOne!=null) GlobVarDeclOne.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(GlobVarDeclOne!=null) GlobVarDeclOne.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobVarDeclOneIv(\n");

        if(GlobVarDeclOne!=null)
            buffer.append(GlobVarDeclOne.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobVarDeclOneIv]");
        return buffer.toString();
    }
}
