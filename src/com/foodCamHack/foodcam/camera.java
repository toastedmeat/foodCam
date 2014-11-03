package com.foodCamHack.foodcam;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

public class camera extends Activity {

	private Camera cam = null;
	private ShowCamera showCam;
	private String pictureLocation;

	public static Camera isCamAvail() {
		Camera Object = null;
		try {
			Object = Camera.open();
		} catch (Exception e) {

		}
		return Object;
	}

	private PictureCallback capturedIt = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {

			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			if (bitmap == null) {
				Toast.makeText(getApplicationContext(), "Error when taking picture",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "Picture taken",
						Toast.LENGTH_SHORT).show();
				File storageDir = new File(Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
						, "foodCam");
				
				if(!storageDir.exists()){
					if(!storageDir.mkdir()){
						Toast.makeText(getApplicationContext()
								, "Failed to create directory", Toast.LENGTH_LONG).show();
					}
				}
				
				//developer.android.com/training/camera/photobasics.html
				String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
				File pictureName = new File(storageDir.getPath()+ File.separator + "IMG_" + timeStamp + ".jpg");
				
				OutputStream makePicture;
				
				try{
					makePicture = new FileOutputStream(pictureName);
					makePicture.write(data);
					pictureLocation = pictureName.toString();
					Toast.makeText(getApplicationContext(), "Location: " + pictureLocation, Toast.LENGTH_LONG).show();
					makePicture.flush();
					makePicture.close();
				} catch (FileNotFoundException e){
					e.printStackTrace();
				} catch (IOException e){
					e.printStackTrace();
				}
			}
			cam.release();
			
		}
	};

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_screen);
		cam = isCamAvail();
		cam.setDisplayOrientation(90);
		showCam = new ShowCamera(this, cam);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(showCam);
	}

	public void snapIt(View view) {
		cam.takePicture(null, null, capturedIt);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public String[] callClarifaiApi(String imageLocation){
		return null;
	}
}
