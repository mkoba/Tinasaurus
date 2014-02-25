package com.ucevents.profile;

import java.util.ArrayList;
import java.util.List;

import com.android.ucevents.R;
import com.ucevents.events.Events;
import com.ucevents.menu.MenuActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ProfileActivity extends MenuActivity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frag_profile);

	}
}
