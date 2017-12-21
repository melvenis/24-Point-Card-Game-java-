package project3;
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Class   Validate
 * File:     Validate.java
 * Description:   Validation class to verify expression input from user to ensure the expression
 *                        is legal, contains the correct numbers and can be evaluated.
 * @author        Mel Leggett
 * Environment:   PC, Windows 10, jdk 1.8, NetBeans 8.2
 * Date:         03/01/2017
 * @version         1.0
 * History Log:  03/01/2017, 03/032017
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class Validate { 
   private static int startIndex = 0;
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * parenthesisCheck()
     * Description ensures that there are equal amounts of open and closed parenthesis in the expression.
     * Return true and proceed if true, return false and fail if not equal
     * @param tokens char array holding the elements of the expression
     * @return result of test "are the parenthesis equal"
     * date 03/03/2017
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public static boolean parenthesisCheck(char[] tokens) {
        int open = 0;
        int closed = 0;
        for(int i = 0; i < tokens.length; i++) {
            if(tokens[i] == '(') 
                open++;
            else if(tokens[i] == ')')
                closed++;
            if(closed > open) { //closed parenthesis before open
                return false;
            }
        }
        //return(open == closed);
        if(open == closed)
            return true;
        else
            return false;
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * startExpressionCheck()
     * Description Finds the first character of the expression. If it is an
     * opening parenthesis or a space, move on to the next character and
     * recurse.  If the first character(or first character evaluated after
     * recursion is not a digit, fail. Otherwise, proceed.
     * @param tokens char array of all the chars entered in the expression
     * @return true or false (pass or fail) start of the expression
     * date 03/01/2017
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public static boolean startExpressionCheck(char[] tokens) { 
        //If expression starts with '(' or white space recurse, check next
        if(tokens[startIndex] == '(' || tokens[startIndex] == ' ') { 
            startIndex++;
            startExpressionCheck(tokens);
        }        
        if(!(tokens[startIndex] >='0' && tokens[startIndex] <= '9'))
            return false; //expression starts with operator, fail
        return true; //legal start of expression, proceed
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * doubleOperatorCheck()
     * Description Upon finding an operator, check to ensure the operator was
     * not the last element in the char array and check to ensure the next
     * char is not another operator (double operator error) return true
     * if no error is detected and continue, otherwise return false and abort.
     * @param tokens the char array of the expression entered by the user
     * @param i the index of the char array currently being evaluated
     * @return true for pass false for fail
     * date 03/01/2017
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public static boolean doubleOperatorCheck(char[] tokens, int i) {
        if(i+1 >= tokens.length || tokens[i+1] == '+' || tokens[i+1] == '-' || 
                  tokens[i+1] == '*' || tokens[i+1] == '/') {
            return false; //consecutive or ends with operator, invalid, failed
        }
        return true; //no error detected, return true, continue
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * cardsUsed()
     * Description verifies that all numbers entered are card values, that all cards are
     * used and that none are repeated.
     * @param tokens the char array of the expression entered
     * @return boolean of true (test passed) or false (test failed)
     * date 03/01/2017
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public static boolean cardsUsed(char[] tokens) {
        int temp1 = DeckOfCards.cardValue1;
        int temp2 = DeckOfCards.cardValue2;
        int temp3 = DeckOfCards.cardValue3;
        int temp4 = DeckOfCards.cardValue4;
        int extraCard = 0;
        for(int i = 0; i < tokens.length; i++) {
            // Current token is a whitespace, skip it
            if (tokens[i] == ' ')
                continue;
            String inputNumber = "";
            if (tokens[i] >= '0' && tokens[i] <= '9') 
            {  // Current token is a number, check next token to see if it is a double digit number
                inputNumber = String.valueOf(tokens[i]);
                int tempIndex = i + 1;
                while (tempIndex < tokens.length && tokens[tempIndex] >= '0' && tokens[tempIndex] <= '9') {
                    inputNumber = inputNumber.concat(String.valueOf(tokens[tempIndex]));
                    tempIndex++;
                    i++;
                } //compares each valCheck to the cards to ensure all card values are used
                int valCheck = Integer.valueOf(inputNumber);
                if(valCheck == temp1)
                    temp1 = -1;
                else if(valCheck == temp2)
                    temp2 = -1;
                else if(valCheck == temp3)
                    temp3 = -1;
                else if(valCheck == temp4)
                    temp4 = -1;
                else
                    extraCard = -1;
            }
        }
        startIndex = 0; //reset startIndex
        //if all temp values < 0, all were found, return true, otherwise missing card, return false
        if(temp1 < 0 && temp2 < 0 && temp3 < 0 && temp4 < 0 && extraCard == 0)
            return true;
        return false;
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * fullExpression()
     * Description ensures that a minimum of 7 characters (3 operators and 4 integers) 
     * are entered, as any less cannot be a correct use of all cards and valid mathematical
     * operation.
     * @param express the String value of the expression entered by the user.
     * @return  boolean true(for pass) or false(for fail)
     * date 03/01/2017
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public static boolean fullExpression(String express) {
        return(express.length() > 6);           
    }
    
}
