/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodhub;

/**
 *
 * @author Chan Jia Zhil
 */
public class Customer extends User{
    private String username;
    private String selectedVendor;
    
    public Customer(){    
    }
    
    public Customer(String username, String hashedPassword, String role){
        super(username, hashedPassword, role);
    }
    
    public String getSelectedVendor(){
        return selectedVendor;
    }
    
    public void setSelectedVendor(String vendorID){
        this.selectedVendor = vendorID;
    }
    
    public String getUsername(){
        return username;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
}
