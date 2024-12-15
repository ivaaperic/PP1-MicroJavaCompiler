// generated with ast extension for cup
// version 0.8
// 24/7/2023 2:19:40


package rs.ac.bg.etf.pp1.ast;

public class MatchedWhileIv extends Matched {

    private Condition Condition;
    private InWhile InWhile;
    private Matched Matched;
    private OutWhile OutWhile;

    public MatchedWhileIv (Condition Condition, InWhile InWhile, Matched Matched, OutWhile OutWhile) {
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.InWhile=InWhile;
        if(InWhile!=null) InWhile.setParent(this);
        this.Matched=Matched;
        if(Matched!=null) Matched.setParent(this);
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

    public Matched getMatched() {
        return Matched;
    }

    public void setMatched(Matched Matched) {
        this.Matched=Matched;
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
        if(Matched!=null) Matched.accept(visitor);
        if(OutWhile!=null) OutWhile.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(InWhile!=null) InWhile.traverseTopDown(visitor);
        if(Matched!=null) Matched.traverseTopDown(visitor);
        if(OutWhile!=null) OutWhile.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(InWhile!=null) InWhile.traverseBottomUp(visitor);
        if(Matched!=null) Matched.traverseBottomUp(visitor);
        if(OutWhile!=null) OutWhile.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MatchedWhileIv(\n");

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

        if(Matched!=null)
            buffer.append(Matched.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OutWhile!=null)
            buffer.append(OutWhile.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MatchedWhileIv]");
        return buffer.toString();
    }
}
