package analizer;

import Classes.*;
import analizer.semantic.*;
import java.util.*;

public class AnalizeSemantic {

    private List<Variable> listVariables;
    private List<Function> listFunctions;
    private List<Assign> listAssigns;
    private List<BlockCode> listBlockCodes;
    private TableSymbols table;
    private List<TokenInLine> auxInLines;
    private boolean flagVariableContextFunction = false;//bandera de variable dentro de una función
    private boolean flagFunction = false;//bandera de una función
    private boolean changueContextToBlock = false;//bandera de cambio de contexto global if, while etc
    private String contextVariable;

    public AnalizeSemantic(TableSymbols table) {
        this.table = table;
        this.listVariables = new ArrayList<>();
        this.listFunctions = new ArrayList<>();
        this.listAssigns = new ArrayList<>();
        this.listBlockCodes = new ArrayList<>();
        this.auxInLines = new ArrayList<>();
        this.contextVariable = "GLOBAL";
    }

    public String detectAndInicializeTheTableSymbol() {
        String errors = "";
        for (int index = 0; index < this.table.size(); index++) {
            TokenInLine line = this.table.getLineTable(index);
            if(this.detectBlock(line)){
                errors += detectErrorsInBlock(line);
                this.changueContextToBlock = true;
                this.auxInLines.add(line);
            }
            else if(this.changueContextToBlock){
                errors += detectErrorsInBlock(line);
                this.auxInLines.add(line);
                this.changueContextToBlock = this.detectEndBlock(line);
                if(!this.changueContextToBlock){
                    this.assignBlock();
                }
            }
            else{
                errors += this.detectErrorsInAssignGlobal(line);
            }
            
        }
        return errors;
    }
    
    private void assignBlock(){
        BlockCode code = new BlockCode();
        code.setListPreviusVariables(this.listVariables);
        code.setLines(this.auxInLines);
        this.listBlockCodes.add(code);
        //this.auxInLines.clear();
    }
    
    private boolean detectEndBlock(TokenInLine line){
        if(line.size() > 0){
            Token t = line.getToken(0);
            if(t.getLexeme().equals("}")){
                return false;
            }
        }
        return true;
    }
    
    private String detectErrorsInBlock(TokenInLine line){
        
        return "encontre una linea del bloque\n";
    }
    
    public boolean detectBlock(TokenInLine line){
        if(line.size() > 0){
            Token t = line.getToken(0);
            if(t.getLexeme().equals("IF")){
                return true;
            }
        }
        return false;
    }
    
    public void printSizeBlock(){
        for(BlockCode code : this.listBlockCodes){
            code.sizeBlock();
        }
    }

    
    private String detectErrorsInAssignGlobal(TokenInLine line) {
        Assign a = new Assign();
        a.detectAsign(line);
        String error = "";
        this.listAssigns.add(a);
        int i = 0;
        if (a.getV() != null) {
            this.listVariables.add(a.getV());
        } 
        List<String> nameVariables = a.getNameVariables();
        for(i = (a.getV() != null)?1:0; i < nameVariables.size(); i++){
            String nameVariable = nameVariables.get(i);
            int result = this.detectErrorsUndeclaredVariable(nameVariable);
            if(result == -1){
                error += "La variable: " + nameVariable +" no ha sido declarada\n";
            }else{
                this.listVariables.get(result).setUsed(true);
          
            }
        }
        return error;
    }

    public String detectUnusedvariables(){
        String warning = "";
        for(Variable v : this.listVariables){
            System.out.println(v.isUsed());
            if(!v.isUsed()){
                warning += "la variable: " + v.getIdVariable() + " no ha sido utilizada\n";
            }
        }
        return warning;
    }
    
    public String detectErrorsInBlocks(){
        String errors = "";
        for(BlockCode code : this.listBlockCodes){
            errors += code.detectErrorsInBlock();
            errors += code.detectUnusedvariables();
            System.out.println("primer blocque");
        }
        return errors;
    }
    
    private int detectErrorsUndeclaredVariable(String nameVariable) {
        int position = -1;
        for (int i = 0; i < this.listVariables.size(); i++) {
            Variable v = this.listVariables.get(i);
            if (v.getIdVariable().equals(nameVariable)) {
                position = i;
                break;
            }
        }
        return position;
    }

    public void detectErrors() {
        String nameVariable = " ";
        for (Assign a : this.listAssigns) {
            if (a.getV() != null) {
                nameVariable = a.getV().getIdVariable();
            } else {
                nameVariable = a.getNameVariable();
            }
            int result = this.detectErrorsUndeclaredVariable(nameVariable);
            if (result == -1) {
                System.out.println("La variable: " + nameVariable + " no existe");
            } else {
                this.listVariables.get(result).setUsed(true);
            }
        }
    }

    public void printVariablesDetect() {
        for (Assign a : this.listAssigns) {
            a.printInfo();
        }

    }

}
