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
    private Variable v;
    private String symbolEqual;
    private String symbolsToAsign[];
    private String symbolNumericAssign[];
    private String nameVariableAssign;
    private String symbolOperation;
    private String contextAssign;
    private String typeVariableAssign;

    public String getNameVariable() {
        return nameVariableAssign;
    }
    
    public Assign() {
        this.v = null;
        this.symbolsToAsign = new String[2];
        this.symbolNumericAssign = new String[2];
        this.nameVariableAssign = " ";
        this.symbolEqual = " ";
        this.symbolOperation = " ";
        this.contextAssign = " ";
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

    public String[] getSymbolsToAsign() {
        return symbolsToAsign;
    }

    public void setSymbolsToAsign(String[] symbolsToAsign) {
        this.symbolsToAsign = symbolsToAsign;
    }
    
    private boolean isSymbolOperation(String token){
        if(token.equals("Token suma")){
            return true;
        }
        else if(token.equals("Token resta")){
            return true;
        }
        else if(token.equals("Token multiplicacion")){
            return true;
        }
        else if(token.equals("Token division")){
            return true;
        }
        else if(token.equals("Token modulo")){
            return true;
        }
        return false;
    }
    
    public void detectAsign(TokenInLine line){
        String nameVariable = " ";
        String typeVariable = " "; 
        int numericSymbol = 0;
        int numberSymbol = 0;
        for(int i = 0; i < line.size(); i++){
            Token t = line.getToken(i);
            if(t.getTokenResult().equals("T_Dato")){
                typeVariable = t.getLexeme();
            }
            if(t.getTokenResult().equals("Token identificador") && !typeVariable.equals(" ")){
                nameVariable = t.getLexeme();
                this.v = new Variable(nameVariable, typeVariable);
                
            }
            if(t.getTokenResult().equals("Token identificador") && this.nameVariableAssign.equals(" ")){
                this.nameVariableAssign = t.getLexeme();
                //typeVariable
            }
            if(t.getTokenResult().equals("Token igual")){
                this.symbolEqual = t.getLexeme();
            }
            if(t.getTokenResult().equals("Token identificador") && !this.symbolEqual.equals(" ")){
                this.symbolsToAsign[numberSymbol] = t.getLexeme();
                numberSymbol++;
            }
            else if(t.getTokenResult().equals("Token numero real") && !this.symbolEqual.equals(" ")){
                this.symbolNumericAssign[numericSymbol] = t.getTokenResult();
                numericSymbol++;
            }
            else if(t.getTokenResult().equals("Token numero entero") && !this.symbolEqual.equals(" ")){
                 this.symbolNumericAssign[numericSymbol] = t.getTokenResult();
                numericSymbol++;
            }
            if(this.isSymbolOperation(t.getTokenResult()) && !this.symbolEqual.equals(" ")){
                this.symbolOperation = t.getLexeme();
            }
        }
    }
    
    private boolean detectRepeatVariable(List<String> nameVariables, String value){
        boolean state = true;
        for(String str : nameVariables){
            if(str.equals(value)){
                state = false;
                break;
            }
        }
        return state;
    }
    
    public List<String> getNameVariables(){
        List<String> nameVariables = new ArrayList<>();
        if(this.v != null){
            nameVariables.add(this.v.getIdVariable());
        }
        else{
            nameVariables.add(this.nameVariableAssign);
        }
        for(int i = 0; i < 2; i++){
            if(this.symbolsToAsign[i] != null){
                String value = this.symbolsToAsign[i];
                if(this.detectRepeatVariable(nameVariables, value)){
                    nameVariables.add(value);
                }
            }
        }
        for(String a : nameVariables){
            System.out.println("a = " + a);
        }
        System.out.println("");
        return nameVariables;
    }

    public void printInfo(){
        if(v != null){
            System.out.println(v);
        }
        if(!this.nameVariableAssign.equals(" ")){
            System.out.println("Nombre variable: " + this.nameVariableAssign);
        }
        System.out.println("Variables o numeros");
        for(int i = 0; i < 2; i++){
            if(this.symbolsToAsign[i] != null){
                System.out.println(this.symbolsToAsign[i]);
            }
        }
        System.out.println("Simbolo operacion: " + this.symbolOperation);
    }
    
    
}
