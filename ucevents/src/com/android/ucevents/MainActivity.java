package com.android.ucevents;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button bsignUp;
	Button blogin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addListenerOnButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void addListenerOnButton() {
		bsignUp = (Button) findViewById(R.id.signUp);
		bsignUp.setOnClickListener(new OnClickListener() {
			 
			//@Override
			public void onClick(View arg0) {
		
				Intent i= new Intent(MainActivity.this, signupActivity.class);
			
				startActivity(i);
				}
			});
	}

}
