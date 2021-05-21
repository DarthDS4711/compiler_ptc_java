package analizer;
import java_cup.runtime.Symbol;
%%
%class LexerCup
%type java_cup.runtime.Symbol
%cup
%full
%line
%char
%unicode
L=[a-zA-Z_]+
D=(\-)?[0-9]+\.[0-9]+
DN=[0-9]+ 
espacio=[ ,\t,\r,\n]+
%{
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }
    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }
%}
%%

/* Espacios en blanco */
{espacio} {/*Ignore*/}

/* Comentarios */
( "#".* ) {/*Ignore*/}

/* Tipos de datos */
( INT | INTB | FLOAT ) {return new Symbol(sym.T_dato, yychar, yyline, yytext());}

/*Valor logico*/
( TRUE|FALSE ) {return new Symbol(sym.Valor_Logico, yychar, yyline, yytext());} 

/* Palabra reservada If */
( IF ) {return new Symbol(sym.If, yychar, yyline, yytext());}

/* Palabra reservada While */
( WHILE ) {return new Symbol(sym.While, yychar, yyline, yytext());}

/* Palabra reservada In */
( IN ) {return new Symbol(sym.In, yychar, yyline, yytext());}

/* Palabra reservada OUT */
( OUT ) {return new Symbol(sym.Out, yychar, yyline, yytext());}

/* Palabra reservada For */
( FOR ) {return new Symbol(sym.For, yychar, yyline, yytext());}

/* Palabra reservada Fun */
( FUN ) {return new Symbol(sym.Fun, yychar, yyline, yytext());}

/* Operador Referencia */
( "%" ) {return new Symbol(sym.Operador_Referencia, yychar, yyline, yytext());}

/* Operador Igual */
( "=" ) {return new Symbol(sym.Operador_Igual, yychar, yyline, yytext());}

/* Operador Suma */
( "+" ) {return new Symbol(sym.Operador_Suma, yychar, yyline, yytext());}

/* Operador Resta */
( "-" ) {return new Symbol(sym.Operador_Resta, yychar, yyline, yytext());}

/* Operador Multiplicacion */
( "*" ) {return new Symbol(sym.Operador_Multiplicacion, yychar, yyline, yytext());}

/* Operador Division */
( "/" ) {return new Symbol(sym.Operador_Division, yychar, yyline, yytext());}

/* Operador Modulo */
( "//" ) {return new Symbol(sym.Operador_Modulo, yychar, yyline, yytext());}

/*Operadores Relacionales */
( ">" | "<" | "==" |  ">=" | "<=" ) {return new Symbol(sym.Op_Relacional, yychar, yyline, yytext());}

/* Parentesis de apertura */
( "(" ) {return new Symbol(sym.Parentesis_a, yychar, yyline, yytext());} 

/* Parentesis de cierre */
( ")" ) {return new Symbol(sym.Parentesis_c, yychar, yyline, yytext());} 

/* Llave de apertura */
( "{" ) {return new Symbol(sym.Llave_a, yychar, yyline, yytext());} 

/* Llave de cierre */
( "}" ) {return new Symbol(sym.Llave_c, yychar, yyline, yytext());} 

/* Separador */
( ";" ) {return new Symbol(sym.Op_Separator, yychar, yyline, yytext());} 

/* Identificador */
{L}({L}|{DN})* {return new Symbol(sym.Identificador, yychar, yyline, yytext());} 

/* Numero */
{D} {return new Symbol(sym.Numero, yychar, yyline, yytext());} 

/* Numero ENTERO */
{DN} {return new Symbol(sym.Numero_entero, yychar, yyline, yytext());} 


/* Error de analisis */
 . {return new Symbol(sym.ERROR, yychar, yyline, yytext());} 