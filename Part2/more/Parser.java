import java.util.*;


class Parser {
    private List<Symbol> tokensList;
    private int tokenIndex;
//    private HashMap<LexicalUnit, Runnable> HashMap;
    private boolean isGood;
    private int parenCounter;

    public Parser(List<Symbol> symbolList){
        tokensList = symbolList;
        tokenIndex = 0;
        isGood = true;
        parenCounter = 0;
//        createHashMap();
    }

    public void printError(){
        Symbol token = tokensList.get(tokenIndex);
        isGood = false;
        System.out.println("--- We have a syntax error with ---\nToken: ["+token.getValue() +"] at line " +token.getLine());
    }

    public void incrementIndex(){
        tokenIndex++;
    }

    public void start() {
        Symbol token = tokensList.get(tokenIndex);
        if (token.getType() == LexicalUnit.BEGIN){
            incrementIndex();
            analyzeInstruction();
            // Si jamais on est au dernier token avec un "END"
            if (isGood){
                if (tokenIndex+1 == tokensList.size() && getCurrentTokenType() == LexicalUnit.END){
                    System.out.println("On a fini.");
                }
                else{
                    System.out.println("There cannot be any instruction after an end instruction.");
                }

            }
            // Sinon c'est une erreur
        }
        else{
            printError();
        }
    }
/*
    public void createHashMap(){
        HashMap = new HashMap<LexicalUnit,Runnable>();
        HashMap.put(LexicalUnit.BEGIN, () -> begin());
    }

    public void analyze(Symbol token){
        HashMap.get(token.getType()).run();
    }
*/
    public void analyzeInstruction(){
        Symbol token = tokensList.get(tokenIndex);
        LexicalUnit tokenType = token.getType();
        if (tokenType == LexicalUnit.END){
            // print LL(1)
        }
        else if (tokenType == LexicalUnit.VARNAME){
            incrementIndex();
            analyzeAssign();
        }
        else if (tokenType == LexicalUnit.IF) {

        }
        else if (tokenType == LexicalUnit.WHILE){

        }
        else if (tokenType == LexicalUnit.FOR){

        }
        else if (tokenType == LexicalUnit.PRINT){

        }
        else if (tokenType == LexicalUnit.READ){

        }
        else {
            printError();
        }

    }

    public LexicalUnit getCurrentTokenType(){
        return tokensList.get(tokenIndex).getType();
    }

    public void analyzeAssign(){
        // On attend un := pour l'assignation
        if (getCurrentTokenType() == LexicalUnit.ASSIGN) {
            incrementIndex();
            analyzeExprArithmAssign();
        }
        else {
            printError();
        }
    }

    public void analyzeExprArithmAssign(){
        // On attend une expr arithm
        if (getCurrentTokenType() == LexicalUnit.VARNAME || getCurrentTokenType() == LexicalUnit.NUMBER){
            incrementIndex();
            if (getCurrentTokenType() == LexicalUnit.PLUS || getCurrentTokenType() == LexicalUnit.MINUS || getCurrentTokenType() == LexicalUnit.TIMES || getCurrentTokenType() == LexicalUnit.DIVIDE){
                incrementIndex();
                analyzeExprArithmAssign();
            }
            else if (getCurrentTokenType() == LexicalUnit.SEMICOLON){
                incrementIndex();
                analyzeInstruction();
            }
            else if (getCurrentTokenType() != LexicalUnit.END && parenCounter == 0)  {
                printError();
            }
        }

        else if (getCurrentTokenType() == LexicalUnit.LPAREN){
            incrementIndex();
            parenCounter++;
            analyzeExprArithmAssign();
            System.out.println(getCurrentTokenType());
            if (tokenIndex+1 == tokensList.size()){
                printError();
            }
            else {
                incrementIndex();
                if (getCurrentTokenType() != LexicalUnit.RPAREN) {
                    printError();
                }
                else{
                    parenCounter--;
                }
            }
        }

        else if (getCurrentTokenType() == LexicalUnit.MINUS) {
            incrementIndex();
            analyzeExprArithmAssign();
        }
        else {
            printError();
        }
    }


}
