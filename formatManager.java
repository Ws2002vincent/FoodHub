/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodhub;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author User
 */
public class formatManager {
    
    public static String formatNumber(double amount){
    DecimalFormat formatter = new DecimalFormat("#,##0.00");
    return formatter.format(amount);
}
    public String getTime() {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    LocalDateTime now = LocalDateTime.now();

    return now.format(dtf);
}
    
}
