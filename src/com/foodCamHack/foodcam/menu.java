package com.foodCamHack.foodcam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        
        getActionBar().setTitle("Food Cam");
		
		Button explbut = (Button) findViewById(R.id.explore_button);
		explbut.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent("com.foodcamhack.foodcam.EXPLORE"));
			}
		});
		
		Button discBut = (Button) findViewById(R.id.discover_button);
		discBut.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent("com.foodcamhack.foodcam.DISCOVER"));
			}
		});
		
		Button camBut = (Button) findViewById(R.id.camera_button);
		camBut.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent("com.foodcamhack.foodcam.CAMERA"));
			}
		});
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
}
