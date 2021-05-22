package analizer.semantic;

import Classes.Token;
import Classes.TokenInLine;
import java.util.ArrayList;
import java.util.List;

public class Function {
    private List<Variable> listVariablesBlock;
    private List<Variable> listPreviusVariables;
    private List<BlockCode> listBlockCodes;
    private List<Assign> listAssigns;
    private List<TokenInLine> lines;
    private String nameFunction;
    private List<TokenInLine> auxInLines;
    private boolean changueContextToBlock = false;//bandera de cambio de contexto global if, while etc
    private boolean usedFunction = false;

    public Function() {
        this.listBlockCodes = new ArrayList<>();
        this.listVariablesBlock = new ArrayList<>();
        this.listPreviusVariables = new ArrayList<>();
        this.lines = new ArrayList<>();
        this.listAssigns = new ArrayList<>();
        this.auxInLines = new ArrayList<>();
        this.nameFunction = " ";
    }

    public boolean isUsedFunction() {
        return usedFunction;
    }

    public void setUsedFunction(boolean usedFunction) {
        this.usedFunction = usedFunction;
    }

    
    
    public void setListPreviusVariables(List<Variable> listPreviusVariables) {
        this.listPreviusVariables = listPreviusVariables;
    }

    public void setLines(List<TokenInLine> lines) {
        this.lines = lines;
    }

 
    
    public void setNameFunction(){
        TokenInLine line = this.lines.get(0);
        for(int i = 0; i < line.size(); i++){
            Token t = line.getToken(i);
            if(t.getTokenResult().equals("Token identificador")){
                this.nameFunction = t.getLexeme();
                //System.out.println("nameFunction = " + nameFunction);
                break;
            }
        }
    }
    
    

    public String detectErrorsInBlockFunction() {
        String errors = "";
        this.setNameFunction();
        for (int index = 1; index < this.lines.size(); index++) {
            TokenInLine line = this.lines.get(index);
            if(this.detectBlockInFunction(line) && !this.changueContextToBlock){
                this.changueContextToBlock = true;
                this.auxInLines.add(line);
            }
            else if(this.changueContextToBlock){
                this.auxInLines.add(line);
                this.changueContextToBlock = this.detectEndBlockFunction(line);
                if(!this.changueContextToBlock){
                    this.assignBlockInFunction();
                }
            }
            else{
                errors += this.detectErrorsInAssignGlobalFunction(line);
            }
        }
        System.out.println("Nombre funcion: " + this.nameFunction);
        errors += this.detectErrorsInBlocksOfFunction();
        return errors;
    }

    public boolean detectBlockInFunction(TokenInLine line) {
        if (line.size() > 0) {
            Token t = line.getToken(0);
            if (t.getLexeme().equals("IF") || t.getLexeme().equals("FOR") || t.getLexeme().equals("WHILE")) {
                return true;
            }
        }
        return false;
    }

    private boolean detectEndBlockFunction(TokenInLine line) {
        if (line.size() > 0) {
            Token t = line.getToken(0);
            if (t.getLexeme().equals("}")) {
                return false;
            }
        }
        return true;
    }

    private void assignBlockInFunction() {
        List<Variable> variables = new ArrayList<>();
        BlockCode code = new BlockCode();
        int sizePreviusVariables = this.listPreviusVariables.size();
        int sizeVariablesInBlock = this.listVariablesBlock.size();
        int size = (sizeVariablesInBlock > sizePreviusVariables) ? sizeVariablesInBlock : sizePreviusVariables;
        for (int i = 0; i < size; i++) {
            if (i < sizeVariablesInBlock) {
                Variable v = this.listVariablesBlock.get(i);
                variables.add(v);
            }
            if (i < sizePreviusVariables) {
                Variable v = this.listPreviusVariables.get(i);
                variables.add(v);
            }
        }
        code.setListPreviusVariables(variables);
        code.setLines(this.auxInLines);
        this.listBlockCodes.add(code);
    }

    private String detectErrorsInAssignGlobalFunction(TokenInLine line) {
        Assign a = new Assign();
        a.detectAsign(line);
        String error = "";
        this.listAssigns.add(a);
        int i = 0;
        if (a.getV() != null) {
            this.listVariablesBlock.add(a.getV());
        }
        List<String> nameVariables = a.getNameVariables();
        for (i = (a.getV() != null) ? 1 : 0; i < nameVariables.size(); i++) {
            String nameVariable = nameVariables.get(i);
            if (!nameVariable.equals(" ")) {
                int result = this.detectErrorsUndeclaredVariableInFunction(nameVariable);
                if (result == -1) {
                    result = this.detectErrorsUndeclaredVariableGlobalFunction(nameVariable);
                    if (result == -1) {
                        error += "\tFunction:= La variable: " + nameVariable + " no ha sido declarada\n";
                    } else {
                        this.listVariablesBlock.get(result).setUsed(true);
                    }
                } else {
                    this.listPreviusVariables.get(result).setUsed(true);

                }
            }
        }
        return error;
    }

    private int detectErrorsUndeclaredVariableInFunction(String nameVariable) {
        int position = -1;
        for (int i = 0; i < this.listPreviusVariables.size(); i++) {
            Variable v = this.listPreviusVariables.get(i);
            if (v.getIdVariable().equals(nameVariable)) {
                position = i;
                break;
            }
        }
        return position;
    }

    private int detectErrorsUndeclaredVariableGlobalFunction(String nameVariable) {
        int position = -1;
        for (int i = 0; i < this.listVariablesBlock.size(); i++) {
            Variable v = this.listVariablesBlock.get(i);
            if (v.getIdVariable().equals(nameVariable)) {
                position = i;
                break;
            }
        }
        return position;
    }

    public String detectUnusedVariablesInFunction() {
        String warning = "";
        for (Variable v : this.listVariablesBlock) {
            System.out.println(v.isUsed());
            if (!v.isUsed()) {
                warning += "\tFunction := la variable: " + v.getIdVariable() + " no ha sido utilizada\n";
            }
        }
        return warning;
    }

    public String detectErrorsInBlocksOfFunction() {
        String errors = "";
        for(BlockCode code : this.listBlockCodes){
            errors += code.detectErrorsInBlock();
            errors += code.detectUnusedvariables();
        }
        return errors;
    }

    public String getNameFunction() {
        return nameFunction;
    }
}
