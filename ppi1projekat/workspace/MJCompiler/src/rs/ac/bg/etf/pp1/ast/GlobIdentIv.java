// generated with ast extension for cup
// version 0.8
// 24/7/2023 2:19:40


package rs.ac.bg.etf.pp1.ast;

public class GlobIdentIv extends GlobIdent {

    private String globVarName;

    public GlobIdentIv (String globVarName) {
        this.globVarName=globVarName;
    }

    public String getGlobVarName() {
        return globVarName;
    }

    public void setGlobVarName(String globVarName) {
        this.globVarName=globVarName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobIdentIv(\n");

        buffer.append(" "+tab+globVarName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobIdentIv]");
        return buffer.toString();
    }
}
