package project3;

import java.util.ArrayList;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Class   Solutions
 * File:     Solutions.java
 * Description:   Takes in 4 card values and runs them through every order and operation to determine
 *                        all the combinations to reach a value of 24, returns the number(if any) of solutions
 *                        or simply if a solution exists, depending upon which method is ran
 * @author        Mel Leggett
 * Environment:   PC, Windows 10, jdk 1.8, NetBeans 8.2
 * Date:         03/06/2017
 * @version         2.0
 * History Log:  03/06/2017, 03/08/2017, 03/09/2017, 03/10/2017
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class Solutions {
    private int a, b, c, d;
    private ArrayList<String> solList = new ArrayList<>();
    
    /**
     * Solutions
     * Default constructor
     */
    public Solutions() {        
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * findSolutions
     * Description takes in the 4 card values and cycles through all combinations of card placement
     * as well as mathematical operators to attempt to reach 24, and collects all combinations of 24.
     * @param aa card 1's value
     * @param bb card 2's value
     * @param cc card 3's value
     * @param dd card 4's value
     * @return  ArrayLList of all the expressions that total 24
     * date 03/08/2017
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public ArrayList findSolutions(int aa, int bb, int cc, int dd) {
        a = aa; b = bb; c = cc; d = dd;
        String attempt = a + "+ " + b + "+ " + c + "+" + d;
        int result = EvaluateString.evaluate(attempt);//computes result
        if(result == 24) 
            solList.add(attempt);
        attempt = a+"*"+b+"*"+c+"*"+d;
         if(EvaluateString.evaluate(attempt) == 24)
             solList.add(attempt);
        
        int outterCount = 1;
        while(outterCount < 4) {
            threeByOne();
            int temp = a;
            a = b;
            b = c;
            c = d;
            d = temp;
            outterCount++;
        }
        outterCount = 1;
        while(outterCount < 2) {
            twoByTwo();
            int temp = a;
            a = c;
            c = temp;
            temp = b;
            b = d;
            d = temp;
            outterCount++;
        }
        
        return solList;
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * threeByOne
     * Description orders the cards with 3 in parenthesis and cycles through all the operations with
     * the final 4th card on the outside to perform its action last
     * date 03/06/2017
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void threeByOne() {
         String attempt = "";
         int result = 0;
        int temp = 0;
        int count = 1;
        while(count < 7) {
            char[] val = new char[4];
            val[0] = '+';
            val[1] = '-';
            val[2] = '*';
            val[3] = '/';    
            
            for(int i = 0; i < val.length; i++) {
                if(i != 0) { //skip if val = '+' avoid repetition
              attempt =  "("+ b +"+"+ c+"+"+ d + ")" + val[i] + a;
              result = EvaluateString.evaluate(attempt);//computes result
              if(result == 24) 
                    solList.add(attempt);
                }
                attempt =  "("+ b +"+"+ c+"-"+ d + ")" + val[i] + a;
                result = EvaluateString.evaluate(attempt);//computes result              
                if(result == 24)                     
                    solList.add(attempt);
                attempt =  "("+ b +"+"+ c+"*"+ d + ")" + val[i] + a;
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24)                     
                        solList.add(attempt);
                    attempt =  "("+"("+ b +"+"+ c+")*"+ d + ")" + val[i] + a;
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24)                     
                        solList.add(attempt);
                    attempt =  "("+ b +"+"+ c+"/"+ d + ")" + val[i] + a;
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24)                     
                        solList.add(attempt);
                    attempt =  "(("+ b +"+"+ c+")/"+ d + ")" + val[i] + a;
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24)                     
                        solList.add(attempt);
                      attempt =  "("+ b +"-"+ c+"-"+ d + ")" + val[i] + a;
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24)                     
                        solList.add(attempt);
                    attempt =  "("+ b +"-"+ c+"+"+ d + ")" + val[i] + a;
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24)                     
                        solList.add(attempt);
                    attempt =  "(("+ b +"-"+ c+")*"+ d + ")" + val[i] + a;
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24)                     
                        solList.add(attempt);
                    attempt =  "("+ b +"-"+ c+"/"+ d + ")" + val[i] + a;
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24)                     
                        solList.add(attempt);
                    attempt =  "(("+ b +"-"+ c+")/"+ d + ")" + val[i] + a;
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24)                     
                        solList.add(attempt);
                    attempt =  "("+ b +"*"+ c+"*"+ d + ")" + val[i] + a;
                 result = EvaluateString.evaluate(attempt);
                 if(result ==24)
                     solList.add(attempt);
                 attempt =  "("+ b +"*"+ c+"/"+ d + ")" + val[i] + a;
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24)                     
                        solList.add(attempt);
                if(c/d != 0) {
                    attempt =  "("+ b +"*("+ c+"/"+ d + "))" + val[i] + a;
                        result = EvaluateString.evaluate(attempt);//computes result              
                        if(result == 24)                     
                            solList.add(attempt);
                }//divide no need for b/c + d already have b + c/d with swap
                attempt =  "("+ b +"/("+ c+"+"+ d + ")"+")" + val[i] + a; 
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24)                     
                        solList.add(attempt);
                    attempt =  "("+ b +"/"+ c+"-"+ d + ")" + val[i] + a;
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24)                     
                        solList.add(attempt);
                if(c-d != 0) {
                    attempt =  "("+ b +"/("+ c+"-"+ d + "))" + val[i] + a;
                        result = EvaluateString.evaluate(attempt);//computes result              
                        if(result == 24)                     
                            solList.add(attempt);
                }
                attempt =  "("+ b +"/"+ c+"/"+ d + ")" + val[i] + a;
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24)                     
                        solList.add(attempt);
                if(c/d != 0) {
                    attempt =  "("+ b +"/("+ c+"/"+ d + ")"+")" + val[i] + a;
                        result = EvaluateString.evaluate(attempt);//computes result              
                        if(result == 24)                     
                            solList.add(attempt);
                }
            }
            //swap the cards
            count++;
            switch(count % 2) {         
                case 0:
                    temp = c;    
                    c = d;
                    d = temp;
                    break;
                case 1:
                    temp = b;
                    b = c;
                    c = temp;
                    break;
            }
        }
        //reset cards
        temp = b;
        b = c;
        c = temp;           
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * twoByTwo
     * Description groups 2 sets of cards together to perform an operation then brings the
     * two values together and cycles through all operations.
     * date 03/08/2017
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void twoByTwo() {
        int count = 1;
        String attempt = "";
        int result = 0;
        char[] val = {'+', '-', '*','/'};
        for(int i = 0; i < val.length; i++) {
            for(int j = 0; j < val.length; j++) {
                for(int k = 0; k < val.length;k++) {
                    attempt = "(" + a + val[i] + b +")" + val[j] + "(" + c + val[k] + d +")";
                    if(EvaluateString.evaluate(attempt) == 24)
                        solList.add(attempt);
                } //reflect cards
                int temp = a;
                a = b;
                b = temp;
                temp = c;
                c = d;
                d = temp;
                for(int k = 0; k < val.length;k++) {
                    attempt = "(" + a + val[i] + b +")" + val[j] + "(" + c + val[k] + d +")";
                    if(EvaluateString.evaluate(attempt) == 24)
                        solList.add(attempt);
                }//return cards
                temp = a;
                a = b;
                b = temp;
                temp = c;
                c = d;
                d = temp;
            }
         }
    }
    
      /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * findOneSolution
     * Description takes in the 4 card values and cycles through all combinations of card placement
     * as well as mathematical operators to attempt to reach 24, once one solution is found
     * return true, else return false
     * @param aa card 1's value
     * @param bb card 2's value
     * @param cc card 3's value
     * @param dd card 4's value
     * @return  true/false for solution found
     * date 03/10/2017
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public int findOneSolution(int aa, int bb, int cc, int dd) {
        boolean found = false; //boolean searching for solution
        a = aa; b = bb; c = cc; d = dd;
        String attempt = a + "+ " + b + "+ " + c + "+" + d;
        int result = EvaluateString.evaluate(attempt);//computes result
        if(result == 24) 
            return 1;
        attempt = a+"*"+b+"*"+c+"*"+d;
         if(EvaluateString.evaluate(attempt) == 24)
             return 1;
        
        int outterCount = 1;
        while(outterCount < 4 && found == false) {
            found = threeByOneFindOne();
            int temp = a;
            a = b;
            b = c;
            c = d;
            d = temp;
            outterCount++;
        }
        outterCount = 1;
        while(outterCount < 2 && found == false) {
            found = twoByTwoFindOne();
            int temp = a;
            a = c;
            c = temp;
            temp = b;
            b = d;
            d = temp;
            outterCount++;
        }
        if(found)
            return 1;
        return 0;
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * threeByOne
     * Description orders the cards with 3 in parenthesis and cycles through all the operations with
     * the final 4th card on the outside to perform its action last
     * @return found boolean of whether solution has been found
     * date 03/06/2017
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public boolean threeByOneFindOne() {
         String attempt = "";
         boolean found = false;
         int result = 0;
        int temp = 0;
        int count = 1;
        while(count < 7 && found == false) {
            char[] val = new char[4];
            val[0] = '+';
            val[1] = '-';
            val[2] = '*';
            val[3] = '/';    
            
            for(int i = 0; i < val.length; i++) {
                if(i != 0) { //skip if val = '+' avoid repetition
              attempt =  "("+ b +"+"+ c+"+"+ d + ")" + val[i] + a;
              result = EvaluateString.evaluate(attempt);//computes result
              if(result == 24) {
                    found = true;
                     return found; 
                    }
                }
                attempt =  "("+ b +"+"+ c+"-"+ d + ")" + val[i] + a;
                result = EvaluateString.evaluate(attempt);//computes result              
                if(result == 24)  {
                    found = true;
                     return found; 
                    }
                attempt =  "("+ b +"+"+ c+"*"+ d + ")" + val[i] + a;
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24) {
                    found = true;
                     return found; 
                    }
                    attempt =  "("+"("+ b +"+"+ c+")*"+ d + ")" + val[i] + a;
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24)  {
                    found = true;
                     return found; 
                    }
                    attempt =  "("+ b +"+"+ c+"/"+ d + ")" + val[i] + a;
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24)  {
                    found = true;
                     return found; 
                    }
                    attempt =  "(("+ b +"+"+ c+")/"+ d + ")" + val[i] + a;
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24) {
                    found = true;
                     return found; 
                    }
                      //minus no need for b - c + d already have b + c -d
                      attempt =  "("+ b +"-"+ c+"-"+ d + ")" + val[i] + a;
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24) {
                    found = true;
                     return found; 
                    }
                    attempt =  "("+ b +"-"+ c+"+"+ d + ")" + val[i] + a;
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24) {
                    found = true;
                     return found; 
                    }
                    attempt =  "(("+ b +"-"+ c+")*"+ d + ")" + val[i] + a;
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24)   {
                    found = true;
                     return found; 
                    }
                    attempt =  "("+ b +"-"+ c+"/"+ d + ")" + val[i] + a;
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24)   {
                    found = true;
                     return found; 
                    }
                    attempt =  "(("+ b +"-"+ c+")/"+ d + ")" + val[i] + a;
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24)     {
                    found = true;
                     return found; 
                    }
                    attempt =  "("+ b +"*"+ c+"*"+ d + ")" + val[i] + a;
                 result = EvaluateString.evaluate(attempt);
                 if(result ==24){
                    found = true;
                     return found; 
                    }
                 attempt =  "("+ b +"*"+ c+"/"+ d + ")" + val[i] + a;
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24)    {
                    found = true;
                     return found; 
                    }
                if(c/d != 0) {
                    attempt =  "("+ b +"*("+ c+"/"+ d + "))" + val[i] + a;
                        result = EvaluateString.evaluate(attempt);//computes result              
                        if(result == 24)  {
                    found = true;
                     return found; 
                    }
                }
                attempt =  "("+ b +"/("+ c+"+"+ d + ")"+")" + val[i] + a; 
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24)   {
                    found = true;
                     return found; 
                    }
                    attempt =  "("+ b +"/"+ c+"-"+ d + ")" + val[i] + a;
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24)    {
                    found = true;
                     return found; 
                    }
                if(c-d != 0) {
                    attempt =  "("+ b +"/("+ c+"-"+ d + "))" + val[i] + a;
                        result = EvaluateString.evaluate(attempt);//computes result              
                        if(result == 24)  {
                    found = true;
                     return found; 
                    }
                }
                attempt =  "("+ b +"/"+ c+"/"+ d + ")" + val[i] + a;
                    result = EvaluateString.evaluate(attempt);//computes result              
                    if(result == 24)  {
                    found = true;
                     return found; 
                    }
                if(c/d != 0) {
                    attempt =  "("+ b +"/("+ c+"/"+ d + ")"+")" + val[i] + a;
                        result = EvaluateString.evaluate(attempt);//computes result              
                        if(result == 24)  {
                    found = true;
                     return found; 
                    }
                }
            }
            
            count++;
            switch(count % 2) {         
                case 0:
                    temp = c;    
                    c = d;
                    d = temp;
                    break;
                case 1:
                    temp = b;
                    b = c;
                    c = temp;
                    break;
            }
        }
        //reset cards
        temp = b;
        b = c;
        c = temp;   
        return found; //not found return false
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * twoByTwoFindOne
     * Description groups 2 sets of cards together to perform an operation then brings the
     * two values together and cycles through all operations. stops when one solution of 24 is found
     * @return found, boolean to indicate solution found or not
     * date 03/08/2017
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public boolean twoByTwoFindOne() {
        boolean found = false;
        String attempt = "";
        char[] val = {'+', '-', '*','/'};
        for(int i = 0; i < val.length && found == false; i++) {
            for(int j = 0; j < val.length && found == false; j++) {
                for(int k = 0; k < val.length && found == false;k++) {
                    attempt = "(" + a + val[i] + b +")" + val[j] + "(" + c + val[k] + d +")";
                    if(EvaluateString.evaluate(attempt) == 24) {
                        found = true;
                        return found; 
                    }
                    
                } //reflect cards
                int temp = a;
                a = b;
                b = temp;
                temp = c;
                c = d;
                d = temp;
                for(int k = 0; k < val.length && found == false;k++) {
                    attempt = "(" + a + val[i] + b +")" + val[j] + "(" + c + val[k] + d +")";
                    if(EvaluateString.evaluate(attempt) == 24) {
                        found =  true;
                        return found;
                    }
                }//return cards
                temp = a;
                a = b;
                b = temp;
                temp = c;
                c = d;
                d = temp;
            }
         }
        return found;
    }
}
