// generated with ast extension for cup
// version 0.8
// 24/7/2023 2:19:40


package rs.ac.bg.etf.pp1.ast;

public class ProgramGlobVarIv extends ProgDecl {

    private Type Type;
    private GlobVarDecl GlobVarDecl;

    public ProgramGlobVarIv (Type Type, GlobVarDecl GlobVarDecl) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.GlobVarDecl=GlobVarDecl;
        if(GlobVarDecl!=null) GlobVarDecl.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
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
        if(Type!=null) Type.accept(visitor);
        if(GlobVarDecl!=null) GlobVarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(GlobVarDecl!=null) GlobVarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(GlobVarDecl!=null) GlobVarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ProgramGlobVarIv(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(GlobVarDecl!=null)
            buffer.append(GlobVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ProgramGlobVarIv]");
        return buffer.toString();
    }
}
