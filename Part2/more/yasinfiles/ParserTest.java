import java.util.*;
import java.io.*;

class Parser {
    private String fileName = "rules.txt";
    private List<Symbol> tokensList;
    private Stack<Symbol> stack;
    private List<String> terminalsList = new ArrayList<String>();
    private List<String> statesList = new ArrayList<String>();
    private ArrayList<ArrayList<String>> rulesMatrix = new ArrayList<ArrayList<String>>();
    private String startState = "Program";
    private String currentState = startState;

    private ArrayList<ArrayList<String>> grammarMatrix = new ArrayList<ArrayList<String>>();

    public Parser(List<Symbol> symbolList){
        tokensList = symbolList;
        stack = new Stack();
        getGrammarFromFile(fileName);
        start();
    }

    public void getGrammarFromFile(String myFile){
        try{
            FileReader fileReader = new FileReader(myFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String states = bufferedReader.readLine();
            String terminals = bufferedReader.readLine();
            fillStatesList(states);
            fillTerminalsList(terminals);
            fillRules(bufferedReader);
            fillGrammar();
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

    public void fillRules(BufferedReader bufferedReader){
        try{
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                String[] elemLine = line.split(" ");
                ArrayList<String> rules = new ArrayList<String>();
                for (int i=0; i<elemLine.length; ++i){
                    rules.add(elemLine[i]);
                }
                rulesMatrix.add(rules);
            }
        }
        catch(Exception e){
            System.out.println("Problem in grammar");
        }
        printrulesMatrix();
    }

    public void printrulesMatrix(){
        for(int i=0; i<rulesMatrix.size(); ++i){
            for(int j=0; j<rulesMatrix.get(i).size(); ++j){
                String z = " ";
                if (rulesMatrix.get(i).get(j).length() == 1){
                    z = "  ";
                }
                System.out.print(rulesMatrix.get(i).get(j) + z);
            }
            System.out.println();
        }
    }

    public void fillGrammar(){
        grammarMatrix.add("none");
        grammarMatrix.add()
        Program
        Code
        InstList
        Instruction
        AfterInstruction
        Assign
        Expr
        Expr'
        ProdOrDiv
        ProdOrDiv'
        Atom
        FirstOp
        SecondOp
        If
        AfterIf
        Cond
        Cond'
        AndCond
        AndCon'
        SimpleCond
        NotCond
        Comp
        whileFor
        AfterFor
        ByExpr
        Print
        Read

    }

    public void start(){
        Symbol firstState = new Symbol(null, startState);
        stack.push(firstState);

        while (!stack.empty()){
            Symbol token = stack.pop();
            if (token.isTerminal){
                match(token);
            }
            else{
                produce(token);
            }
        }
    }

    public void produce(Symbol token){

    }

}
