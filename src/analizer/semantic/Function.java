package analizer.semantic;

public class Function {
    private boolean used;
    private int numberParams;
    private String nameVariable;

    public Function(int numberParams, String nameVariable) {
        this.numberParams = numberParams;
        this.nameVariable = nameVariable;
    }
    
    
    public Function() {
        this.used = false;
    }
    
    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getNumberParams() {
        return numberParams;
    }

    public void setNumberParams(int numberParams) {
        this.numberParams = numberParams;
    }

    public String getNameVariable() {
        return nameVariable;
    }

    public void setNameVariable(String nameVariable) {
        this.nameVariable = nameVariable;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Function{used=").append(used);
        sb.append(", numberParams=").append(numberParams);
        sb.append(", nameVariable=").append(nameVariable);
        sb.append('}');
        return sb.toString();
    }
    
}
