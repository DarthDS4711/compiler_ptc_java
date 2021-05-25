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
    private List<Assign> listAssignsFunctions;
    private List<Function> listFunctions;
    private List<TokenInLine> lines;
    private String nameFunction;
    private List<TokenInLine> auxInLines;
    private boolean changueContextToBlock = false;//bandera de cambio de contexto global if, while etc
    private boolean usedFunction = false;
    private int numberParams;

    public Function() {
        this.listBlockCodes = new ArrayList<>();
        this.listVariablesBlock = new ArrayList<>();
        this.listPreviusVariables = new ArrayList<>();
        this.lines = new ArrayList<>();
        this.listAssigns = new ArrayList<>();
        this.listFunctions = new ArrayList<>();
        this.listAssignsFunctions = new ArrayList<>();
        this.auxInLines = new ArrayList<>();
        this.nameFunction = " ";
        this.numberParams = 0;
    }

    public boolean isUsedFunction() {
        return usedFunction;
    }

    public void setUsedFunction(boolean usedFunction) {
        this.usedFunction = usedFunction;
    }

    public void setListFunctions(List<Function> listFunctions) {
        this.listFunctions = listFunctions;
    }

    public void setListPreviusVariables(List<Variable> listPreviusVariables) {
        this.listPreviusVariables = listPreviusVariables;
    }

    public void setLines(List<TokenInLine> lines) {
        this.lines = lines;
    }

    private void setNameFunction() {
        TokenInLine line = this.lines.get(0);
        for (int i = 0; i < line.size(); i++) {
            Token t = line.getToken(i);
            System.out.println("Lexeme: " + t.getLexeme());
            if (t.getTokenResult().equals("Token identificador")) {
                this.nameFunction = t.getLexeme();
                break;
            }
        }
    }

    private void setParamsFunction() {
        TokenInLine line = this.lines.get(0);
        String nameVariable = " ", typeVariable = " ";
        for (int index = 0; index < line.size(); index++) {
            Token t = line.getToken(index);
            if (t.getTokenResult().equals("T_Dato") && typeVariable.equals(" ")) {
                typeVariable = t.getLexeme();
            }
            if (t.getTokenResult().equals("Token identificador") && !typeVariable.equals(" ")) {
                nameVariable = t.getLexeme();
                Variable variable = new Variable(nameVariable, typeVariable, false, "");
                this.listVariablesBlock.add(variable);
                this.numberParams++;
                typeVariable = " ";
            }
        }
    }

    public String detectErrorsInBlockFunction() {
        String errors = "";
        this.setParamsFunction();
        this.setNameFunction();
        for (int index = 1; index < this.lines.size(); index++) {
            TokenInLine line = this.lines.get(index);
            if (this.detectBlockInFunction(line) && !this.changueContextToBlock) {
                this.changueContextToBlock = true;
                this.auxInLines.add(line);
            } else if (this.changueContextToBlock) {
                this.auxInLines.add(line);
                this.changueContextToBlock = this.detectEndBlockFunction(line);
                if (!this.changueContextToBlock) {
                    this.assignBlockInFunction();
                }
            } else {
                errors += this.detectErrorsInAssignGlobalFunction(line);
            }
        }
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
        code.setListFunctions(this.listFunctions);
        code.setListPreviusVariables(variables);
        code.setLines(this.auxInLines);
        this.listBlockCodes.add(code);
    }

    private String detectErrorsInAssignGlobalFunction(TokenInLine line) {
        Assign a = new Assign();
        a.detectAsign(line);
        String error = "";
        if (a.getFunctionCalled() == null) {
            if (a.getV() != null) {
                this.listVariablesBlock.add(a.getV());
            }
            error += this.detectErrorsUndeclaredVariables(a);
        } else if (a.getFunctionCalled() != null) {
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
                int result = this.detectErrorsUndeclaredVariableInFunction(nameVariable);
                if (result == -1) {
                    result = this.detectErrorsUndeclaredVariableGlobalFunction(nameVariable);
                    if (result == -1) {
                        errors += "\tFunction:= La variable: " + nameVariable + " no ha sido declarada\n";
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
        for (BlockCode code : this.listBlockCodes) {
            errors += code.detectErrorsInBlock();
            errors += code.detectUnusedvariables();
        }
        return errors;
    }

    public String getNameFunction() {
        return nameFunction;
    }

    public int getNumberParams() {
        return numberParams;
    }

    public boolean detectSameTypeVariableParams(Variable v, int subIndex) {
        Variable variable1 = this.listVariablesBlock.get(subIndex);
        String typeVariable1 = variable1.getTypeVariable();
        String typeVariable2 = v.getTypeVariable();
        return typeVariable1.equals(typeVariable2);
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
