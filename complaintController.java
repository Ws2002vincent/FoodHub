
package foodhub;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class complaintController {
    private  complaintManager complaintManager;
    private  DefaultTableModel tableModel;
    private  JTable table;
    private  JTextArea complaintContentArea;
    private  JTextArea replyArea;

    public complaintManager getComplaintManager() {
        return complaintManager;
    }

    public void setComplaintManager(complaintManager complaintManager) {
        this.complaintManager = complaintManager;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public JTextArea getComplaintContentArea() {
        return complaintContentArea;
    }

    public void setComplaintContentArea(JTextArea complaintContentArea) {
        this.complaintContentArea = complaintContentArea;
    }

    public JTextArea getReplyArea() {
        return replyArea;
    }

    public void setReplyArea(JTextArea replyArea) {
        this.replyArea = replyArea;
    }
    
    

    public complaintController(complaintManager complaintManager, DefaultTableModel tableModel, JTable table, JTextArea complaintContentArea, JTextArea replyArea) {
        this.complaintManager = complaintManager;
        this.tableModel = tableModel;
        this.table = table;
        this.complaintContentArea = complaintContentArea;
        this.replyArea = replyArea;
    }

    // Update the table based on selected filters
    public void updateTable(String category, String answerStatus) {
    tableModel.setRowCount(0); // Clear the table

    // Debugging logs to track filtering
    System.out.println("Updating table with:");
    System.out.println("Category: " + category);
    System.out.println("Answer Status: " + answerStatus);

    List<complaintModel> filteredComplaints = complaintManager.filterComplaints(category, answerStatus);
    for (complaintModel complaint : filteredComplaints) {
        tableModel.addRow(new Object[]{complaint.getComplainID(), complaint.getUsername()});
    }

    // Debugging log to verify complaints being added to the table
    System.out.println("Number of complaints displayed: " + tableModel.getRowCount());
}

    // Display the selected complaint's details in the text areas
    public void showComplaintDetails() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String complainID = (String) tableModel.getValueAt(selectedRow, 0);
            complaintModel complaint = complaintManager.getComplaints().stream()
                    .filter(c -> c.getComplainID().equals(complainID))
                    .findFirst()
                    .orElse(null);
            if (complaint != null) {
                complaintContentArea.setText(complaint.getComment());
                replyArea.setText(complaint.getAnswer() == null ? "" : complaint.getAnswer());
            }
        }
    }

    // Save the reply for the selected complaint and update the file
    public void saveReply() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String complainID = (String) tableModel.getValueAt(selectedRow, 0);
            complaintModel complaint = complaintManager.getComplaints().stream()
                    .filter(c -> c.getComplainID().equals(complainID))
                    .findFirst()
                    .orElse(null);
            if (complaint != null) {
                complaint.setAnswer(replyArea.getText());
                complaintManager.saveComplaints();
                JOptionPane.showMessageDialog(null, "Reply saved successfully!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a complaint to reply to.");
        }
    }
}
