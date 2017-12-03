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

    public void generateExpression(String var){
        convertInfixToPostfix();
        System.out.println();
        System.out.print(var+" = ");
        for (int i = 0; i < infixNotation.size(); ++i){
            System.out.print(infixNotation.get(i));
        }
        System.out.println();
        infixNotation.clear();
        postfixNotation.clear();
    }


    public void convertInfixToPostfix(){
        GenericStack<String> operatorsStack = new GenericStack<String>();
        for (int i = 0; i < infixNotation.size(); ++i){
            String element = infixNotation.get(i);
            if (isOperator(element)){
                if (operatorsStack.empty() || hasHigherPriority(element,operatorsStack.getTopOfStack())){
                    operatorsStack.push(element);
                }
                else{
                    postfixNotation.add(operatorsStack.pop());
                    operatorsStack.push(element);
                }
            }
            else if (element == "("){
                operatorsStack.push(element);
            }

            else if (element == ")"){
                postfixNotation.add(operatorsStack.pop());
                operatorsStack.pop();
            }

            else{
                postfixNotation.add(element);
            }
        }

        while(!operatorsStack.empty()){
            postfixNotation.add(operatorsStack.pop());
        }

        System.out.println("----------------");
        for (int i = 0; i < postfixNotation.size(); ++i){
            System.out.print(postfixNotation.get(i)+ " ");
        }
        System.out.println("-----------------");

    }

    public boolean isOperator(String op){
        return op == "+" || op == "-" || op == "*" || op == "/";
    }

    /**
     * Check if the operator has a higher priority
     * than the top of stack
     * @param  String op           Top Of stack
     * @param  String tos            Operator
     * @return        true if yes, false otherwise
     */
    public boolean hasHigherPriority(String op, String tos){
        boolean flag;
        if (tos =="("){
            flag = true;
        }
        else {
            flag = (op == "*" || op == "/") && (tos == "+" || tos == "-");
        }
        return flag;
    }

}
