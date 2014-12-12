package com.example.chasethetrace;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;

import android.app.IntentService;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;

public class Hintergrundprozess extends IntentService {
	public String Email_adress;  
<<<<<<< Updated upstream
	private static final int INTERVAL = 30000;
=======
	public String Parent_name;
	private static final int INTERVAL = 300000;
>>>>>>> Stashed changes
	Email email;
	DateFormat df;
	
	public Hintergrundprozess() {
		super("ChaseTheTrace Hintergrundprozess");
	}
	
	@Override
	public int onStartCommand(Intent workIntent, int flags, int startId) {
		email=new Email();
		email.startLocationListener(getApplicationContext());
		
		Email_adress = workIntent.getStringExtra("passed_email");
		Parent_name = workIntent.getStringExtra("passed_name");

		
		BackgroundTimer myTask = new BackgroundTimer(this);
		Timer myTimer = new Timer();
		
		myTimer.schedule(myTask, INTERVAL, INTERVAL);

		return START_STICKY;
	}

	@Override
<<<<<<< Updated upstream
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		
=======
	protected void onHandleIntent(Intent workIntent) {
		Email_adress = workIntent.getStringExtra("passed_email");
		Parent_name = workIntent.getStringExtra("passed_name");
		
		try {
			if (email.currentLocation != null){
				Log.v("Debug", "Position:" + email.currentLocation);
				email.sendEmail(Email_adress, 
						"Ihr Kind möchte abgeholt werden!", 
						"Hilfe! \n Achtung, Ihr Kind lässt von seinem Endgerät um Hilfe rufen! Die letzte bekannte Position ist: \n http://www.maps.google.com/maps/?q=loc:" + email.currentLocation + "\n Ihr ChaseTheTrace-Team"); 
			} else {
				Log.v("Debug", "Position:" + email.currentLocation);
				email.sendEmail(Email_adress, "Ihr Kind möchte abgeholt werden!", "Leider konnte keine Position bestimmt werden.");
			}			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
>>>>>>> Stashed changes
	}
}
