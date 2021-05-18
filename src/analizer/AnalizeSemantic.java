package analizer;

import Classes.*;
import analizer.semantic.*;
import java.util.*;

public class AnalizeSemantic {

    private List<Variable> listVariables;
    private List<Function> listFunctions;
    private TableSymbols table;
    private boolean flagVariable = false;
    private boolean flagFunction = false;
    private String typeVariable;
    private String nameFunction;

    public AnalizeSemantic(TableSymbols table) {
        this.table = table;
        this.listVariables = new ArrayList<>();
        this.listFunctions = new ArrayList<>();
        this.typeVariable = " ";
        this.nameFunction = " ";
    }

    public void detectAndInicializeTheTableSymbol() {
        int numberParams = 0;
        for (int index = 0; index < this.table.size(); index++) {
            TokenInLine line = this.table.getLineTable(index);
            for (int subIndex = 0; subIndex < line.size(); subIndex++) {
                Token t = line.getToken(subIndex);
                if(t.getTokenResult().equals("T_Dato") || this.flagVariable){
                    detectVariable(t);
                }
                else if(t.getTokenResult().equals("Token fun") || this.flagFunction){
                    numberParams = detectFunction(t, numberParams);
                }
            }
        }
    }

    private void detectVariable(Token t) {
        String tokenVariable = t.getTokenResult();
        if (tokenVariable.equals("T_Dato") && !this.flagVariable) {
            this.flagVariable = true;
            this.typeVariable = t.getLexeme();
        } else if (tokenVariable.equals("Token identificador") && this.flagVariable) {
            Variable v = new Variable(t.getLexeme(), this.typeVariable);
            this.listVariables.add(v);
            this.flagVariable = false;
        }

    }

    private int detectFunction(Token t, int numberParams) {
        String tokenVariable = t.getTokenResult();
        if (tokenVariable.equals("Token fun") && !this.flagFunction) {
            this.flagFunction = true;
            //System.out.println("Funcion encontrada");
        }
        //System.out.println("tokenVariable = " + tokenVariable);
        if (tokenVariable.equals("Token identificador") && this.flagFunction) {
            //System.out.println("Parametro: " + numberParams + 1);
            if (numberParams == 0) {
                this.nameFunction = t.getLexeme();
                System.out.println(this.nameFunction);
            }
            numberParams++;
        } else if (tokenVariable.equals("Token parentesis cerrado") && this.flagFunction) {
            Function function = new Function(numberParams - 1, this.nameFunction);
            System.out.println(function.getNameVariable());
            this.listFunctions.add(function);
            this.flagFunction = false;
            numberParams = 0;
        }
        return numberParams;
    }

    public void printVariablesDetect() {
        for (Variable v : this.listVariables) {
            System.out.println(v);
        }
        for (Function f : this.listFunctions) {
            System.out.println(f);
        }
    }
}
