package project3;
 
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Class   EvaluateString
 * File:     EvaluateString.java
 * Description:   uses two generic stacks to evaluate the expression entered on the GUIDriver
 *                        operators and operands are pushed into separate stacks and result is returned
 * @author        Mel Leggett
 * Environment:   PC, Windows 10, jdk 1.8, NetBeans 8.2
 * Date:         03/03/2017
 * @version         1.1
 * History Log:  03/03/2017, 03/04/2017
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class EvaluateString
{
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * evaluate()
     * Description uses two generic stacks to evaluate the expression entered on the GUIDriver
    *                        operators and operands are pushed into separate stacks and result is returned
     * @param expression the expression entered on the GUIDriver
     * @return  returns -999 for failed operation or the real result of the expression
     * date 03/04/2017
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public static int evaluate(String expression)
    {    //the char Array of the expression entered
        char[] tokens = expression.toCharArray();
        //verify all cards are used and only used once
        if(!Validate.cardsUsed(tokens)) {
            Exceptions.cardsNotUsed();
            return -999; //return fail
        }
        //checks that the parenthesis match and there are never more close than open
        if(!Validate.parenthesisCheck(tokens)) {
            Exceptions.parenthesisCheck();
            return -999; //return fail
        }
        //checks for legal start of expression, if fail warn user and quit
        if(!Validate.startExpressionCheck(tokens)) {
            Exceptions.illegalStartExp();
            return -999; //return fail
        }
        
         // Stack for numbers: 'values'
        GenericStack<Integer> values = new GenericStack();
 
        // Stack for Operators: 'operator'
        GenericStack<Character> operator = new GenericStack();
 
        for (int i = 0; i < tokens.length; i++) {
             // Current token is a whitespace, skip it
            if (tokens[i] == ' ')
                continue;
 
            // Current token is a number, push it to stack for numbers
            if (tokens[i] >= '0' && tokens[i] <= '9')
            {
                String digit = String.valueOf(tokens[i]);
                int tempIndex = i + 1; //tempIndex for checking next number
                //if next char is a number, concat, it is a multi digit integer
                while (tempIndex < tokens.length && tokens[tempIndex] >= '0' 
                        && tokens[tempIndex] <= '9')  {
                    digit = digit.concat(String.valueOf(tokens[tempIndex++]));
                }
                 //add the entire number to the values Stack
                values.push(Integer.parseInt(digit));
                i = tempIndex - 1;  //set i to the first remaining non digit token
            }
 
            // if current token is an opening brace, push it to 'operator'
            else if (tokens[i] == '(')
                operator.push(tokens[i]);
 
            // if closing brace encountered, solve entire brace
            else if (tokens[i] == ')')
            {
                while (operator.peek() != '(')
                  values.push(execute(operator.pop(), values.pop(), values.pop()));
                //opening brace found, stop execution, pop brace
                operator.pop();
            }
 
            // Current token is an operator.
            else if (tokens[i] == '+' || tokens[i] == '-' ||
                     tokens[i] == '*' || tokens[i] == '/')
            {
                if(!Validate.doubleOperatorCheck(tokens, i)) {
                    Exceptions.doubleOperator();
                    return -999; //failed to exectute
                }
                
                // While top of 'operator' has same or greater precedence to current
                // token, which is an operator. Apply operator on top of 'operator'
                // to top two elements in values stack
                //when adding an operator, if another operator already exists
                //find which operator has priority and perform it first
                while (!operator.isEmpty() && priority(
                        tokens[i], operator.peek())) {
                  values.push(execute(operator.pop(), values.pop(), values.pop()));  
                }
                // Push current token to 'operator'.
                operator.push(tokens[i]);
            }
        }
           
        //no more tokens to add to the stacks, execute remainig operators
        while (!operator.isEmpty())
            values.push(execute(operator.pop(), values.pop(), values.pop()));
 
        // Top of 'values' contains result, return it
        return values.pop();
    }
    
   /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * priority()
    * Description returns true if the operator in the stack has higher priority than the
    * operator currently in the token, otherwise returns false.
    * @param token the next token to be pushed or popped
    * @param inStack the token on the top of the operator stack
    * @return  true or false as to whether the new token has higher priority than that in stack
    * date 03/03/2017
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public static boolean priority(char token, char inStack)
    {
        if (inStack == '(' || inStack == ')')
            return false;
        if ((token == '*' || token == '/') && (inStack == '+' || inStack == '-'))
            return false;
        else
            return true;
    }
 
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * execute()
     * Description takes the operator and applies it to the top two numbers in the operand stack
     * @param operate the operator to be used in the calculation
     * @param val2 the second numerical value
     * @param val1 the first numerical value
     * @return int result of the operation
     * date 03/03/2017
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public static int execute(char operate, int val2, int val1)
    {
        switch (operate)
        {
        case '+':
            return val1 + val2;
        case '-':
            return val1 - val2;
        case '*':
            return val1 * val2;
        case '/':
            if (val2 == 0)
                break; 
            return val1 / val2;
        }
        return 0;
    }
}

