// generated with ast extension for cup
// version 0.8
// 24/7/2023 2:19:40


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclIv extends ConstDecl {

    private Type Type;
    private OneConst OneConst;
    private MoreConst MoreConst;

    public ConstDeclIv (Type Type, OneConst OneConst, MoreConst MoreConst) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.OneConst=OneConst;
        if(OneConst!=null) OneConst.setParent(this);
        this.MoreConst=MoreConst;
        if(MoreConst!=null) MoreConst.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public OneConst getOneConst() {
        return OneConst;
    }

    public void setOneConst(OneConst OneConst) {
        this.OneConst=OneConst;
    }

    public MoreConst getMoreConst() {
        return MoreConst;
    }

    public void setMoreConst(MoreConst MoreConst) {
        this.MoreConst=MoreConst;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(OneConst!=null) OneConst.accept(visitor);
        if(MoreConst!=null) MoreConst.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(OneConst!=null) OneConst.traverseTopDown(visitor);
        if(MoreConst!=null) MoreConst.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(OneConst!=null) OneConst.traverseBottomUp(visitor);
        if(MoreConst!=null) MoreConst.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclIv(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OneConst!=null)
            buffer.append(OneConst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MoreConst!=null)
            buffer.append(MoreConst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclIv]");
        return buffer.toString();
    }
}
