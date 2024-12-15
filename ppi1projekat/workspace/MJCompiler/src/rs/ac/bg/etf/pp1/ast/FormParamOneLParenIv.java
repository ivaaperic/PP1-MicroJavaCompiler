// generated with ast extension for cup
// version 0.8
// 24/7/2023 2:19:40


package rs.ac.bg.etf.pp1.ast;

public class FormParamOneLParenIv extends FormParamOneLParen {

    private FormParam FormParam;
    private SquareBrackets SquareBrackets;

    public FormParamOneLParenIv (FormParam FormParam, SquareBrackets SquareBrackets) {
        this.FormParam=FormParam;
        if(FormParam!=null) FormParam.setParent(this);
        this.SquareBrackets=SquareBrackets;
        if(SquareBrackets!=null) SquareBrackets.setParent(this);
    }

    public FormParam getFormParam() {
        return FormParam;
    }

    public void setFormParam(FormParam FormParam) {
        this.FormParam=FormParam;
    }

    public SquareBrackets getSquareBrackets() {
        return SquareBrackets;
    }

    public void setSquareBrackets(SquareBrackets SquareBrackets) {
        this.SquareBrackets=SquareBrackets;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormParam!=null) FormParam.accept(visitor);
        if(SquareBrackets!=null) SquareBrackets.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormParam!=null) FormParam.traverseTopDown(visitor);
        if(SquareBrackets!=null) SquareBrackets.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormParam!=null) FormParam.traverseBottomUp(visitor);
        if(SquareBrackets!=null) SquareBrackets.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParamOneLParenIv(\n");

        if(FormParam!=null)
            buffer.append(FormParam.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SquareBrackets!=null)
            buffer.append(SquareBrackets.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParamOneLParenIv]");
        return buffer.toString();
    }
}
