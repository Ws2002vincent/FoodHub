package foodhub.runner;

import java.awt.*;
import javax.swing.*;

public class RunnerMenu extends javax.swing.JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;
    private String runnerID;
    
    public RunnerMenu(String runnerID, String username) {
        initComponents();
        this.runnerID = runnerID;
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("Runner Management System");
        this.setLayout(new BorderLayout());
        
        java.net.URL imageURL = getClass().getResource("/foodhub/images/Foodhub.png");
        ImageIcon image = new ImageIcon(imageURL);
        setIconImage(image.getImage());
        
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        
        // Add Content Panel
        contentPanel.add(new RunnerViewTask(runnerID), "ViewTask");
        contentPanel.add(new RunnerMyTask(runnerID), "MyTask");
        contentPanel.add(new RunnerTaskHistory(runnerID), "TaskHistory");
        contentPanel.add(new RunnerReview(runnerID), "CustomerReview");
        contentPanel.add(new RunnerRevenue(runnerID), "RunnerRevenue");
        
        // Add the contentPanel and navbar to the JFrame
        this.setLayout(new BorderLayout());
        this.add(contentPanel, BorderLayout.CENTER);
        
        RunnerNavBar navbar = new RunnerNavBar(this, runnerID, username, contentPanel, cardLayout);
        this.add(navbar, BorderLayout.WEST);
        this.revalidate();
        this.repaint();
    }
    
    private RunnerMenu() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RunnerMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RunnerMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RunnerMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RunnerMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RunnerMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
