package analizer.semantic;

public class Variable {
    private String idVariable;
    private String typeVariable;
    private boolean used;

    public Variable(String idVariable, String typeVariable) {
        this.idVariable = idVariable;
        this.typeVariable = typeVariable;
    }

    @Override
    public String toString() {
        return "Variable{" + "idVariable=" + idVariable + ", typeVariable=" + typeVariable + '}';
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
    
    
    
}
