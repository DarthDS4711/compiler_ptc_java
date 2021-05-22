/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handleSourceCode;

import Classes.*;
import analizer.*;
import java.io.IOException;
import java.util.*;
import java.io.StringReader;
import java_cup.runtime.Symbol;

/**
 *
 * @author Daniel_Santiago
 */
public class InputSourceCode {

    //constructor que guarda el codigo fuente
    public InputSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
        this.line = new TokenInLine();
        this.table = new TableSymbols();
    }

    public void analizeLexical(List<Token> listTokens) throws IOException {
        StringReader stream = new StringReader(sourceCode);
        Lexer lexer = new Lexer(stream);
        Token tokenResult = null;
        int lineAnalized = 0;
        while (true) {
            Tokens token = lexer.yylex();
            if (token == null) {
                return;
            }
            switch (token) {
                case T_dato:
                    tokenResult = new Token("T_Dato", lexer.lexeme, lineAnalized);
                    listTokens.add(tokenResult);
                    this.line.addToken(tokenResult);
                    break;
                case If:
                    tokenResult = new Token("Token if", lexer.lexeme);
                    listTokens.add(tokenResult);
                    this.line.addToken(tokenResult);
                    break;
                case While:
                    tokenResult = new Token("Token while", lexer.lexeme);
                    listTokens.add(tokenResult);
                    this.line.addToken(tokenResult);
                    break;
                case In:
                    tokenResult = new Token("Token in", lexer.lexeme);
                    listTokens.add(tokenResult);
                    break;
                case Out:
                    tokenResult = new Token("Token Out", lexer.lexeme);
                    listTokens.add(tokenResult);
                    break;
                case For:
                    tokenResult = new Token("Token For", lexer.lexeme);
                    listTokens.add(tokenResult);
                    this.line.addToken(tokenResult);
                    break;
                case Fun:
                    tokenResult = new Token("Token fun", lexer.lexeme, lineAnalized);
                    listTokens.add(tokenResult);
                    this.line.addToken(tokenResult);
                    break;
                case Operador_Referencia:
                    tokenResult = new Token("Token referencia", lexer.lexeme);
                    listTokens.add(tokenResult);
                    break;
                case Operador_Igual:
                    tokenResult = new Token("Token igual", lexer.lexeme, lineAnalized);
                    listTokens.add(tokenResult);
                    this.line.addToken(tokenResult);
                    break;
                case Operador_Suma:
                    tokenResult = new Token("Token suma", lexer.lexeme, lineAnalized);
                    listTokens.add(tokenResult);
                    this.line.addToken(tokenResult);
                    break;
                case Operador_Resta:
                    tokenResult = new Token("Token resta", lexer.lexeme, lineAnalized);
                    listTokens.add(tokenResult);
                    this.line.addToken(tokenResult);
                    break;
                case Operador_Multiplicacion:
                    tokenResult = new Token("Token multiplicacion", lexer.lexeme, lineAnalized);
                    listTokens.add(tokenResult);
                    this.line.addToken(tokenResult);
                    break;
                case Operador_Division:
                    tokenResult = new Token("Token division", lexer.lexeme, lineAnalized);
                    listTokens.add(tokenResult);
                    this.line.addToken(tokenResult);
                    break;
                case Operador_Modulo:
                    tokenResult = new Token("Token modulo", lexer.lexeme, lineAnalized);
                    listTokens.add(tokenResult);
                    this.line.addToken(tokenResult);
                    break;
                case Op_Relacional:
                    tokenResult = new Token("Token relacional", lexer.lexeme);
                    listTokens.add(tokenResult);
                    this.line.addToken(tokenResult);
                    break;
                case Parentesis_a:
                    tokenResult = new Token("Token parentesis abierto", lexer.lexeme);
                    listTokens.add(tokenResult);
                    this.line.addToken(tokenResult);
                    break;
                case Parentesis_c:
                    tokenResult = new Token("Token parentesis cerrado", lexer.lexeme);
                    listTokens.add(tokenResult);
                    this.line.addToken(tokenResult);
                    break;
                case Llave_a:
                    tokenResult = new Token("Token llave abierto", lexer.lexeme);
                    listTokens.add(tokenResult);
                    this.line.addToken(tokenResult);
                    break;
                case Llave_c:
                    tokenResult = new Token("Token llave cerrado", lexer.lexeme);
                    listTokens.add(tokenResult);
                    this.line.addToken(tokenResult);
                    break;
                case Op_Separator:
                    tokenResult = new Token("Token separador", lexer.lexeme, lineAnalized);
                    listTokens.add(tokenResult);
                    this.line.addToken(tokenResult);
                    break;
                case Identificador:
                    tokenResult = new Token("Token identificador", lexer.lexeme, lineAnalized);
                    listTokens.add(tokenResult);
                    this.line.addToken(tokenResult);
                    break;
                case Numero:
                    tokenResult = new Token("Token numero real", lexer.lexeme, lineAnalized);
                    listTokens.add(tokenResult);
                    this.line.addToken(tokenResult);
                    break;
                case Numero_entero:
                    tokenResult = new Token("Token numero entero", lexer.lexeme, lineAnalized);
                    listTokens.add(tokenResult);
                    this.line.addToken(tokenResult);
                    break;
                case Valor_Logico:
                    tokenResult = new Token("Token valor logico", lexer.lexeme, lineAnalized);
                    listTokens.add(tokenResult);
                    this.line.addToken(tokenResult);
                    break;
                case Linea:
                    this.table.addLineInTable(line);
                    this.line = new TokenInLine();
                    break;
                case ERROR:
                    tokenResult = new Token("Token invalido", lexer.lexeme);
                    listTokens.add(tokenResult);
                    break;

            }
        }
    }

    public String analizeSyntax() {
        StringReader stream = new StringReader(sourceCode);
        Sintax syntax = new Sintax(new analizer.LexerCup(stream));
        String errorsSyntax = "";
        try {
            syntax.parse();
            errorsSyntax = "Analisis realizado correctamente";
        } catch (Exception e) {
            Symbol sym = syntax.getS();
            errorsSyntax = "Error de sintaxis. Linea: " + (sym.right + 1)
                    + " Columna: " + (sym.left + 1)
                    + ", Texto: \"" + sym.value + "\"";
        }
        return errorsSyntax;
    }
    
    public String printTable(){
        String errorrsAndwarnings = "";
        if(this.line.size() > 0){
            this.table.addLineInTable(this.line);
        }
        //this.table.printAllTable();
        this.semanticToAnalize = new AnalizeSemantic(this.table);
        errorrsAndwarnings += this.semanticToAnalize.detectAndInicializeTheTableSymbol();
        return errorrsAndwarnings;
    }
    
    private String sourceCode;
    private TableSymbols table;
    private TokenInLine line;
    private AnalizeSemantic semanticToAnalize;
}
