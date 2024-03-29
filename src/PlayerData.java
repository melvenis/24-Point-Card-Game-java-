package project3;

import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Class   PlayerData
 * File:     PlayerDataGUI.java
 * Description:   GUI allows user to select existing, edit, delete or create new player
 * @author        Mel Leggett
 * Environment:   PC, Windows 10, jdk 1.8, NetBeans 8.2
 * Date:         03/05/2017
 * @version         1.0
 * History Log:  03/05/2017
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class PlayerData extends javax.swing.JDialog {
    private static Player chosenPlayer;
    private final ArrayList<Player> players;
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * PlayerData()
     * Description creates PlayerData form sets features
     * @param parent the form to return to upon close
     * @param modal true, pauses parent until this form closes
     * @param players ArrayList of all the Player objects
     * @see java.awt.Toolkit
     *  date 03/05/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public PlayerData(java.awt.Frame parent, boolean modal, ArrayList<Player> players) {
        super(parent, modal);
        //builds form
        initComponents();
         //sets form in center of screen
        this.setLocationRelativeTo(null);
        //sets close as active key (fire on Enter)
        this.getRootPane().setDefaultButton(selectJButton);
        //sets title
        this.setTitle("Player Managment");
        //sets icon on top of Menu
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("src/images/icon.png"));
        //set players to class level
        this.players = players;
        //load list of players to choose from
        existingPlayers();
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * existingPlayer()
     * Description loads all existing players in the list to the JList 
     * and enables valid buttons
     * date 03/05/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void existingPlayers() {
        if(players.size() > 0) { //list exists, load names, enable select button
            selectJButton.setEnabled(true);
            //build new array with all player names
            String[] names = new String[players.size()];
            for(int i = 0; i < players.size(); i++) {
                names[i] = players.get(i).getName();
            }//load array of names into JList
            nameJList.setListData(names);
            nameJList.setSelectedIndex(0); //set index to 0
        }
        else //no players in the list, disable select button
            selectJButton.setEnabled(false);
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * getChosenPlayer()
     * Description returns a Player Object
     * @return chosenPlayer, player object selected or built new
     * date 03/05/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public Player getChosenPlayer() {
        return chosenPlayer;
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * setChosenPlayer()
     * Description allows GUIDriver form to set the current player in database managment
     * @param player player to set chosen player to, (usually null for logout purposes)
     * date 03/09/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setChosenPlayer(Player player) {
        chosenPlayer = player;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundJPanel = new javax.swing.JPanel();
        titleJLabel = new javax.swing.JLabel();
        nameJScrollPane = new javax.swing.JScrollPane();
        nameJList = new javax.swing.JList<>();
        buttonJPanel = new javax.swing.JPanel();
        selectJButton = new javax.swing.JButton();
        newJButton = new javax.swing.JButton();
        editJButton = new javax.swing.JButton();
        deleteJButton = new javax.swing.JButton();
        quitJButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        backgroundJPanel.setBackground(new java.awt.Color(255, 204, 153));

        titleJLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        titleJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleJLabel.setText("24 Point Card Game");

        nameJList.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        nameJScrollPane.setViewportView(nameJList);

        buttonJPanel.setBackground(new java.awt.Color(255, 204, 153));
        buttonJPanel.setLayout(new java.awt.GridLayout(2, 2, 5, 5));

        selectJButton.setBackground(new java.awt.Color(255, 255, 255));
        selectJButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        selectJButton.setMnemonic('s');
        selectJButton.setText("Select Player");
        selectJButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        selectJButton.setEnabled(false);
        selectJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectJButtonActionPerformed(evt);
            }
        });
        buttonJPanel.add(selectJButton);

        newJButton.setBackground(new java.awt.Color(255, 255, 255));
        newJButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        newJButton.setMnemonic('n');
        newJButton.setText("New Player");
        newJButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        newJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newJButtonActionPerformed(evt);
            }
        });
        buttonJPanel.add(newJButton);

        editJButton.setBackground(new java.awt.Color(255, 255, 255));
        editJButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        editJButton.setMnemonic('e');
        editJButton.setText("Edit Player");
        editJButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        editJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editJButtonActionPerformed(evt);
            }
        });
        buttonJPanel.add(editJButton);

        deleteJButton.setBackground(new java.awt.Color(255, 255, 255));
        deleteJButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        deleteJButton.setMnemonic('d');
        deleteJButton.setText("Delete Player");
        deleteJButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        deleteJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteJButtonActionPerformed(evt);
            }
        });
        buttonJPanel.add(deleteJButton);

        quitJButton.setBackground(new java.awt.Color(255, 255, 255));
        quitJButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        quitJButton.setMnemonic('q');
        quitJButton.setText("Quit");
        quitJButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        quitJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitJButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout backgroundJPanelLayout = new javax.swing.GroupLayout(backgroundJPanel);
        backgroundJPanel.setLayout(backgroundJPanelLayout);
        backgroundJPanelLayout.setHorizontalGroup(
            backgroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundJPanelLayout.createSequentialGroup()
                .addGroup(backgroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(quitJButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonJPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(titleJLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(backgroundJPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(nameJScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5))
        );
        backgroundJPanelLayout.setVerticalGroup(
            backgroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundJPanelLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(titleJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameJScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(buttonJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(quitJButton, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 5, Short.MAX_VALUE)
                .addComponent(backgroundJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 5, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(backgroundJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * selectJButtonActionPerformed
     * Description sets the player selected in the JList to chosenPlayer and closes form
     * @param evt selectJButton click
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void selectJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectJButtonActionPerformed
        chosenPlayer = players.get(nameJList.getSelectedIndex());
        this.dispose();
    }//GEN-LAST:event_selectJButtonActionPerformed
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * newJButtonActionPerformed()
     * Description prompts player for new player name and searches List to ensure
     * the name is not a duplicate
     * @param evt newJButton click
     * date 03/05/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void newJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newJButtonActionPerformed
        String newName = JOptionPane.showInputDialog(this, "Enter Your Name:", 
                "New Player", JOptionPane.OK_CANCEL_OPTION);
        //if player is not null and not empty, check for duplicate entry
        if(newName != null && !newName.isEmpty()) { 
            boolean found = false;
           
            //if duplicate hilight the name and allow option to continue this player
            for(int i = 0; i < players.size() && found == false; i++) {
                if(newName.equalsIgnoreCase(players.get(i).getName())) {
                    Exceptions.duplicateEntry(newName);
                    nameJList.setSelectedIndex(i);
                    found = true; //set name found as true, dont make new player
                }                
            } //if the player wasnt found, create a new player
            if(!found) {
                chosenPlayer = new Player(newName);
                players.add(chosenPlayer); //add new player to List
                this.dispose(); //close form
            } 
        }
        else
            Exceptions.nullName(); //warn of empty name entry
    }//GEN-LAST:event_newJButtonActionPerformed

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * editJButtonActionPerformed()
     * Description builds Edit object and opens form to edit existing player
     * @param evt editJButton click
     * date 03/05/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void editJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editJButtonActionPerformed
        //gets index of player to edit
        int index = nameJList.getSelectedIndex();
        //new Edit object and set visible
        Edit editor = new Edit(this, true, players, index);
        editor.setVisible(true);
        //refresh JList after return from editing
        existingPlayers();
        //hilight the player edited
        nameJList.setSelectedIndex(index);
    }//GEN-LAST:event_editJButtonActionPerformed

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * deleteJButtonActionPerformed
     * Description deletes selected player upon confirmation and refreshes list
     * @param evt deleteJButton clicked
     * date 03/05/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void deleteJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteJButtonActionPerformed
        //gets index of player to delete
        int index = nameJList.getSelectedIndex();
        //get confirmation to delete
        int confirmation = JOptionPane.showConfirmDialog(rootPane, "Would you really like to delete " +
                players.get(index).getName() + "? This cannot be undone.", "Delete Player", 
                JOptionPane.YES_NO_OPTION);
        //if confirmed, remove and refresh list, otherwise do nothing
        if(confirmation == JOptionPane.YES_OPTION)
            players.remove(index);
        existingPlayers();
    }//GEN-LAST:event_deleteJButtonActionPerformed

    private void quitJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitJButtonActionPerformed
        System.exit(0); //terminate application
    }//GEN-LAST:event_quitJButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backgroundJPanel;
    private javax.swing.JPanel buttonJPanel;
    private javax.swing.JButton deleteJButton;
    private javax.swing.JButton editJButton;
    private javax.swing.JList<String> nameJList;
    private javax.swing.JScrollPane nameJScrollPane;
    private javax.swing.JButton newJButton;
    private javax.swing.JButton quitJButton;
    private javax.swing.JButton selectJButton;
    private javax.swing.JLabel titleJLabel;
    // End of variables declaration//GEN-END:variables
}
