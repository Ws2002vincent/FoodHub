/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodhub;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author akcw0
 */
public class IdGenerator {
       /**
     * Reads the last ID from the given role file, parses it,
     * and returns the next ID. If file is empty or no lines found,
     * returns prefix + "1" (e.g. "C1").
     * 
     * Also ensures we don’t exceed 5 characters total, e.g. "C9999".
     */
    public static String getNextRoleID(String prefix, String roleFilePath) {
        // 1. Read all lines from the role file
        File file = new File(roleFilePath);
        if (!file.exists()) {
            // If file doesn’t exist yet, start from prefix + 1
            return prefix + "1";
        }
        
        String lastLine = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Keep reading until the end
                lastLine = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If file is empty, return prefix + "1"
        if (lastLine == null || lastLine.trim().isEmpty()) {
            return prefix + "1";
        }

        // 2. Parse the ID from the last line
        // Typically the format might be: "C3 | username | ..."
        // So let's split by '|' and look at the first token which is the ID
        String[] tokens = lastLine.split("\\|");
        String lastID = tokens[0].trim(); // e.g. "C3"
        
        // We assume first character is the prefix (e.g. 'C'), rest is the number
        // e.g. lastID = "C3" => prefix = 'C', numberPart = "3"
        String numberPart = lastID.substring(1);
        int numeric = 0;
        try {
            numeric = Integer.parseInt(numberPart);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // 3. Increment the integer portion
        int nextNumeric = numeric + 1;
        
        // 4. Build the next ID with prefix
        // Make sure not to exceed 5 characters total: e.g. "C9999" is max
        // If nextNumeric > 9999, handle as needed (maybe throw an error).
        
        if (nextNumeric > 9999) {
            throw new RuntimeException("ID limit reached for role " + prefix);
        }
        
        // "C" + nextNumeric, e.g. "C4"
        return prefix + nextNumeric;
    }
}