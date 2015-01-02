package com.example.chasethetrace;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat") 
public class MainActivity extends ActionBarActivity {
	
	/*
	 * 		Platz für die Funktionen!
	 * 
	 */
	LocationManager locationManager;
	SharedPreferences sharedPref;
	Email email;
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	
	public void firstOpenFinish(View v){

		//Referenz zu den Textfeldern herstellen:
		EditText emailadress = (EditText)findViewById(R.id.Emailfeld);
		EditText username = (EditText)findViewById(R.id.username);
		
		//Einen SharedPreferences Editor initialisieren:
		SharedPreferences.Editor editor = sharedPref.edit();
		
		//Den Editor anweisen, neue Werte in die SharedPreferences zu schreiben:
		editor.putBoolean("isFirstOpen", false);
		editor.putString("receiving_email_adress", emailadress.getText().toString());
		editor.putString("parent_name", username.getText().toString());
		editor.commit();
		
		//Das Layout ändern:
		super.setContentView(R.layout.second_open);
		
		//Den Link im TextView des neuen Layouts "klickbar" machen:
		TextView textview = (TextView) findViewById(R.id.textView4);
		textview.setMovementMethod(LinkMovementMethod.getInstance());
	}
	
	public void secondOpenFinish (View v){
		//Layout ändern:
		super.setContentView(R.layout.activity_main);
		
		//Hintergrundprozess starten:
		Intent HintergrundService = new Intent(this, Hintergrundprozess.class);
		HintergrundService.putExtra("passed_email", sharedPref.getString("receiving_email_adress", ""));
		HintergrundService.putExtra("passed_name", sharedPref.getString("parent_name", "Elternteil"));
		startService(HintergrundService);
		        
	}
	
	public void sendLocationEmail(View v){
				Intent HintergrundService = new Intent(this, Hintergrundprozess.class);
				HintergrundService.putExtra("passed_email", sharedPref.getString("receiving_email_adress", ""));
				HintergrundService.putExtra("passed_name", sharedPref.getString("parent_name", "Elternteil"));
		        startService(HintergrundService);
		        finish();
		}
	
	
	

	
	/*
	 * 		OnCreate-Funktionen
	 * 
	 */

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		
		//Initialisierung der Variablen:
		
		sharedPref = getSharedPreferences("ChaseTheTrace", 0);
		
	
		//Herausfinden, ob die App das erste Mal gestartet wird:
		
		if (sharedPref.getBoolean("isFirstOpen", true) == true){
			setContentView(R.layout.first_open);
		}
		else {
			setContentView(R.layout.activity_main);
			
			//Hintergrundprozess starten:
			Intent HintergrundService = new Intent(this, Hintergrundprozess.class);
			HintergrundService.putExtra("passed_email", sharedPref.getString("receiving_email_adress", null));
	        startService(HintergrundService);
		}
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
