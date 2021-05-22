package analizer;

import Classes.*;
import analizer.semantic.*;
import java.util.*;

public class AnalizeSemantic {

    private List<Variable> listVariables;
    private List<Function> listFunctions;
    private List<Assign> listAssigns;
    private List<Assign> listAssignsFunctions;
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
        this.listAssignsFunctions = new ArrayList<>();
        this.listBlockCodes = new ArrayList<>();
        this.auxInLines = new ArrayList<>();
        this.contextVariable = "GLOBAL";
    }

    public String detectAndInicializeTheTableSymbol() {
        String errors = "";
        for (int index = 0; index < this.table.size(); index++) {
            TokenInLine line = this.table.getLineTable(index);
            if (this.detectStartFunction(line) && !this.flagFunction) {
                this.flagFunction = true;
                this.auxInLines.add(line);
            } else if (this.flagFunction) {
                this.auxInLines.add(line);
                this.flagFunction = this.detectEndBlock(line);
                if (!this.flagFunction) {
                    this.assingFunction();
                }
            } else if (this.detectBlock(line) && !this.changueContextToBlock) {
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
        errors += this.detectErrorsInFunctions();
        errors += this.detectErrorsInCalledFunction();
        errors += this.detectUnusedFunctions();
        errors += this.detectUnusedvariables();
        return errors;
    }

    private void assingFunction() {
        Function function = new Function();
        function.setListPreviusVariables(this.listVariables);
        function.setLines(this.auxInLines);
        this.listFunctions.add(function);
        this.auxInLines = new ArrayList<>();
    }

    private String detectErrorsInCalledFunction() {
        String error = "";
        System.out.println("tamanio: " + this.listAssignsFunctions.size());
        for (Assign a : this.listAssignsFunctions) {
            String nameFunctionAssign = a.getFunctionCalled().getNameFunction();
            System.out.println("nameFunctionAssign = " + nameFunctionAssign);
            if (!nameFunctionAssign.equals(" ")) {
                int result = this.detectErrorsUndeclaredFunction(nameFunctionAssign);
                if (result != -1) {
                    this.listFunctions.get(result).setUsedFunction(true);
                    List<String> nameVariables = a.getFunctionCalled().getNameParams();
                    for (int index = 0; index < nameVariables.size(); index++) {
                        String nameVariable = nameVariables.get(index);
                        if (!nameVariable.equals(" ")) {
                            int resultPrev = this.detectErrorsUndeclaredVariable(nameVariable);
                            if (resultPrev == -1) {
                                error += "La variable como parametro: " + nameVariable + " no ha sido declarada\n";
                            } else {
                                this.listVariables.get(result).setUsed(true);

                            }
                        }
                    }
                } else {
                    error += "La funcion: " + nameFunctionAssign + " no ha sido declarada\n";
                }
            }
        }
        return error;
    }

    private boolean detectStartFunction(TokenInLine line) {
        if (line.size() > 0) {
            Token t = line.getToken(0);
            if (t.getLexeme().equals("FUN")) {
                return true;
            }
        }
        return false;
    }

    private void assignBlock() {
        BlockCode code = new BlockCode();
        code.setListPreviusVariables(this.listVariables);
        code.setLines(this.auxInLines);
        this.listBlockCodes.add(code);
        this.auxInLines = new ArrayList<>();
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

    public boolean detectBlock(TokenInLine line) {
        if (line.size() > 0) {
            Token t = line.getToken(0);
            if (t.getLexeme().equals("IF") || t.getLexeme().equals("FOR") || t.getLexeme().equals("WHILE")) {
                return true;
            }
        }
        return false;
    }

    private String detectErrorsInAssignGlobal(TokenInLine line) {
        Assign a = new Assign();
        a.detectAsign(line);
        String error = "";
        this.listAssigns.add(a);
        int i = 0;
        if (a.getFunctionCalled() != null) {
            this.listAssignsFunctions.add(a);
        }
        if (a.getV() != null) {
            this.listVariables.add(a.getV());
        }
        if (a.getFunctionCalled() == null) {
            List<String> nameVariables = a.getNameVariables();
            for (i = (a.getV() != null) ? 1 : 0; i < nameVariables.size(); i++) {
                String nameVariable = nameVariables.get(i);
                if (!nameVariable.equals(" ")) {
                    int result = this.detectErrorsUndeclaredVariable(nameVariable);
                    if (result == -1) {
                        error += "La variable: " + nameVariable + " no ha sido declarada\n";
                    } else {
                        this.listVariables.get(result).setUsed(true);

                    }
                }

            }
        }

        return error;
    }

    private int detectErrorsUndeclaredFunction(String nameVariable) {
        System.out.println("Size: " + this.listFunctions.size());
        for (int i = 0; i < this.listFunctions.size(); i++) {
            Function f = this.listFunctions.get(i);
            String nameFun = f.getNameFunction();
            if (nameFun.equals(nameVariable)) {
                return i;
            }
        }
        return -1;
    }

    public String detectUnusedvariables() {
        String warning = "";
        for (Variable v : this.listVariables) {
            //System.out.println(v.isUsed());
            if (!v.isUsed()) {
                warning += "Advertencia ::=  la variable: " + v.getIdVariable() + " no ha sido utilizada\n";
            }
        }
        return warning;
    }
    
    public String detectUnusedFunctions() {
        String warning = "";
        for (Function f : this.listFunctions) {
            if (!f.isUsedFunction()) {
                warning += "Advertencia ::= la funcion: " + f.getNameFunction() + " no ha sido utilizada\n";
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

    public String detectErrorsInFunctions() {
        String errors = "";
        for (Function fun : this.listFunctions) {
            errors += fun.detectErrorsInBlockFunction();
            errors += fun.detectUnusedVariablesInFunction();
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
