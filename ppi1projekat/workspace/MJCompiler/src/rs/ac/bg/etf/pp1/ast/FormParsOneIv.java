// generated with ast extension for cup
// version 0.8
// 24/7/2023 2:19:40


package rs.ac.bg.etf.pp1.ast;

public class FormParsOneIv extends FormPars {

    private FormParamOneLParen FormParamOneLParen;

    public FormParsOneIv (FormParamOneLParen FormParamOneLParen) {
        this.FormParamOneLParen=FormParamOneLParen;
        if(FormParamOneLParen!=null) FormParamOneLParen.setParent(this);
    }

    public FormParamOneLParen getFormParamOneLParen() {
        return FormParamOneLParen;
    }

    public void setFormParamOneLParen(FormParamOneLParen FormParamOneLParen) {
        this.FormParamOneLParen=FormParamOneLParen;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormParamOneLParen!=null) FormParamOneLParen.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormParamOneLParen!=null) FormParamOneLParen.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormParamOneLParen!=null) FormParamOneLParen.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParsOneIv(\n");

        if(FormParamOneLParen!=null)
            buffer.append(FormParamOneLParen.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParsOneIv]");
        return buffer.toString();
    }
}
