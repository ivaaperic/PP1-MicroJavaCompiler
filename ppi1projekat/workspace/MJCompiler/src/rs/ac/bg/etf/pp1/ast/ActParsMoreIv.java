// generated with ast extension for cup
// version 0.8
// 24/7/2023 2:19:40


package rs.ac.bg.etf.pp1.ast;

public class ActParsMoreIv extends ActPars {

    private ActPars ActPars;
    private ActOne ActOne;

    public ActParsMoreIv (ActPars ActPars, ActOne ActOne) {
        this.ActPars=ActPars;
        if(ActPars!=null) ActPars.setParent(this);
        this.ActOne=ActOne;
        if(ActOne!=null) ActOne.setParent(this);
    }

    public ActPars getActPars() {
        return ActPars;
    }

    public void setActPars(ActPars ActPars) {
        this.ActPars=ActPars;
    }

    public ActOne getActOne() {
        return ActOne;
    }

    public void setActOne(ActOne ActOne) {
        this.ActOne=ActOne;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActPars!=null) ActPars.accept(visitor);
        if(ActOne!=null) ActOne.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActPars!=null) ActPars.traverseTopDown(visitor);
        if(ActOne!=null) ActOne.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActPars!=null) ActPars.traverseBottomUp(visitor);
        if(ActOne!=null) ActOne.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActParsMoreIv(\n");

        if(ActPars!=null)
            buffer.append(ActPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActOne!=null)
            buffer.append(ActOne.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActParsMoreIv]");
        return buffer.toString();
    }
}
