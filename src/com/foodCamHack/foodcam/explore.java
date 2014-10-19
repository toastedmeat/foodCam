package com.foodCamHack.foodcam;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

public class explore extends Activity {

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		int currentAPIVersion = android.os.Build.VERSION.SDK_INT;
		if(currentAPIVersion >= android.os.Build.VERSION_CODES.HONEYCOMB){
			getActionBar().setDisplayHomeAsUpEnabled(true);			
		}
		setContentView(R.layout.explore_screen);
	}
	
}
