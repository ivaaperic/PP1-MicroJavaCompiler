// generated with ast extension for cup
// version 0.8
// 24/7/2023 2:19:40


package rs.ac.bg.etf.pp1.ast;

public class GlobalVarDeclOneIv extends GlobVarDeclOne {

    private GlobIdent GlobIdent;
    private SquareBrackets SquareBrackets;

    public GlobalVarDeclOneIv (GlobIdent GlobIdent, SquareBrackets SquareBrackets) {
        this.GlobIdent=GlobIdent;
        if(GlobIdent!=null) GlobIdent.setParent(this);
        this.SquareBrackets=SquareBrackets;
        if(SquareBrackets!=null) SquareBrackets.setParent(this);
    }

    public GlobIdent getGlobIdent() {
        return GlobIdent;
    }

    public void setGlobIdent(GlobIdent GlobIdent) {
        this.GlobIdent=GlobIdent;
    }

    public SquareBrackets getSquareBrackets() {
        return SquareBrackets;
    }

    public void setSquareBrackets(SquareBrackets SquareBrackets) {
        this.SquareBrackets=SquareBrackets;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(GlobIdent!=null) GlobIdent.accept(visitor);
        if(SquareBrackets!=null) SquareBrackets.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(GlobIdent!=null) GlobIdent.traverseTopDown(visitor);
        if(SquareBrackets!=null) SquareBrackets.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(GlobIdent!=null) GlobIdent.traverseBottomUp(visitor);
        if(SquareBrackets!=null) SquareBrackets.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobalVarDeclOneIv(\n");

        if(GlobIdent!=null)
            buffer.append(GlobIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SquareBrackets!=null)
            buffer.append(SquareBrackets.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobalVarDeclOneIv]");
        return buffer.toString();
    }
}
