package analizer.semantic;

public class Variable implements Comparable<Variable>{
    private String idVariable;
    private String typeVariable;
    private boolean used;
    private String contextVariable;

    public Variable(String idVariable, String typeVariable) {
        this.idVariable = idVariable;
        this.typeVariable = typeVariable;
        this.used = false;
    }

    public Variable(String idVariable, String typeVariable, boolean used, String contextVariable) {
        this.idVariable = idVariable;
        this.typeVariable = typeVariable;
        this.used = used;
        this.contextVariable = contextVariable;
    }

    public String getTypeVariable() {
        return typeVariable;
    }

    public void setTypeVariable(String typeVariable) {
        this.typeVariable = typeVariable;
    }

    public String getContextVariable() {
        return contextVariable;
    }

    public void setContextVariable(String contextVariable) {
        this.contextVariable = contextVariable;
    }
    
    
    public String getIdVariable(){
        return this.idVariable;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Variable{idVariable=").append(idVariable);
        sb.append(", typeVariable=").append(typeVariable);
        sb.append(", used=").append(used);
        sb.append(", contextVariable=").append(contextVariable);
        sb.append('}');
        return sb.toString();
    }
    
    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    @Override
    public int compareTo(Variable o) {
        return this.idVariable.compareToIgnoreCase(o.getIdVariable());
        
    }
    
    
    
}
