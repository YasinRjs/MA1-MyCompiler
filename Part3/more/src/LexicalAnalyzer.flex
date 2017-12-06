import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
%%// Options of the scanner

%class LexicalAnalyzer             //Name
%unicode                //Use unicode
%line                   //Use line counter (yyline variable)
%column                 //Use character counter by line (yycolumn variable)
%type Symbol            //Says that the return type is Symbol
%standalone             //Standalone mode

// Return value of the program
%{
    List<Symbol> listIdentifier = new ArrayList<Symbol>();
    private static List<String> listSymbolsAsString = new ArrayList<String>();
    private static List<Symbol> symbolList = new ArrayList<Symbol>();
    private static GenericStack<Symbol> stack = new GenericStack<Symbol>();

    /**
    *Add an Identifier which is a Symbol in the ListIdentifier
    *if it doesn't contain it.
    *@param newSymbol The Identifier
    *
    */
    public void addElemInListIfNotPresent(Symbol newSymbol){
        if (!listSymbolsAsString.contains((String) newSymbol.getValue())){
            listIdentifier.add(newSymbol);
            listSymbolsAsString.add((String) newSymbol.getValue());
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
    public Symbol createSymbol(LexicalUnit unit, int line, int column, Object value){
        Symbol newSymbol = new Symbol(unit,line,column,value);
        //System.out.println(newSymbol.toString());
        symbolList.add(newSymbol);
        return newSymbol;
    }

    /**
     * Display the identifier in an alphabetical order
     */
    public void printIdentifiers(){
        Collections.sort(listSymbolsAsString); // In order to display in an alphabetical order
        System.out.println("----- Identifiers -----");
        for (int i=0; i<listIdentifier.size(); ++i){
            int j = 0;
            int index;
            String identifier = listSymbolsAsString.get(i);
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
    }


    public static List<String> getVariablesList(){
        return listSymbolsAsString;
    }

    public static GenericStack<Symbol> getSymbolList(){
        for(int i=symbolList.size()-1; i>=0; --i){
            stack.push(symbolList.get(i));
        }
        return stack;
    }
%}

%eof{
    //printIdentifiers();
%eof}

%eofval{
    return new Symbol(LexicalUnit.EOS,yyline,yycolumn);
%eofval}


ALPHA = [a-zA-Z]
NUM = [0-9]
NUM_1_9 = [1-9]
// Extended Regular Expressions

VARNAME = {ALPHA}({ALPHA} | {NUM})*
NUMBER = {NUM_1_9}{NUM}* | 0
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
NEWLINE = "\n"

START_COMMENT = "(*"
END_COMMENT = "*)"


%xstate YYINITIAL, STARTCOMMENT
%%// Identification of tokens

<YYINITIAL> {
    // Relational operators
    {NUMBER}        {
        return createSymbol(LexicalUnit.NUMBER,yyline,yycolumn,yytext());
    }
    {BEGIN}         {
        return createSymbol(LexicalUnit.BEGIN,yyline,yycolumn,yytext());
    }
    {END}           {
        return createSymbol(LexicalUnit.END,yyline,yycolumn,yytext());
    }
    {SEMICOLON}     {
        return createSymbol(LexicalUnit.SEMICOLON,yyline,yycolumn,yytext());
    }
    {IF}            {
        return createSymbol(LexicalUnit.IF,yyline,yycolumn,yytext());
    }
    {Assign}    {
        return createSymbol(LexicalUnit.ASSIGN,yyline,yycolumn,yytext());
    }
    {LPAREN}    {
        return createSymbol(LexicalUnit.LPAREN,yyline,yycolumn,yytext());
    }
    {RPAREN}    {
        return createSymbol(LexicalUnit.RPAREN,yyline,yycolumn,yytext());
    }
    {MINUS}    {
        return createSymbol(LexicalUnit.MINUS,yyline,yycolumn,yytext());
    }
    {PLUS}    {
        return createSymbol(LexicalUnit.PLUS,yyline,yycolumn,yytext());
    }
    {TIMES}    {
        return createSymbol(LexicalUnit.TIMES,yyline,yycolumn,yytext());
    }
    {DIVIDE}    {
        return createSymbol(LexicalUnit.DIVIDE,yyline,yycolumn,yytext());
    }
    {THEN}    {
        return createSymbol(LexicalUnit.THEN,yyline,yycolumn,yytext());
    }
    {ENDIF}    {
        return createSymbol(LexicalUnit.ENDIF,yyline,yycolumn,yytext());
    }
    {ELSE}    {
        return createSymbol(LexicalUnit.ELSE,yyline,yycolumn,yytext());
    }
    {NOT}    {
        return createSymbol(LexicalUnit.NOT,yyline,yycolumn,yytext());
    }
    {AND}    {
        return createSymbol(LexicalUnit.AND,yyline,yycolumn,yytext());
    }
    {OR}    {
        return createSymbol(LexicalUnit.OR,yyline,yycolumn,yytext());
    }
    {EQ}    {
        return createSymbol(LexicalUnit.EQ,yyline,yycolumn,yytext());
    }
    {GEQ}    {
        return createSymbol(LexicalUnit.GEQ,yyline,yycolumn,yytext());
    }
    {GT}    {
        return createSymbol(LexicalUnit.GT,yyline,yycolumn,yytext());
    }
    {LEQ}    {
        return createSymbol(LexicalUnit.LEQ,yyline,yycolumn,yytext());
    }
    {LT}    {
        return createSymbol(LexicalUnit.LT,yyline,yycolumn,yytext());
    }
    {NEQ}    {
        return createSymbol(LexicalUnit.NEQ,yyline,yycolumn,yytext());
    }
    {WHILE}    {
        return createSymbol(LexicalUnit.WHILE,yyline,yycolumn,yytext());
    }
    {DO}    {
        return createSymbol(LexicalUnit.DO,yyline,yycolumn,yytext());
    }
    {DONE}    {
        return createSymbol(LexicalUnit.DONE,yyline,yycolumn,yytext());
    }
    {FOR}    {
        return createSymbol(LexicalUnit.FOR,yyline,yycolumn,yytext());
    }
    {FROM}    {
        return createSymbol(LexicalUnit.FROM,yyline,yycolumn,yytext());
    }
    {BY}    {
        return createSymbol(LexicalUnit.BY,yyline,yycolumn,yytext());
    }
    {TO}    {
        return createSymbol(LexicalUnit.TO,yyline,yycolumn,yytext());
    }
    {PRINT}    {
        return createSymbol(LexicalUnit.PRINT,yyline,yycolumn,yytext());
    }
    {READ}    {
        return createSymbol(LexicalUnit.READ,yyline,yycolumn,yytext());
    }
    {EOS}    {
        return createSymbol(LexicalUnit.EOS,yyline,yycolumn,yytext());
    }
    {VARNAME}       {
        Symbol newSymbol = createSymbol(LexicalUnit.VARNAME,yyline,yycolumn,yytext());
        addElemInListIfNotPresent(newSymbol);
        return newSymbol;
    }
    {START_COMMENT} {
        yybegin(STARTCOMMENT);
    }
    {SPACES}      {}
    {NEWLINE}  {}
    .        {
        try {   // It's abnormal but doing this avoid to modify the java file
            throw new AnalyzingException(yytext(), yyline);
        }
        catch (AnalyzingException e){
            System.exit(1);
        }
        //System.out.println("token: "+yytext()+"\tlexical unit: ERROR 404");
    }
}

<STARTCOMMENT> {
    {END_COMMENT}    {
        yybegin(YYINITIAL);
    }
    .       {}
}
