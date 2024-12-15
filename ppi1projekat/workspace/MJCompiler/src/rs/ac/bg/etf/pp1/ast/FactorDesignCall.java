// generated with ast extension for cup
// version 0.8
// 24/7/2023 2:19:40


package rs.ac.bg.etf.pp1.ast;

public class FactorDesignCall extends Factor {

    private Designator Designator;
    private FactorDesignAct FactorDesignAct;
    private KrajPozivaMetode KrajPozivaMetode;

    public FactorDesignCall (Designator Designator, FactorDesignAct FactorDesignAct, KrajPozivaMetode KrajPozivaMetode) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.FactorDesignAct=FactorDesignAct;
        if(FactorDesignAct!=null) FactorDesignAct.setParent(this);
        this.KrajPozivaMetode=KrajPozivaMetode;
        if(KrajPozivaMetode!=null) KrajPozivaMetode.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public FactorDesignAct getFactorDesignAct() {
        return FactorDesignAct;
    }

    public void setFactorDesignAct(FactorDesignAct FactorDesignAct) {
        this.FactorDesignAct=FactorDesignAct;
    }

    public KrajPozivaMetode getKrajPozivaMetode() {
        return KrajPozivaMetode;
    }

    public void setKrajPozivaMetode(KrajPozivaMetode KrajPozivaMetode) {
        this.KrajPozivaMetode=KrajPozivaMetode;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(FactorDesignAct!=null) FactorDesignAct.accept(visitor);
        if(KrajPozivaMetode!=null) KrajPozivaMetode.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(FactorDesignAct!=null) FactorDesignAct.traverseTopDown(visitor);
        if(KrajPozivaMetode!=null) KrajPozivaMetode.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(FactorDesignAct!=null) FactorDesignAct.traverseBottomUp(visitor);
        if(KrajPozivaMetode!=null) KrajPozivaMetode.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorDesignCall(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FactorDesignAct!=null)
            buffer.append(FactorDesignAct.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(KrajPozivaMetode!=null)
            buffer.append(KrajPozivaMetode.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorDesignCall]");
        return buffer.toString();
    }
}
