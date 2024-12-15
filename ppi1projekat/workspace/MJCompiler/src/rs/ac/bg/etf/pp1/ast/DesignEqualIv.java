// generated with ast extension for cup
// version 0.8
// 24/7/2023 2:19:40


package rs.ac.bg.etf.pp1.ast;

public class DesignEqualIv extends DesignatorStatement {

    private DesignatorEqualStatement DesignatorEqualStatement;

    public DesignEqualIv (DesignatorEqualStatement DesignatorEqualStatement) {
        this.DesignatorEqualStatement=DesignatorEqualStatement;
        if(DesignatorEqualStatement!=null) DesignatorEqualStatement.setParent(this);
    }

    public DesignatorEqualStatement getDesignatorEqualStatement() {
        return DesignatorEqualStatement;
    }

    public void setDesignatorEqualStatement(DesignatorEqualStatement DesignatorEqualStatement) {
        this.DesignatorEqualStatement=DesignatorEqualStatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorEqualStatement!=null) DesignatorEqualStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorEqualStatement!=null) DesignatorEqualStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorEqualStatement!=null) DesignatorEqualStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignEqualIv(\n");

        if(DesignatorEqualStatement!=null)
            buffer.append(DesignatorEqualStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignEqualIv]");
        return buffer.toString();
    }
}
