package analizer;
import static analizer.Tokens.*;
import java_cup.runtime.*;
%%
%class Lexer
%type Tokens
L=[a-zA-Z_]+
D=(\+|\-)?[0-9]+(\.[0-9]+)?
DN=[0-9]+ 
espacio=[ ,\t,\r]+
%{
    public String lexeme;
%}
%%

/* Espacios en blanco */
{espacio} {/*Ignore*/}

/* Comentarios */
( "#".* ) {/*Ignore*/}

/* Salto de linea */
( "\n" ) {return Linea;}

/* Comillas */

/* Tipos de datos */
( INT | INTB | FLOAT ) {lexeme=yytext(); return T_dato;}


/* Palabra reservada If */
( IF ) {lexeme=yytext(); return If;}

/* Palabra reservada While */
( WHILE ) {lexeme=yytext(); return While;}

/* Palabra reservada In */
( IN ) {lexeme=yytext(); return In;}

/* Palabra reservada OUT */
( OUT ) {lexeme=yytext(); return Out;}

/* Palabra reservada For */
( FOR ) {lexeme=yytext(); return For;}

/* Palabra reservada Fun */
( FUN ) {lexeme=yytext(); return Fun;}

/* Operador Referencia */
( "%" ) {lexeme=yytext(); return Operador_Referencia;}

/* Operador Igual */
( "=" ) {lexeme=yytext(); return Operador_Igual;}

/* Operador Suma */
( "+" ) {lexeme=yytext(); return Operador_Suma;}

/* Operador Resta */
( "-" ) {lexeme=yytext(); return Operador_Resta;}

/* Operador Multiplicacion */
( "*" ) {lexeme=yytext(); return Operador_Multiplicacion;}

/* Operador Division */
( "/" ) {lexeme=yytext(); return Operador_Division;}

/* Operador Modulo */
( "//" ) {lexeme=yytext(); return Operador_Modulo;}

/*Operadores Relacionales */
( ">" | "<" | "==" |  ">=" | "<=" ) {lexeme = yytext(); return Op_Relacional;}


/* Parentesis de apertura */
( "(" ) {lexeme=yytext(); return Parentesis_a;}

/* Parentesis de cierre */
( ")" ) {lexeme=yytext(); return Parentesis_c;}

/* Llave de apertura */
( "{" ) {lexeme=yytext(); return Llave_a;}

/* Llave de cierre */
( "}" ) {lexeme=yytext(); return Llave_c;}

/* Separador */
( ";" ) {lexeme=yytext(); return Op_Separator;}

/* Identificador */
{L}({L}|{DN})* {lexeme=yytext(); return Identificador;}

/* Numero */
{D} {lexeme=yytext(); return Numero;}

/* Error de analisis */
 . {return ERROR;}