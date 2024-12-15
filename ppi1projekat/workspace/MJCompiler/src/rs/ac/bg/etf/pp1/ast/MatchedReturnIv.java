// generated with ast extension for cup
// version 0.8
// 24/7/2023 2:19:40


package rs.ac.bg.etf.pp1.ast;

public class MatchedReturnIv extends Matched {

    private ExprOrEps ExprOrEps;

    public MatchedReturnIv (ExprOrEps ExprOrEps) {
        this.ExprOrEps=ExprOrEps;
        if(ExprOrEps!=null) ExprOrEps.setParent(this);
    }

    public ExprOrEps getExprOrEps() {
        return ExprOrEps;
    }

    public void setExprOrEps(ExprOrEps ExprOrEps) {
        this.ExprOrEps=ExprOrEps;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExprOrEps!=null) ExprOrEps.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExprOrEps!=null) ExprOrEps.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExprOrEps!=null) ExprOrEps.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MatchedReturnIv(\n");

        if(ExprOrEps!=null)
            buffer.append(ExprOrEps.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MatchedReturnIv]");
        return buffer.toString();
    }
}
