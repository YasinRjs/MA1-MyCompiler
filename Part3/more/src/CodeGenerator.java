import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

class CodeGenerator {

    private int instructionCounter;
    private int codeLabel;
    private int labelCounter;
    private int afterIfCounter;
    private boolean codeFound;
    private boolean notSymbol;
    private PrintWriter writer;
    private List<String> variables;
    private List<String> infixNotation;
    private List<String> postfixNotation;
    private List<String> conditions;
    private List<String> conditionsPostfix;
    public CodeGenerator(){
        instructionCounter = 0;
        codeLabel = 0;
        labelCounter = 0;
        afterIfCounter = 0;
        codeFound = false;
        notSymbol = false;
        variables = new ArrayList<String>();
        infixNotation = new ArrayList<String>();
        postfixNotation = new ArrayList<String>();
        conditions = new ArrayList<String>();
        try {
            writer = new PrintWriter("file.lli", "UTF-8");
            init();
        }
        catch (FileNotFoundException|UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    /**
     * Generate the print and read function that will be used later
     */
    public void init(){
        // writing the print function
        writer.println("@.strP = private unnamed_addr constant [4 x i8] c\"%d\\0A\\00\", align 1");
        writer.println("define void @println(i32 %x) {");
        writer.println("%1 = alloca i32, align 4");
        writer.println("store i32 %x, i32* %1, align 4");
        writer.println("%2 = load i32, i32* %1, align 4");
        writer.println("%3 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.strP, i32 0, i32 0), i32 %2)");
        writer.println("ret void");
        writer.println("}");
        writer.println("declare i32 @printf(i8*, ...)");

        writer.println("");
        // writing the read function
        writer.println("@.strR = private unnamed_addr constant [3 x i8] c\"%d\\00\", align 1");
        writer.println("define i32 @readInt() {");
        writer.println("%x = alloca i32, align 4");
        writer.println("%1 = call i32 (i8*, ...) @__isoc99_scanf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.strR, i32 0, i32 0), i32* %x)");
        writer.println("%2 = load i32, i32* %x, align 4");
        writer.println("ret i32 %2");
        writer.println("}");
        writer.println("declare i32 @__isoc99_scanf(i8*, ...)");
    }


    /**
     * Add an element from the expression read
     * @param String element The element to add
     */
    public void addElementInExpression(String element){
        infixNotation.add(element);
    }

    /**
     * Generate the standard beginning of the code
     */
    public void generateBegin(){
        writer.println("define i32 @main() {");
        writer.println("entry:");

    }

    /**
     * Generate the end of the code
     */
    public void generateEnding(){
        writer.println("ret i32 0");
        writer.println("}");
        writer.close();
    }

    /**
     * If the variable doesn't exist, add it in the list
     * @param String var Variable
     */
    private void addVariables(String var){
        if (!variables.contains(var)){
            writer.println(var+ " = alloca i32");
            variables.add(var);
        }
    }
    /**
     * Will generate the code for the Assign instruction
     * @param String var The variable assigned
     */
    public void generateAssign(String var){
        String temporaryVar = generateExpression();
        addVariables(var);
        writer.println("store i32 "+temporaryVar+", i32* "+var);
    }

    public String generateVariable(String var){
        String temporaryVar = "%"+instructionCounter ++;
        writer.println(temporaryVar+" = load i32, i32* "+var);
        return temporaryVar;
    }

    /**
     * Will generate the code for an expression
     * @param String var the variable assigned
     * @return Will return the given temporary var for the expression
     */
    public String generateExpression(){
        postfixNotation = NotationConverter.convertInfixToPostfix(infixNotation);
        if (postfixNotation.size() == 1){
            postfixNotation.add("0");
            postfixNotation.add("+");
        }
        GenericStack<String> operandsStack = new GenericStack<String>();
        boolean isOperator = false;
        for (int i = 0; i < postfixNotation.size(); ++i){
            String element = postfixNotation.get(i);
            String operator = "";
            if (element.equals("+")){
                operator = "add";
                isOperator = true;
            }
            else if (element.equals("-")){
                operator = "sub";
                isOperator = true;
            }
            else if (element.equals("*") || element.equals("~")){
                operator = "mul";
                isOperator = true;
            }
            else if (element.equals("/")){
                operator = "sdiv";
                isOperator = true;
            }
            else{ // It's an operand, we push it into the stack
                operandsStack.push(element);
            }
            if (isOperator){
                String operand2 = operandsStack.pop();
                String operand1;
                if (element.equals("~")){
                    operand1 = "-1";
                }
                else{
                    operand1 = operandsStack.pop();
                }
                String tempVar = "%"+instructionCounter++;
                writer.println(tempVar + " = "+operator+" i32 "+operand1+","+operand2);
                operandsStack.push(tempVar);
                isOperator = false;
            }
        }
        infixNotation.clear();
        postfixNotation.clear();

        return operandsStack.pop();
    }


    public void addCondition(String element){
        conditions.add(element);
    }

    /**
     * Generate the code for the first condition
     * @param String exp1 [description]
     * @param String comp [description]
     * @param String exp2 [description]
     */
    public void generateIf(){
        int i = 0;
        while (i < conditions.size()){
            String element = conditions.get(i);
            if (element.equals("or")){
                if (!codeFound){
                    codeFound = true;
                    codeLabel = labelCounter++;
                }
                String exp1 = conditions.get(++i);
                String comp = convertComp(conditions.get(++i));
                String exp2 = conditions.get(++i);
                writer.println("afterIf"+afterIfCounter++ +":");
                if (i+1 < conditions.size() && conditions.get(i+1).equals("and")) { // it's the last and of a "pair"
                    generateCondition(comp,exp1,exp2,labelCounter);
                }
                else{
                    generateCondition(comp,exp1,exp2,codeLabel);
                }
            }
            else if (element.equals("and")){
                String exp1 = conditions.get(++i);
                String comp = convertComp(conditions.get(++i));
                String exp2 = conditions.get(++i);
                writer.println("label"+labelCounter++ + ":");
                if (codeFound && (i+1 == conditions.size() || conditions.get(i+1).equals("or"))) { // it's the last and of a "pair"
                    generateCondition(comp,exp1,exp2,codeLabel);
                }
                else{
                    System.out.println("----------");
                    System.out.println(labelCounter);
                    generateCondition(comp,exp1,exp2,labelCounter);
                }
            }
            else if(element.equals("not")){
                notSymbol = true;
            }
            else{ // The very first condition
                String exp1 = element;
                String comp = convertComp(conditions.get(++i));
                String exp2 = conditions.get(++i);
                generateCondition(comp,exp1,exp2,labelCounter);
           }
            ++i;
        }
        if (!codeFound){
            writer.println("label"+labelCounter++ +":");
        }
        else{
            writer.println("label"+codeLabel+":");
            codeFound = false;
        }

    }

    /**
     * Generate the condition
     * @param String comp Comparator
     * @param String exp1 Expression 1
     * @param String exp2 Expression 2
     * @param String labelID   ID of the label to write
     */
    public void generateCondition(String comp, String exp1, String exp2, int labelID){
        String temporaryVar = "%"+instructionCounter++;
        writer.println(temporaryVar+ " = icmp "+comp+" i32 "+exp1+","+exp2);
        writer.println("br i1 "+temporaryVar+", label %label"+labelID+", label %afterIf"+afterIfCounter);
    }

    /**
     * Generate the code after the end of an If
     */
    public void generateEndIf(){
        writer.println("br label %afterIf"+afterIfCounter);
        writer.println("afterIf"+afterIfCounter+":");
        afterIfCounter++;
    }

    /**
     * Convert the IMP comp to the adequate llvm comp
     * @param  String comp          the comparator
     * @return        The llvm comparator
     */
    public String convertComp(String comp){
        String res = "";
        if (comp.equals("=")){
            if (notSymbol){
                res = "ne";
            }
            else{
                res = "eq";
            }
        }
        else if (comp.equals("<>")){
            if (notSymbol){
                res = "eq";
            }
            else{
                res = "ne";
            }
        }
        else if (comp.equals("<")){
            if (notSymbol){
                res = "sge";
            }
            else{
                res = "slt";
            }
        }
        else if (comp.equals(">")){
            if (notSymbol){
                res = "sle";
            }
            else{
                res = "sgt";
            }
        }
        else if (comp.equals("<=")){
            if (notSymbol){
                res = "sgt";
            }
            else{
                res = "sle";
            }
        }
        else {  //(comp == ">=")
            if (notSymbol){
                res = "slt";
            }
            else{
                res = "sge";
            }
        }

        return res;
    }



    /**
     * Generate the code for the print instruction
     * @param String var The variable to print
     */
    public void generatePrint(String var){
        String temporaryVar = "%"+instructionCounter++;
        writer.println(temporaryVar+"= load i32, i32*"+var);
        writer.println("call void @println(i32 "+temporaryVar+")");
    }

    /**
     * Generate the code the read instruction
     * @param String var The variable that will read the input
     */
    public void generateRead(String var){
        addVariables(var);
        String temporaryVar = "%"+instructionCounter++;
        writer.println(temporaryVar+" = call i32 @readInt()");
        writer.println("store i32 "+temporaryVar+", i32* "+var);
    }
}
