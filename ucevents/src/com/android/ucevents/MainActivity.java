package com.android.ucevents;

import com.ucevents.signup.signupActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
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
	
	public void addListenerOnButton() {
		bsignUp = (Button) findViewById(R.id.signUp);
		bsignUp.setOnClickListener(new OnClickListener() {
			 
			//@Override
			public void onClick(View arg0) {
		
				Intent i= new Intent(MainActivity.this, signupActivity.class);
			
				startActivity(i);
				}
			});
		
		blogin = (Button) findViewById(R.id.login);
		blogin.setOnClickListener(new OnClickListener() {
			 
			//@Override
			public void onClick(View arg0) {
		
				Intent i= new Intent(MainActivity.this, com.ucevents.login.loginActivity.class);
			
				startActivity(i);
				}
			});
	}

}
