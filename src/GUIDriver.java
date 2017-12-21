package project3;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Class   GUIDriver
 * File:     GUIDriver.java
 * Description:   GUI with database of players that allows players to play the 24 point card game.  
 *                        Random cards are generated and the user must use a value correlated with them to form
 *                        24 points, the program verifies the input and the result.
 * @author        Mel Leggett
 * Environment:   PC, Windows 10, jdk 1.8, NetBeans 8.2
 * Date:         03/7/2017
 * @version         1.1
 * History Log:  03/04/2017, 03/05/2017, 03/7/2017,03/08/2017
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class GUIDriver extends javax.swing.JFrame {
    private final int MAX_TIME = 300; //upper boundary for timer(in seconds)
    private final int MIN_TIME = 10; //lower boundary for timer(in seconds)
    private final int SECOND = 1000; //value of one second in miliseconds
    private static int count = 1; //count corresponding to what card is to be displayed next
    private ArrayList<Player> players = new ArrayList<>(); //list of all Player objects
    private ArrayList<String> solutions = new ArrayList<>();
    private Player currentPlayer; //the player currently playing
    private Timer cardTimer; //timer for displaying the 4 random cards one at  a time to simulate dealing
    private Timer handTimer; //timer for max time to solve the 24 point probem
    private int counter = 0; //counter that helps end the turn when it reaches the max allowed time
    private final String DATA_FILE = "src/data/database.txt"; //location of the player's database
    private int timerSeconds; //the maxtime allowed per turn, set by the player
   
     /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Method           GUIDriver()
     * Description     constructs form, adds effects to display, reads the database
     *                          and finds the player that is playing
     * @author          Mel Leggett
     * Date                03/07/2017
     * @see               java.awt.Toolkit
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public GUIDriver() {
       //builds form
        initComponents();
        //set shuffleJButton as default
        this.getRootPane().setDefaultButton(dealJButton); 
        //set icon for form
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("src/images/icon.png"));
        //centers form
        this.setLocationRelativeTo(null);   
        //sets title of the form
        this.setTitle("24 Point Card Game");
        //setup database
        loadDataBase();
        //determine who is playing
        findPlayer(null);
        //start an action listener to save data when program is closed
        shutDownSaver();
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * loadDataBase()
     * Description Reads the data from the database file and passes the data to a 
     *                      constructor to build the Player object.
     * @see BufferedReader
     * @see FileReader
     * @see StringTokenizer
     * @see FileNotFoundException
     * @see IOException
     * date 03/04/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void loadDataBase() {
        try { //reads data from the database file
            BufferedReader input = new BufferedReader(new FileReader(DATA_FILE));
            String line = input.readLine();
            while(line != null) { //assigns the values in the tokenizer to the corresponding fields
                Player gamer = new Player();
                StringTokenizer token = new StringTokenizer(line, ",\n");
                gamer.setName(token.nextToken());
                gamer.setGamesPlayed(Integer.valueOf(token.nextToken()));
                gamer.setGamesWon(Integer.valueOf(token.nextToken()));
                gamer.setWinStreak(Integer.valueOf(token.nextToken()));
                gamer.setRecordStreak(Integer.valueOf(token.nextToken()));
                gamer.setRecordSpeed(Integer.valueOf(token.nextToken()));
                if(token.nextToken().equals("false")) {
                    gamer.setTimerIO(false);
                }
                else {
                    gamer.setTimerIO(true);
                }
                gamer.setTimerSeconds(Integer.valueOf(token.nextToken()));
                players.add(gamer);
                line = input.readLine();
            }
            input.close();
        } 
        catch (FileNotFoundException ex) {
            Exceptions.catchFileNotFound();
        }
        catch(IOException ex) {
            Exceptions.catchIO();
        }
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * writeData()
     * Description writes the data in the players list to the external database file to save all changes.
     * @see PrintWriter
     * @see IOException
     * date 03/07/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void writeData() {
        try { //writes the player data line by line into the database file
            PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE, false));
            for(int i = 0; i < players.size(); i++) {
                writer.write(players.get(i).toString());
            }
            writer.close(); //close file on finish
        } 
        catch (IOException ex) {
            Exceptions.catchIO();
        }
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * findPlayer()
     * Description assigns a player to the currentPlayer to play the game, if the player is chosen
     * already via a search etc. Assigns that player as the current, otherwise opens the PlayerData
     * dialog and lets the user choose a player.
     * @param chosen 
     * date 03/06/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void findPlayer(Player chosen) {
        if(chosen == null) { //no player, get new player
            PlayerData login = new PlayerData(this, true, players);
            login.setVisible(true); //set form visible
            currentPlayer = login.getChosenPlayer(); //set currentPlayer as chosen player
            if(currentPlayer == null) { //no player was chosen, inform user, recurse and choose again.
                JOptionPane.showMessageDialog(this,"You must continue an "
                        + "existing player, or start new.", 
                        "No player selected", JOptionPane.ERROR_MESSAGE);
                findPlayer(null);
            }
        }
        else { //player already chosen, set them to currentPlayer
            currentPlayer = chosen;
        }
        //set Name label and timer settings based on currentPlayer
        nameJLabel.setText("Welcome " + currentPlayer.getName());
        if(currentPlayer.isTimerIO()) { //set timer enabled
            timerJCheckBoxMenuItem.setSelected(false);
            timerJCheckBoxMenuItem.doClick();
         }
         else {//no timer desired disable timer
             timerJCheckBoxMenuItem.setSelected(true);
             timerJCheckBoxMenuItem.doClick();
         }//set display of timer based on total time allowed
         timerSeconds = currentPlayer.getTimerSeconds();
         remainJLabel.setText(""+timerSeconds);
         solJButton.setSelected(false); //disable solJButton
         solJMenuItem.setSelected(false);//disable menuItem
         dealJButton.setEnabled(true); //enable deal button
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * getCards()
     * Description method accessed via a timer and incraments a counter on each pass
     * displaying a different card based on counter.
     * date 03/05/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void getCards() {
        DeckOfCards deck = new DeckOfCards();
        switch(count) {
            case 1:
                card1JLabel.setIcon(deck.getImage(DeckOfCards.card1));
                val1JLabel.setText("Value: " + DeckOfCards.cardValue1);
                count++;
                break;
            case 2:
                card2JLabel.setIcon(deck.getImage(DeckOfCards.card2));
                val2JLabel.setText("Value: " + DeckOfCards.cardValue2);
                count++;
                break;
            case 3:
                card3JLabel.setIcon(deck.getImage(DeckOfCards.card3));
                val3JLabel.setText("Value: " + DeckOfCards.cardValue3);
                count++;
                break;
            default:
                card4JLabel.setIcon(deck.getImage(DeckOfCards.card4));
                val4JLabel.setText("Value: " + DeckOfCards.cardValue4);
                count = 1;
                //re-enable deal button
                dealJButton.setEnabled(true);
                cardTimer.stop();
                break;
        }
    }
        
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * newHand()
     * Description New hand is dealt, set valid buttons enabled and increment the total games played
     * date 03/07/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void newHand() {
        evaluateJButton.setEnabled(true);
        openPJButton.setEnabled(true);
        closePJButton.setEnabled(true);
        plusJButton.setEnabled(true);
        minusJButton.setEnabled(true);
        multiplyJButton.setEnabled(true);
        divideJButton.setEnabled(true);
        clearJButton.setEnabled(true);
        
        currentPlayer.setGamesPlayed(currentPlayer.getGamesPlayed() + 1);
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * clear()
     * Description Restores cards to back of card pic, and disables all buttons that are invalid
     * without cards
     * 03/04/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void clear() {
        DeckOfCards deck = new DeckOfCards();
        //stop timers and refresh timer count down display
        if(handTimer != null)
            handTimer.stop();
        if(cardTimer != null)
            cardTimer.stop();
        remainJLabel.setText(""+timerSeconds);
         //hand is over enable timer buttons if necessary
        timerJCheckBoxMenuItem.setEnabled(true);
        if(timerJCheckBoxMenuItem.isSelected())
            timerSettingsJMenuItem.setEnabled(true);
        //return default to deal button
        this.getRootPane().setDefaultButton(dealJButton);
        //disable buttons
        solJButton.setEnabled(false);
        solJMenuItem.setEnabled(false);
        evaluateJButton.setEnabled(false);
        openPJButton.setEnabled(false);
        closePJButton.setEnabled(false);
        plusJButton.setEnabled(false);
        minusJButton.setEnabled(false);
        multiplyJButton.setEnabled(false);
        divideJButton.setEnabled(false);
        clearJButton.setEnabled(false);
        //reset card value to blank
        val1JLabel.setText("Value: ");
        val2JLabel.setText("Value: ");
        val3JLabel.setText("Value: ");
        val4JLabel.setText("Value: ");
        //change images to back of cards
        card1JLabel.setIcon(deck.getImage(DeckOfCards.DECK_SIZE));
        card2JLabel.setIcon(deck.getImage(DeckOfCards.DECK_SIZE)); 
        card3JLabel.setIcon(deck.getImage(DeckOfCards.DECK_SIZE)); 
        card4JLabel.setIcon(deck.getImage(DeckOfCards.DECK_SIZE)); 
        expressionJTextField.setText("");
        resultJTextField.setText("");
        //resets winLossJTextField and Solutions
        solJLabel.setText("Solutions");
        winLossJTextField.setText("");
        winLossJTextField.setBackground(Color.white);
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * findIndex()
     * Description finds the index in the player list of the currentPlayer object
     * @return i, the index of where the currentPlayer is in the players list
     * date 03/03/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public int findIndex() {
        for(int i = 0; i < players.size(); i++) {
            if(currentPlayer.equals(players.get(i)))
                return i;
        }
        return -1;
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * fail()
     * Description Handles failure from expired timer
     * date 03/07/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
     private void fail(int failCase) {
        handTimer.stop(); //halts timer
        //disables invalid buttons
        clearJButton.setEnabled(false); 
        evaluateJButton.setEnabled(false);
        clearJMenuItem.setEnabled(false);
        //alerts user of failure
        winLossJTextField.setBackground(Color.red);
        if(failCase == 0)
            winLossJTextField.setText("Times Up!");
        if(failCase == 1)
            winLossJTextField.setText("Hint Given");
        //ends win streak
        currentPlayer.setWinStreak(0);
        //saves results
        writeData();
        //hand is over enable timer buttons if necessary
        timerJCheckBoxMenuItem.setEnabled(true);
        if(timerJCheckBoxMenuItem.isSelected())
            timerSettingsJMenuItem.setEnabled(true);
        //return default to deal button
        this.getRootPane().setDefaultButton(dealJButton);
    }
     
     /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      * shutDownSaver()
      * Description Sets a ShutdownHook to ensure the program 
      *                    saves the data before terminating
      * date 03/07/2017
      *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
     public void shutDownSaver() {
         Runtime.getRuntime().addShutdownHook(new Thread() {            
            public void run() {     
                //saves data so data will not be lost on shutdown from user prior to saving
                writeData();            
            }        
        }); 
     }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backGroundJPanel = new javax.swing.JPanel();
        cardJPanel = new javax.swing.JPanel();
        card1JLabel = new javax.swing.JLabel();
        card2JLabel = new javax.swing.JLabel();
        card3JLabel = new javax.swing.JLabel();
        card4JLabel = new javax.swing.JLabel();
        valueJPanel = new javax.swing.JPanel();
        val1JLabel = new javax.swing.JLabel();
        val2JLabel = new javax.swing.JLabel();
        val3JLabel = new javax.swing.JLabel();
        val4JLabel = new javax.swing.JLabel();
        expressionJTextField = new javax.swing.JTextField();
        operatorJPanel = new javax.swing.JPanel();
        openPJButton = new javax.swing.JButton();
        closePJButton = new javax.swing.JButton();
        plusJButton = new javax.swing.JButton();
        minusJButton = new javax.swing.JButton();
        multiplyJButton = new javax.swing.JButton();
        divideJButton = new javax.swing.JButton();
        evaluateJButton = new javax.swing.JButton();
        equalsJLabel = new javax.swing.JLabel();
        resultJTextField = new javax.swing.JTextField();
        winLossJTextField = new javax.swing.JTextField();
        buttonJPanel = new javax.swing.JPanel();
        dealJButton = new javax.swing.JButton();
        clearJButton = new javax.swing.JButton();
        logoutJButton = new javax.swing.JButton();
        statisticsJButton = new javax.swing.JButton();
        printJButton = new javax.swing.JButton();
        timeJLabel = new javax.swing.JLabel();
        nameJLabel = new javax.swing.JLabel();
        remainJLabel = new javax.swing.JLabel();
        solutionsJPanel = new javax.swing.JPanel();
        solJLabel = new javax.swing.JLabel();
        solJButton = new javax.swing.JButton();
        titleJMenuBar = new javax.swing.JMenuBar();
        fileJMenu = new javax.swing.JMenu();
        clearJMenuItem = new javax.swing.JMenuItem();
        printJMenuItem = new javax.swing.JMenuItem();
        saveJMenuItem = new javax.swing.JMenuItem();
        exitJMenuItem = new javax.swing.JMenuItem();
        playerDataJMenu = new javax.swing.JMenu();
        playerManagmentJMenuItem = new javax.swing.JMenuItem();
        searchJMenuItem = new javax.swing.JMenuItem();
        statisticsJMenuItem = new javax.swing.JMenuItem();
        logoutJMenuItem = new javax.swing.JMenuItem();
        ExtrasJMenu = new javax.swing.JMenu();
        timerJCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        timerSettingsJMenuItem = new javax.swing.JMenuItem();
        gameStatisticsMenuItem = new javax.swing.JMenuItem();
        solJMenuItem = new javax.swing.JMenuItem();
        trainerJMenuItem = new javax.swing.JMenuItem();
        helpJMenu = new javax.swing.JMenu();
        aboutJMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        backGroundJPanel.setBackground(new java.awt.Color(255, 204, 204));

        cardJPanel.setBackground(new java.awt.Color(255, 204, 204));
        cardJPanel.setLayout(new java.awt.GridLayout(1, 4, 5, 0));

        card1JLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/53.PNG"))); // NOI18N
        cardJPanel.add(card1JLabel);

        card2JLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/53.PNG"))); // NOI18N
        cardJPanel.add(card2JLabel);

        card3JLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/53.PNG"))); // NOI18N
        cardJPanel.add(card3JLabel);

        card4JLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/53.PNG"))); // NOI18N
        cardJPanel.add(card4JLabel);

        valueJPanel.setBackground(new java.awt.Color(255, 255, 204));
        valueJPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        valueJPanel.setLayout(new java.awt.GridLayout(1, 4, 10, 0));

        val1JLabel.setBackground(new java.awt.Color(204, 255, 204));
        val1JLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        val1JLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        val1JLabel.setText("Value:");
        val1JLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        valueJPanel.add(val1JLabel);

        val2JLabel.setBackground(new java.awt.Color(204, 255, 204));
        val2JLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        val2JLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        val2JLabel.setText("Value:");
        val2JLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        valueJPanel.add(val2JLabel);

        val3JLabel.setBackground(new java.awt.Color(204, 255, 204));
        val3JLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        val3JLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        val3JLabel.setText("Value:");
        val3JLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        valueJPanel.add(val3JLabel);

        val4JLabel.setBackground(new java.awt.Color(204, 255, 204));
        val4JLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        val4JLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        val4JLabel.setText("Value:");
        val4JLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        valueJPanel.add(val4JLabel);

        expressionJTextField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        expressionJTextField.setNextFocusableComponent(evaluateJButton);
        expressionJTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                expressionJTextFieldKeyTyped(evt);
            }
        });

        operatorJPanel.setBackground(new java.awt.Color(255, 204, 204));
        operatorJPanel.setLayout(new java.awt.GridLayout(1, 6));

        openPJButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        openPJButton.setText("(");
        openPJButton.setEnabled(false);
        openPJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openPJButtonActionPerformed(evt);
            }
        });
        operatorJPanel.add(openPJButton);

        closePJButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        closePJButton.setText(")");
        closePJButton.setEnabled(false);
        closePJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closePJButtonActionPerformed(evt);
            }
        });
        operatorJPanel.add(closePJButton);

        plusJButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        plusJButton.setText("+");
        plusJButton.setEnabled(false);
        plusJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plusJButtonActionPerformed(evt);
            }
        });
        operatorJPanel.add(plusJButton);

        minusJButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        minusJButton.setText("-");
        minusJButton.setEnabled(false);
        minusJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minusJButtonActionPerformed(evt);
            }
        });
        operatorJPanel.add(minusJButton);

        multiplyJButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        multiplyJButton.setText("*");
        multiplyJButton.setEnabled(false);
        multiplyJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                multiplyJButtonActionPerformed(evt);
            }
        });
        operatorJPanel.add(multiplyJButton);

        divideJButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        divideJButton.setText("÷");
        divideJButton.setEnabled(false);
        divideJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                divideJButtonActionPerformed(evt);
            }
        });
        operatorJPanel.add(divideJButton);

        evaluateJButton.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        evaluateJButton.setMnemonic('e');
        evaluateJButton.setText("Evaluate");
        evaluateJButton.setToolTipText("Evaluate the Expression");
        evaluateJButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        evaluateJButton.setEnabled(false);
        evaluateJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                evaluateJButtonActionPerformed(evt);
            }
        });

        equalsJLabel.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        equalsJLabel.setText("=");

        resultJTextField.setEditable(false);
        resultJTextField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        resultJTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        resultJTextField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        winLossJTextField.setEditable(false);
        winLossJTextField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        winLossJTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        winLossJTextField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        buttonJPanel.setLayout(new java.awt.GridLayout(1, 5));

        dealJButton.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        dealJButton.setMnemonic('d');
        dealJButton.setText("Deal");
        dealJButton.setToolTipText("Shuffle and deal a new hand");
        dealJButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        dealJButton.setNextFocusableComponent(expressionJTextField);
        dealJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dealJButtonActionPerformed(evt);
            }
        });
        buttonJPanel.add(dealJButton);

        clearJButton.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        clearJButton.setMnemonic('c');
        clearJButton.setText("Clear");
        clearJButton.setToolTipText("clears hand");
        clearJButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        clearJButton.setEnabled(false);
        clearJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearJButtonActionPerformed(evt);
            }
        });
        buttonJPanel.add(clearJButton);

        logoutJButton.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        logoutJButton.setMnemonic('L');
        logoutJButton.setText("Logout");
        logoutJButton.setToolTipText("clears hand");
        logoutJButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        logoutJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutJButtonActionPerformed(evt);
            }
        });
        buttonJPanel.add(logoutJButton);

        statisticsJButton.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        statisticsJButton.setMnemonic('s');
        statisticsJButton.setText("Statistics");
        statisticsJButton.setToolTipText("clears hand");
        statisticsJButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        statisticsJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statisticsJButtonActionPerformed(evt);
            }
        });
        buttonJPanel.add(statisticsJButton);

        printJButton.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        printJButton.setMnemonic('p');
        printJButton.setText("Print");
        printJButton.setToolTipText("clears hand");
        printJButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        printJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printJButtonActionPerformed(evt);
            }
        });
        buttonJPanel.add(printJButton);

        timeJLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        timeJLabel.setText("Time Remaining:");

        nameJLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        nameJLabel.setText("Welcome Player Name");

        remainJLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        remainJLabel.setText("01:00");
        remainJLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        solutionsJPanel.setBackground(new java.awt.Color(255, 204, 204));
        solutionsJPanel.setLayout(new java.awt.GridLayout(2, 0, 0, 10));

        solJLabel.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        solJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        solJLabel.setText(" Solutions");
        solJLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        solutionsJPanel.add(solJLabel);

        solJButton.setMnemonic('u');
        solJButton.setText("Show Solutions");
        solJButton.setToolTipText("Get Solutions for current hand");
        solJButton.setEnabled(false);
        solJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                solJButtonActionPerformed(evt);
            }
        });
        solutionsJPanel.add(solJButton);

        javax.swing.GroupLayout backGroundJPanelLayout = new javax.swing.GroupLayout(backGroundJPanel);
        backGroundJPanel.setLayout(backGroundJPanelLayout);
        backGroundJPanelLayout.setHorizontalGroup(
            backGroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backGroundJPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(backGroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backGroundJPanelLayout.createSequentialGroup()
                        .addComponent(solutionsJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(backGroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(backGroundJPanelLayout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(nameJLabel))
                            .addGroup(backGroundJPanelLayout.createSequentialGroup()
                                .addGap(172, 172, 172)
                                .addComponent(timeJLabel)
                                .addGap(18, 18, 18)
                                .addComponent(remainJLabel)))
                        .addContainerGap())
                    .addComponent(valueJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backGroundJPanelLayout.createSequentialGroup()
                        .addGroup(backGroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(operatorJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(expressionJTextField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(equalsJLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backGroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(backGroundJPanelLayout.createSequentialGroup()
                                .addComponent(resultJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(winLossJTextField))
                            .addComponent(evaluateJButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(backGroundJPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(cardJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        backGroundJPanelLayout.setVerticalGroup(
            backGroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backGroundJPanelLayout.createSequentialGroup()
                .addGroup(backGroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backGroundJPanelLayout.createSequentialGroup()
                        .addComponent(nameJLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(backGroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(timeJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(remainJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16))
                    .addGroup(backGroundJPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(solutionsJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)))
                .addComponent(cardJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(valueJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(backGroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(backGroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(expressionJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(equalsJLabel))
                    .addComponent(resultJTextField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(winLossJTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(backGroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backGroundJPanelLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(operatorJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(evaluateJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(buttonJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );

        fileJMenu.setMnemonic('f');
        fileJMenu.setText("File");

        clearJMenuItem.setMnemonic('c');
        clearJMenuItem.setText("Clear");
        clearJMenuItem.setToolTipText("clear form");
        clearJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(clearJMenuItem);

        printJMenuItem.setMnemonic('p');
        printJMenuItem.setText("Print");
        printJMenuItem.setToolTipText("Print Form");
        printJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(printJMenuItem);

        saveJMenuItem.setMnemonic('s');
        saveJMenuItem.setText("Save");
        saveJMenuItem.setToolTipText("Save game");
        saveJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(saveJMenuItem);

        exitJMenuItem.setMnemonic('x');
        exitJMenuItem.setText("Exit");
        exitJMenuItem.setToolTipText("Quit Program");
        exitJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(exitJMenuItem);

        titleJMenuBar.add(fileJMenu);

        playerDataJMenu.setMnemonic('y');
        playerDataJMenu.setText("Player Data");
        playerDataJMenu.setToolTipText("");

        playerManagmentJMenuItem.setMnemonic('m');
        playerManagmentJMenuItem.setText("Player Managment");
        playerManagmentJMenuItem.setToolTipText("Add/Edit/Delete players");
        playerManagmentJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playerManagmentJMenuItemActionPerformed(evt);
            }
        });
        playerDataJMenu.add(playerManagmentJMenuItem);

        searchJMenuItem.setMnemonic('f');
        searchJMenuItem.setText("Find Player");
        searchJMenuItem.setToolTipText("Search for player");
        searchJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchJMenuItemActionPerformed(evt);
            }
        });
        playerDataJMenu.add(searchJMenuItem);

        statisticsJMenuItem.setMnemonic('s');
        statisticsJMenuItem.setText("Statistics");
        statisticsJMenuItem.setToolTipText("Displays your statistics");
        statisticsJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statisticsJMenuItemActionPerformed(evt);
            }
        });
        playerDataJMenu.add(statisticsJMenuItem);

        logoutJMenuItem.setMnemonic('l');
        logoutJMenuItem.setText("Logout");
        logoutJMenuItem.setToolTipText("logout/choose new player");
        logoutJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutJMenuItemActionPerformed(evt);
            }
        });
        playerDataJMenu.add(logoutJMenuItem);

        titleJMenuBar.add(playerDataJMenu);

        ExtrasJMenu.setMnemonic('x');
        ExtrasJMenu.setText("Extras");
        ExtrasJMenu.setToolTipText("");

        timerJCheckBoxMenuItem.setMnemonic('t');
        timerJCheckBoxMenuItem.setSelected(true);
        timerJCheckBoxMenuItem.setText("Timer Enabled");
        timerJCheckBoxMenuItem.setToolTipText("toggle timer on/off");
        timerJCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timerJCheckBoxMenuItemActionPerformed(evt);
            }
        });
        ExtrasJMenu.add(timerJCheckBoxMenuItem);

        timerSettingsJMenuItem.setMnemonic('e');
        timerSettingsJMenuItem.setText("Edit Time");
        timerSettingsJMenuItem.setToolTipText("Change the time alotted ");
        timerSettingsJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timerSettingsJMenuItemActionPerformed(evt);
            }
        });
        ExtrasJMenu.add(timerSettingsJMenuItem);

        gameStatisticsMenuItem.setMnemonic('g');
        gameStatisticsMenuItem.setText("Game Statistics");
        gameStatisticsMenuItem.setToolTipText("The Overall statistics on 24Point");
        gameStatisticsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gameStatisticsMenuItemActionPerformed(evt);
            }
        });
        ExtrasJMenu.add(gameStatisticsMenuItem);

        solJMenuItem.setMnemonic('u');
        solJMenuItem.setText("Solutions");
        solJMenuItem.setToolTipText("Get solutions for current hand");
        solJMenuItem.setEnabled(false);
        solJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                solJMenuItemActionPerformed(evt);
            }
        });
        ExtrasJMenu.add(solJMenuItem);

        trainerJMenuItem.setMnemonic('i');
        trainerJMenuItem.setText("Trainer Game");
        trainerJMenuItem.setToolTipText("launch the training game");
        trainerJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trainerJMenuItemActionPerformed(evt);
            }
        });
        ExtrasJMenu.add(trainerJMenuItem);

        titleJMenuBar.add(ExtrasJMenu);

        helpJMenu.setMnemonic('h');
        helpJMenu.setText("Help");

        aboutJMenuItem.setMnemonic('a');
        aboutJMenuItem.setText("About");
        aboutJMenuItem.setToolTipText("Launches About form");
        aboutJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutJMenuItemActionPerformed(evt);
            }
        });
        helpJMenu.add(aboutJMenuItem);

        titleJMenuBar.add(helpJMenu);

        setJMenuBar(titleJMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backGroundJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backGroundJPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * dealJButtonActionPerformed
 * Description Deals a new hand, increments the total hands played, starts the timers
 * @param evt the dealJButton click
 * date 03/03/2017
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void dealJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dealJButtonActionPerformed
        dealJButton.setEnabled(false); //avoid button hammering
        clear();
        //disable timer buttons once hand is in progress
        timerJCheckBoxMenuItem.setEnabled(false);
        timerSettingsJMenuItem.setEnabled(false);
        DeckOfCards.shuffle();
        newHand();
        Solutions sol = new Solutions();
        solutions.clear();
        solutions = sol.findSolutions(DeckOfCards.cardValue1,
                DeckOfCards.cardValue2, DeckOfCards.cardValue3, DeckOfCards.cardValue4);
        solJLabel.setText(solutions.size() + " Solutions");
        if(solutions.isEmpty()) {//no solutions found, disable solutions button
            solJButton.setEnabled(false);
            solJMenuItem.setEnabled(false);
        }
        else {//found solutions, enable solutions buttons
            solJButton.setEnabled(true);
            solJMenuItem.setEnabled(true);
        }
        cardTimer = new Timer(100, cardTimerListener);
        cardTimer.start();
        if(timerJCheckBoxMenuItem.isSelected()) {
            counter = 0;
            remainJLabel.setText(""+timerSeconds);
            handTimer = new Timer(SECOND, handTimerListener);
            handTimer.start();
        }
        this.getRootPane().setDefaultButton(evaluateJButton);
        expressionJTextField.requestFocus();
    }//GEN-LAST:event_dealJButtonActionPerformed
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * evaluateJButtonActionPerformed
 * Description Validates input and if correct proceeds to check the result
 *                     if the expression evaluates to 24, correct increment gamesWon and winStreak
 *                    otherwise fail
 * @param evt evaluateJButton click
 * date 03/03/2017
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void evaluateJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_evaluateJButtonActionPerformed
        //re-Enable deal button
        dealJButton.setEnabled(true);
        //ensures there is a minimum amount of characters to be a valid expression if not notify user
        if(!Validate.fullExpression(expressionJTextField.getText())) {
            JOptionPane.showMessageDialog(this, "Invalid expression and/or "
                    + "missing card values!","Expression too short", 
                    JOptionPane.ERROR_MESSAGE);
        }
        else { //continue, and check results
            handTimer.stop(); //stop timer
            int result = EvaluateString.evaluate(expressionJTextField.getText());//computes result
            if(result == -999)
                resultJTextField.setText("ERROR");
            else
                resultJTextField.setText(String.valueOf(result)); //displays result
            if(result == 24) { //correct increment games won and winStreak
                winLossJTextField.setBackground(Color.green);
                winLossJTextField.setText("Correct!");
                currentPlayer.setGamesWon(currentPlayer.getGamesWon() + 1);
                currentPlayer.setWinStreak(currentPlayer.getWinStreak() + 1);
                if(currentPlayer.getWinStreak() > currentPlayer.getRecordStreak())
                    //if winStreak is greater than recordStreak then set RecordStreak
                    currentPlayer.setRecordStreak(currentPlayer.getWinStreak());
                if(currentPlayer.getRecordSpeed() == 0 || 
                        counter < currentPlayer.getRecordSpeed())
                    //if current speed was less than old record or current record is zero, set new record
                    currentPlayer.setRecordSpeed(counter);
            }
            else { //incorrect, display wrong and end streak
                winLossJTextField.setBackground(Color.red);
                winLossJTextField.setText("Wrong!");
                currentPlayer.setWinStreak(0);
            }
            clearJButton.setEnabled(false);
            evaluateJButton.setEnabled(false);
            clearJMenuItem.setEnabled(false);
        writeData(); //save results
        //hand is over enable timer buttons if necessary
        timerJCheckBoxMenuItem.setEnabled(true);
        if(timerJCheckBoxMenuItem.isSelected())
            timerSettingsJMenuItem.setEnabled(true);
        this.getRootPane().setDefaultButton(dealJButton);
        }
    }//GEN-LAST:event_evaluateJButtonActionPerformed
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * openPJButtonActionPerformed
     * Description grabs substrings before and after the current Caret Position
     * and inputs the operator in between and resets the newly formed string
     * @param evt the openPJButton click
     * date 03/04/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void openPJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openPJButtonActionPerformed
        int position = expressionJTextField.getCaretPosition();
        String sub1 = expressionJTextField.getText().substring(0,position);
        String sub2 = expressionJTextField.getText().substring(position);
        expressionJTextField.setText(sub1.concat("()").concat(sub2));
        expressionJTextField.requestFocus();
        expressionJTextField.setCaretPosition(position+1);
    }//GEN-LAST:event_openPJButtonActionPerformed
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * closePJButtonActionPerformed
     * Description grabs substrings before and after the current Caret Position
     * and inputs the operator in between and resets the newly formed string
     * @param evt the closePJButton click
     * date 03/04/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void closePJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closePJButtonActionPerformed
        int position = expressionJTextField.getCaretPosition();
        String sub1 = expressionJTextField.getText().substring(0,position);
        String sub2 = expressionJTextField.getText().substring(position);
        expressionJTextField.setText(sub1.concat(")").concat(sub2));
        expressionJTextField.requestFocus();
        expressionJTextField.setCaretPosition(position+1);
    }//GEN-LAST:event_closePJButtonActionPerformed
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * plusJButtonActionPerformed
     * Description grabs substrings before and after the current Caret Position
     * and inputs the operator in between and resets the newly formed string
     * @param evt the plusJButton click
     * date 03/04/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void plusJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plusJButtonActionPerformed
        int position = expressionJTextField.getCaretPosition();
        String sub1 = expressionJTextField.getText().substring(0,position);
        String sub2 = expressionJTextField.getText().substring(position);
        expressionJTextField.setText(sub1.concat("+").concat(sub2));
        expressionJTextField.requestFocus();
        expressionJTextField.setCaretPosition(position+1);
    }//GEN-LAST:event_plusJButtonActionPerformed
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * minusJButtonActionPerformed
     * Description grabs substrings before and after the current Caret Position
     * and inputs the operator in between and resets the newly formed string
     * @param evt the minusJButton click
     * date 03/04/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void minusJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minusJButtonActionPerformed
        int position = expressionJTextField.getCaretPosition();
        String sub1 = expressionJTextField.getText().substring(0,position);
        String sub2 = expressionJTextField.getText().substring(position);
        expressionJTextField.setText(sub1.concat("-").concat(sub2));
        expressionJTextField.requestFocus();
        expressionJTextField.setCaretPosition(position+1);
    }//GEN-LAST:event_minusJButtonActionPerformed
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * multiplyJButtonActionPerformed
     * Description grabs substrings before and after the current Caret Position
     * and inputs the operator in between and resets the newly formed string
     * @param evt the multiplyJButton click
     * date 03/04/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void multiplyJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_multiplyJButtonActionPerformed
       int position = expressionJTextField.getCaretPosition();
        String sub1 = expressionJTextField.getText().substring(0,position);
        String sub2 = expressionJTextField.getText().substring(position);
        expressionJTextField.setText(sub1.concat("*").concat(sub2));
        expressionJTextField.requestFocus();
        expressionJTextField.setCaretPosition(position+1);
    }//GEN-LAST:event_multiplyJButtonActionPerformed
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * divideJButtonActionPerformed
     * Description grabs substrings before and after the current Caret Position
     * and inputs the operator in between and resets the newly formed string
     * @param evt the divideJButton click
     * date 03/04/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void divideJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_divideJButtonActionPerformed
        int position = expressionJTextField.getCaretPosition();
        String sub1 = expressionJTextField.getText().substring(0,position);
        String sub2 = expressionJTextField.getText().substring(position);
        expressionJTextField.setText(sub1.concat("/").concat(sub2));
        expressionJTextField.requestFocus();
        expressionJTextField.setCaretPosition(position+1);
    }//GEN-LAST:event_divideJButtonActionPerformed
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * expressionJTextFieldKeyTyped
 * Description allows only valid keys( operators and numbers, backspace, del and enter) to be 
 * entered in the field, otherwise consumes the key types.
 * @param evt key typed in expressionJTextField
 * date 03/03/2017
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void expressionJTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_expressionJTextFieldKeyTyped
        //only allows digits and operators to be entered
        char c = evt.getKeyChar();
        //if anything but a digit, operator, backspace, delete or enter is pressed, consume the input
        if (!((Character.isDigit(c) || (c >39 && c < 44) || (c == '-') || (c == '/')
                ||(c == KeyEvent.VK_BACK_SPACE) 
                || (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_ENTER))))
        {
            getToolkit().beep(); //on violation sounds a beep
            evt.consume(); //consumes invalid keystrokes
        }
    }//GEN-LAST:event_expressionJTextFieldKeyTyped
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * clearJButtonActionPerformed
 * calls the clear method
 * @param evt clearJButton click
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void clearJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearJButtonActionPerformed
        clear(); 
    }//GEN-LAST:event_clearJButtonActionPerformed
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * cearJMenuItemActionPerformed
 * calls the clear button
 * @param evt click clearJButton
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void clearJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearJMenuItemActionPerformed
        clearJButton.doClick(); //calls clear button
    }//GEN-LAST:event_clearJMenuItemActionPerformed
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * printJMenuItemActionPerformed
 * prints the form by calling the PrintUtilities static method printComponents
 * @param evt clicking the printJMenuItem
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void printJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printJMenuItemActionPerformed
        PrintUtilities.printComponent(this);
    }//GEN-LAST:event_printJMenuItemActionPerformed
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * saveJMenuItemActionPerformed
 * calls writeData method
 * @param evt saveJMenuItem click
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void saveJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveJMenuItemActionPerformed
        writeData();
    }//GEN-LAST:event_saveJMenuItemActionPerformed
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * exitJMenuItemActionPerformed
 * closes form
 * @param evt exitJMenu clicked 
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void exitJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitJMenuItemActionPerformed
     System.exit(0);
    }//GEN-LAST:event_exitJMenuItemActionPerformed
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * aboutJMenuItemActionPerformed
 * Description builds new About object and sets visible to give tips and description of program
 * @param evt aboutJMenuItem click
 * 03/03/2017
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void aboutJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutJMenuItemActionPerformed
        clear(); //stops game if one was in progress
        About help = new About(this, true);
        help.setVisible(true);
    }//GEN-LAST:event_aboutJMenuItemActionPerformed
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * playerManagmentJMenuItem
 * Description builds PlayerData object and sets visible to allow user to 
 * add, edit, delete or switch users.
 * @param evt playerManagmentJMenuItem click
 * date 03/03/2017
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void playerManagmentJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playerManagmentJMenuItemActionPerformed
        clear(); //stops game if one was in progress
        PlayerData managment = new PlayerData(this, true, players);
        managment.setVisible(true);
        currentPlayer = managment.getChosenPlayer();
        findPlayer(currentPlayer);
    }//GEN-LAST:event_playerManagmentJMenuItemActionPerformed
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * searchJMenuItemActionPerformed
 * Description Prompts user for a name of player to search for, if found allows user
 * to load the player and begin the game with them as the currentPlayer
 * @param evt searchJMenuItemActionPerformed
 * date 03/04/2017
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void searchJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchJMenuItemActionPerformed
       clear(); //stops game if one was in progress
        try{
            String name = (JOptionPane.showInputDialog(this, "Enter the name of the player"
                + " to search for:", "Player Search", 
                JOptionPane.OK_CANCEL_OPTION)).toLowerCase();
            
            boolean found = false; //flag to find name
            if(name != null) { //if a name was entered search for it
                for(int i = 0; i < players.size() && found == false; i++) {
                    if(players.get(i).getName().toLowerCase().contains(name)) {
                        found = true; // name found set to true
                        
                //inform user player was found, give option to load the found player
                        int confirm = JOptionPane.showConfirmDialog(this,players
                            .get(i).getName() + " was found, load this player?",
                            "Succesful Search", JOptionPane.YES_NO_OPTION);
                        if(confirm == JOptionPane.YES_OPTION) { //user confirmed found player
                            writeData(); //save current data
                            clear(); //clear form
                            findPlayer(players.get(i)); //load the found player
                        } //else player canceled load, do nothing
                    }
                }
                if(!found) //not found, inform user
                    JOptionPane.showMessageDialog(this, name + " was not found.");
            }
        }
        catch(NullPointerException ex) {
            //No name searched, do nothing
        }
    }//GEN-LAST:event_searchJMenuItemActionPerformed
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * statisticsJMenuItemActionPerformed
 * Description builds a statistics object and sets the form visible to show the statistics
 *                    of the current player
 * @param evt  statistcsJMenuItem clicked
 * date 03/05/17
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void statisticsJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statisticsJMenuItemActionPerformed
         clear(); //stops game if one was in progress
        boolean gameStatistics = false; //need player statistics not game
        Statistics stats = new Statistics(this, true, currentPlayer, gameStatistics);
        stats.setVisible(true);
    }//GEN-LAST:event_statisticsJMenuItemActionPerformed
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * logoutJMenuItemActionPerformed
 * Description saves data, then clears the form and allows user to login with an 
 * existing or new user
 * @param evt logoutJMenuItem click
 * date 03/06/2017
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void logoutJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutJMenuItemActionPerformed
        writeData();
        clear();
        PlayerData playData = new PlayerData(this,true, players);
        playData.setChosenPlayer(null);
        findPlayer(null);
    }//GEN-LAST:event_logoutJMenuItemActionPerformed
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * logoutJButtonActionPerformed
 * Description perform the logoutJMenuItem click
 * @param evt logoutJButton click
 * 03/05/17
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void logoutJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutJButtonActionPerformed
        logoutJMenuItem.doClick();
    }//GEN-LAST:event_logoutJButtonActionPerformed
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * statisticsJButtonActionPerformed
 * Description perform the statisticsJButton click
 * @param evt statisticsJButton 
 * 03/03/2017
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void statisticsJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statisticsJButtonActionPerformed
        statisticsJMenuItem.doClick();
    }//GEN-LAST:event_statisticsJButtonActionPerformed
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * printJButtonActionPerformed
 * Description perform the printJMenuItem click
 * @param evt printJMenuItem click
 * 03/01/2017
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void printJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printJButtonActionPerformed
      printJMenuItem.doClick();
    }//GEN-LAST:event_printJButtonActionPerformed
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * timerSettingsJMenuItemActionPerformed
 * Description prompts the user to enter a time in seconds for the user
 * to solve the 24 hand.  If the entry is outside of the Min/Max range recurse and prompt again.
 * @param evt the timerSettingsJMenuItem click
 * @see NumberFormatException
 * date 03/05/2017
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void timerSettingsJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timerSettingsJMenuItemActionPerformed
        try {
            timerSeconds = Integer.valueOf(JOptionPane.showInputDialog(
                    this, "Enter the time, in seconds, for your timer (10 - 300)"));
            if(timerSeconds > MAX_TIME || timerSeconds < MIN_TIME) {
                timerSeconds = 45; //reset to default
                Exceptions.timerInputException(); //recurse
                timerSettingsJMenuItem.doClick();
            } //sucessful range, update player and save data
            currentPlayer.setTimerSeconds(timerSeconds);
            writeData(); 
        }
        catch(NumberFormatException ex) {
         Exceptions.timerInputException();
         timerSettingsJMenuItem.doClick(); //recurse
        }
    }//GEN-LAST:event_timerSettingsJMenuItemActionPerformed
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * timerJCheckBoxMenuItemActionPerformed
 * Description upon click toggle timer on/off, set menus visible/invisible and write data to database
 * @param evt JCheckBoxMenuItem clicked
 * date 03/04/2017
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void timerJCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timerJCheckBoxMenuItemActionPerformed
        if(timerJCheckBoxMenuItem.isSelected()) { //selected, set labels visible and set to true
            timeJLabel.setText("Time Remaining: ");
            remainJLabel.setVisible(true);
            timerSettingsJMenuItem.setEnabled(true);
            currentPlayer.setTimerIO(true);
            writeData(); //save data
        }
        else { //not selected, set lables not visible and set variables to false
            timeJLabel.setText("Timer: Disabled");
            remainJLabel.setVisible(false);
            timerSettingsJMenuItem.setEnabled(false);
            currentPlayer.setTimerIO(false);
            writeData(); //save data
        }
    }//GEN-LAST:event_timerJCheckBoxMenuItemActionPerformed
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * gameStatisticsMenuItemActionPerformed
 * Description builds a Statistics object and displays a dialog of
 *                   the overall 24 point game statistics.
 * @param evt gameStatisticsMenuItem click
 * date 03/08/2017
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void gameStatisticsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gameStatisticsMenuItemActionPerformed
        clear(); //stops game if one was in progress
        //may take a moment, set mouse to wait cursor
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        boolean gameStatistics = true; //need game statistics
        Statistics game = new Statistics(this, true, currentPlayer, gameStatistics);
        game.setVisible(true);
        //return cursor to normal
        setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_gameStatisticsMenuItemActionPerformed
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * solJButtonActionPerformed
     * Description upon confirmation from user, ends the current hand as fail (if hand is still in progress)
     *              fills array with the solutions from the solutions List and passes to the 
     *              SolutionList class to display all solutions found for the current hand
     * @param evt solJButton click
     * date 03/08/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void solJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_solJButtonActionPerformed
        int solutionsGivenFail = 1;
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure? This will end"
                + " the hand if the game is still in progress.", "Show Solutions",
                JOptionPane.OK_CANCEL_OPTION);
        if(confirm == JOptionPane.OK_OPTION) { //if user confirmed, show solutions
            fail(solutionsGivenFail); //call the fail method for solutions given
            String[] solArray = new String[solutions.size()]; //fill array with solutions
                for(int i = 0; i < solutions.size(); i++) 
                    solArray[i] = solutions.get(i);
            SolutionList solList = new SolutionList(this, true, solArray); //creates solution dialog object
            solList.setVisible(true);
        }//else do nothing
    }//GEN-LAST:event_solJButtonActionPerformed

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * trainerJMenuItemActionPerformed
     * Description loads a practice game where the user can put in any 4 card values and the 
     * program will fetch alll posible solutions and display for the user
     * @param evt trainerJMenuItem click
     * 03/09/2017
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void trainerJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trainerJMenuItemActionPerformed
        clear(); //ends current game if one is in progress
        TrainerGame trainer = new TrainerGame(this, true); //create new Trainer Game object
        trainer.setVisible(true); //display Trainer game
    }//GEN-LAST:event_trainerJMenuItemActionPerformed
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * solJMenuItemActionPerformed
     * Description fires the solJButton to see solutions
     * @param evt solJMenuItemAction click
     * 03/08/2017
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void solJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_solJMenuItemActionPerformed
        solJButton.doClick(); 
    }//GEN-LAST:event_solJMenuItemActionPerformed

     /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * main() method
     * starting method creates a splash screen object and displays it prior to running the program
     * @param args the command line arguments
     * date 03/03/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public static void main(String args[]) {
        //creates splash object and sends 3200 miliseconds for duration
       SplashScreen mySplashScreen = new SplashScreen(3200);
       mySplashScreen.showSplash();
       //creates GUI object and sets it visible
       GUIDriver myForm = new GUIDriver();
       myForm.setVisible(true);
    }
    
         ActionListener cardTimerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getCards();
            }
        };
         ActionListener handTimerListener = new ActionListener() {
             int timerFail = 0;
             @Override
             public void actionPerformed(ActionEvent e) {
                 remainJLabel.setText(""+ (timerSeconds - ++counter));
                 if(counter == timerSeconds) 
                 fail(timerFail); //call fail, reason: timer expired
             }
         };

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu ExtrasJMenu;
    private javax.swing.JMenuItem aboutJMenuItem;
    private javax.swing.JPanel backGroundJPanel;
    private javax.swing.JPanel buttonJPanel;
    private javax.swing.JLabel card1JLabel;
    private javax.swing.JLabel card2JLabel;
    private javax.swing.JLabel card3JLabel;
    private javax.swing.JLabel card4JLabel;
    private javax.swing.JPanel cardJPanel;
    private javax.swing.JButton clearJButton;
    private javax.swing.JMenuItem clearJMenuItem;
    private javax.swing.JButton closePJButton;
    private javax.swing.JButton dealJButton;
    private javax.swing.JButton divideJButton;
    private javax.swing.JLabel equalsJLabel;
    private javax.swing.JButton evaluateJButton;
    private javax.swing.JMenuItem exitJMenuItem;
    private javax.swing.JTextField expressionJTextField;
    private javax.swing.JMenu fileJMenu;
    private javax.swing.JMenuItem gameStatisticsMenuItem;
    private javax.swing.JMenu helpJMenu;
    private javax.swing.JButton logoutJButton;
    private javax.swing.JMenuItem logoutJMenuItem;
    private javax.swing.JButton minusJButton;
    private javax.swing.JButton multiplyJButton;
    private javax.swing.JLabel nameJLabel;
    private javax.swing.JButton openPJButton;
    private javax.swing.JPanel operatorJPanel;
    private javax.swing.JMenu playerDataJMenu;
    private javax.swing.JMenuItem playerManagmentJMenuItem;
    private javax.swing.JButton plusJButton;
    private javax.swing.JButton printJButton;
    private javax.swing.JMenuItem printJMenuItem;
    private javax.swing.JLabel remainJLabel;
    private javax.swing.JTextField resultJTextField;
    private javax.swing.JMenuItem saveJMenuItem;
    private javax.swing.JMenuItem searchJMenuItem;
    private javax.swing.JButton solJButton;
    private javax.swing.JLabel solJLabel;
    private javax.swing.JMenuItem solJMenuItem;
    private javax.swing.JPanel solutionsJPanel;
    private javax.swing.JButton statisticsJButton;
    private javax.swing.JMenuItem statisticsJMenuItem;
    private javax.swing.JLabel timeJLabel;
    private javax.swing.JCheckBoxMenuItem timerJCheckBoxMenuItem;
    private javax.swing.JMenuItem timerSettingsJMenuItem;
    private javax.swing.JMenuBar titleJMenuBar;
    private javax.swing.JMenuItem trainerJMenuItem;
    private javax.swing.JLabel val1JLabel;
    private javax.swing.JLabel val2JLabel;
    private javax.swing.JLabel val3JLabel;
    private javax.swing.JLabel val4JLabel;
    private javax.swing.JPanel valueJPanel;
    private javax.swing.JTextField winLossJTextField;
    // End of variables declaration//GEN-END:variables
}
