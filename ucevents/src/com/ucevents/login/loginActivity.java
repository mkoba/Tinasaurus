package com.ucevents.login;
import com.android.ucevents.MainActivity;
import com.android.ucevents.R;
import com.ucevents.signup.signupActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class loginActivity extends Activity{
	Button bLogin;
	Button bBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		addListenerOnButton();
	}

	private void addListenerOnButton() {
		bLogin = (Button) findViewById(R.id.bloginpage);
		bLogin.setOnClickListener(new OnClickListener() {
			 
			//@Override
			public void onClick(View arg0) {
		
				Intent i= new Intent(loginActivity.this, com.ucevents.tab.Tabs.class);
			
				startActivity(i);
				}
			});
		
	}
}