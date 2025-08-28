/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodhub;

/**
 *
 * @author User
 */
import javax.swing.JOptionPane;

public class DialogManager {

    private String title;
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Constructor to set a default title
    public DialogManager(String title) {
        this.title = title;
    }

    // Method to show an information dialog
    public void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(
            null,
            message,
            title,
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    // Method to show a warning dialog
    public void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(
            null,
            message,
            title,
            JOptionPane.WARNING_MESSAGE
        );
    }

    // Method to show an error dialog
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(
            null,
            message,
            title,
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    public void showErrorBackend(int number) {
          switch (number) {
            case 1:
                System.out.println("ERROR: session for role id is not set.");
                break;
            case 2:
                System.out.println("ERROR: failed to find the file path, database may be corrupted");
                break;
            case 3:
                System.out.println("Error: Case 3 encountered.");
                break;
            case 4:
                System.out.println("Error: Case 4 encountered.");
                break;
            case 5:
                System.out.println("Error: Case 5 encountered.");
                break;
            case 6:
                System.out.println("Error: Case 6 encountered.");
                break;
            case 7:
                System.out.println("Error: Case 7 encountered.");
                break;
            case 8:
                System.out.println("Error: Case 8 encountered.");
                break;
            case 9:
                System.out.println("Error: Case 9 encountered.");
                break;
            case 10:
                System.out.println("Error: Case 10 encountered.");
                break;
            default:
                System.out.println("Number is out of the expected range.");
                break;
        }
    }    
}

