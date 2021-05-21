package analizer.semantic;

import Classes.*;
import java.util.*;

public class BlockCode {

    private List<Variable> listVariablesBlock;
    private List<Variable> listPreviusVariables;
    private List<BlockCode> listBlockCodes;
    private List<Assign> listAssigns;
    private List<TokenInLine> lines;
    private String nameBlock;
    private List<TokenInLine> auxInLines;
    private boolean changueContextToBlock = false;//bandera de cambio de contexto global if, while etc

    public BlockCode() {
        this.listBlockCodes = new ArrayList<>();
        this.listVariablesBlock = new ArrayList<>();
        this.listPreviusVariables = new ArrayList<>();
        this.lines = new ArrayList<>();
        this.listAssigns = new ArrayList<>();
        this.auxInLines = new ArrayList<>();
        this.nameBlock = " ";
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

    public String detectErrorsInBlock() {
        String errors = "";
        System.out.println("Hola");
        for (int index = 1; index < this.lines.size(); index++) {
            TokenInLine line = this.lines.get(index);
            if(this.detectBlock(line) && !this.changueContextToBlock){
                this.changueContextToBlock = true;
                this.auxInLines.add(line);
                System.out.println("\t\tPrimela linea");
            }
            else if(this.changueContextToBlock){
                this.auxInLines.add(line);
                this.changueContextToBlock = this.detectEndBlock(line);
                if(!this.changueContextToBlock){
                    this.assignBlock();
                    System.out.println("\t\tFin bloque");
                }
            }
            else{
                errors += this.detectErrorsInAssignGlobal(line);
            }
        }
        errors += this.detectErrorsInBlocks();
        return errors;
    }

    public boolean detectBlock(TokenInLine line) {
        if (line.size() > 0) {
            Token t = line.getToken(0);
            if (t.getLexeme().equals("IF")) {
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
        this.listBlockCodes.add(code);
    }

    private String detectErrorsInAssignGlobal(TokenInLine line) {
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
                int result = this.detectErrorsUndeclaredVariable(nameVariable);
                if (result == -1) {
                    result = this.detectErrorsUndeclaredVariableGlobal(nameVariable);
                    if (result == -1) {
                        error += "\t\tLa variable: " + nameVariable + " no ha sido declarada\n";
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
        for(BlockCode code : this.listBlockCodes){
            errors += code.detectErrorsInBlock();
            errors += code.detectUnusedvariables();
        }
        return errors;
    }
}
