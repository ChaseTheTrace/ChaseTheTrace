package com.example.chasethetrace;

import java.text.DecimalFormat;
import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
	
	/*
	 * 		Platz für die Funktionen!
	 * 
	 */
	LocationManager locationManager;
	String currentLocation;
	SharedPreferences sharedPref;
	Email email;
	
	public void saveEmail(View v){
		EditText et = (EditText)findViewById(R.id.Emailfeld);
		
		SharedPreferences.Editor editor = sharedPref.edit();
		
		editor.putBoolean("isFirstOpen", false);
		editor.putString("receiving_email_adress", et.getText().toString());
		editor.commit();
		
		super.setContentView(R.layout.activity_main);
		
		//Hintergrundprozess starten:
		
		Log.v("Debug", "Starting Intent!");
		Intent HintergrundService = new Intent(this, Hintergrundprozess.class);
		HintergrundService.putExtra("passed_email", sharedPref.getString("receiving_email_adress", null));
        startService(HintergrundService);
        
	}
	
	public void sendLocationEmail(View v){
		try {
			if (currentLocation != null){
				email.sendEmail(sharedPref.getString("receiving_email_adress", null), "Resquebutton gedrückt!", "http://www.maps.google.com/maps/?q=loc:" + currentLocation);
			} else {
				email.sendEmail(sharedPref.getString("receiving_email_adress", null), "Resquebutton gedrückt!", "Leider konnte keine Position bestimmt werden.");
			}
		}
		catch (Exception e) {Log.v("error:", e.toString());}
	}
	
	public String convertToString(Location position){
		DecimalFormat df = new DecimalFormat("#.###");
		String locationString = df.format(position.getLatitude()) + "," + df.format(position.getLongitude());
		return locationString;
	}
	
	
	

	
	/*
	 * 		OnCreate-Funktion, Festlegung jegliche Interaktion des Layouts!
	 * 
	 */

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		
		//Herausfinden, ob die App das erste Mal gestartet wird:
		
		sharedPref = getSharedPreferences("ChaseTheTrace", 0);
		email = new Email(this);
		
		if (sharedPref.getBoolean("isFirstOpen", true) == true){
			Log.v("log", "way to go!");
			setContentView(R.layout.first_open);
		}
		else {
			Log.v("crap", "crap");
			setContentView(R.layout.activity_main);
			
			//Hintergrundprozess starten:
			Intent HintergrundService = new Intent(this, Hintergrundprozess.class);
			HintergrundService.putExtra("passed_email", sharedPref.getString("receiving_email_adress", null));
	        startService(HintergrundService);
		}
		
		//Location-Listener registrieren:
		
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		
		LocationListener listener = new LocationListener(){
	        @Override
	        public void onLocationChanged(Location location) {
	    		currentLocation = convertToString(location);
	    		Log.v("Check", currentLocation);
	        }
	        	// Uninteressante Optionen, die aber trotzdem gehandlet werden müssen:
	        @Override
	        public void onProviderDisabled(String provider) {}
	        @Override
	        public void onProviderEnabled(String provider) {}
	        @Override
	        public void onStatusChanged(String provider, int status,Bundle extras) {}
		};
		
		if (locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null){
			currentLocation = convertToString(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
		}
		
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener, null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

}
