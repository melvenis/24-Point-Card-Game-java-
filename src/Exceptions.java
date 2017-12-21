package project3;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Class   Exceptions
 * File:     Exceptions.java
 * Description:   Custom class to handle specific Exceptions in GUIDriver
 * @author        Mel Leggett
 * Environment:   PC, Windows 10, jdk 1.8, NetBeans 8.2
 * Date:         03/07/2017
 * @version         1.0
 * History Log:  03/07/2017, 03/09/20017
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
import javax.swing.JOptionPane;

public class Exceptions {
     
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * catchFileNotFound
     * Description Error message when database file is not found
     * date 03/09/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public static void catchFileNotFound() {
        JOptionPane.showMessageDialog(null, "Please check the file path and try again.",
                "File Not Found", JOptionPane.ERROR_MESSAGE);
    }
     /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * catchIO()
     * Description Error message when a database file is corrupt or cannot be opened
     * date 03/09/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public static void catchIO() {
        JOptionPane.showMessageDialog(null, "File could not be loaded",
                "File IO Error", JOptionPane.ERROR_MESSAGE);
    }
     /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * trainerNumberException()
     * Description Error message when a user enters a number out of the range for
     * card value or attempts to enter a non integer
     * date 03/09/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public static void trainerNumberException() {
        JOptionPane.showMessageDialog(null, "All cards must be integer value and in \n"
                + "the range of 1 - 13.  Please try again", "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }
     /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * nullName()
     * Description Error message when a user attempts to enter a blank name for player
     * date 03/09/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public static void nullName() {
        JOptionPane.showMessageDialog(null, "Name cannot be left blank, try again.");
    }
    
     /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * duplicateEntry()
     * Description Error message when user attempts to edit name to or save a new player
     * with a name that already exists in the database
     * @param name Name of the player that was already in the database
     * date 03/09/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public static void duplicateEntry(String name) {
        JOptionPane.showMessageDialog(null, name + " already exists, name not added.");
    }
    
     /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * timerInputException()
     * Description Error message when user enters an invalid input for the timer duration
     * date 03/09/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public static void timerInputException() {
        JOptionPane.showMessageDialog(null, "Timer must be integer value of seconds only\n"
                + "and in the range of 10 - 300.  Try Again.");
    }
    
     /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * cardNotUsed()
     * Description Error message when a user either enters more than the 
     *  4 allowed card values, incorrect card values or omits a card value
     * date 03/09/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public static void cardsNotUsed() {
        JOptionPane.showMessageDialog(null, "You must use all of the card"
                    + " values, only the card values, and only one time each.",
                    "Invalid Values Entered", JOptionPane.ERROR_MESSAGE);
    }
    
     /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * parenthesisCheck()
     * Description Error message when an invalid expression is entered by the user 
     * date 03/09/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public static void parenthesisCheck() {
        JOptionPane.showMessageDialog(null, "Parenthesis do not match, try again.",
                "Invalid Expression", JOptionPane.ERROR_MESSAGE);
    }
    
     /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * illegalStartExp()
     * Description Error message when an invalid expression is entered by the user 
     * date 03/09/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public static void illegalStartExp() {
            JOptionPane.showMessageDialog(null, "Illegal start of expression.", "Invalid Expression",
                    JOptionPane.ERROR_MESSAGE);
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * doubleOperator()
     * Description Error message when an invalid expression is entered by the user 
     * date 03/09/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public static void doubleOperator() {
        JOptionPane.showMessageDialog(null, "Expression cannot contain consecutive "
                + "\noperators or end with an operator.", "Illegal Expression", 
                            JOptionPane.ERROR_MESSAGE);
    }
}
