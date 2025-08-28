package foodhub.runner;

import foodhub.Login;
import java.awt.*;
import javax.swing.*;

public class RunnerNavBar extends javax.swing.JPanel {

    private JFrame frame;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private String runnerID;
    
    public RunnerNavBar(JFrame frame, String runnerID, String username, JPanel contentPanel, CardLayout cardLayout) {
        initComponents();
        this.frame = frame;
        this.runnerID = runnerID;
        this.contentPanel = contentPanel;
        this.cardLayout = cardLayout;

        usernameLabel.setText(username);
        usernameLabel.revalidate();
        usernameLabel.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sideNavBar = new javax.swing.JPanel();
        homeBtn = new javax.swing.JButton();
        itemBtn = new javax.swing.JButton();
        orderBtn = new javax.swing.JButton();
        revenueBtn = new javax.swing.JButton();
        signOutBtn = new javax.swing.JButton();
        welcomeLabel = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        homeIcon = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        reviewBtn = new javax.swing.JButton();

        sideNavBar.setBackground(new java.awt.Color(245, 245, 220));
        sideNavBar.setPreferredSize(new java.awt.Dimension(220, 300));

        homeBtn.setBackground(new java.awt.Color(255, 204, 186));
        homeBtn.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        homeBtn.setText("View Task");
        homeBtn.setPreferredSize(new java.awt.Dimension(200, 40));
        homeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                homeBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                homeBtnMouseExited(evt);
            }
        });
        homeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeBtnActionPerformed(evt);
            }
        });

        itemBtn.setBackground(new java.awt.Color(255, 204, 186));
        itemBtn.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        itemBtn.setText("My Task");
        itemBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                itemBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                itemBtnMouseExited(evt);
            }
        });
        itemBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemBtnActionPerformed(evt);
            }
        });

        orderBtn.setBackground(new java.awt.Color(255, 204, 186));
        orderBtn.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        orderBtn.setText("Task History");
        orderBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                orderBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                orderBtnMouseExited(evt);
            }
        });
        orderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderBtnActionPerformed(evt);
            }
        });

        revenueBtn.setBackground(new java.awt.Color(255, 204, 186));
        revenueBtn.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        revenueBtn.setText("Revenue Dashboard");
        revenueBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                revenueBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                revenueBtnMouseExited(evt);
            }
        });
        revenueBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                revenueBtnActionPerformed(evt);
            }
        });

        signOutBtn.setBackground(new java.awt.Color(255, 204, 186));
        signOutBtn.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        signOutBtn.setText("Sign Out");
        signOutBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signOutBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                signOutBtnMouseExited(evt);
            }
        });
        signOutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signOutBtnActionPerformed(evt);
            }
        });

        welcomeLabel.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        welcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        welcomeLabel.setText("Welcome Back,");

        usernameLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        usernameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        homeIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foodhub/images/Foodhub.png"))); // NOI18N
        homeIcon.setText("FoodHub");
        homeIcon.setMaximumSize(new java.awt.Dimension(151, 96));
        homeIcon.setPreferredSize(new java.awt.Dimension(141, 96));

        jSeparator1.setBackground(new java.awt.Color(211, 211, 211));

        reviewBtn.setBackground(new java.awt.Color(255, 204, 186));
        reviewBtn.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        reviewBtn.setText("Customer Review");
        reviewBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reviewBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                reviewBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                reviewBtnMouseExited(evt);
            }
        });
        reviewBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reviewBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sideNavBarLayout = new javax.swing.GroupLayout(sideNavBar);
        sideNavBar.setLayout(sideNavBarLayout);
        sideNavBarLayout.setHorizontalGroup(
            sideNavBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideNavBarLayout.createSequentialGroup()
                .addGroup(sideNavBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sideNavBarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(sideNavBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addGroup(sideNavBarLayout.createSequentialGroup()
                                .addGroup(sideNavBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(welcomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(reviewBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(sideNavBarLayout.createSequentialGroup()
                        .addGroup(sideNavBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(sideNavBarLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(sideNavBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(revenueBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(signOutBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(orderBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(itemBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(homeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(sideNavBarLayout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addComponent(homeIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 8, Short.MAX_VALUE)))
                .addContainerGap())
        );
        sideNavBarLayout.setVerticalGroup(
            sideNavBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideNavBarLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(homeIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(welcomeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernameLabel)
                .addGap(24, 24, 24)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(homeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(itemBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(orderBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(reviewBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(revenueBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(signOutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sideNavBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sideNavBar, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void homeBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeBtnMouseEntered
        homeBtn.setBackground(new Color(247, 127, 102));
        homeBtn.setForeground(Color.WHITE);
    }//GEN-LAST:event_homeBtnMouseEntered

    private void homeBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeBtnMouseExited
        homeBtn.setBackground(new Color(255, 204, 186));
        homeBtn.setForeground(Color.BLACK);
    }//GEN-LAST:event_homeBtnMouseExited

    private void homeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeBtnActionPerformed
        cardLayout.show(contentPanel, "ViewTask");
    }//GEN-LAST:event_homeBtnActionPerformed

    private void itemBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemBtnMouseEntered
        itemBtn.setBackground(new Color(247, 127, 102));
        itemBtn.setForeground(Color.WHITE);
    }//GEN-LAST:event_itemBtnMouseEntered

    private void itemBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemBtnMouseExited
        itemBtn.setBackground(new Color(255, 204, 186));
        itemBtn.setForeground(Color.BLACK);
    }//GEN-LAST:event_itemBtnMouseExited

    private void itemBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemBtnActionPerformed
        cardLayout.show(contentPanel, "MyTask");
    }//GEN-LAST:event_itemBtnActionPerformed

    private void orderBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderBtnMouseEntered
        orderBtn.setBackground(new Color(247, 127, 102));
        orderBtn.setForeground(Color.WHITE);
    }//GEN-LAST:event_orderBtnMouseEntered

    private void orderBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderBtnMouseExited
        orderBtn.setBackground(new Color(255, 204, 186));
        orderBtn.setForeground(Color.BLACK);
    }//GEN-LAST:event_orderBtnMouseExited

    private void orderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderBtnActionPerformed
        cardLayout.show(contentPanel, "TaskHistory");
    }//GEN-LAST:event_orderBtnActionPerformed

    private void revenueBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_revenueBtnMouseEntered
        revenueBtn.setBackground(new Color(247, 127, 102));
        revenueBtn.setForeground(Color.WHITE);
    }//GEN-LAST:event_revenueBtnMouseEntered

    private void revenueBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_revenueBtnMouseExited
        revenueBtn.setBackground(new Color(255, 204, 186));
        revenueBtn.setForeground(Color.BLACK);
    }//GEN-LAST:event_revenueBtnMouseExited

    private void revenueBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_revenueBtnActionPerformed
        cardLayout.show(contentPanel, "RunnerRevenue");
    }//GEN-LAST:event_revenueBtnActionPerformed

    private void signOutBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signOutBtnMouseEntered
        signOutBtn.setBackground(new Color(247, 127, 102));
        signOutBtn.setForeground(Color.WHITE);
    }//GEN-LAST:event_signOutBtnMouseEntered

    private void signOutBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signOutBtnMouseExited
        signOutBtn.setBackground(new Color(255, 204, 186));
        signOutBtn.setForeground(Color.BLACK);
    }//GEN-LAST:event_signOutBtnMouseExited

    private void signOutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signOutBtnActionPerformed
        frame.dispose();
        System.out.println("Signing out...");
        new Login().setVisible(true);
    }//GEN-LAST:event_signOutBtnActionPerformed

    private void reviewBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reviewBtnMouseEntered
        reviewBtn.setBackground(new Color(247, 127, 102));
        reviewBtn.setForeground(Color.WHITE);
    }//GEN-LAST:event_reviewBtnMouseEntered

    private void reviewBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reviewBtnMouseExited
        reviewBtn.setBackground(new Color(255, 204, 186));
        reviewBtn.setForeground(Color.BLACK);
    }//GEN-LAST:event_reviewBtnMouseExited

    private void reviewBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reviewBtnActionPerformed
        cardLayout.show(contentPanel, "CustomerReview");
    }//GEN-LAST:event_reviewBtnActionPerformed

    private void reviewBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reviewBtnMouseClicked
        // TODO add your handling code here:
        cardLayout.show(contentPanel, "CustomerReview");
    }//GEN-LAST:event_reviewBtnMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton homeBtn;
    private javax.swing.JLabel homeIcon;
    private javax.swing.JButton itemBtn;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton orderBtn;
    private javax.swing.JButton revenueBtn;
    private javax.swing.JButton reviewBtn;
    private javax.swing.JPanel sideNavBar;
    private javax.swing.JButton signOutBtn;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables
}
