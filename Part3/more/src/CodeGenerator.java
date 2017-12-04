import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

class CodeGenerator {

    private int instructionCounter;
    private PrintWriter writer;
    private List<String> infixNotation;
    private List<String> postfixNotation;
    public CodeGenerator(){
        instructionCounter = 0;
        infixNotation = new ArrayList<String>();
        postfixNotation = new ArrayList<String>();
        try {
            writer = new PrintWriter("file.lli", "UTF-8");
            init();
        }
        catch (FileNotFoundException|UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

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


    public void addElementInExpression(String element){
        infixNotation.add(element);
    }

    public void generateBegin(){
        writer.println("define i32 @main() {");
        writer.println("entry:");

    }

    public void generateEnding(){
        writer.println("ret i32 0");
        writer.println("}");
        writer.close();
    }


//    public void generateVariable(String var){
//        writer.println("%"+var+" = alloca i32");
//    }
    /**
     * Will generate the code about the assign expression
     * @param String var the variable assigned
     */
    public void generateExpression(String var){
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
            if (element == "+"){
                operator = "add";
                isOperator = true;
            }
            else if (element == "-"){
                operator = "sub";
                isOperator = true;
            }
            else if (element == "*" || element == "~"){
                operator = "mul";
                isOperator = true;
            }
            else if (element == "/"){
                operator = "sdiv";
                isOperator = true;
            }
            else{ // It's an operand, we push it into the stack
                operandsStack.push(element);
            }
            if (isOperator){
                String operand2 = operandsStack.pop();
                String operand1;
                if (element == "~"){
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
        writer.println("%"+var+" = add i32 0,"+operandsStack.pop());


        infixNotation.clear();
        postfixNotation.clear();
    }

    /**
     * Generate the code for the print
     * @param String var The variable to print
     */
    public void generatePrint(String var){
        writer.println("call void @println(i32 %"+var+")");
    }
}
