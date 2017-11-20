import java.util.*;
import java.io.*;

class Parser {
    List<Symbol> tokensList;
    LexicalUnit token;
    int current = 0;

    public Parser(List<Symbol> symbolList){
        tokensList = symbolList;
        updateCurrentToken();
        for (int i=0; i<tokensList.size(); ++i){
            System.out.println(i + ": " + tokensList.get(i));
        }
    }

    public void init(){
        program();
        System.out.println();
        System.out.println("------- Parsing done with success ------------");
    }

    public void program(){
        if (token == LexicalUnit.BEGIN){
            System.out.print("1 ");
            match(LexicalUnit.BEGIN); code(); match(LexicalUnit.END);
        }
        else{
            syntax_error();
        }
    }

    public void code(){
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
            syntax_error();
        }
    }

    public void instList(){
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
            syntax_error();
        }
    }

    public void instruction(){
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
            syntax_error();
        }
    }

    public void afterInstruction(){
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
            syntax_error();
        }
    }

    public void assign(){
        boolean var = token == LexicalUnit.VARNAME;
        if (var){
            System.out.print("13 ");
            match(LexicalUnit.VARNAME); match(LexicalUnit.ASSIGN); expression();
        }
        else{
            syntax_error();
        }
    }

    public void expression(){
        boolean var = token == LexicalUnit.VARNAME;
        boolean num = token == LexicalUnit.NUMBER;
        boolean lparen = token == LexicalUnit.LPAREN;
        boolean minus = token == LexicalUnit.MINUS;
        if (var || num || lparen || minus){
            System.out.print("14 ");
            prodOrDiv(); expressionPrime();
        }
        else {
            syntax_error();
        }
    }

    public void expressionPrime(){
        boolean plusOp = token == LexicalUnit.PLUS;
        boolean minusOp = token == LexicalUnit.MINUS;

        boolean semicolon = token == LexicalUnit.SEMICOLON;
        boolean end = token == LexicalUnit.END;
        boolean endif = token == LexicalUnit.ENDIF;
        boolean elseInstr = token == LexicalUnit.ELSE;
        boolean doneInstr = token == LexicalUnit.DONE;
        boolean rparen = token == LexicalUnit.RPAREN;
        boolean doInstr = token == LexicalUnit.DO;
        boolean to = token == LexicalUnit.TO;
        boolean andInstr = token == LexicalUnit.AND;
        boolean orInstr = token == LexicalUnit.OR;
        boolean then = token == LexicalUnit.THEN;
        boolean by = token == LexicalUnit.BY;
        boolean equal = token == LexicalUnit.EQ;
        boolean leq = token == LexicalUnit.LEQ;
        boolean gt = token == LexicalUnit.GT;
        boolean geq = token == LexicalUnit.GEQ;
        boolean lt = token == LexicalUnit.LT;
        boolean neq = token == LexicalUnit.NEQ;

        if (plusOp || minusOp){
            System.out.print("15 ");
            secondOp(); expression();
        }
        else if (semicolon || end || endif || elseInstr || doneInstr || rparen || doInstr || to ||
                 andInstr || orInstr || then || by || equal || leq || gt || geq || lt || neq){
            System.out.print("16 ");
        }
        else{
            syntax_error();
        }
    }

    public void prodOrDiv(){
        boolean var = token == LexicalUnit.VARNAME;
        boolean num = token == LexicalUnit.NUMBER;
        boolean lparen = token == LexicalUnit.LPAREN;
        boolean minusOp = token == LexicalUnit.MINUS;

        if (var || num || lparen || minusOp){
            System.out.print("17 ");
            atom(); prodOrDivPrime();
        }
        else{
            syntax_error();
        }
    }

    public void prodOrDivPrime(){
        boolean timesOp = token == LexicalUnit.TIMES;
        boolean divOp = token == LexicalUnit.DIVIDE;

        boolean semicolon = token == LexicalUnit.SEMICOLON;
        boolean end = token == LexicalUnit.END;
        boolean endif = token == LexicalUnit.ENDIF;
        boolean elseInstr = token == LexicalUnit.ELSE;
        boolean doneInstr = token == LexicalUnit.DONE;
        boolean rparen = token == LexicalUnit.RPAREN;
        boolean doInstr = token == LexicalUnit.DO;
        boolean to = token == LexicalUnit.TO;
        boolean andInstr = token == LexicalUnit.AND;
        boolean orInstr = token == LexicalUnit.OR;
        boolean then = token == LexicalUnit.THEN;
        boolean by = token == LexicalUnit.BY;
        boolean equal = token == LexicalUnit.EQ;
        boolean leq = token == LexicalUnit.LEQ;
        boolean gt = token == LexicalUnit.GT;
        boolean geq = token == LexicalUnit.GEQ;
        boolean lt = token == LexicalUnit.LT;
        boolean neq = token == LexicalUnit.NEQ;
        boolean plusOp = token == LexicalUnit.PLUS;
        boolean minusOp = token == LexicalUnit.MINUS;

        if (timesOp || divOp){
            System.out.print("18 ");
            firstOp(); prodOrDiv();
        }
        else if (semicolon || end || endif || elseInstr || doneInstr || rparen || doInstr || to ||
                 andInstr || orInstr || then || by || equal || leq || gt || geq || lt || neq || plusOp || minusOp){
            System.out.print("19 ");
        }
        else{
            syntax_error();
        }
    }

    public void atom(){
        boolean minusOp = token == LexicalUnit.MINUS;
        boolean var = token == LexicalUnit.VARNAME;
        boolean num = token == LexicalUnit.NUMBER;
        boolean lparen = token == LexicalUnit.LPAREN;

        if (minusOp){
            System.out.print("20 ");
            match(LexicalUnit.MINUS); atom();
        }
        else if (var){
            System.out.print("21 ");
            match(LexicalUnit.VARNAME);
        }
        else if (num){
            System.out.print("22 ");
            match(LexicalUnit.NUMBER);
        }
        else if (lparen){
            System.out.print("23 ");
            match(LexicalUnit.LPAREN); expression(); match(LexicalUnit.RPAREN);
        }
        else {
            syntax_error();
        }
    }

    public void firstOp(){
        boolean timesOp = token == LexicalUnit.TIMES;
        boolean divOp = token == LexicalUnit.DIVIDE;

        if (timesOp){
            System.out.print("24 ");
            match(LexicalUnit.TIMES);
        }
        else if (divOp){
            System.out.print("25 ");
            match(LexicalUnit.DIVIDE);
        }
        else{
            syntax_error();
        }
    }

    public void secondOp(){
        boolean plusOp = token == LexicalUnit.PLUS;
        boolean minusOp = token == LexicalUnit.MINUS;

        if (plusOp){
            System.out.print("26 ");
            match(LexicalUnit.PLUS);
        }
        else if (minusOp){
            System.out.print("27 ");
            match(LexicalUnit.MINUS);
        }
        else {
            syntax_error();
        }
    }

    public void ifState(){
        boolean ifInstr = token == LexicalUnit.IF;

        if (ifInstr){
            System.out.print("28 ");
            match(LexicalUnit.IF); cond(); match(LexicalUnit.THEN); code(); afterIf();
        }
        else{
            syntax_error();
        }
    }

    public void afterIf(){
        boolean endif = token == LexicalUnit.ENDIF;
        boolean elseInstr = token == LexicalUnit.ELSE;

        if (endif){
            System.out.print("29 ");
            match(LexicalUnit.ENDIF);
        }
        else if (elseInstr){
            System.out.print("30 ");
            match(LexicalUnit.ELSE); code(); match(LexicalUnit.ENDIF);
        }
        else{
            syntax_error();
        }
    }

    public void cond(){
        boolean minusOp = token == LexicalUnit.MINUS;
        boolean var = token == LexicalUnit.VARNAME;
        boolean num = token == LexicalUnit.NUMBER;
        boolean lparen = token == LexicalUnit.LPAREN;
        boolean notOp = token == LexicalUnit.NOT;

        if (var || num || minusOp || lparen || notOp){
            System.out.print("31 ");
            andCond(); condPrime();
        }
        else {
            syntax_error();
        }
    }

    public void condPrime(){
        boolean orInstr = token == LexicalUnit.OR;
        boolean then = token == LexicalUnit.THEN;
        boolean doInstr = token == LexicalUnit.DO;

        if (orInstr){
            System.out.print("32 ");
            match(LexicalUnit.OR); cond();
        }
        else if (then || doInstr){
            System.out.print("33 ");
        }
        else {
            syntax_error();
        }
    }

    public void andCond(){
        boolean minusOp = token == LexicalUnit.MINUS;
        boolean var = token == LexicalUnit.VARNAME;
        boolean num = token == LexicalUnit.NUMBER;
        boolean lparen = token == LexicalUnit.LPAREN;
        boolean notOp = token == LexicalUnit.NOT;

        if (var || num || minusOp || lparen || notOp){
            System.out.print("34 ");
            simpleCond(); andCondPrime();
        }
        else {
            syntax_error();
        }
    }

    public void andCondPrime(){
        boolean andInstr = token ==LexicalUnit.AND;
        boolean orInstr = token == LexicalUnit.OR;
        boolean then = token == LexicalUnit.THEN;
        boolean doInstr = token == LexicalUnit.DO;

        if (andInstr){
            System.out.print("35 ");
            match(LexicalUnit.AND); andCond();
        }
        else if (orInstr || then || doInstr){
            System.out.print("36 ");
        }
        else{
            syntax_error();
        }
    }

    public void simpleCond(){
        boolean minusOp = token == LexicalUnit.MINUS;
        boolean var = token == LexicalUnit.VARNAME;
        boolean num = token == LexicalUnit.NUMBER;
        boolean lparen = token == LexicalUnit.LPAREN;
        boolean notOp = token == LexicalUnit.NOT;

        if (var || num || minusOp || lparen || notOp){
            System.out.print("37 ");
            notCond(); expression(); comp(); expression();
        }
        else {
            syntax_error();
        }
    }

    public void notCond(){
        boolean notOp = token == LexicalUnit.NOT;
        boolean minusOp = token == LexicalUnit.MINUS;
        boolean var = token == LexicalUnit.VARNAME;
        boolean num = token == LexicalUnit.NUMBER;
        boolean lparen = token == LexicalUnit.LPAREN;

        if (notOp){
            System.out.print("38 ");
            match(LexicalUnit.NOT);
        }
        else if (num || var || minusOp || lparen){
            System.out.print("39 ");
        }
        else{
            syntax_error();
        }
    }

    public void comp(){
        boolean equal = token == LexicalUnit.EQ;
        boolean leq = token == LexicalUnit.LEQ;
        boolean gt = token == LexicalUnit.GT;
        boolean geq = token == LexicalUnit.GEQ;
        boolean lt = token == LexicalUnit.LT;
        boolean neq = token == LexicalUnit.NEQ;

        if (equal){
            System.out.print("40 ");
            match(LexicalUnit.EQ);
        }
        else if (leq){
            System.out.print("41 ");
            match(LexicalUnit.LEQ);
        }
        else if (gt){
            System.out.print("42 ");
            match(LexicalUnit.GT);
        }
        else if (geq){
            System.out.print("43 ");
            match(LexicalUnit.GEQ);
        }
        else if (lt){
            System.out.print("44 ");
            match(LexicalUnit.LT);
        }
        else if (neq){
            System.out.print("45 ");
            match(LexicalUnit.NEQ);
        }
        else{
            syntax_error();
        }
    }

    public void whileState(){
        boolean whileInstr = token == LexicalUnit.WHILE;

        if (whileInstr){
            System.out.print("46 ");
            match(LexicalUnit.WHILE); cond(); match(LexicalUnit.DO); code(); match(LexicalUnit.DONE);
        }
        else{
            syntax_error();
        }
    }

    public void forState(){
        boolean forInstr = token == LexicalUnit.FOR;
        if (forInstr){
            System.out.print("47 ");
            match(LexicalUnit.FOR); match(LexicalUnit.VARNAME); match(LexicalUnit.FROM); expression(); afterFor();
        }
        else{
            syntax_error();
        }
    }

    public void afterFor(){
        boolean by = token == LexicalUnit.BY;
        boolean to = token == LexicalUnit.TO;

        if (by || to){
            System.out.print("48 ");
            byExpr(); match(LexicalUnit.TO); expression(); match(LexicalUnit.DO); code(); match(LexicalUnit.DONE);
        }
        else{
            syntax_error();
        }
    }

    public void byExpr(){
        boolean by = token == LexicalUnit.BY;
        boolean to = token == LexicalUnit.TO;

        if (by){
            System.out.print("49 ");
            match(LexicalUnit.BY); expression();
        }
        else if (to){
            System.out.print("50 ");
        }
        else{
            syntax_error();
        }
    }

    public void printState(){
        boolean printInstr = token == LexicalUnit.PRINT;

        if (printInstr){
            System.out.print("51 ");
            match(LexicalUnit.PRINT); match(LexicalUnit.LPAREN); match(LexicalUnit.VARNAME); match(LexicalUnit.RPAREN);
        }
        else {
            syntax_error();
        }
    }

    public void readState(){
        boolean readInstr = token == LexicalUnit.READ;

        if (readInstr){
            System.out.print("52 ");
            match(LexicalUnit.READ); match(LexicalUnit.LPAREN); match(LexicalUnit.VARNAME); match(LexicalUnit.RPAREN);
        }
        else {
            syntax_error();
        }
    }

    public LexicalUnit getCurrentToken(){
        LexicalUnit tokenType = tokensList.get(current).getType();
        return tokenType;
    }

    public void match(LexicalUnit tokenType){
        if (tokensList.get(current).getType() == tokenType){
            current++;
            if (current != tokensList.size()){
                updateCurrentToken();
            }
        }
        else{
            syntax_error();
        }

    }

    public void updateCurrentToken(){
        token = getCurrentToken();
    }

    public void syntax_error(){
        System.out.println("--------");
        System.out.println("Didn't expect "+ token + ".");
        System.out.println(current);
        System.exit(0);
    }
}
