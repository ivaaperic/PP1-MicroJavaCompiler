// generated with ast extension for cup
// version 0.8
// 24/7/2023 2:19:40


package rs.ac.bg.etf.pp1.ast;

public class UnmatchedWhileIv extends Unmatched {

    private Condition Condition;
    private InWhile InWhile;
    private Unmatched Unmatched;
    private OutWhile OutWhile;

    public UnmatchedWhileIv (Condition Condition, InWhile InWhile, Unmatched Unmatched, OutWhile OutWhile) {
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.InWhile=InWhile;
        if(InWhile!=null) InWhile.setParent(this);
        this.Unmatched=Unmatched;
        if(Unmatched!=null) Unmatched.setParent(this);
        this.OutWhile=OutWhile;
        if(OutWhile!=null) OutWhile.setParent(this);
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public InWhile getInWhile() {
        return InWhile;
    }

    public void setInWhile(InWhile InWhile) {
        this.InWhile=InWhile;
    }

    public Unmatched getUnmatched() {
        return Unmatched;
    }

    public void setUnmatched(Unmatched Unmatched) {
        this.Unmatched=Unmatched;
    }

    public OutWhile getOutWhile() {
        return OutWhile;
    }

    public void setOutWhile(OutWhile OutWhile) {
        this.OutWhile=OutWhile;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Condition!=null) Condition.accept(visitor);
        if(InWhile!=null) InWhile.accept(visitor);
        if(Unmatched!=null) Unmatched.accept(visitor);
        if(OutWhile!=null) OutWhile.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(InWhile!=null) InWhile.traverseTopDown(visitor);
        if(Unmatched!=null) Unmatched.traverseTopDown(visitor);
        if(OutWhile!=null) OutWhile.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(InWhile!=null) InWhile.traverseBottomUp(visitor);
        if(Unmatched!=null) Unmatched.traverseBottomUp(visitor);
        if(OutWhile!=null) OutWhile.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("UnmatchedWhileIv(\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(InWhile!=null)
            buffer.append(InWhile.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Unmatched!=null)
            buffer.append(Unmatched.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OutWhile!=null)
            buffer.append(OutWhile.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [UnmatchedWhileIv]");
        return buffer.toString();
    }
}
