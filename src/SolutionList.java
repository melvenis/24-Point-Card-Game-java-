package project3;

import java.awt.Toolkit;
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Class:            SolutionList
 * File:              SolutionList.java
 * Description:     JDialoug pop-up that gives all solutions for the current 24 point hand
 * @author        Mel Leggett
 * Environment:   PC, Windows 10, jdk 1.8, NetBeans 8.2
 * Date:         03/03/2017
 * @version         1.0
 * History Log:  03/03/2017
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class SolutionList extends javax.swing.JDialog {

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Method           SolutionList()
     * Description     builds form and adds affects
     * @author         Mel Leggett
     * Date            03/03/2017
     * @param parent this
     * @param modal true
     * @param solution array of all solutions to load into the JList
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
  public SolutionList(java.awt.Frame parent, boolean modal, String[] solution) 
  {
        super(parent, modal);
        //builds form
        initComponents();
        //sets form in center of screen
        this.setLocationRelativeTo(null);
        //sets close as active key (fire on Enter)
        this.getRootPane().setDefaultButton(closeJButton);
        //sets title
        this.setTitle("Solution List");
        //sets icon on top of Menu
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("src/images/icon.png"));
        solJList.setListData(solution);
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
        closeJButton = new javax.swing.JButton();
        headingJLabel = new javax.swing.JLabel();
        solJScrollPane = new javax.swing.JScrollPane();
        solJList = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        backgroundJPanel.setBackground(new java.awt.Color(153, 204, 255));

        closeJButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        closeJButton.setMnemonic('c');
        closeJButton.setText("Close");
        closeJButton.setToolTipText("close the form");
        closeJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeJButtonActionPerformed(evt);
            }
        });

        headingJLabel.setFont(new java.awt.Font("MV Boli", 0, 36)); // NOI18N
        headingJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        headingJLabel.setText("Solutions");

        solJList.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        solJList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        solJScrollPane.setViewportView(solJList);

        javax.swing.GroupLayout backgroundJPanelLayout = new javax.swing.GroupLayout(backgroundJPanel);
        backgroundJPanel.setLayout(backgroundJPanelLayout);
        backgroundJPanelLayout.setHorizontalGroup(
            backgroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundJPanelLayout.createSequentialGroup()
                .addGroup(backgroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(closeJButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(headingJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
                    .addComponent(solJScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        backgroundJPanelLayout.setVerticalGroup(
            backgroundJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundJPanelLayout.createSequentialGroup()
                .addComponent(headingJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(solJScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addGap(5, 5, 5)
                .addComponent(closeJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeJButtonActionPerformed
        //closes about form
        this.dispose();
    }//GEN-LAST:event_closeJButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backgroundJPanel;
    private javax.swing.JButton closeJButton;
    private javax.swing.JLabel headingJLabel;
    private javax.swing.JList<String> solJList;
    private javax.swing.JScrollPane solJScrollPane;
    // End of variables declaration//GEN-END:variables
}
