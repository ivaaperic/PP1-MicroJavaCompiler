// generated with ast extension for cup
// version 0.8
// 24/7/2023 2:19:40


package rs.ac.bg.etf.pp1.ast;

public class MatchedForeachIv extends Matched {

    private Designator Designator;
    private String foreachIdent;
    private InForeach InForeach;
    private Statement Statement;
    private OutForeach OutForeach;

    public MatchedForeachIv (Designator Designator, String foreachIdent, InForeach InForeach, Statement Statement, OutForeach OutForeach) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.foreachIdent=foreachIdent;
        this.InForeach=InForeach;
        if(InForeach!=null) InForeach.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.OutForeach=OutForeach;
        if(OutForeach!=null) OutForeach.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public String getForeachIdent() {
        return foreachIdent;
    }

    public void setForeachIdent(String foreachIdent) {
        this.foreachIdent=foreachIdent;
    }

    public InForeach getInForeach() {
        return InForeach;
    }

    public void setInForeach(InForeach InForeach) {
        this.InForeach=InForeach;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public OutForeach getOutForeach() {
        return OutForeach;
    }

    public void setOutForeach(OutForeach OutForeach) {
        this.OutForeach=OutForeach;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(InForeach!=null) InForeach.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(OutForeach!=null) OutForeach.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(InForeach!=null) InForeach.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(OutForeach!=null) OutForeach.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(InForeach!=null) InForeach.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(OutForeach!=null) OutForeach.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MatchedForeachIv(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+foreachIdent);
        buffer.append("\n");

        if(InForeach!=null)
            buffer.append(InForeach.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OutForeach!=null)
            buffer.append(OutForeach.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MatchedForeachIv]");
        return buffer.toString();
    }
}
