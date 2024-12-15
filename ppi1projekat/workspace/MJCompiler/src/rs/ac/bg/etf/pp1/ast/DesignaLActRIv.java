// generated with ast extension for cup
// version 0.8
// 24/7/2023 2:19:40


package rs.ac.bg.etf.pp1.ast;

public class DesignaLActRIv extends DesignatorStatement {

    private Designator Designator;
    private DesignAct DesignAct;
    private KrajPozivaMetode KrajPozivaMetode;

    public DesignaLActRIv (Designator Designator, DesignAct DesignAct, KrajPozivaMetode KrajPozivaMetode) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.DesignAct=DesignAct;
        if(DesignAct!=null) DesignAct.setParent(this);
        this.KrajPozivaMetode=KrajPozivaMetode;
        if(KrajPozivaMetode!=null) KrajPozivaMetode.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public DesignAct getDesignAct() {
        return DesignAct;
    }

    public void setDesignAct(DesignAct DesignAct) {
        this.DesignAct=DesignAct;
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
        if(DesignAct!=null) DesignAct.accept(visitor);
        if(KrajPozivaMetode!=null) KrajPozivaMetode.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(DesignAct!=null) DesignAct.traverseTopDown(visitor);
        if(KrajPozivaMetode!=null) KrajPozivaMetode.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(DesignAct!=null) DesignAct.traverseBottomUp(visitor);
        if(KrajPozivaMetode!=null) KrajPozivaMetode.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignaLActRIv(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignAct!=null)
            buffer.append(DesignAct.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(KrajPozivaMetode!=null)
            buffer.append(KrajPozivaMetode.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignaLActRIv]");
        return buffer.toString();
    }
}
