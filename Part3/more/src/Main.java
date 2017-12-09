import java.io.*;
class Main {
    public static void main(String[] args) {
        LexicalAnalyzer.main(args);
        Rules.prepareVariables(LexicalAnalyzer.getVariablesList());
        Parser pars = new Parser(LexicalAnalyzer.getSymbolList());
        pars.init();
        try {
            System.out.println("---------------------------------");
            System.out.println("The generated code is:");
            BufferedReader br = new BufferedReader(new FileReader("file.lli"));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("---------------------------------");
            System.out.println("The code is contained in file.lli");
            System.out.println("To compile: llvm-as file.lli -o=test.bc");
            System.out.println("To execute: lli test.bc");
        } catch (IOException e){
            System.err.println("Error while reading generated code");
        }

    }

}
