/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodhub;

/**
 *
 * @author User
 */
public class ValidationManager {
    
    //a long convertion validation
    private boolean longValidation(String input){
    if(input == null || input.isEmpty()) return false; // Empty Value
    try{ // Forcefully Convert to Long
        Long.valueOf(input);
        return true; // Integer Value
    } catch (NumberFormatException e) {
        return false;}} // Alphabets

    //check if the input is null
    public boolean nullCheck(String input){ // Check if Input is Empty
        return !(input == null || input.isEmpty() || input.equals(""));
    }
    
    public boolean characterCheck(String input){ // Check if contains any special character
        return !(input.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/? ].*"));
    }
    
    //for phone validation 
    public boolean phoneValidation(String phoneNumber){ // Verify Phone Format
        ValidationManager verify = new ValidationManager();
        String[] phoneTypes = {"603", "604", "605", "606", "607", "608",
                                      "6011", "6012", "6013", "6014", "6015",
                                      "6016", "6017", "6018", "6019"}; // Phone Format Container
        if (!verify.longValidation(phoneNumber)){
            return false;}
        for (String phone : phoneTypes){
            if (phoneNumber.contains(phone)) // Detects Correct Phone Format
                return (10 == phoneNumber.length() || 11 == phoneNumber.length()
                        || phoneNumber.length() == 12);}
        return false;}
    
        // **NEW: Validate password strength**
    public boolean passwordValidation(String password) {
        if (password.length() < 8) {
            return false; // Must be at least 8 characters
        }

        if (!password.matches(".*\\d.*")) {
            return false; // Must contain at least one digit (0-9)
        }

        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            return false; // Must contain at least one special character
        }

        return true; // Valid password
    }

    // **Check if input is a number**
    public boolean isNumeric(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        return input.matches("\\d+"); // Only digits allowed
    }
}
