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
    private GenericStack<String> context;
    private GenericStack<String> forVariables;
    private List<String> variables;
    private List<String> infixNotation;
    private List<String> postfixNotation;
    private List<String> conditions;
    private List<String> conditionsPostfix;
    public CodeGenerator(List<String> v){
        instructionCounter = 0;
        codeLabel = 0;
        labelCounter = 0;
        afterIfCounter = 0;
        codeFound = false;
        notSymbol = false;
        context = new GenericStack<String>();
        forVariables = new GenericStack<String>();
        variables = v;
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
        for (int i = 0; i < variables.size(); ++i){
            addVariables("%"+variables.get(i));
        }

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
    public void addVariables(String var){
        writer.println(var+ " = alloca i32");
    }
    /**
     * Will generate the code for the Assign instruction
     * @param String var The variable assigned
     */
    public void generateAssign(String var){
        String temporaryVar = generateExpression();
        writer.println("store i32 "+temporaryVar+", i32* "+var);
    }

    /**
     * Generate the code for the loading of a variable
     * @param  String var           The variable
     * @return        The temporary variable that stocks the value of the variable
     */
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

    /**
     * Add an element to the condition list
     * @param String element An element of a condition
     */
    public void addCondition(String element){
        conditions.add(element);
    }

    /**
     * Generate the code for the condition
     * Can also be used for the while since in term of low level
     * a while is a simple if with a jump back to the condition
     * @param String exp1 [description]
     * @param String comp [description]
     * @param String exp2 [description]
     */
    public void generateIf(){
        int i = 0;
        while (i < conditions.size()){
            String element = conditions.get(i);
            if (element.equals("or")){

                if (conditions.get(i+1).equals("not")){
                    notSymbol = true;
                    ++i;
                }
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
                if (conditions.get(i+1).equals("not")){
                    notSymbol = true;
                    ++i;
                }
                String exp1 = conditions.get(++i);
                String comp = convertComp(conditions.get(++i));
                String exp2 = conditions.get(++i);
                writer.println("label"+labelCounter++ + ":");
                if (codeFound && (i+1 == conditions.size() || conditions.get(i+1).equals("or"))) { // it's the last and of a "pair"
                    generateCondition(comp,exp1,exp2,codeLabel);
                }
                else{
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

        context.push("afterIf"+afterIfCounter++);
        conditions.clear();
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
        String afterLabel = context.pop();
        writer.println("br label %"+afterLabel);
        writer.println(afterLabel+":");
    }



    /**
     * Generate the code for the Else part
     */
    public void generateElse(){
        String afterLabel = context.pop();
        String afterElse = "afterIf"+afterIfCounter++;
        writer.println("br label %"+afterElse);
        context.push(afterElse);
        writer.println(afterLabel+":");
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
        notSymbol = false;

        return res;
    }


    /**
     * Generate the code for the while loop
     */
    public void generateWhile(){
        String whileLabel = "label"+labelCounter++;
        context.push(whileLabel);
        writer.println("br label %"+whileLabel);
        writer.println(whileLabel+":");
    }

    /**
     * Generate the code for the end of a while
     */
    public void generateDoneWhile(){
        String afterWhile = context.pop();
        String whileLabel = context.pop();
        writer.println("br label %"+whileLabel);
        writer.println(afterWhile+":");

    }


    public void addForVariables(String var){
        forVariables.push(var);
    }

    /**
     * Generate the code for the for loop
     * @param String byVar Increment variable
     * @param String toVar Limit variable
     */
    public void generateFor(String byVar, String toVar){
        String var = forVariables.getTopOfStack();
        forVariables.push(byVar);
        String forLabel = "label"+labelCounter++;
        String afterForLabel = "afterIf"+afterIfCounter++;
        context.push(forLabel);
        context.push(afterForLabel);
        writer.println("br label %"+forLabel);
        writer.println(forLabel+":");
        String temporaryVar = generateVariable(var);
        String booleanVar = "%"+instructionCounter++;
        writer.println(booleanVar+" = icmp sle i32 "+temporaryVar+","+toVar);
        String temporaryLabel = "label"+labelCounter++;
        writer.println("br i1 "+booleanVar+", label %"+temporaryLabel+", label %"+afterForLabel);
        writer.println(temporaryLabel+":");

    }

    /**
     * Generate the code for the end of a for loop
     */
    public void generateDoneFor(){
        String byVar = forVariables.pop();
        String var = forVariables.pop();
        String temporaryVar = generateVariable(var);
        String temporaryVar2 = "%"+instructionCounter++;
        writer.println(temporaryVar2+" = add i32 "+temporaryVar+","+byVar);
        writer.println("store i32 "+temporaryVar2+", i32* "+var);
        String afterForLabel = context.pop();
        String forLabel = context.pop();
        writer.println("br label %"+forLabel);
        writer.println(afterForLabel+":");
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
        String temporaryVar = "%"+instructionCounter++;
        writer.println(temporaryVar+" = call i32 @readInt()");
        writer.println("store i32 "+temporaryVar+", i32* "+var);
    }
}
