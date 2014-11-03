package com.foodCamHack.foodcam;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class explore extends Activity {
	
	private Integer[] pics = { R.drawable.antartica1, R.drawable.antartica2,
			R.drawable.antartica3, R.drawable.antartica4,
			R.drawable.antartica5, R.drawable.antartica6,
			R.drawable.antartica7, R.drawable.antartica8,
			R.drawable.antartica9, R.drawable.antartica10 };
	private ImageView imageView;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		int currentAPIVersion = android.os.Build.VERSION.SDK_INT;
		if(currentAPIVersion >= android.os.Build.VERSION_CODES.HONEYCOMB){
			getActionBar().setDisplayHomeAsUpEnabled(true);
			getActionBar().setTitle("Explore");
			getActionBar().setBackgroundDrawable(new ColorDrawable(0xFF70DBDB));
		}
		setContentView(R.layout.explore_screen);
		
		Gallery gallery = (Gallery) findViewById(R.id.gallery1);
		// create adapter Gallery
		gallery.setAdapter(new ImageAdapter(this));
		imageView = (ImageView) findViewById(R.id.imageView1);
		gallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "pic: " + arg2,
						Toast.LENGTH_SHORT).show();
				imageView.setImageResource(pics[arg2]);
			}
		});
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class ImageAdapter extends BaseAdapter {
		private Context context;
		int imageBackground;

		public ImageAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pics.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ImageView imageView = new ImageView(context);
			imageView.setImageResource(pics[arg0]);
			return imageView;
		}
	}
}

