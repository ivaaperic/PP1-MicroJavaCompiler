// generated with ast extension for cup
// version 0.8
// 24/7/2023 2:19:40


package rs.ac.bg.etf.pp1.ast;

public class GlobVarDeclMoreIv extends GlobVarDecl {

    private GlobVarDeclMore GlobVarDeclMore;
    private GlobVarDecl GlobVarDecl;

    public GlobVarDeclMoreIv (GlobVarDeclMore GlobVarDeclMore, GlobVarDecl GlobVarDecl) {
        this.GlobVarDeclMore=GlobVarDeclMore;
        if(GlobVarDeclMore!=null) GlobVarDeclMore.setParent(this);
        this.GlobVarDecl=GlobVarDecl;
        if(GlobVarDecl!=null) GlobVarDecl.setParent(this);
    }

    public GlobVarDeclMore getGlobVarDeclMore() {
        return GlobVarDeclMore;
    }

    public void setGlobVarDeclMore(GlobVarDeclMore GlobVarDeclMore) {
        this.GlobVarDeclMore=GlobVarDeclMore;
    }

    public GlobVarDecl getGlobVarDecl() {
        return GlobVarDecl;
    }

    public void setGlobVarDecl(GlobVarDecl GlobVarDecl) {
        this.GlobVarDecl=GlobVarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(GlobVarDeclMore!=null) GlobVarDeclMore.accept(visitor);
        if(GlobVarDecl!=null) GlobVarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(GlobVarDeclMore!=null) GlobVarDeclMore.traverseTopDown(visitor);
        if(GlobVarDecl!=null) GlobVarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(GlobVarDeclMore!=null) GlobVarDeclMore.traverseBottomUp(visitor);
        if(GlobVarDecl!=null) GlobVarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobVarDeclMoreIv(\n");

        if(GlobVarDeclMore!=null)
            buffer.append(GlobVarDeclMore.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(GlobVarDecl!=null)
            buffer.append(GlobVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobVarDeclMoreIv]");
        return buffer.toString();
    }
}
