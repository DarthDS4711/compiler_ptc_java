package analizer.semantic;
import Classes.*;
import java.util.*;
public class FunctionCalled {
    private String nameFunction;
    private List<String> nameParams;

    public FunctionCalled() {
        this.nameParams = new ArrayList<>();
    }

    public String getNameFunction() {
        return nameFunction;
    }

    public void setNameFunction(String nameFunction) {
        this.nameFunction = nameFunction;
    }
    
    public void setLineParams(TokenInLine line){
        for(int i = 2; i < line.size(); i++){
            Token t = line.getToken(i);
            if(t.getTokenResult().equals("Token identificador")){
                this.nameParams.add(t.getLexeme());
            }
        }
    }

    public List<String> getNameParams() {
        return nameParams;
    }
    
    
    
}
