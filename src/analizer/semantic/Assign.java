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
    private List<String>symbolNumericAssign;
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
            } else if (t.getTokenResult().equals("Token identificador") && this.nameVariableAssign.equals(" ")) {
                this.nameVariableAssign = t.getLexeme();
            } else if (t.getTokenResult().equals("Token parentesis abierto") && !this.nameVariableAssign.equals(" ")) {
                this.nameFunctionAssign = this.nameVariableAssign;
                assingFunction(line);
                this.nameVariableAssign = " ";
                break;
            } else if (t.getTokenResult().equals("Token igual")) {
                this.symbolEqual = t.getLexeme();
            } else if (t.getTokenResult().equals("Token identificador") && !this.symbolEqual.equals(" ")) {
                this.symbolsToAsign.add(t.getLexeme());
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

    private boolean detectRepeatVariable(List<String> nameVariables, String value) {
        boolean state = true;
        for (String str : nameVariables) {
            if (str.equals(value)) {
                state = false;
                break;
            }
        }
        return state;
    }

    public List<String> getNameVariables() {
        List<String> nameVariables = new ArrayList<>();
        if (this.v != null) {
            nameVariables.add(this.v.getIdVariable());
        } else {
            nameVariables.add(this.nameVariableAssign);
        }
        for (int i = 0; i < this.symbolsToAsign.size(); i++) {
            if (this.symbolsToAsign.get(i) != null) {
                String value = this.symbolsToAsign.get(i);
                if (this.detectRepeatVariable(nameVariables, value)) {
                    nameVariables.add(value);
                }
            }
        }

        return nameVariables;
    }

    public FunctionCalled getFunctionCalled() {
        return functionCalled;
    }

    public boolean typeDataAccept(String typeData1, String typeData2, String dataType3) {
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

    public String detectErrorTypeAssign() {
        String errString = "";
        if(this.variablesToCompare.isEmpty()){
            if(this.symbolNumericAssign.size() == 1){
                String symbol = this.symbolNumericAssign.get(0);
                if(!this.typeDataAccept(this.v.getTypeVariable(), symbol, "")){
                    errString += "Error tipo 2::= El tipo de asignacion de: " + v.getIdVariable() + " no es compatible\n";
                }
            }
        }
        return errString;
    }
}
