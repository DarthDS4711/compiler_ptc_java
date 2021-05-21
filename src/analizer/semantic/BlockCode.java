package analizer.semantic;

import Classes.*;
import java.util.*;

public class BlockCode {

    private List<Variable> listVariablesBlock;
    private List<Variable> listPreviusVariables;
    private List<Assign> listAssigns;
    private List<TokenInLine> lines;
    private String nameBlock;
    private List<TokenInLine> auxInLines;
    private boolean changueContextToBlock = false;//bandera de cambio de contexto global if, while etc

    public BlockCode() {
        this.listVariablesBlock = new ArrayList<>();
        this.listPreviusVariables = new ArrayList<>();
        this.lines = new ArrayList<>();
        this.listAssigns = new ArrayList<>();
        this.nameBlock = " ";
    }

    public void setListPreviusVariables(List<Variable> listPreviusVariables) {
        this.listPreviusVariables = listPreviusVariables;
    }

    public String detectBlock(TokenInLine line) {
        return " ";
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
        for (int index = 1; index < this.lines.size(); index++) {
            TokenInLine line = this.lines.get(index);
            errors += this.detectErrorsInAssignGlobal(line);
        }
        return errors;
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
                        error += "\tLa variable: " + nameVariable + " no ha sido declarada\n";
                    } else {
                        this.listPreviusVariables.get(result).setUsed(true);
                    }
                } else {
                    this.listVariablesBlock.get(result).setUsed(true);

                }
            }
        }
        //System.out.println(error);
        return error;
    }

    private int detectErrorsUndeclaredVariable(String nameVariable) {
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
                warning += "\tla variable: " + v.getIdVariable() + " no ha sido utilizada\n";
            }
        }
        return warning;
    }
}
