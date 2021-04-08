/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Charly Ponce
 */
public class Principal {
    public static void main(String[] args) throws Exception {
        String ruta1 = "D:/Documents/NetBeansProjects/AnalizadorLexico/src/analizer/Lexer.flex";
        String ruta2 = "D:/Documents/NetBeansProjects/AnalizadorLexico/src/analizer/LexerCup.flex";
        String[] rutaS = {"-parser", "Sintax", "D:/Documents/NetBeansProjects/AnalizadorLexico/src/analizer/Sintax.cup"};
        generar(ruta1, ruta2, rutaS);
    }
    public static void generar(String ruta1, String ruta2, String[] rutaS) throws IOException, Exception{
        File archivo;
        archivo = new File(ruta1);
        JFlex.Main.generate(archivo);
        archivo = new File(ruta2);
        JFlex.Main.generate(archivo);
        java_cup.Main.main(rutaS);
        
        Path rutaSym = Paths.get("D:/Documents/NetBeansProjects/AnalizadorLexico/src/analizer/sym.java");
        if (Files.exists(rutaSym)) {
            Files.delete(rutaSym);
        }
        Files.move(
                Paths.get("D:/Documents/NetBeansProjects/AnalizadorLexico/sym.java"), 
                Paths.get("D:/Documents/NetBeansProjects/AnalizadorLexico/src/analizer/sym.java")
        );
        Path rutaSin = Paths.get("D:/Documents/NetBeansProjects/AnalizadorLexico/src/analizer/Sintax.java");
        if (Files.exists(rutaSin)) {
            Files.delete(rutaSin);
        }
        Files.move(
                Paths.get("D:/Documents/NetBeansProjects/AnalizadorLexico/Sintax.java"), 
                Paths.get("D:/Documents/NetBeansProjects/AnalizadorLexico/src/analizer/Sintax.java")
        );
    }
}
