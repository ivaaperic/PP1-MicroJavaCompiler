// generated with ast extension for cup
// version 0.8
// 24/7/2023 2:19:40


package rs.ac.bg.etf.pp1.ast;

public class ProgramParIv extends ProgPar {

    private ProgPar ProgPar;
    private ProgDecl ProgDecl;

    public ProgramParIv (ProgPar ProgPar, ProgDecl ProgDecl) {
        this.ProgPar=ProgPar;
        if(ProgPar!=null) ProgPar.setParent(this);
        this.ProgDecl=ProgDecl;
        if(ProgDecl!=null) ProgDecl.setParent(this);
    }

    public ProgPar getProgPar() {
        return ProgPar;
    }

    public void setProgPar(ProgPar ProgPar) {
        this.ProgPar=ProgPar;
    }

    public ProgDecl getProgDecl() {
        return ProgDecl;
    }

    public void setProgDecl(ProgDecl ProgDecl) {
        this.ProgDecl=ProgDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ProgPar!=null) ProgPar.accept(visitor);
        if(ProgDecl!=null) ProgDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ProgPar!=null) ProgPar.traverseTopDown(visitor);
        if(ProgDecl!=null) ProgDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ProgPar!=null) ProgPar.traverseBottomUp(visitor);
        if(ProgDecl!=null) ProgDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ProgramParIv(\n");

        if(ProgPar!=null)
            buffer.append(ProgPar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ProgDecl!=null)
            buffer.append(ProgDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ProgramParIv]");
        return buffer.toString();
    }
}
