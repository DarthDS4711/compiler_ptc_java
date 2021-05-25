/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizer.semantic;

import Classes.*;
import java.util.*;

/**
 *
 * @author Daniel_Santiago
 */
public class Assign {

    private List<Variable> variablesToCompare;
    private Variable v;
    private String symbolEqual;
    private String valueOfSymbolOperation = " ";
    private List<String> symbolsToAsign;
    private List<String> symbolNumericAssign;
    private String nameVariableAssign;
    private String symbolOperation;
    private FunctionCalled functionCalled;
    private String contextAssign;
    private String typeVariableAssign;
    private String nameFunctionAssign = " ";

    public String getNameVariable() {
        return nameVariableAssign;
    }

    public Assign() {
        this.v = null;
        this.symbolsToAsign = new ArrayList<>();
        this.variablesToCompare = new ArrayList<>();
        this.functionCalled = null;
        this.symbolNumericAssign = new ArrayList<>();
        this.nameVariableAssign = " ";
        this.symbolEqual = " ";
        this.symbolOperation = " ";
        this.contextAssign = " ";
    }

    public void addVariableToCompare(Variable v) {
        this.variablesToCompare.add(v);
    }

    public String getTypeVariableAssign() {
        return typeVariableAssign;
    }

    public void setTypeVariableAssign(String typeVariableAssign) {
        this.typeVariableAssign = typeVariableAssign;
    }

    public String getContextAssign() {
        return contextAssign;
    }

    public void setContextAssign(String contextAssign) {
        this.contextAssign = contextAssign;
    }

    public Variable getV() {
        return v;
    }

    public void setV(Variable v) {
        this.v = v;
    }

    public String getSymbolEqual() {
        return symbolEqual;
    }

    public void setSymbolEqual(String symbolEqual) {
        this.symbolEqual = symbolEqual;
    }

    private boolean isSymbolOperation(String token) {
        switch (token) {
            case "Token suma":
                return true;
            case "Token resta":
                return true;
            case "Token multiplicacion":
                return true;
            case "Token division":
                return true;
            case "Token modulo":
                return true;
            default:
                break;
        }
        return false;
    }

    private boolean isValueOperation(String token) {
        boolean flag = false;
        switch (token) {
            case "Token numero real":
                flag = true;
                break;
            case "Token numero entero":
                flag = true;
                break;
            case "Token valor logico":
                flag = true;
                break;
            default:
                break;
        }
        return flag;
    }

    public void assingFunction(TokenInLine line) {
        this.functionCalled = new FunctionCalled();
        this.functionCalled.setNameFunction(this.nameFunctionAssign);
        this.functionCalled.setLineParams(line);
    }

    public void detectAsign(TokenInLine line) {
        String nameVariable = " ";
        String typeVariable = " ";
        for (int i = 0; i < line.size(); i++) {
            Token t = line.getToken(i);
            String tokenAnalize = t.getTokenResult();
            if (t.getTokenResult().equals("T_Dato")) {
                typeVariable = t.getLexeme();
            } else if (t.getTokenResult().equals("Token identificador") && !typeVariable.equals(" ")) {
                nameVariable = t.getLexeme();
                this.v = new Variable(nameVariable, typeVariable);
                typeVariable = " ";
            } else if (t.getTokenResult().equals("Token igual")) {
                this.symbolEqual = t.getLexeme();
            } else if (t.getTokenResult().equals("Token identificador") && (this.v == null && this.nameVariableAssign.equals(" "))) {
                this.nameVariableAssign = t.getLexeme();
            } else if (t.getTokenResult().equals("Token identificador")) {
                this.symbolsToAsign.add(t.getLexeme());
                System.out.println("variable: " + t.getLexeme());
            } else if (t.getTokenResult().equals("Token parentesis abierto") && !this.nameVariableAssign.equals(" ")) {
                this.nameFunctionAssign = this.nameVariableAssign;
                assingFunction(line);
                this.nameVariableAssign = " ";
                break;
            } else if (this.isValueOperation(tokenAnalize)) {
                this.symbolNumericAssign.add(t.getTokenResult());
            } else if (this.isSymbolOperation(t.getTokenResult()) && !this.symbolEqual.equals(" ")) {
                this.symbolOperation = t.getLexeme();
            } else if (t.getTokenResult().equals("Token relacional") && (!this.symbolEqual.equals(" ") || this.symbolEqual.equals(" "))) {
                this.symbolOperation = t.getTokenResult();
                this.valueOfSymbolOperation = t.getLexeme();
            }
        }
    }

    public List<String> getNameVariables() {
        List<String> nameVariables = new ArrayList<>();
        if (this.v != null) {
            nameVariables.add(this.v.getIdVariable());
        } else {
            nameVariables.add(this.nameVariableAssign);
        }
        for (int i = 0; i < this.symbolsToAsign.size(); i++) {
            String value = this.symbolsToAsign.get(i);
            nameVariables.add(value);
        }
        System.out.println("");
        return nameVariables;
    }

    public FunctionCalled getFunctionCalled() {
        return functionCalled;
    }

    public boolean typeDataAccept(String typeData1, String typeData2) {
        boolean compatible = false;
        switch (typeData1) {
            case "INT":
                if (typeData2.equals("Token numero entero")) {
                    compatible = true;
                }
                break;
            case "FLOAT":
                if (typeData2.equals("Token numero real")) {
                    compatible = true;
                }
                break;
            case "INTB":
                if (typeData2.equals("Token valor logico")) {
                    compatible = true;
                }
                break;
        }
        return compatible;
    }

    private String detectErrorInDeclarationVariable() {
        String errString = "";
        String symbol = "";
        String symbolOne = "";
        switch (this.variablesToCompare.size()) {
            case 0:
                if (this.symbolNumericAssign.size() == 1) {
                    symbol = this.symbolNumericAssign.get(0);
                    if (!this.typeDataAccept(this.v.getTypeVariable(), symbol)) {
                        errString += "Error tipo 2::= El tipo de asignacion/comparacion de : "
                                + this.v.getIdVariable() + " no es compatible\n";
                    }
                } else if (this.symbolNumericAssign.size() == 2) {
                    symbol = this.symbolNumericAssign.get(0);
                    symbolOne = this.symbolNumericAssign.get(1);
                    if (symbol.equals(symbolOne)) {
                        if (!this.typeDataAccept(this.v.getTypeVariable(), symbol)) {
                            errString += "Error tipo 2::= El tipo de asignacion/comparacion de : "
                                    + this.v.getIdVariable() + " no es compatible\n";
                        }
                    } else {
                        errString += "Error tipo 3::= operacion entre tipos no compatible\n";
                    }
                }
                break;
            case 1:
                Variable variable = this.variablesToCompare.get(0);
                if (this.symbolNumericAssign.size() == 1) {
                    symbol = this.symbolNumericAssign.get(0);
                    if (this.typeDataAccept(variable.getTypeVariable(), symbol)) {
                        if (!this.typeDataAccept(this.v.getTypeVariable(), symbol)) {
                            errString += "Error tipo 2::= El tipo de asignacion/comparacion de : "
                                    + this.v.getIdVariable() + " no es compatible\n";
                        }
                    } else {
                        errString += "Error tipo 2::= El tipo de operacion entre tipos no es compatible\n";
                    }
                } else {
                    String typeDataVariable = this.v.getTypeVariable();
                    String typeDataVariable1 = variable.getTypeVariable();
                    if (!typeDataVariable.equals(typeDataVariable1)) {
                        errString += "Error tipo 2::= El tipo de asignacion/comparacion de : "
                                + this.v.getIdVariable() + " no es compatible\n";
                    }
                }
                break;
            case 2:
                Variable variableOne = this.variablesToCompare.get(0);
                Variable variableTwo = this.variablesToCompare.get(1);
                String typeOne = variableOne.getTypeVariable();
                String typeTwo = variableTwo.getTypeVariable();
                if (typeOne.equals(typeTwo)) {
                    String typeThree = this.v.getTypeVariable();
                    if (!typeThree.equals(typeOne)) {
                        errString += "Error tipo 2::= El tipo de asignacion/comparacion de : "
                                + this.v.getIdVariable() + " no es compatible\n";
                    }
                } else {
                    errString += "Error tipo 3::= operacion entre tipos no compatible\n";
                }
                break;
            default:
                break;
        }
        return errString;
    }

    private String detectErrorInAssignSingleVariable() {
        String errString = "";
        if (this.symbolNumericAssign.size() == 1) {
            Variable variable = this.variablesToCompare.get(0);
            String symbol = this.symbolNumericAssign.get(0);
            if (!this.typeDataAccept(variable.getTypeVariable(), symbol)) {
                errString += "Error tipo 2::= El tipo de asignacion/comparacion de : "
                        + variable.getIdVariable() + " no es compatible\n";
            }
        } else if (this.symbolNumericAssign.size() == 2) {
            String symbol = this.symbolNumericAssign.get(0);
            String symbolOne = this.symbolNumericAssign.get(1);
            if (symbol.equals(symbolOne)) {
                Variable variable = this.variablesToCompare.get(0);
                if (!this.typeDataAccept(variable.getTypeVariable(), symbol)) {
                    errString += "Error tipo 2::= El tipo de asignacion/comparacion de : "
                            + variable.getIdVariable() + " no es compatible\n";
                }
            } else {
                errString += "Error tipo 3::= operacion entre tipos no compatible\n";
            }
        }
        return errString;
    }

    private String detectErrorInAssignDoubleVariable() {
        String errString = "";
        Variable variable = this.variablesToCompare.get(0);
        Variable variable1 = this.variablesToCompare.get(1);
        if (this.symbolNumericAssign.size() == 1) {
            String symbol = this.symbolNumericAssign.get(0);
            if (this.typeDataAccept(variable.getTypeVariable(), symbol)) {
                if (!this.typeDataAccept(variable1.getTypeVariable(), symbol)) {
                    errString += "Error tipo 5::= El tipo de asignacion/comparacion de: "
                            + variable1.getIdVariable() + " no es compatible\n";
                }
            } else {
                errString += "Error tipo 4::= El tipo de asignacion/comparacion de: "
                        + variable.getIdVariable() + " no es compatible\n";
            }
        } else {
            String typeOne = variable.getTypeVariable();
            String typeTwo = variable1.getTypeVariable();
            if (!typeOne.equals(typeTwo)) {
                errString += "Error tipo 2::= El tipo de asignacion/comparacion de: "
                        + variable.getIdVariable() + " no es compatible\n";
            }
        }
        return errString;
    }

    private String detectErrorsInAssignTripleVariable() {
        String errString = "";
        Variable variableOne = this.variablesToCompare.get(0);
        Variable variableTwo = this.variablesToCompare.get(1);
        Variable variableThree = this.variablesToCompare.get(2);
        String typeOne = variableTwo.getTypeVariable();
        String typeTwo = variableThree.getTypeVariable();
        String typeThree = variableOne.getTypeVariable();
        if (typeOne.equals(typeTwo)) {
            if (!typeThree.equals(typeTwo)) {
                errString += "Error tipo 2::= El tipo de asignacion/comparacion de: "
                        + variableOne.getIdVariable() + " no es compatible\n";
            }
        } else {
            errString += "Error tipo 3::= operacion entre tipos no compatible\n";
        }
        return errString;
    }

    public String detectErrorTypeAssign() {
        System.out.println("");
        String errString = "";
        if (this.v != null) {
            //System.out.println("Entre al caso variable");
            errString += this.detectErrorInDeclarationVariable();
        } else {
            switch (this.variablesToCompare.size()) {
                case 1:
                    //una variable ejemplo a = 5/a = 5+3
                    errString += this.detectErrorInAssignSingleVariable();
                    break;
                case 2:
                    //una variable ejemplo a = a + 2
                    errString += this.detectErrorInAssignDoubleVariable();
                    break;
                case 3:
                    errString += this.detectErrorsInAssignTripleVariable();
                    break;
                default:
                    break;
            }
        }
        return errString;
    }
}
