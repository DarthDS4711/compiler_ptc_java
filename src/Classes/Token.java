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
    
    public Token(String token, String lexeme){
        this.tokenResult = token;
        this.lexeme = lexeme;
    }

    public String getTokenResult() {
        return tokenResult;
    }

    public String getLexeme() {
        return lexeme;
    }
    
    private String tokenResult;
    private String lexeme;
}
