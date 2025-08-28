package foodhub.vendor;

import java.awt.*;
import javax.swing.*;

public class VendorMenu extends javax.swing.JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;
    private String vendorID;

    public VendorMenu(String vendorID, String username) {
        initComponents();
        this.vendorID = vendorID;
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("Food Court Management System");
        this.setLayout(new BorderLayout());

        java.net.URL imageURL = getClass().getResource("/foodhub/images/Foodhub.png");
        ImageIcon image = new ImageIcon(imageURL);
        setIconImage(image.getImage());

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Add Content Panel
        contentPanel.add(new VendorMainPage(vendorID), "HomePage");
        contentPanel.add(new Notifications(vendorID), "Notifications");
        contentPanel.add(new ItemListingPage(vendorID), "ItemListing");
        contentPanel.add(new CheckOrderHistory(vendorID), "OrderHistory");
        contentPanel.add(new CustomerReview(vendorID), "CustomerReview");
        contentPanel.add(new RevenueDashboard(), "RevenueDashboard");

        // Add the contentPanel and navbar to the JFrame
        this.setLayout(new BorderLayout());
        this.add(contentPanel, BorderLayout.CENTER);

        VendorNavBar navbar = new VendorNavBar(this, vendorID, username, contentPanel, cardLayout);
        this.add(navbar, BorderLayout.WEST);
        this.revalidate();
        this.repaint();
    }

    private VendorMenu() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String getVendorID() {
        return vendorID;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 374, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(VendorMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VendorMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VendorMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VendorMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VendorMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
