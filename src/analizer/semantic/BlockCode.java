package analizer.semantic;

import Classes.*;
import java.util.*;

public class BlockCode {

    private List<Variable> listVariablesBlock;
    private List<Variable> listPreviusVariables;
    private List<BlockCode> listBlockCodes;
    private List<Assign> listAssignsFunctions;
    private List<Assign> listAssigns;
    private List<TokenInLine> lines;
    private String nameBlock;
    private List<Function> listFunctions;
    private List<TokenInLine> auxInLines;
    private boolean changueContextToBlock = false;//bandera de cambio de contexto global if, while etc

    public BlockCode() {
        this.listBlockCodes = new ArrayList<>();
        this.listVariablesBlock = new ArrayList<>();
        this.listPreviusVariables = new ArrayList<>();
        this.listFunctions = new ArrayList<>();
        this.listAssignsFunctions = new ArrayList<>();
        this.lines = new ArrayList<>();
        this.listAssigns = new ArrayList<>();
        this.auxInLines = new ArrayList<>();
        this.nameBlock = " ";
    }

    public void setListFunctions(List<Function> listFunctions) {
        this.listFunctions = listFunctions;
    }

    public void setListPreviusVariables(List<Variable> listPreviusVariables) {
        this.listPreviusVariables = listPreviusVariables;
    }

    public void sizeBlock() {
        System.out.println(lines.size());
    }

    public void setLines(List<TokenInLine> lines) {
        this.lines = lines;
    }

    public void setNameBlock(String nameBlock) {
        this.nameBlock = nameBlock;
    }
    
    private int detectPositionSeparator(TokenInLine line){
        int position = 0;
        for(int index = 0; index < line.size(); index++){
            Token t = line.getToken(index);
            if(t.getLexeme().equals(";")){
                position =  index;
                break;
            }
        }
        return position;
    }
    
    
    private String setTypeBlockFor(TokenInLine line){
        String errString = "";
        int position = this.detectPositionSeparator(line);
        System.out.println("position = " + position);
        TokenInLine line1 = new TokenInLine();
        for(int i = 0; i < position; i++){
            Token t = line.getToken(i);
            line1.addToken(t);
        }
        Assign a = new Assign();
        a.detectAsign(line1);
        System.out.println(a.getV().getIdVariable());
        this.listVariablesBlock.add(a.getV());
        line1 = new TokenInLine();
        for(int i = (position + 1); i < line.size(); i++){
            Token t = line.getToken(i);
            line1.addToken(t);
        }
        a = new Assign();
        a.detectAsign(line1);
        errString += this.detectErrorsUndeclaredVariables(a);
        return errString;
    }

    private String setTypeBlock() {
        String errString = "";
        TokenInLine line = this.lines.get(0);
        Token t = line.getToken(0);
        if (!t.getLexeme().equals("FOR")) {
            Assign a = new Assign();
            a.detectAsign(line);
            this.listAssigns.add(a);
            errString += this.detectErrorsUndeclaredVariables(a);
        }else{
            errString += this.setTypeBlockFor(line);
        }
        return errString;
    }

    public String detectErrorsInBlock() {
        String errors = "";
        errors += this.setTypeBlock();
        for (int index = 1; index < this.lines.size(); index++) {
            TokenInLine line = this.lines.get(index);
            if (this.detectBlock(line) && !this.changueContextToBlock) {
                this.changueContextToBlock = true;
                this.auxInLines.add(line);
            } else if (this.changueContextToBlock) {
                this.auxInLines.add(line);
                this.changueContextToBlock = this.detectEndBlock(line);
                if (!this.changueContextToBlock) {
                    this.assignBlock();
                }
            } else {
                errors += this.detectErrorsInAssignGlobal(line);
            }
        }
        errors += this.detectErrorsInBlocks();
        errors += this.detectErrorsInCalledFunction();
        return errors;
    }

    public boolean detectBlock(TokenInLine line) {
        if (line.size() > 0) {
            Token t = line.getToken(0);
            if (t.getLexeme().equals("IF") || t.getLexeme().equals("FOR") || t.getLexeme().equals("WHILE")) {
                return true;
            }
        }
        return false;
    }

    private boolean detectEndBlock(TokenInLine line) {
        if (line.size() > 0) {
            Token t = line.getToken(0);
            if (t.getLexeme().equals("}")) {
                return false;
            }
        }
        return true;
    }

    private void assignBlock() {
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
        code.setListFunctions(this.listFunctions);
        this.listBlockCodes.add(code);
    }

    private String detectErrorsUndeclaredVariables(Assign a) {
        String errors = "";
        int i = 0;
        List<String> nameVariables = a.getNameVariables();
        for (i = (a.getV() != null) ? 1 : 0; i < nameVariables.size(); i++) {
            String nameVariable = nameVariables.get(i);
            if (!nameVariable.equals(" ")) {
                int result = this.detectErrorsUndeclaredVariable(nameVariable);
                if (result == -1) {
                    result = this.detectErrorsUndeclaredVariableGlobal(nameVariable);
                    if (result == -1) {
                        errors += "\t\tLa variable: " + nameVariable + " no ha sido declarada\n";
                    } else {
                        Variable variable = this.listVariablesBlock.get(result);
                        variable.setUsed(true);
                        a.addVariableToCompare(variable);
                    }
                } else {
                    Variable variable = this.listPreviusVariables.get(result);
                    variable.setUsed(true);
                    a.addVariableToCompare(variable);
                }
            }
            
        }
        errors += a.detectErrorTypeAssign();
        return errors;
    }

    

    private String detectErrorsInAssignGlobal(TokenInLine line) {
        Assign a = new Assign();
        a.detectAsign(line);
        String error = "";
        if (a.getFunctionCalled() == null) {
            if (a.getV() != null) {
                this.listVariablesBlock.add(a.getV());
            }
            error += this.detectErrorsUndeclaredVariables(a);
        } 
        else if (a.getFunctionCalled() != null) {
            this.listAssignsFunctions.add(a);
        }
        return error;
    }

    private int detectErrorsUndeclaredVariable(String nameVariable) {
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

    private int detectErrorsUndeclaredVariableGlobal(String nameVariable) {
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

    public String detectUnusedvariables() {
        String warning = "";
        for (Variable v : this.listVariablesBlock) {
            System.out.println(v.isUsed());
            if (!v.isUsed()) {
                warning += "\t\tla variable: " + v.getIdVariable() + " no ha sido utilizada\n";
            }
        }
        return warning;
    }

    public String detectErrorsInBlocks() {
        String errors = "";
        for (BlockCode code : this.listBlockCodes) {
            errors += code.detectErrorsInBlock();
            errors += code.detectUnusedvariables();
        }
        return errors;
    }

    private int detectErrorsUndeclaredFunction(String nameVariable) {
        for (int i = 0; i < this.listFunctions.size(); i++) {
            Function f = this.listFunctions.get(i);
            String nameFun = f.getNameFunction();
            if (nameFun.equals(nameVariable)) {
                return i;
            }
        }
        return -1;
    }

    private String detectErrorsInCalledFunction() {
        String error = "";
        for (Assign a : this.listAssignsFunctions) {
            String nameFunctionAssign = a.getFunctionCalled().getNameFunction();
            if (!nameFunctionAssign.equals(" ")) {
                int result = this.detectErrorsUndeclaredFunction(nameFunctionAssign);
                if (result != -1) {
                    this.listFunctions.get(result).setUsedFunction(true);
                    Function function = this.listFunctions.get(result);
                    FunctionCalled fCall = a.getFunctionCalled();
                    if (function.getNumberParams() == fCall.getNumberParams()) {
                        List<String> nameVariables = a.getFunctionCalled().getNameParams();
                        int subIndex = 0;
                        for (int index = 0; index < nameVariables.size(); index++) {
                            String nameVariable = nameVariables.get(index);
                            if (!nameVariable.equals(" ")) {
                                int resultPrev = this.detectErrorsUndeclaredVariable(nameVariable);
                                if (resultPrev == -1) {
                                    error += "La variable como parametro: " + nameVariable + " no ha sido declarada\n";
                                } else {
                                    this.listPreviusVariables.get(resultPrev).setUsed(true);
                                    Variable v = this.listPreviusVariables.get(resultPrev);
                                    if (!function.detectSameTypeVariableParams(v, subIndex)) {
                                        error += "Error: el tipo de la variable: " + nameVariable + " no es el correcto\n";
                                        subIndex++;
                                    }
                                }
                            }
                        }
                    } else {
                        error += "Error el numero de parametros no es el correcto\n";
                    }
                } else {
                    error += "La funcion: " + nameFunctionAssign + " no ha sido declarada\n";
                }
            }
        }
        return error;
    }
    
}
