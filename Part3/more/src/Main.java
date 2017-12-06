class Main {
    public static void main(String[] args) {
        LexicalAnalyzer.main(args);
        Rules.prepareVariables(LexicalAnalyzer.getVariablesList());
        Parser pars = new Parser(LexicalAnalyzer.getSymbolList());
        pars.init();
    }
}
