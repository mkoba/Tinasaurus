package com.android.ucevents;

import android.app.Application;

public class UCEvents_App extends Application {
    private String userId;
    public String getUserId() { return this.userId; }
    public void setUserId(String id) { this.userId = id; }
    
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
