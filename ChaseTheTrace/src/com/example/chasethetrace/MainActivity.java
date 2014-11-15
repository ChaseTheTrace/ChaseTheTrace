package com.example.chasethetrace;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
	
	/*
	 * 		Platz f�r die Funktionen!
	 * 
	 */
	SharedPreferences sharedPref;
	
	public void saveEmail(View v){
		EditText et = (EditText)findViewById(R.id.Emailfeld);
		
		SharedPreferences.Editor editor = sharedPref.edit();
		
		editor.putBoolean("isFirstOpen", false);
		editor.putString("receiving_email_adress", et.getText().toString());
		editor.commit();
		
		super.setContentView(R.layout.activity_main);
		
		//Hintergrundprozess starten:
		
		Intent HintergrundService = new Intent(this, Hintergrundprozess.class);
		HintergrundService.putExtra("passed_email", sharedPref.getString("receiving_email_adress", "lasse.kgs@arcor.de"));
        startService(HintergrundService);
        
	}
	
	public void sendLocationEmail(View v){
		Intent newEmail = new Intent(this, Hintergrundprozess.class);
		newEmail.putExtra("passed_email", sharedPref.getString("receiving_email_adress", "lasse.kgs@arcor.de"));
		startService(newEmail);
		
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
