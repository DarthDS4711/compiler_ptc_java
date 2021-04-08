/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handleSourceCode;

import Classes.Token;
import analizer.Lexer;
import analizer.LexerCup;
import analizer.Sintax;
import analizer.Tokens;
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
    }

    public void analizeLexical(List<Token> listTokens) throws IOException {
        StringReader stream = new StringReader(sourceCode);
        Lexer lexer = new Lexer(stream);
        Token tokenResult = null;
        while (true) {
            Tokens token = lexer.yylex();
            if (token == null) {
                return;
            }
            switch (token) {
                case T_dato:
                    tokenResult = new Token("T_Dato", lexer.lexeme);
                    listTokens.add(tokenResult);
                    break;
                case If:
                    tokenResult = new Token("Token if", lexer.lexeme);
                    listTokens.add(tokenResult);
                    break;
                case While:
                    tokenResult = new Token("Token while", lexer.lexeme);
                    listTokens.add(tokenResult);
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
                    break;
                case Fun:
                    tokenResult = new Token("Token fun", lexer.lexeme);
                    listTokens.add(tokenResult);
                    break;
                case Operador_Referencia:
                    tokenResult = new Token("Token referencia", lexer.lexeme);
                    listTokens.add(tokenResult);
                    break;
                case Operador_Igual:
                    tokenResult = new Token("Token igual", lexer.lexeme);
                    listTokens.add(tokenResult);
                    break;
                case Operador_Suma:
                    tokenResult = new Token("Token suma", lexer.lexeme);
                    listTokens.add(tokenResult);
                    break;
                case Operador_Resta:
                    tokenResult = new Token("Token resta", lexer.lexeme);
                    listTokens.add(tokenResult);
                    break;
                case Operador_Multiplicacion:
                    tokenResult = new Token("Token multiplicacion", lexer.lexeme);
                    listTokens.add(tokenResult);
                    break;
                case Operador_Division:
                    tokenResult = new Token("Token division", lexer.lexeme);
                    listTokens.add(tokenResult);
                    break;
                case Operador_Modulo:
                    tokenResult = new Token("Token modulo", lexer.lexeme);
                    listTokens.add(tokenResult);
                    break;
                case Op_Relacional:
                    tokenResult = new Token("Token relacional", lexer.lexeme);
                    listTokens.add(tokenResult);
                    break;
                case Parentesis_a:
                    tokenResult = new Token("Token parentesis abierto", lexer.lexeme);
                    listTokens.add(tokenResult);
                    break;
                case Parentesis_c:
                    tokenResult = new Token("Token parentesis cerrado", lexer.lexeme);
                    listTokens.add(tokenResult);
                    break;
                case Llave_a:
                    tokenResult = new Token("Token llave abierto", lexer.lexeme);
                    listTokens.add(tokenResult);
                    break;
                case Llave_c:
                    tokenResult = new Token("Token llave cerrado", lexer.lexeme);
                    listTokens.add(tokenResult);
                    break;
                case Op_Separator:
                    tokenResult = new Token("Token separador", lexer.lexeme);
                    listTokens.add(tokenResult);
                    break;
                case Identificador:
                    tokenResult = new Token("Token identificador", lexer.lexeme);
                    listTokens.add(tokenResult);
                    break;
                case Numero:
                    tokenResult = new Token("Token numero", lexer.lexeme);
                    listTokens.add(tokenResult);
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
    private String sourceCode;
}
