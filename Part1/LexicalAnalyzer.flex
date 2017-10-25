import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
%%// Options of the scanner

%class LexicalAnalyzer  //Name
%unicode                //Use unicode
%line                   //Use line counter (yyline variable)
%column                 //Use character counter by line (yycolumn variable)
%type Symbol            //Says that the return type is Symbol
%standalone             //Standalone mode

// Return value of the program
%{
    List<Symbol> listIdentifier = new ArrayList<Symbol>();
    List<String> listSymbols = new ArrayList<String>();

    /**
    *Add an Identifier which is a Symbol in the ListIdentifier
    *if it doesn't contain it.
    *@param newSymbol The Identifier
    *
    */
    public void addElemInListIfNotPresent(Symbol newSymbol){
        if (!listSymbols.contains((String) newSymbol.getValue())){
            listIdentifier.add(newSymbol);
            listSymbols.add((String) newSymbol.getValue());
        }
    }
    /**
    *Create an object Symbol and display it
    *
    *@param unit  A LexicalUnit which give us the type of the token
    *@param line The line where the token is encountered
    *@param column The column where the token is encountered
    *@param value The actual token
    *
    *@return The Symbol
    */
    public Symbol createAndDisplaySymbols(LexicalUnit unit, int line, int column, Object value){
        Symbol newSymbol = new Symbol(unit,line,column,value);
        System.out.println(newSymbol.toString());
        return newSymbol;
    }
%}

%eof{
    Collections.sort(listSymbols);
    System.out.println("----- Identifiers -----");
    for (int i=0; i<listIdentifier.size(); ++i){
        int j = 0;
        int index;
        String identifier = listSymbols.get(i);
        boolean found = false;
        while (j < listIdentifier.size() && !found){
            Symbol elem = listIdentifier.get(j);
            if (identifier == elem.getValue()){
                found = true;
                System.out.println(elem.getValue() + "\t" + elem.getLine());
            }
            j++;
        }
    }
    System.out.println("-----------------------");
%eof}

%eofval{
    return new Symbol(LexicalUnit.EOS,yyline,yycolumn);
%eofval}


ALPHA = [a-zA-Z]
NUM = [0-9]
NUM_1_9 = [1-9]
// Extended Regular Expressions

VARNAME = {ALPHA}({ALPHA} | {NUM})*
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
        return createAndDisplaySymbols(LexicalUnit.NUMBER,yyline,yycolumn,yytext());
    }
    {BEGIN}         {
        return createAndDisplaySymbols(LexicalUnit.BEGIN,yyline,yycolumn,yytext());
    }
    {END}           {
        return createAndDisplaySymbols(LexicalUnit.END,yyline,yycolumn,yytext());
    }
    {SEMICOLON}     {
        return createAndDisplaySymbols(LexicalUnit.SEMICOLON,yyline,yycolumn,yytext());
    }
    {IF}            {
        return createAndDisplaySymbols(LexicalUnit.IF,yyline,yycolumn,yytext());
    }
    {Assign}    {
        return createAndDisplaySymbols(LexicalUnit.ASSIGN,yyline,yycolumn,yytext());
    }
    {LPAREN}    {
        return createAndDisplaySymbols(LexicalUnit.LPAREN,yyline,yycolumn,yytext());
    }
    {RPAREN}    {
        return createAndDisplaySymbols(LexicalUnit.RPAREN,yyline,yycolumn,yytext());
    }
    {MINUS}    {
        return createAndDisplaySymbols(LexicalUnit.MINUS,yyline,yycolumn,yytext());
    }
    {PLUS}    {
        return createAndDisplaySymbols(LexicalUnit.PLUS,yyline,yycolumn,yytext());
    }
    {TIMES}    {
        return createAndDisplaySymbols(LexicalUnit.TIMES,yyline,yycolumn,yytext());
    }
    {DIVIDE}    {
        return createAndDisplaySymbols(LexicalUnit.DIVIDE,yyline,yycolumn,yytext());
    }
    {THEN}    {
        return createAndDisplaySymbols(LexicalUnit.THEN,yyline,yycolumn,yytext());
    }
    {ENDIF}    {
        return createAndDisplaySymbols(LexicalUnit.ENDIF,yyline,yycolumn,yytext());
    }
    {ELSE}    {
        return createAndDisplaySymbols(LexicalUnit.ELSE,yyline,yycolumn,yytext());
    }
    {NOT}    {
        return createAndDisplaySymbols(LexicalUnit.NOT,yyline,yycolumn,yytext());
    }
    {AND}    {
        return createAndDisplaySymbols(LexicalUnit.AND,yyline,yycolumn,yytext());
    }
    {OR}    {
        return createAndDisplaySymbols(LexicalUnit.OR,yyline,yycolumn,yytext());
    }
    {EQ}    {
        return createAndDisplaySymbols(LexicalUnit.EQ,yyline,yycolumn,yytext());
    }
    {GEQ}    {
        return createAndDisplaySymbols(LexicalUnit.GEQ,yyline,yycolumn,yytext());
    }
    {GT}    {
        return createAndDisplaySymbols(LexicalUnit.GT,yyline,yycolumn,yytext());
    }
    {LEQ}    {
        return createAndDisplaySymbols(LexicalUnit.LEQ,yyline,yycolumn,yytext());
    }
    {LT}    {
        return createAndDisplaySymbols(LexicalUnit.LT,yyline,yycolumn,yytext());
    }
    {NEQ}    {
        return createAndDisplaySymbols(LexicalUnit.NEQ,yyline,yycolumn,yytext());
    }
    {WHILE}    {
        return createAndDisplaySymbols(LexicalUnit.WHILE,yyline,yycolumn,yytext());
    }
    {DO}    {
        return createAndDisplaySymbols(LexicalUnit.DO,yyline,yycolumn,yytext());
    }
    {DONE}    {
        return createAndDisplaySymbols(LexicalUnit.DONE,yyline,yycolumn,yytext());
    }
    {FOR}    {
        return createAndDisplaySymbols(LexicalUnit.FOR,yyline,yycolumn,yytext());
    }
    {FROM}    {
        return createAndDisplaySymbols(LexicalUnit.FROM,yyline,yycolumn,yytext());
    }
    {BY}    {
        return createAndDisplaySymbols(LexicalUnit.BY,yyline,yycolumn,yytext());
    }
    {TO}    {
        return createAndDisplaySymbols(LexicalUnit.TO,yyline,yycolumn,yytext());
    }
    {PRINT}    {
        return createAndDisplaySymbols(LexicalUnit.PRINT,yyline,yycolumn,yytext());
    }
    {READ}    {
        return createAndDisplaySymbols(LexicalUnit.READ,yyline,yycolumn,yytext());
    }
    {EOS}    {
        return createAndDisplaySymbols(LexicalUnit.EOS,yyline,yycolumn,yytext());
    }
    {VARNAME}       {
        Symbol newSymbol = createAndDisplaySymbols(LexicalUnit.PLUS,yyline,yycolumn,yytext());
        addElemInListIfNotPresent(newSymbol);
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
