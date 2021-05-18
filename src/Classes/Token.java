/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author Daniel_Santiago
 */
public class Token {
    
    public Token(String token, String lexeme, int line){
        this.tokenResult = token;
        this.lexeme = lexeme;
        this.line = line;
    }

    public Token(String tokenResult, String lexeme) {
        this.tokenResult = tokenResult;
        this.lexeme = lexeme;
    }
    
    
    public String getTokenResult() {
        return tokenResult;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getLine() {
        return line;
    }
    
    private String tokenResult;
    private String lexeme;
    private int line;
}
