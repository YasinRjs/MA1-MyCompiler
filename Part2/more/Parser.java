import java.util.*;
import java.io.*;

class Parser {
    List<Symbol> tokensList;
    LexicalUnit token;
    int current = 0;

    public Parser(List<Symbol> symbolList){
        tokensList = symbolList;
        token = getCurrentToken();
        System.out.println(tokensList);
    }

    public void init(){
        program();
    }

    public void program(){
        updateCurrentToken()
        if (token == LexicalUnit.BEGIN){
            System.out.print("1 ");
            match(LexicalUnit.BEGIN); code(); match(LexicalUnit.END);
        }
        else{
            syntax_error(token);
        }
    }

    public void code(){
        LexicalUnit token = getCurrentToken();
        boolean var = token == LexicalUnit.VARNAME;
        boolean ifInstr = token == LexicalUnit.IF;
        boolean whileInstr = token == LexicalUnit.WHILE;
        boolean forInstr = token == LexicalUnit.FOR;
        boolean printInstr = token == LexicalUnit.PRINT;
        boolean readInstr = token == LexicalUnit.READ;
        boolean end = token == LexicalUnit.END;
        boolean endif = token == LexicalUnit.ENDIF;
        boolean elseInstr = token == LexicalUnit.ELSE;
        boolean doneInstr = token == LexicalUnit.DONE;

        if (var || ifInstr || whileInstr || forInstr || printInstr || readInstr){
            System.out.print("3 ");
            instList();
        }
        else if (end || endif || elseInstr || doneInstr){
            System.out.print("2 ");
        }
        else{
            syntax_error(token);
        }
    }

    public void instList(){
        updateCurrentToken();
        boolean var = token == LexicalUnit.VARNAME;
        boolean ifInstr = token == LexicalUnit.IF;
        boolean whileInstr = token == LexicalUnit.WHILE;
        boolean forInstr = token == LexicalUnit.FOR;
        boolean printInstr = token == LexicalUnit.PRINT;
        boolean readInstr = token == LexicalUnit.READ;

        if (var || ifInstr || whileInstr || forInstr || printInstr || readInstr){
            System.out.print("4 ");
            instruction(); afterInstruction();
        }
        else{
            syntax_error(token);
        }
    }

    public void instruction(){
        updateCurrentToken();
        boolean var = token == LexicalUnit.VARNAME;
        boolean ifInstr = token == LexicalUnit.IF;
        boolean whileInstr = token == LexicalUnit.WHILE;
        boolean forInstr = token == LexicalUnit.FOR;
        boolean printInstr = token == LexicalUnit.PRINT;
        boolean readInstr = token == LexicalUnit.READ;

        if (var){
            System.out.print("5 ");
            assign();
        }
        else if (ifInstr){
            System.out.print("6 ");
            ifState();
        }
        else if (whileInstr){
            System.out.print("7 ");
            whileState();
        }
        else if (forInstr){
            System.out.print("8 ");
            forState();
        }
        else if (printInstr){
            System.out.print("9 ");
            printState();
        }
        else if (readInstr){
            System.out.print("10 ");
            readState();
        }
        else{
            syntax_error(token);
        }
    }

    public void afterInstruction(){
        updateCurrentToken();
        boolean semicolon = token == LexicalUnit.SEMICOLON;
        boolean end = token == LexicalUnit.END;
        boolean endif = token == LexicalUnit.ENDIF;
        boolean elseInstr = token == LexicalUnit.ELSE;
        boolean doneInstr = token == LexicalUnit.DONE;

        if (semicolon){
            System.out.print("12 ");
            match(LexicalUnit.SEMICOLON); instList();
        }
        else if (end || endif || elseInstr || doneInstr){
            System.out.print("11 ");
        }
        else{
            syntax_error(token);
        }
    }

    public void assign(){
        updateCurrentToken();
        boolean var = token == LexicalUnit.VARNAME;
        if (var){
            System.out.print("13 ");
            match(LexicalUnit.VARNAME); match(LexicalUnit.ASSIGN); expression();
        }
        else{
            syntax_error(token);
        }
    }

    public LexicalUnit getCurrentToken(){
        LexicalUnit tokenType = tokensList.get(current).getType();
        return tokenType;
    }

    public void match(LexicalUnit tokenType){
        if (tokensList.get(current).getType() == tokenType){
            current++;
        }
        else{
            syntax_error(tokenType);
        }
    }

    public void updateCurrentToken(){
        token = getCurrentToken();
    }

    public void syntax_error(LexicalUnit tokenType){
        System.out.println("Expected "+ tokenType + " but got "+tokenList.get(current).getType());
        System.exit(0);
    }
}
