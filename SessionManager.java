/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodhub;

/**
 *
 * @author User
 */
public class SessionManager {
    private static SessionManager instance;
    private SessionID sessionID;

    // Private constructor to prevent instantiation
    private SessionManager() {
        sessionID = new SessionID(); // Initialize with a new SessionID object
    }

    // Public method to access the singleton instance
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // Method to set the session ID
    public void setSessionID(String sessionid) {
        sessionID.setSessionid(sessionid);
    }

    // Method to get the session ID
    public String getSessionID() {
        return sessionID.getSessionid();
    }
}
