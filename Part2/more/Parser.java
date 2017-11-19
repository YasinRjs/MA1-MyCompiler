import java.util.*;
import java.io.*;

class Parser {
    private String fileName = "grammar.txt";
    private List<Symbol> tokensList;
    private Stack<Symbol> stack;
    private List<String> terminalsList = new ArrayList<String>();
    private List<String> statesList = new ArrayList<String>();
    private ArrayList<ArrayList<String>> grammarRules = new ArrayList<ArrayList<String>>();

    public Parser(List<Symbol> symbolList){
        tokensList = symbolList;
        stack = new Stack();
        getGrammarFromFile(fileName);
    }

    public void getGrammarFromFile(String myFile){
        try{
            FileReader fileReader = new FileReader(myFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String states = bufferedReader.readLine();
            String terminals = bufferedReader.readLine();
            fillStatesList(states);
            fillTerminalsList(terminals);
            fillGrammar(bufferedReader);
        }
        catch(Exception e){
            System.out.println("Problem in grammar file");
        }
    }

    public void fillStatesList(String line){
        String[] newList = line.split(" ");
        for (int i=0; i<newList.length; ++i){
            statesList.add(newList[i]);
        }
    }

    public void fillTerminalsList(String line){
        String[] newList = line.split(" ");
        for (int i=0; i<newList.length; ++i){
            terminalsList.add(newList[i]);
        }
    }

    public void fillGrammar(BufferedReader bufferedReader){
        try{
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                String[] elemLine = line.split(" ");
                ArrayList<String> rules = new ArrayList<String>();
                for (int i=0; i<elemLine.length; ++i){
                    rules.add(elemLine[i]);
                }
                grammarRules.add(rules);
            }
        }
        catch(Exception e){
            System.out.println("Problem in grammar");
        }
        printGrammarRules();
    }

    public void printGrammarRules(){
        for(int i=0; i<grammarRules.size(); ++i){
            for(int j=0; j<grammarRules.get(i).size(); ++j){
                String z = " ";
                if (grammarRules.get(i).get(j).length() == 1){
                    z = "  ";
                }
                System.out.print(grammarRules.get(i).get(j) + z);
            }
            System.out.println();
        }
    }

    public void start(){
        Symbol firstState = new Symbol(null, "<Program>");
        stack.push(firstState);
    }

}
