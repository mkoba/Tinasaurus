package com.ucevents.menu;

import com.android.ucevents.R;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;


public class LogoutMenuActivity extends Activity{
	Button ballEvents;
	Button bbyCategory;
	Button bUserInterest;
	Button logout;
/*	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_events);
    }*/
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout, menu);
 
        return super.onCreateOptionsMenu(menu);
    }
	
	/**
     * On selecting action bar icons
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.action_logout:
        	clickedLogout();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

  
	// clicks logout
	private void clickedLogout() {
		//Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(LogoutMenuActivity.this, com.android.ucevents.LogoutActivity.class);
		startActivity(intent);
	}
}
