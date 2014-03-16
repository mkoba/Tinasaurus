package com.ucevents.login;

import android.app.Application;

public class UCEvents_App extends Application {
    private String userId; // fb email login
    private String firstName;
    private String lastName;
    private String fbid; // fb user id
    public String getUserId() { return this.userId; }
    public void setUserId(String id) { this.userId = id; }
    public void setFirstName(String name) {this.firstName = name;}
    public String getFirstName() { return this.firstName; }
    public void setLastName(String name) {this.lastName = name;}
    public String getLastName() {return this.lastName; }
    
    public void setFbId(String user) {
    	fbid=user;
    }
    public String getUserFbId() {
    	return fbid;
    }
    /* How to: 
     * UCEvents_App appState = ((UCEvents_App)getApplicationContext());
     * 
     * Grab Userid:
     * String state = appState.getState();
     * 
     * Set Userid:
     * appState.setState(state);
     */
}
