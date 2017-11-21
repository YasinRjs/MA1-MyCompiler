import java.util.*;
import java.io.*;

class Parser {
    boolean begin;
    boolean var;
    boolean num;
    boolean ifInstr;
    boolean whileInstr;
    boolean forInstr;
    boolean printInstr;
    boolean readInstr;
    boolean end;
    boolean endif;
    boolean elseInstr;
    boolean doneInstr;
    boolean by;
    boolean to;
    boolean equal;
    boolean leq;
    boolean gt;
    boolean geq;
    boolean lt;
    boolean neq;
    boolean notOp;
    boolean minusOp;
    boolean plusOp;
    boolean lparen;
    boolean andInstr;
    boolean orInstr;
    boolean then;
    boolean doInstr;
    boolean timesOp;
    boolean divOp;
    boolean semicolon;
    boolean rparen;
    List<Symbol> tokensList;
    LexicalUnit token;
    int current = 0;

    public Parser(List<Symbol> symbolList){
        tokensList = symbolList;
        updateCurrentToken();
    }

    public void init(){
        program();
        System.out.println();
        System.out.println("------- Parsing done with success ------------");
    }

    public void applyRule(int number){
        Rules.applyRule(this, number);
    }

    public void program(){
        if (begin){
            applyRule(1);
        }
        else{
            syntax_error();
        }
    }

    public void code(){
        if (var || ifInstr || whileInstr || forInstr || printInstr || readInstr){
            applyRule(3);
        }
        else if (end || endif || elseInstr || doneInstr){
            applyRule(2);
        }
        else{
            syntax_error();
        }
    }

    public void instList(){
        if (var || ifInstr || whileInstr || forInstr || printInstr || readInstr){
            applyRule(4);
        }
        else{
            syntax_error();
        }
    }

    public void instruction(){
        if (var){
            applyRule(5);
        }
        else if (ifInstr){
            applyRule(6);
        }
        else if (whileInstr){
            applyRule(7);
        }
        else if (forInstr){
            applyRule(8);
        }
        else if (printInstr){
            applyRule(9);
        }
        else if (readInstr){
            applyRule(10);
        }
        else{
            syntax_error();
        }
    }

    public void afterInstruction(){
        if (semicolon){
            applyRule(12);
        }
        else if (end || endif || elseInstr || doneInstr){
            applyRule(11);
        }
        else{
            syntax_error();
        }
    }

    public void assign(){
        if (var){
            applyRule(13);
        }
        else{
            syntax_error();
        }
    }

    public void expression(){
        if (var || num || lparen || minusOp){
            applyRule(14);
        }
        else {
            syntax_error();
        }
    }

    public void expressionPrime(){
        if (plusOp || minusOp){
            applyRule(15);
        }
        else if (semicolon || end || endif || elseInstr || doneInstr || rparen || doInstr || to ||
                 andInstr || orInstr || then || by || equal || leq || gt || geq || lt || neq){
            applyRule(16);
        }
        else{
            syntax_error();
        }
    }

    public void prodOrDiv(){
        if (var || num || lparen || minusOp){
            applyRule(17);
        }
        else{
            syntax_error();
        }
    }

    public void prodOrDivPrime(){
        if (timesOp || divOp){
            applyRule(18);
        }
        else if (semicolon || end || endif || elseInstr || doneInstr || rparen || doInstr || to ||
                 andInstr || orInstr || then || by || equal || leq || gt || geq || lt || neq || plusOp || minusOp){
            applyRule(19);
        }
        else{
            syntax_error();
        }
    }

    public void atom(){
        if (minusOp){
            applyRule(20);
        }
        else if (var){
            applyRule(21);
        }
            else if (num){
            applyRule(22);
        }
        else if (lparen){
            applyRule(23);
        }
        else {
            syntax_error();
        }
    }

    public void firstOp(){
        if (timesOp){
            applyRule(24);
        }
        else if (divOp){
            applyRule(25);
        }
        else{
            syntax_error();
        }
    }

    public void secondOp(){
        if (plusOp){
            applyRule(26);
        }
        else if (minusOp){
            applyRule(27);
        }
        else {
            syntax_error();
        }
    }

    public void ifState(){
        if (ifInstr){
            applyRule(28);
        }
        else{
            syntax_error();
        }
    }

    public void afterIf(){
        if (endif){
            applyRule(29);
        }
        else if (elseInstr){
            applyRule(30);
        }
        else{
            syntax_error();
        }
    }

    public void cond(){
        if (var || num || minusOp || lparen || notOp){
            applyRule(31);
        }
        else {
            syntax_error();
        }
    }

    public void condPrime(){
        if (orInstr){
            applyRule(32);
        }
        else if (then || doInstr){
            applyRule(33);
        }
        else {
            syntax_error();
        }
    }

    public void andCond(){
        if (var || num || minusOp || lparen || notOp){
            applyRule(34);
        }
        else {
            syntax_error();
        }
    }

    public void andCondPrime(){
        if (andInstr){
            applyRule(35);
        }
        else if (orInstr || then || doInstr){
            applyRule(36);
        }
        else{
            syntax_error();
        }
    }

    public void simpleCond(){
        if (var || num || minusOp || lparen || notOp){
            applyRule(37);
        }
        else {
            syntax_error();
        }
    }

    public void notCond(){
        if (notOp){
            applyRule(38);
        }
        else if (num || var || minusOp || lparen){
            applyRule(39);
        }
        else{
            syntax_error();
        }
    }

    public void comp(){
        if (equal){
            applyRule(40);
        }
        else if (leq){
            applyRule(41);
        }
        else if (gt){
            applyRule(42);
        }
        else if (geq){
            applyRule(43);
        }
        else if (lt){
            applyRule(44);
        }
        else if (neq){
            applyRule(45);
        }
        else{
            syntax_error();
        }
    }

    public void whileState(){
        if (whileInstr){
            applyRule(46);
        }
        else{
            syntax_error();
        }
    }

    public void forState(){
        if (forInstr){
            applyRule(47);
        }
        else{
            syntax_error();
        }
    }

    public void afterFor(){
        if (by || to){
            applyRule(48);
        }
        else{
            syntax_error();
        }
    }

    public void byExpr(){
        if (by){
            applyRule(49);
        }
        else if (to){
            applyRule(50);
        }
        else{
            syntax_error();
        }
    }

    public void printState(){
        if (printInstr){
            applyRule(51);
        }
        else {
            syntax_error();
        }
    }

    public void readState(){
        if (readInstr){
            applyRule(52);
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

        begin = token == LexicalUnit.BEGIN;
        var = token == LexicalUnit.VARNAME;
        num = token == LexicalUnit.NUMBER;
        ifInstr = token == LexicalUnit.IF;
        whileInstr = token == LexicalUnit.WHILE;
        forInstr = token == LexicalUnit.FOR;
        printInstr = token == LexicalUnit.PRINT;
        readInstr = token == LexicalUnit.READ;
        end = token == LexicalUnit.END;
        endif = token == LexicalUnit.ENDIF;
        elseInstr = token == LexicalUnit.ELSE;
        doneInstr = token == LexicalUnit.DONE;
        by = token == LexicalUnit.BY;
        to = token == LexicalUnit.TO;
        equal = token == LexicalUnit.EQ;
        leq = token == LexicalUnit.LEQ;
        gt = token == LexicalUnit.GT;
        geq = token == LexicalUnit.GEQ;
        lt = token == LexicalUnit.LT;
        neq = token == LexicalUnit.NEQ;
        notOp = token == LexicalUnit.NOT;
        minusOp = token == LexicalUnit.MINUS;
        plusOp = token == LexicalUnit.PLUS;
        lparen = token == LexicalUnit.LPAREN;
        andInstr = token ==LexicalUnit.AND;
        orInstr = token == LexicalUnit.OR;
        then = token == LexicalUnit.THEN;
        doInstr = token == LexicalUnit.DO;
        timesOp = token == LexicalUnit.TIMES;
        divOp = token == LexicalUnit.DIVIDE;
        semicolon = token == LexicalUnit.SEMICOLON;
        rparen = token == LexicalUnit.RPAREN;
    }

    public void syntax_error(){
        System.out.println("--------");
        System.out.println("Didn't expect "+ token + ".");
        System.out.println(current);
        System.exit(0);
    }
}
