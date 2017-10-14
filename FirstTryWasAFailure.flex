%%// Options of the scanner

%class MyScanner        //Name
%unicode                //Use unicode
%line                   //Use line counter (yyline variable)
%column                 //Use character counter by line (yycolumn variable)
%type Symbol            //Says that the return type is Symbol
%standalone             //Standalone mode

// Return value of the program
%eofval{
    //Whatever
%eofval}

// Extended Regular Expressions

ALPHA = [a-zA-Z]
NUM = [0-9]
Inf_NUM = (1-9)[0-9]*
UNDERSCORE = [_]
OP = ["+" + "-" + "*" + "/"]
LPAREN = "("
RPAREN = ")"
MINUS = "-"

VARNAME = ALPHA(ALPHA+NUM+UNDERSCORE)*
EndOfLine = "\r"?"\n"
Assign_OP = ":="
ExprArithm_Basic = (VARNAME  Inf_NUM)(OP)(VARNAME + Inf_NUM)
ExprArithm_Par = (LPAREN)(Arithm_Expr)(RPAREN)
ExprArithm_Minus = (MINUS)(ArithmExpr) | (MINUS)(Arithm_Expr_Par)
//Subtilité parenthèse dans parenthèses etc
ExprArithm = (ExprArithm_Basic + ExprArithm_Par + ExprArithm_Minus)



%xstate YYINITIAL,CODE,INSTLIST,COND,ASSIGN,
%%// Identification of tokens

// Relational operators

<YYINITIAL> { //State : PROGRAM
    "begin"         {Symbol newSymbol = new Symbol(LexicalUnit.BEGIN,yyline,yycolumn,yytext());
                    System.out.println(newSymbol.toString());
                    yybegin(CODE);
                    return newSymbol;
    }
    .               {}
}

<CODE> {
    "end"           {Symbol newSymbol = new Symbol(LexicalUnit.END,yyline,yycolumn,yytext());
                    System.out.println(newSymbol.toString());
                    yybegin(YYINITIAL);
                    return newSymbol;
    }
    {VARNAME}       {Symbol newSymbol = new Symbol(LexicalUnit.VARNAME,yyline,yycolumn,yytext());
                    System.out.println(newSymbol.toString());
                    yybegin(ASSIGN);
                    return newSymbol;
    }
    .               {}
}

<INSTLIST> {
    "if"            {Symbol newSymbol = new Symbol(LexicalUnit.IF,yyline,yycolumn,yytext());
                    System.out.println(newSymbol.toString());
                    yybegin(COND);
                    return newSymbol;
    }
    //";"             {System.out.println("token: " + yytext()); return new Symbol(LexicalUnit.)}
    .               {}
}

<COND> {
    .               {}
}

<ASSIGN> {
    {Assign_OP}   {Symbol newSymbol = new Symbol(LexicalUnit.ASSIGN,yyline,yycolumn,yytext());
                   System.out.println(newSymbol.toString());
                   return newSymbol;
    }
    {ExprArithm}   {Symbol newSymbol = new Symbol(LexicalUnit.)

    }
    {EndOfLine}     {

    }
    .               {}
}
