// generated with ast extension for cup
// version 0.8
// 24/7/2023 2:19:40


package rs.ac.bg.etf.pp1.ast;

public class NoMethodFormParsIv extends MethodFormPars {

    private KrajMetDec KrajMetDec;

    public NoMethodFormParsIv (KrajMetDec KrajMetDec) {
        this.KrajMetDec=KrajMetDec;
        if(KrajMetDec!=null) KrajMetDec.setParent(this);
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
        if(KrajMetDec!=null) KrajMetDec.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(KrajMetDec!=null) KrajMetDec.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(KrajMetDec!=null) KrajMetDec.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NoMethodFormParsIv(\n");

        if(KrajMetDec!=null)
            buffer.append(KrajMetDec.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NoMethodFormParsIv]");
        return buffer.toString();
    }
}
