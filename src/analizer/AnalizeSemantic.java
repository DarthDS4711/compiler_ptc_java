package analizer;

import Classes.*;
import analizer.semantic.*;
import java.util.*;

public class AnalizeSemantic {

    private List<Variable> listVariables;
    private List<Function> listFunctions;
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
        errors += this.detectErrorsInFunctions();
        errors += this.detectErrorsInBlocks();
        errors += this.detectErrorsInCalledFunction();
        errors += this.detectUnusedFunctions();
        errors += this.detectUnusedvariables();
        return errors;
    }

    private void assingFunction() {
        Function function = new Function();
        function.setListPreviusVariables(this.listVariables);
        function.setListFunctions(this.listFunctions);
        function.setLines(this.auxInLines);
        this.listFunctions.add(function);
        this.auxInLines = new ArrayList<>();
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
                                    this.listVariables.get(resultPrev).setUsed(true);
                                    Variable v = this.listVariables.get(resultPrev);
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
        code.setListFunctions(listFunctions);
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
        if (a.getFunctionCalled() == null) {
            if (a.getV() != null) {
                this.listVariables.add(a.getV());
                //a.addVariableToCompare(a.getV());
            }
            error += this.detectErrorsUndeclaredVariables(a);
        } 
        else if (a.getFunctionCalled() != null) {
            this.listAssignsFunctions.add(a);
        }
        return error;
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
                    errors += "Error tipo 1::= La variable: " + nameVariable + " no ha sido declarada\n";
                } else {
                    Variable variable = this.listVariables.get(result);
                    variable.setUsed(true);
                    a.addVariableToCompare(variable);
                }
            }

        }
        errors += a.detectErrorTypeAssign();
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

    public String detectUnusedvariables() {
        String warning = "";
        for (Variable v : this.listVariables) {
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

}
