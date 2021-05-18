package Classes;
import java.util.*;
public class TableSymbols {
    private List<TokenInLine> tableTokens;

    public TableSymbols() {
        this.tableTokens = new ArrayList<>();
    }
    
    public void addLineInTable(TokenInLine line){
        this.tableTokens.add(line);
    }
    
    public void printAllTable(){
        for(TokenInLine ln : this.tableTokens){
            ln.toStringArray();
        }
    }
    
    public TokenInLine getLineTable(int index){
        return this.tableTokens.get(index);
    }
    
    public int size(){
        return this.tableTokens.size();
    }
}
