package com.foodCamHack.foodcam;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

public class results extends Activity {

	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results_screen);
		
		int currentAPIVersion = android.os.Build.VERSION.SDK_INT;
		if(currentAPIVersion >= android.os.Build.VERSION_CODES.HONEYCOMB){
			getActionBar().setDisplayHomeAsUpEnabled(true);	
			getActionBar().setTitle("Results");
			getActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFAD85));
		}
		
	}

}
