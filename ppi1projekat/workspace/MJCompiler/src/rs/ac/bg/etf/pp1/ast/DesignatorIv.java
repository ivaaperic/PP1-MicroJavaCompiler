// generated with ast extension for cup
// version 0.8
// 24/7/2023 2:19:40


package rs.ac.bg.etf.pp1.ast;

public class DesignatorIv extends Designator {

    private String designatorIdent;
    private DesList DesList;

    public DesignatorIv (String designatorIdent, DesList DesList) {
        this.designatorIdent=designatorIdent;
        this.DesList=DesList;
        if(DesList!=null) DesList.setParent(this);
    }

    public String getDesignatorIdent() {
        return designatorIdent;
    }

    public void setDesignatorIdent(String designatorIdent) {
        this.designatorIdent=designatorIdent;
    }

    public DesList getDesList() {
        return DesList;
    }

    public void setDesList(DesList DesList) {
        this.DesList=DesList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesList!=null) DesList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesList!=null) DesList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesList!=null) DesList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorIv(\n");

        buffer.append(" "+tab+designatorIdent);
        buffer.append("\n");

        if(DesList!=null)
            buffer.append(DesList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorIv]");
        return buffer.toString();
    }
}
