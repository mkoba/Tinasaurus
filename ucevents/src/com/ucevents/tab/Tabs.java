package com.ucevents.tab;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import com.android.ucevents.R;
import com.google.analytics.tracking.android.EasyTracker;

public class Tabs extends FragmentActivity{
	
	private FragmentTabHost mTabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_tab);

	 mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
	    mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
	    mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Events"),
	            com.ucevents.tab.FragEvents.class, null);
	    mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Schedule"),
	    		com.ucevents.tab.FragSchedule.class, null);
	    mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator("Profile"),
	    		com.ucevents.tab.FragProfile.class, null);		
	}
	
	/**
	 * An example Activity using Google Analytics and EasyTracker.
	 */
	  @Override
	  public void onStart() {
	    super.onStart();
	    // The rest of your onStart() code.
	    EasyTracker.getInstance(this).activityStart(this);  // Add this method.
	  }

	  @Override
	  public void onStop() {
	    super.onStop();
	    // The rest of your onStop() code.
	    EasyTracker.getInstance(this).activityStop(this);  // Add this method.
	}
}
