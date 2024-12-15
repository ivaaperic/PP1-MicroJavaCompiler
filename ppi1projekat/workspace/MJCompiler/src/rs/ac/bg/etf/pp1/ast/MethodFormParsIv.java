// generated with ast extension for cup
// version 0.8
// 24/7/2023 2:19:40


package rs.ac.bg.etf.pp1.ast;

public class MethodFormParsIv extends MethodFormPars {

    private FormPars FormPars;
    private KrajMetDec KrajMetDec;

    public MethodFormParsIv (FormPars FormPars, KrajMetDec KrajMetDec) {
        this.FormPars=FormPars;
        if(FormPars!=null) FormPars.setParent(this);
        this.KrajMetDec=KrajMetDec;
        if(KrajMetDec!=null) KrajMetDec.setParent(this);
    }

    public FormPars getFormPars() {
        return FormPars;
    }

    public void setFormPars(FormPars FormPars) {
        this.FormPars=FormPars;
    }

    public KrajMetDec getKrajMetDec() {
        return KrajMetDec;
    }

    public void setKrajMetDec(KrajMetDec KrajMetDec) {
        this.KrajMetDec=KrajMetDec;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormPars!=null) FormPars.accept(visitor);
        if(KrajMetDec!=null) KrajMetDec.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormPars!=null) FormPars.traverseTopDown(visitor);
        if(KrajMetDec!=null) KrajMetDec.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormPars!=null) FormPars.traverseBottomUp(visitor);
        if(KrajMetDec!=null) KrajMetDec.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodFormParsIv(\n");

        if(FormPars!=null)
            buffer.append(FormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(KrajMetDec!=null)
            buffer.append(KrajMetDec.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodFormParsIv]");
        return buffer.toString();
    }
}
