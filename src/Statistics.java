package project3;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.text.DecimalFormat;
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Class  Statistics
 File Statistics.java
 Description  Displays the statistics of the selected Player and Game overall
 *  @author        Mel Leggett
 * Date             03/06/2017
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class Statistics extends javax.swing.JDialog {
    private Player player;
    private int solvedHands = 0; //number of hands found with a solution

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Method          Statistics
     * Description     constructs form, adds effects to display
     * @author          Mel Leggett
     * Date                03/06/2017
     * @see               java.awt.Toolkit
     * @param parent the form that called this form
     * @param modal pause or allow the form to continue in the background, in this case true
     * @param player the player whose statistics are to be displayed
     * @param game true false value to display at start up of the player or game overall
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public Statistics(java.awt.Frame parent, boolean modal, Player player, boolean game) {
        super(parent, modal);
        //builds form
        initComponents();
        //sets player to class level
        this.player = player;
        //set addButton as default
        this.getRootPane().setDefaultButton(okJButton); 
        //set icon
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("src/images/icon.png"));
        //center form
        this.setLocationRelativeTo(null);    
        //determines player or game statistics to be displayed and calls event handler to display
        if(game) {
            gameJRadioButton.setSelected(true);
            gameJRadioButton.doClick();
        }
        else {
            playerJRadioButton.setSelected(true);
            playerJRadioButton.doClick();
        }
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * gameStatistics()
     * Description displays game statistics with players win % and the difference in results
     *                      by running all non redundant 4 card hands and searching for at least one solution
     * @see DecimalFormat
     * date 03/06/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void gameStatistics() {
        //titles form
        this.setTitle("Game Statistics");
        //puts proper text in labels for the statistics to be displayed
        headingJLabel.setText("~24 Card Game Info~");
        totalJLabel.setText("Total Unique Hands:");
        wonJLabel.setText("Solveable Hands:");
        percentJLabel.setText("Percent Solveable:");
        streakJLabel.setText("Your win percentage:");
        fastJLabel.setText("Player Error:");
        //resets solvedHands
        solvedHands = 0;
        //may take a moment, set mouse to wait cursor
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //populate the fields with the game stats in the textBoxes
        int total = totalHands();
        totalJTextField.setText("" + total);
        wonJTextField.setText("" + solvedHands);
        DecimalFormat form = new DecimalFormat("0.0%");
        double percent1 = (solvedHands/(total * 1.0));
        percentJTextField.setText(form.format(percent1));
        double percent2 = 0;
        if(player.getGamesPlayed() != 0) //avoid dividing by zero
            percent2 = (player.getGamesWon()/(player.getGamesPlayed()*1.0));
        playerWinJTextField.setText(form.format(percent2));
        errorJTextField.setText(form.format(1 - (percent2 / percent1)));
        //return cursor to normal
        setCursor(Cursor.getDefaultCursor());
    }
    
      /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * playerStatistics()
     * Description displays player's statistics and personal records for speed and win streak
     * @see DecimalFormat
     * date 03/06/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void playerStatistics() {
        //titles form
        this.setTitle("Player Statistics");
        //puts proper text in labels for the statistics to be displayed
        headingJLabel.setText(player.getName() + " Stats:");
        totalJLabel.setText("Total Games: ");
        wonJLabel.setText("Games Won: ");
        percentJLabel.setText("Win Percentage: ");
        streakJLabel.setText("Longest Win Streak: ");
        fastJLabel.setText("Quickest Solution:");
        
        //populate the player fields with the stats
        totalJTextField.setText(""+player.getGamesPlayed());
        wonJTextField.setText(""+player.getGamesWon());
        double percent = 0;
        if(player.getGamesPlayed() != 0) //avoid dividing by zero
            percent = (player.getGamesWon()/(player.getGamesPlayed()*1.0));
         DecimalFormat form = new DecimalFormat("0.0%");
        percentJTextField.setText(form.format(percent));
        playerWinJTextField.setText(String.valueOf(player.getRecordStreak()));
        errorJTextField.setText(player.getRecordSpeed() + " seconds");
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * totalHands
     * Description cycles through all non redundant 4 card hands (ignoring suit) and returns
     * the total possible hands.  Also sends each unique hand to hasSolution() to find if the 
     * hand has a solution for 24
     * @return total uinque hands
     * date 03/10/2017
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public int totalHands() {
        int regularCount = 0;
        for(int i = 0; i < 10; i++) {
            for(int j = i+1; j < 11; j++) {
                for(int k = j+1; k < 12; k++) {
                    for(int l = k+1; l< 13; l++) {
                        regularCount++;
                        hasSolution(i,j,k,l);
                    }
                }
            }
        }
        int doubleCount = 0;
        for(int i = 0; i <13; i++) {
            for(int j = 0; j < 12; j++) {
                for(int k = j+1; k < 13; k++) {
                    doubleCount++;
                    hasSolution(i,i,j,k);
                }
            }
        }
        int tripleCount = 0; 
        for(int i = 0; i < 13; i++) {
            hasSolution(i,i,i,i);
            tripleCount++;
        }
        return (regularCount + doubleCount + tripleCount);
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * hasSolution
     * Description takes the value for the 4 cards passed, makes a new deck object
     * and searches if it has a solution, if so, increments the counter
     * @param a card 1
     * @param b card 2
     * @param c card 3
     * @param d  card 4
     * date 03/10/2017
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void hasSolution(int a, int b, int c, int d) {
        //creates hand of cards with the values being tested
        DeckOfCards deck = new DeckOfCards(a, b, c, d);
        //replace the card index with the card value
        a = DeckOfCards.cardValue1;
        b = DeckOfCards.cardValue2;
        c = DeckOfCards.cardValue3;
        d = DeckOfCards.cardValue4;
        Solutions sol = new Solutions(); //new Solutions object
        solvedHands += sol.findOneSolution(a, b, c, d); //increments if solution found
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gameButtonGroup = new javax.swing.ButtonGroup();
        backGroundJPanel = new javax.swing.JPanel();
        headingJPanel = new javax.swing.JPanel();
        iconJLabel = new javax.swing.JLabel();
        headingJLabel = new javax.swing.JLabel();
        radioJPanel = new javax.swing.JPanel();
        gameJRadioButton = new javax.swing.JRadioButton();
        playerJRadioButton = new javax.swing.JRadioButton();
        labelJPanel = new javax.swing.JPanel();
        totalJLabel = new javax.swing.JLabel();
        wonJLabel = new javax.swing.JLabel();
        percentJLabel = new javax.swing.JLabel();
        streakJLabel = new javax.swing.JLabel();
        fastJLabel = new javax.swing.JLabel();
        inputJPanel = new javax.swing.JPanel();
        totalJTextField = new javax.swing.JTextField();
        wonJTextField = new javax.swing.JTextField();
        percentJTextField = new javax.swing.JTextField();
        playerWinJTextField = new javax.swing.JTextField();
        errorJTextField = new javax.swing.JTextField();
        southJPanel = new javax.swing.JPanel();
        okJButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        backGroundJPanel.setBackground(new java.awt.Color(204, 204, 255));
        backGroundJPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        headingJPanel.setBackground(new java.awt.Color(204, 204, 255));

        iconJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconJLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon.png"))); // NOI18N

        headingJLabel.setFont(new java.awt.Font("Segoe Print", 1, 22)); // NOI18N
        headingJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        headingJLabel.setText("~ 24 Card Game Info ~");

        radioJPanel.setBackground(new java.awt.Color(204, 204, 255));
        radioJPanel.setLayout(new java.awt.GridLayout(2, 0));

        gameJRadioButton.setBackground(new java.awt.Color(204, 204, 255));
        gameButtonGroup.add(gameJRadioButton);
        gameJRadioButton.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        gameJRadioButton.setSelected(true);
        gameJRadioButton.setText("Game Stats");
        gameJRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gameJRadioButtonActionPerformed(evt);
            }
        });
        radioJPanel.add(gameJRadioButton);

        playerJRadioButton.setBackground(new java.awt.Color(204, 204, 255));
        gameButtonGroup.add(playerJRadioButton);
        playerJRadioButton.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        playerJRadioButton.setText("Player Stats");
        playerJRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playerJRadioButtonActionPerformed(evt);
            }
        });
        radioJPanel.add(playerJRadioButton);

        javax.swing.GroupLayout headingJPanelLayout = new javax.swing.GroupLayout(headingJPanel);
        headingJPanel.setLayout(headingJPanelLayout);
        headingJPanelLayout.setHorizontalGroup(
            headingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headingJPanelLayout.createSequentialGroup()
                .addGroup(headingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(headingJPanelLayout.createSequentialGroup()
                        .addComponent(iconJLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radioJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(headingJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        headingJPanelLayout.setVerticalGroup(
            headingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headingJPanelLayout.createSequentialGroup()
                .addGroup(headingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iconJLabel)
                    .addGroup(headingJPanelLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(radioJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(headingJLabel))
        );

        labelJPanel.setBackground(new java.awt.Color(204, 204, 255));
        labelJPanel.setLayout(new java.awt.GridLayout(5, 0));

        totalJLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        totalJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalJLabel.setText("Total Unique Hands:");
        labelJPanel.add(totalJLabel);

        wonJLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        wonJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        wonJLabel.setText("Solveable Hands:");
        labelJPanel.add(wonJLabel);

        percentJLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        percentJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        percentJLabel.setText("Percent Solveable:");
        labelJPanel.add(percentJLabel);

        streakJLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        streakJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        streakJLabel.setText("Your Win Percentage:");
        labelJPanel.add(streakJLabel);

        fastJLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        fastJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        fastJLabel.setText("User Error:");
        labelJPanel.add(fastJLabel);

        inputJPanel.setBackground(new java.awt.Color(204, 204, 255));
        inputJPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        inputJPanel.setLayout(new java.awt.GridLayout(5, 1));

        totalJTextField.setEditable(false);
        totalJTextField.setBackground(new java.awt.Color(255, 255, 255));
        totalJTextField.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        totalJTextField.setForeground(new java.awt.Color(51, 51, 51));
        totalJTextField.setToolTipText("Total Games");
        totalJTextField.setEnabled(false);
        inputJPanel.add(totalJTextField);

        wonJTextField.setEditable(false);
        wonJTextField.setBackground(new java.awt.Color(255, 255, 255));
        wonJTextField.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        wonJTextField.setToolTipText("Games Won");
        wonJTextField.setEnabled(false);
        inputJPanel.add(wonJTextField);

        percentJTextField.setEditable(false);
        percentJTextField.setBackground(new java.awt.Color(255, 255, 255));
        percentJTextField.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        percentJTextField.setToolTipText("Win Percentage %");
        percentJTextField.setEnabled(false);
        inputJPanel.add(percentJTextField);

        playerWinJTextField.setEditable(false);
        playerWinJTextField.setBackground(new java.awt.Color(255, 255, 255));
        playerWinJTextField.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        playerWinJTextField.setToolTipText("Most wins in a row");
        playerWinJTextField.setEnabled(false);
        inputJPanel.add(playerWinJTextField);

        errorJTextField.setEditable(false);
        errorJTextField.setBackground(new java.awt.Color(255, 255, 255));
        errorJTextField.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        errorJTextField.setToolTipText("Record time for fastest solution");
        errorJTextField.setEnabled(false);
        inputJPanel.add(errorJTextField);

        southJPanel.setBackground(new java.awt.Color(204, 204, 255));
        southJPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        okJButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        okJButton.setMnemonic('O');
        okJButton.setText("Ok");
        okJButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        okJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okJButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout southJPanelLayout = new javax.swing.GroupLayout(southJPanel);
        southJPanel.setLayout(southJPanelLayout);
        southJPanelLayout.setHorizontalGroup(
            southJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, southJPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(okJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128))
        );
        southJPanelLayout.setVerticalGroup(
            southJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, southJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(okJButton, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout backGroundJPanelLayout = new javax.swing.GroupLayout(backGroundJPanel);
        backGroundJPanel.setLayout(backGroundJPanelLayout);
        backGroundJPanelLayout.setHorizontalGroup(
            backGroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backGroundJPanelLayout.createSequentialGroup()
                .addComponent(labelJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(backGroundJPanelLayout.createSequentialGroup()
                .addComponent(headingJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 15, Short.MAX_VALUE))
            .addComponent(southJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        backGroundJPanelLayout.setVerticalGroup(
            backGroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backGroundJPanelLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(headingJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(backGroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(southJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backGroundJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(backGroundJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okJButtonActionPerformed
        this.dispose(); //close form
    }//GEN-LAST:event_okJButtonActionPerformed

    private void gameJRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gameJRadioButtonActionPerformed
        if(gameJRadioButton.isSelected())
            gameStatistics(); //calls for the gameStatistics to be displayed if gameRadioButton selected
    }//GEN-LAST:event_gameJRadioButtonActionPerformed

    private void playerJRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playerJRadioButtonActionPerformed
        if(playerJRadioButton.isSelected()) 
            playerStatistics(); //calls for the playerStatistics to be displayed if playerRadioButton selected
    }//GEN-LAST:event_playerJRadioButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backGroundJPanel;
    private javax.swing.JTextField errorJTextField;
    private javax.swing.JLabel fastJLabel;
    private javax.swing.ButtonGroup gameButtonGroup;
    private javax.swing.JRadioButton gameJRadioButton;
    private javax.swing.JLabel headingJLabel;
    private javax.swing.JPanel headingJPanel;
    private javax.swing.JLabel iconJLabel;
    private javax.swing.JPanel inputJPanel;
    private javax.swing.JPanel labelJPanel;
    private javax.swing.JButton okJButton;
    private javax.swing.JLabel percentJLabel;
    private javax.swing.JTextField percentJTextField;
    private javax.swing.JRadioButton playerJRadioButton;
    private javax.swing.JTextField playerWinJTextField;
    private javax.swing.JPanel radioJPanel;
    private javax.swing.JPanel southJPanel;
    private javax.swing.JLabel streakJLabel;
    private javax.swing.JLabel totalJLabel;
    private javax.swing.JTextField totalJTextField;
    private javax.swing.JLabel wonJLabel;
    private javax.swing.JTextField wonJTextField;
    // End of variables declaration//GEN-END:variables
}
