package com.ucevents.tab;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import com.android.ucevents.R;
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
}
