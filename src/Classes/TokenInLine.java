package Classes;
import java.util.*;
public class TokenInLine {
    private List<Token> listTokensInLine;

    public TokenInLine() {
        this.listTokensInLine = new ArrayList<>();
    }
    
    public void addToken(Token token){
        this.listTokensInLine.add(token);
    }
    
    public void deleteList(){
        this.listTokensInLine.clear();
    } 

    public void toStringArray(){
        for(Token t :this.listTokensInLine){
            System.out.print(t.getTokenResult());
        }
        System.out.println("");
    }
    
    public Token getToken(int index){
        return this.listTokensInLine.get(index);
    }
    
    public int size(){
        return this.listTokensInLine.size();
    }
}
