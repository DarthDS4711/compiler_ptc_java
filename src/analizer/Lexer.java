/* The following code was generated by JFlex 1.4.3 on 21/05/21 13:47 */

package analizer;
import static analizer.Tokens.*;
import java_cup.runtime.*;

/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 21/05/21 13:47 from the specification file
 * <tt>D:/Documents/NetBeansProjects/Compilador_PTC/src/analizer/Lexer.flex</tt>
 */
public class Lexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 0
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\5\1\7\2\0\1\5\22\0\1\5\2\0\1\6\1\0"+
    "\1\26\2\0\1\34\1\35\1\31\1\30\1\5\1\2\1\4\1\32"+
    "\12\3\1\0\1\40\1\33\1\27\1\33\2\0\1\17\1\13\2\1"+
    "\1\22\1\14\1\1\1\21\1\10\2\1\1\15\1\1\1\11\1\16"+
    "\2\1\1\24\1\25\1\12\1\23\1\1\1\20\3\1\4\0\1\1"+
    "\1\0\32\1\1\36\1\0\1\37\uff82\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\2\1\3\1\4\2\5\1\6\5\2"+
    "\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16"+
    "\1\17\1\20\1\21\2\0\1\22\1\23\7\2\1\14"+
    "\1\24\1\25\1\26\2\2\1\27\1\2\1\30\1\31"+
    "\1\2\1\26\1\32\2\2\1\33";

  private static int [] zzUnpackAction() {
    int [] result = new int[51];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\41\0\102\0\143\0\204\0\245\0\306\0\41"+
    "\0\347\0\u0108\0\u0129\0\u014a\0\u016b\0\41\0\u018c\0\41"+
    "\0\41\0\u01ad\0\u018c\0\41\0\41\0\41\0\41\0\41"+
    "\0\u01ce\0\u01ef\0\u0210\0\102\0\u0231\0\u0252\0\u0273\0\u0294"+
    "\0\u02b5\0\u02d6\0\u02f7\0\41\0\41\0\u01ef\0\u0318\0\u0339"+
    "\0\u035a\0\102\0\u037b\0\102\0\102\0\u039c\0\102\0\102"+
    "\0\u03bd\0\u03de\0\102";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[51];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\1\3\1\4\1\5\1\2\1\6\1\7\1\10"+
    "\1\11\1\3\1\12\1\3\1\13\1\3\1\14\1\3"+
    "\1\15\5\3\1\16\1\17\1\20\1\21\1\22\1\23"+
    "\1\24\1\25\1\26\1\27\1\30\42\0\1\3\1\0"+
    "\1\3\4\0\16\3\16\0\1\31\40\0\1\5\1\32"+
    "\41\0\1\6\33\0\7\7\1\0\31\7\1\0\1\3"+
    "\1\0\1\3\4\0\1\3\1\33\2\3\1\34\11\3"+
    "\14\0\1\3\1\0\1\3\4\0\14\3\1\35\1\3"+
    "\14\0\1\3\1\0\1\3\4\0\5\3\1\36\1\37"+
    "\1\40\3\3\1\41\2\3\14\0\1\3\1\0\1\3"+
    "\4\0\13\3\1\42\2\3\14\0\1\3\1\0\1\3"+
    "\4\0\11\3\1\43\4\3\42\0\1\44\43\0\1\45"+
    "\11\0\1\31\1\32\37\0\1\46\36\0\1\3\1\0"+
    "\1\3\4\0\2\3\1\47\13\3\14\0\1\3\1\0"+
    "\1\3\4\0\13\3\1\50\2\3\14\0\1\3\1\0"+
    "\1\3\4\0\6\3\1\51\7\3\14\0\1\3\1\0"+
    "\1\3\4\0\14\3\1\52\1\3\14\0\1\3\1\0"+
    "\1\3\4\0\5\3\1\53\10\3\14\0\1\3\1\0"+
    "\1\3\4\0\1\3\1\54\14\3\14\0\1\3\1\0"+
    "\1\3\4\0\2\3\1\55\13\3\14\0\1\3\1\0"+
    "\1\3\4\0\1\56\15\3\14\0\1\3\1\0\1\3"+
    "\4\0\3\3\1\57\12\3\14\0\1\3\1\0\1\3"+
    "\4\0\12\3\1\60\3\3\14\0\1\3\1\0\1\3"+
    "\4\0\7\3\1\61\6\3\14\0\1\3\1\0\1\3"+
    "\4\0\15\3\1\50\14\0\1\3\1\0\1\3\4\0"+
    "\5\3\1\62\10\3\14\0\1\3\1\0\1\3\4\0"+
    "\2\3\1\57\13\3\14\0\1\3\1\0\1\3\4\0"+
    "\12\3\1\63\3\3\13\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[1023];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\11\5\1\1\11\5\1\1\11\1\1\2\11"+
    "\2\1\5\11\2\0\11\1\2\11\16\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[51];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
    public String lexeme;


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public Lexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public Lexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 112) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public Tokens yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 25: 
          { lexeme=yytext(); return Out;
          }
        case 28: break;
        case 7: 
          { lexeme=yytext(); return Operador_Referencia;
          }
        case 29: break;
        case 18: 
          { lexeme=yytext(); return In;
          }
        case 30: break;
        case 15: 
          { lexeme=yytext(); return Llave_a;
          }
        case 31: break;
        case 26: 
          { lexeme=yytext(); return Valor_Logico;
          }
        case 32: break;
        case 12: 
          { lexeme = yytext(); return Op_Relacional;
          }
        case 33: break;
        case 6: 
          { return Linea;
          }
        case 34: break;
        case 10: 
          { lexeme=yytext(); return Operador_Multiplicacion;
          }
        case 35: break;
        case 8: 
          { lexeme=yytext(); return Operador_Igual;
          }
        case 36: break;
        case 19: 
          { lexeme=yytext(); return If;
          }
        case 37: break;
        case 20: 
          { lexeme=yytext(); return Operador_Modulo;
          }
        case 38: break;
        case 17: 
          { lexeme=yytext(); return Op_Separator;
          }
        case 39: break;
        case 14: 
          { lexeme=yytext(); return Parentesis_c;
          }
        case 40: break;
        case 24: 
          { lexeme=yytext(); return Fun;
          }
        case 41: break;
        case 21: 
          { lexeme=yytext(); return Numero;
          }
        case 42: break;
        case 23: 
          { lexeme=yytext(); return For;
          }
        case 43: break;
        case 4: 
          { lexeme=yytext(); return Numero_entero;
          }
        case 44: break;
        case 2: 
          { lexeme=yytext(); return Identificador;
          }
        case 45: break;
        case 1: 
          { return ERROR;
          }
        case 46: break;
        case 3: 
          { lexeme=yytext(); return Operador_Resta;
          }
        case 47: break;
        case 5: 
          { /*Ignore*/
          }
        case 48: break;
        case 9: 
          { lexeme=yytext(); return Operador_Suma;
          }
        case 49: break;
        case 11: 
          { lexeme=yytext(); return Operador_Division;
          }
        case 50: break;
        case 27: 
          { lexeme=yytext(); return While;
          }
        case 51: break;
        case 22: 
          { lexeme=yytext(); return T_dato;
          }
        case 52: break;
        case 13: 
          { lexeme=yytext(); return Parentesis_a;
          }
        case 53: break;
        case 16: 
          { lexeme=yytext(); return Llave_c;
          }
        case 54: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            return null;
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
