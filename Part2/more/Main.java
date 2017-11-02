class Main {
    public static void main(String[] args) {
        LexicalAnalyzer.main(args);
        Parser pars = new Parser(LexicalAnalyzer.getSymbolList());
        pars.start();
    }
}
