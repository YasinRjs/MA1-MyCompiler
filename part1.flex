import java.util.ArrayList;
import java.util.List;
%%// Options of the scanner

%class MyScanner        //Name
%unicode                //Use unicode
%line                   //Use line counter (yyline variable)
%column                 //Use character counter by line (yycolumn variable)
%type Symbol            //Says that the return type is Symbol
%standalone             //Standalone mode

// Return value of the program
%{
    List<Symbol> listIdentifier = new ArrayList<Symbol>();
    List<Object> listSymbols = new ArrayList<Object>();

    public void addElemInListIfNotPresent(Symbol newSymbol){
        if (!listSymbols.contains(newSymbol.getValue())){
            listIdentifier.add(newSymbol);
            listSymbols.add(newSymbol.getValue());
        }
    }
%}

%eof{
    System.out.println("----- Identifiers -----");
    for (int i=0; i<listIdentifier.size(); ++i){
        Symbol elem = listIdentifier.get(i);
        System.out.println(elem.getValue() + "\t" + elem.getLine());
    }
    System.out.println("-----------------------");
%eof}

%eofval{
    return new Symbol(LexicalUnit.EOS,yyline,yycolumn);
%eofval}


ALPHA = [a-zA-Z]
NUM = [0-9]
NUM_1_9 = [1-9]
UNDERSCORE = "_"
// Extended Regular Expressions

VARNAME = {ALPHA}({ALPHA} | {NUM} |{UNDERSCORE})*
NUMBER = {NUM_1_9}[0-9]* | 0
BEGIN = "begin"
END = "end"
SEMICOLON = ";"
Assign = ":="
LPAREN = "("
RPAREN = ")"
MINUS = "-"
PLUS = "+"
TIMES = "*"
DIVIDE = "/"
IF = "if"
THEN = "then"
ENDIF = "endif"
ELSE = "else"
NOT = "not"
AND = "and"
OR = "or"
EQ = "="
GEQ = ">="
GT = ">"
LEQ = "<="
LT = "<"
NEQ = "<>"
WHILE = "while"
DO = "do"
DONE = "done"
FOR = "for"
FROM = "from"
BY = "by"
TO = "to"
PRINT = "print"
READ = "read"
EOS = "LAHASS"
SPACES = " "+ | "\t"+

START_COMMENT = "(*"
END_COMMENT = "*)"


%xstate YYINITIAL, STARTCOMMENT
%%// Identification of tokens

<YYINITIAL> {
    // Relational operators
    {NUMBER}        {
        Symbol newSymbol = new Symbol(LexicalUnit.NUMBER,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {BEGIN}         {
        Symbol newSymbol = new Symbol(LexicalUnit.BEGIN,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {END}           {
        Symbol newSymbol = new Symbol(LexicalUnit.END,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {SEMICOLON}     {
        Symbol newSymbol = new Symbol(LexicalUnit.SEMICOLON,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {IF}            {
        Symbol newSymbol = new Symbol(LexicalUnit.IF,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {Assign}    {
        Symbol newSymbol = new Symbol(LexicalUnit.ASSIGN,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {LPAREN}    {
        Symbol newSymbol = new Symbol(LexicalUnit.LPAREN,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {RPAREN}    {
        Symbol newSymbol = new Symbol(LexicalUnit.RPAREN,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {MINUS}    {
        Symbol newSymbol = new Symbol(LexicalUnit.MINUS,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {PLUS}    {
        Symbol newSymbol = new Symbol(LexicalUnit.PLUS,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    //
    {TIMES}    {
        Symbol newSymbol = new Symbol(LexicalUnit.TIMES,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {DIVIDE}    {
        Symbol newSymbol = new Symbol(LexicalUnit.DIVIDE,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {THEN}    {
        Symbol newSymbol = new Symbol(LexicalUnit.THEN,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {ENDIF}    {
        Symbol newSymbol = new Symbol(LexicalUnit.ENDIF,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {ELSE}    {
        Symbol newSymbol = new Symbol(LexicalUnit.ELSE,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {NOT}    {
        Symbol newSymbol = new Symbol(LexicalUnit.NOT,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {AND}    {
        Symbol newSymbol = new Symbol(LexicalUnit.AND,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {OR}    {
        Symbol newSymbol = new Symbol(LexicalUnit.OR,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {EQ}    {
        Symbol newSymbol = new Symbol(LexicalUnit.EQ,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {GEQ}    {
        Symbol newSymbol = new Symbol(LexicalUnit.GEQ,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {GT}    {
        Symbol newSymbol = new Symbol(LexicalUnit.GT,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {LEQ}    {
        Symbol newSymbol = new Symbol(LexicalUnit.LEQ,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {LT}    {
        Symbol newSymbol = new Symbol(LexicalUnit.LT,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {NEQ}    {
        Symbol newSymbol = new Symbol(LexicalUnit.NEQ,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {WHILE}    {
        Symbol newSymbol = new Symbol(LexicalUnit.WHILE,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {DO}    {
        Symbol newSymbol = new Symbol(LexicalUnit.DO,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {DONE}    {
        Symbol newSymbol = new Symbol(LexicalUnit.DONE,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {FOR}    {
        Symbol newSymbol = new Symbol(LexicalUnit.FOR,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {FROM}    {
        Symbol newSymbol = new Symbol(LexicalUnit.FROM,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {BY}    {
        Symbol newSymbol = new Symbol(LexicalUnit.BY,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {TO}    {
        Symbol newSymbol = new Symbol(LexicalUnit.TO,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {PRINT}    {
        Symbol newSymbol = new Symbol(LexicalUnit.PRINT,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {READ}    {
        Symbol newSymbol = new Symbol(LexicalUnit.READ,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {EOS}    {
        Symbol newSymbol = new Symbol(LexicalUnit.EOS,yyline,yycolumn,yytext());
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {VARNAME}       {
        Symbol newSymbol = new Symbol(LexicalUnit.VARNAME,yyline,yycolumn,yytext());
        addElemInListIfNotPresent(newSymbol);
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
    {START_COMMENT} {
        yybegin(STARTCOMMENT);
    }
    {SPACES}      {}
    .        {
        System.out.println("token: "+yytext()+"\tlexical unit: ERROR 404");
    }
}

<STARTCOMMENT> {
    {END_COMMENT}    {
        yybegin(YYINITIAL);
    }
    .       {}
}
