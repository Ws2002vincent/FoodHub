/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodhub.vendor;

/**
 *
 * @author User
 */
public class SessionVendor {
    private static SessionVendor instance;
    private SessionVendorID sessionID;
    
    public SessionVendor() {

        sessionID = new SessionVendorID();
    }
    
    public static SessionVendor getInstance() {
        if (instance == null) {
            instance = new SessionVendor();
        }
        return instance;
    }
    
    public void setSessionID(String sessionid) {
        sessionID.setSessionid(sessionid);
    }
    
    public String getSessionID() {
        return sessionID.getSessionid();
    }

}
