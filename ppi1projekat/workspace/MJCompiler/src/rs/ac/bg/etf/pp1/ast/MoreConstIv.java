// generated with ast extension for cup
// version 0.8
// 24/7/2023 2:19:40


package rs.ac.bg.etf.pp1.ast;

public class MoreConstIv extends MoreConst {

    private MoreConst MoreConst;
    private OneConst OneConst;

    public MoreConstIv (MoreConst MoreConst, OneConst OneConst) {
        this.MoreConst=MoreConst;
        if(MoreConst!=null) MoreConst.setParent(this);
        this.OneConst=OneConst;
        if(OneConst!=null) OneConst.setParent(this);
    }

    public MoreConst getMoreConst() {
        return MoreConst;
    }

    public void setMoreConst(MoreConst MoreConst) {
        this.MoreConst=MoreConst;
    }

    public OneConst getOneConst() {
        return OneConst;
    }

    public void setOneConst(OneConst OneConst) {
        this.OneConst=OneConst;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MoreConst!=null) MoreConst.accept(visitor);
        if(OneConst!=null) OneConst.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MoreConst!=null) MoreConst.traverseTopDown(visitor);
        if(OneConst!=null) OneConst.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MoreConst!=null) MoreConst.traverseBottomUp(visitor);
        if(OneConst!=null) OneConst.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MoreConstIv(\n");

        if(MoreConst!=null)
            buffer.append(MoreConst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OneConst!=null)
            buffer.append(OneConst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MoreConstIv]");
        return buffer.toString();
    }
}
