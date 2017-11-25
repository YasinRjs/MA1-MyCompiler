import java.util.*;
import java.io.*;

/**
Represent a parser for the language IMP
*/
class Parser {
    /* Those boolean allows us to avoid writing
    the same if, else if... conditions repetedively */
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

    GenericStack<Symbol> tokensList;
    LexicalUnit tokenType;
    Symbol token;

    /**
     * Constructor
     * @param  List<Symbol> symbolList    List of the scanned token
     */
    public Parser(GenericStack<Symbol> symbolList){
        tokensList = symbolList;
        updateCurrentToken();
    }

    /**
     * Start the parsing
     */
    public void init(){
        try {
            program();
            System.out.println();
            System.out.println("------- Parsing done with success ------------");
        }
        catch (ParsingException e){
            System.exit(1);
        }
    }

    /**
     * Apply the rule X
     * @param int number number of the rule used
     * @throws ParsingException A syntax error has been met
     */
    public void applyRule(int number) throws ParsingException{
        Rules.applyRule(this, number);
    }

    /**
     * Program variable
     * @throws ParsingException A syntax error has been met
     */
    public void program() throws ParsingException{
        if (begin){
            applyRule(1);
        }
        else{
            syntax_error();
        }
    }

    /**
     * Code variable
     * @throws ParsingException A syntax error has been met
     */
    public void code() throws ParsingException{
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

    /**
     * InstList variable
     * @throws ParsingException A syntax error has been met
     */
    public void instList() throws ParsingException{
        if (var || ifInstr || whileInstr || forInstr || printInstr || readInstr){
            applyRule(4);
        }
        else{
            syntax_error();
        }
    }

    /**
     * Instruction variable
     * @throws ParsingException A syntax error has been met
     */
    public void instruction() throws ParsingException{
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

    /**
     * AfterInstruction variable
     * @throws ParsingException A syntax error has been met
     */
    public void afterInstruction() throws ParsingException{
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

    /**
     * Assign variable
     * @throws ParsingException A syntax error has been met
     */
    public void assign() throws ParsingException{
        if (var){
            applyRule(13);
        }
        else{
            syntax_error();
        }
    }

    /**
     * Expr variable
     * @throws ParsingException A syntax error has been met
     */
    public void expression() throws ParsingException{
        if (var || num || lparen || minusOp){
            applyRule(14);
        }
        else {
            syntax_error();
        }
    }

    /**
     * Expr' variable
     * @throws ParsingException A syntax error has been met
     */
    public void expressionPrime() throws ParsingException{
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

    /**
     * ProdOrDiv variable
     * @throws ParsingException A syntax error has been met
     */
    public void prodOrDiv() throws ParsingException{
        if (var || num || lparen || minusOp){
            applyRule(17);
        }
        else{
            syntax_error();
        }
    }

    /**
     * ProdOrDiv' variable
     * @throws ParsingException A syntax error has been met
     */
    public void prodOrDivPrime() throws ParsingException{
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

    /**
     * Atom variable
     * @throws ParsingException A syntax error has been met
     */
    public void atom() throws ParsingException{
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

    /**
     * FirstOp variable
     * @throws ParsingException A syntax error has been met
     */
    public void firstOp() throws ParsingException{
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

    /**
     * SecondOp variable
     * @throws ParsingException A syntax error has been met
     */
    public void secondOp() throws ParsingException{
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

    /**
     * If variable
     * @throws ParsingException A syntax error has been met
     */
    public void ifState() throws ParsingException{
        if (ifInstr){
            applyRule(28);
        }
        else{
            syntax_error();
        }
    }

    /**
     * AfterIf variable
     * @throws ParsingException A syntax error has been met
     */
    public void afterIf() throws ParsingException{
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

    /**
     * Cond variable
     * @throws ParsingException A syntax error has been met
     */
    public void cond() throws ParsingException{
        if (var || num || minusOp || lparen || notOp){
            applyRule(31);
        }
        else {
            syntax_error();
        }
    }

    /**
     * Cond' variable
     * @throws ParsingException A syntax error has been met
     */
    public void condPrime() throws ParsingException{
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

    /**
     * AndCond variable
     * @throws ParsingException A syntax error has been met
     */
    public void andCond() throws ParsingException{
        if (var || num || minusOp || lparen || notOp){
            applyRule(34);
        }
        else {
            syntax_error();
        }
    }

    /**
     * AndCond' variable
     * @throws ParsingException A syntax error has been met
     */
    public void andCondPrime() throws ParsingException{
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

    /**
     * SimpleCond variable
     * @throws ParsingException A syntax error has been met
     */
    public void simpleCond() throws ParsingException{
        if (var || num || minusOp || lparen || notOp){
            applyRule(37);
        }
        else {
            syntax_error();
        }
    }

    /**
     * NotCond variable
     * @throws ParsingException A syntax error has been met
     */
    public void notCond() throws ParsingException{
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

    /**
     * Comp variable
     * @throws ParsingException A syntax error has been met
     */
    public void comp() throws ParsingException{
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

    /**
     * While variable
     * @throws ParsingException A syntax error has been met
     */
    public void whileState() throws ParsingException{
        if (whileInstr){
            applyRule(46);
        }
        else{
            syntax_error();
        }
    }

    /**
     * For variable
     * @throws ParsingException A syntax error has been met
     */
    public void forState() throws ParsingException{
        if (forInstr){
            applyRule(47);
        }
        else{
            syntax_error();
        }
    }

    /**
     * AfterFor variable
     * @throws ParsingException A syntax error has been met
     */
    public void afterFor() throws ParsingException{
        if (by || to){
            applyRule(48);
        }
        else{
            syntax_error();
        }
    }

    /**
     * ByExpr variable
     * @throws ParsingException A syntax error has been met
     */
    public void byExpr() throws ParsingException{
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

    /**
     * Print variable
     * @throws ParsingException A syntax error has been met
     */
    public void printState() throws ParsingException{
        if (printInstr){
            applyRule(51);
        }
        else {
            syntax_error();
        }
    }

    /**
     * Read variable
     * @throws ParsingException A syntax error has been met
     */
    public void readState() throws ParsingException{
        if (readInstr){
            applyRule(52);
        }
        else {
            syntax_error();
        }
    }

    /**
     * Get the current token
     * @return token
     */
    public Symbol getCurrentToken(){
        Symbol newToken = tokensList.pop();
        return newToken;
    }

    /**
     * Check wether the token type matches what the parser expected
     * @param  LexicalUnit      tokenType     The expected token type
     * @throws ParsingException A syntax error has been met
     */
    public void match(LexicalUnit expectedTokenType) throws ParsingException{
        if (tokenType == expectedTokenType){
            if (!tokensList.empty()){
                updateCurrentToken();
            }
        }
        else{
            syntax_error();
        }
    }

    /**
     * Update all the boolean attributes to avoid rewriting
     * the same if conditions over and over again
     */
    public void updateCurrentToken(){
        token = getCurrentToken();
        tokenType = token.getType();

        begin = tokenType == LexicalUnit.BEGIN;
        var = tokenType == LexicalUnit.VARNAME;
        num = tokenType == LexicalUnit.NUMBER;
        ifInstr = tokenType == LexicalUnit.IF;
        whileInstr = tokenType == LexicalUnit.WHILE;
        forInstr = tokenType == LexicalUnit.FOR;
        printInstr = tokenType == LexicalUnit.PRINT;
        readInstr = tokenType == LexicalUnit.READ;
        end = tokenType == LexicalUnit.END;
        endif = tokenType == LexicalUnit.ENDIF;
        elseInstr = tokenType == LexicalUnit.ELSE;
        doneInstr = tokenType == LexicalUnit.DONE;
        by = tokenType == LexicalUnit.BY;
        to = tokenType == LexicalUnit.TO;
        equal = tokenType == LexicalUnit.EQ;
        leq = tokenType == LexicalUnit.LEQ;
        gt = tokenType == LexicalUnit.GT;
        geq = tokenType == LexicalUnit.GEQ;
        lt = tokenType == LexicalUnit.LT;
        neq = tokenType == LexicalUnit.NEQ;
        notOp = tokenType == LexicalUnit.NOT;
        minusOp = tokenType == LexicalUnit.MINUS;
        plusOp = tokenType == LexicalUnit.PLUS;
        lparen = tokenType == LexicalUnit.LPAREN;
        andInstr = tokenType ==LexicalUnit.AND;
        orInstr = tokenType == LexicalUnit.OR;
        then = tokenType == LexicalUnit.THEN;
        doInstr = tokenType == LexicalUnit.DO;
        timesOp = tokenType == LexicalUnit.TIMES;
        divOp = tokenType == LexicalUnit.DIVIDE;
        semicolon = tokenType == LexicalUnit.SEMICOLON;
        rparen = tokenType == LexicalUnit.RPAREN;
    }

    /**
     * Throw the ParsingException
     * @throws ParsingException A syntax error has been met
     */
    public void syntax_error() throws ParsingException{
        throw new ParsingException(token);
    }
}
