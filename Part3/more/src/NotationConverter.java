import java.util.ArrayList;
import java.util.List;

/**
 * Class which will convert notation (infix, postfix)
 */
class NotationConverter{

    public static List<String> convertInfixToPostfix(List<String> infixNotation){
        List<String> postfixNotation = new ArrayList<String>();
        GenericStack<String> operatorsStack = new GenericStack<String>();
        for (int i = 0; i < infixNotation.size(); ++i){
            String element = infixNotation.get(i);
            if (isOperator(element)){
                if (operatorsStack.empty() || hasHigherPriority(element,operatorsStack.getTopOfStack())){
                    operatorsStack.push(element);
                }
                else{
                    while (!operatorsStack.empty() && !hasHigherPriority(element, operatorsStack.getTopOfStack())){
                        postfixNotation.add(operatorsStack.pop());
                    }
                    operatorsStack.push(element);
                }
            }
            else if (element.equals("(") || element.equals("~")){
                operatorsStack.push(element);
            }

            else if (element.equals(")")){
                while (operatorsStack.getTopOfStack() != "("){
                    postfixNotation.add(operatorsStack.pop());
                }
                operatorsStack.pop();
            }
            else{
                postfixNotation.add(element);
            }
        }

        while(!operatorsStack.empty()){
            postfixNotation.add(operatorsStack.pop());
        }

        return postfixNotation;
    }

    /**
     * Check weither the parameter is an binary
     * operator
     * @param  String op            The supposed operator
     * @return        true if correct
     */
    public static boolean isOperator(String op){
        return op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/");
    }

    /**
     * Check if the operator has a higher priority
     * than the top of stack
     * @param  String op           Top Of stack
     * @param  String tos            Operator
     * @return        true if yes, false otherwise
     */
    public static boolean hasHigherPriority(String op, String tos){
        boolean flag;
        if (tos.equals("(")){
            flag = true;
        }
        else {
            flag = (op.equals("*") || op.equals("/")) && (tos.equals("+") || tos.equals("-"));
        }
        return flag;
    }


}
