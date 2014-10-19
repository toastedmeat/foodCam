package com.foodCamHack.foodcam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class discover extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.discover_screen);
		
		int currentAPIVersion = android.os.Build.VERSION.SDK_INT;
		if(currentAPIVersion >= android.os.Build.VERSION_CODES.HONEYCOMB){
			getActionBar().setDisplayHomeAsUpEnabled(true);			
		}
		setContentView(R.layout.explore_screen);
		
	}

}
