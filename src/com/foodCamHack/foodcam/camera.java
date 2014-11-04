package com.foodCamHack.foodcam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

public class camera extends Activity {

	private Camera cam = null;
	private ShowCamera showCam;
	TextView txtViewParsedValue;

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
			String pictureLocation = "";
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			if (bitmap == null) {
				Toast.makeText(getApplicationContext(),
						"Error when taking picture", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "Picture taken",
						Toast.LENGTH_SHORT).show();
				File storageDir = new File(
						Environment
								.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
						"foodCam");

				if (!storageDir.exists()) {
					if (!storageDir.mkdir()) {
						Toast.makeText(getApplicationContext(),
								"Failed to create directory", Toast.LENGTH_LONG)
								.show();
					}
				}

				// developer.android.com/training/camera/photobasics.html
				String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
						.format(new Date());
				File pictureName = new File(storageDir.getPath()
						+ File.separator + "IMG_" + timeStamp + ".jpg");

				OutputStream makePicture;

				try {
					makePicture = new FileOutputStream(pictureName);
					makePicture.write(data);
					pictureLocation = pictureName.toString();
					Toast.makeText(getApplicationContext(),
							"Location: " + pictureLocation, Toast.LENGTH_LONG)
							.show();
					makePicture.flush();
					makePicture.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			cam.release();
			String tagsAndProbs = callClarifaiApi(pictureLocation);
			if (tagsAndProbs != null) {
				//txtViewParsedValue.setText(tagsAndProbs);
				//startActivity(new Intent("com.foodcamhack.foodcam.RESULTS"));
			} else {
				Toast.makeText(getApplicationContext(), "Api call failed", Toast.LENGTH_LONG).show();
			}

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
		txtViewParsedValue = (TextView) findViewById(R.id.resultsText);
	}

	public void snapIt(View view) {
		cam.takePicture(null, null, capturedIt);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public String callClarifaiApi(String imageLocation) {
		HttpResponse<JsonNode> response = null;
		JSONObject jsonObject = null;
		JSONArray tags = null, probs = null;
		String urlToken = "https://api.clarifai.com/v1/token/";
		String urlTags = "https://api.clarifai.com/v1/tag/";
		String secret = "vUiBikoM8lQN48kRCSBCh36qA-rYhoIJ1jc6LKkD";
		String clientId = "-QD9nuVdUiex6jTXuIdEMvbACqM7HR27qgeJabmi";
		String token = "", tokenType = ""; // , scope = "" int expires_in = 0;
		try {
			response = Unirest.post(urlToken)
					.field("grant_type", "client_credentials")
					.field("client_id", clientId)
					.field("client_secret", secret).asJson();

			jsonObject = new JSONObject(response.getBody().toString());
			token = jsonObject.getString("access_token");
			// expires_in = jsonObject.getInt("expires_in");
			// scope = jsonObject.getString("scope");
			tokenType = jsonObject.getString("token_type");

			response = Unirest.post(urlTags)
					.header("Authorization", tokenType + " " + token)
					.field("encoded_image", new File(imageLocation)).asJson();
			// System.out.println(response.getBody());

			jsonObject = new JSONObject(response.getBody().toString());
			JSONArray resultsArray = jsonObject.getJSONArray("results");
			tags = resultsArray.getJSONObject(0).getJSONObject("result")
					.getJSONObject("tag").getJSONArray("classes");
			probs = resultsArray.getJSONObject(0).getJSONObject("result")
					.getJSONObject("tag").getJSONArray("probs");

			String parsedTags[] = new String[tags.length()];
			double parsedProbs[] = new double[probs.length()];
			String parsedResultsArray[][] = new String[2][tags.length()];
			String resultsString = "";
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);

			if (jsonObject.getString("status_code").equals("OK")) {
				for (int i = 0; i < tags.length(); i++) {
					parsedTags[i] = tags.getString(i);
					parsedProbs[i] = probs.getDouble(i);
					parsedResultsArray[0][i] = parsedTags[i];
					parsedResultsArray[1][i] = df.format(parsedProbs[i] * 100)
							.toString();
					// System.out.println(parsedTags[i] + " : " +
					// df.format(parsedProbs[i] * 100) + "%");
				}
			}

			for (int i = 0; i < tags.length(); i++) {
				// System.out.println(parsedResultsArray[0][i] + " : " +
				// parsedResultsArray[1][i] + "%");
				resultsString += "\n" + parsedResultsArray[0][i] + " : "
						+ parsedResultsArray[1][i] + "%";
			}
			// System.out.println(response.getBody()); // If you want to see
			// everything in this JSONArray

			Unirest.shutdown();
			return resultsString;
		} catch (UnirestException | IOException | JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
