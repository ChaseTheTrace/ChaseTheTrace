package com.example.chasethetrace;

import java.util.Timer;

import javax.mail.MessagingException;

import android.app.IntentService;
import android.content.Intent;

public class Hintergrundprozess extends IntentService {
	public String Email_adress;  
	private static final int INTERVAL = 30000;
	Email email;
	
	public Hintergrundprozess() {
		super("ChaseTheTrace Hintergrundprozess");
	}
	
	@Override
	public int onStartCommand(Intent workIntent, int flags, int startId) {
		email=new Email();
		email.startLocationListener(getApplicationContext());
		
		Email_adress = workIntent.getStringExtra("passed_email");

		
		BackgroundTimer myTask = new BackgroundTimer(this);
		Timer myTimer = new Timer();
		
		myTimer.schedule(myTask, INTERVAL, INTERVAL);

		return START_STICKY;
	}

	@Override
	protected void onHandleIntent(Intent workIntent) {
		Email_adress = workIntent.getStringExtra("passed_email");
		
		try {
			if (email.currentLocation != null){
				email.sendEmail(Email_adress, "Automatisches Positionsupdate", "http://www.maps.google.com/maps/?q=loc:" + email.currentLocation);
			} else {
				email.sendEmail(Email_adress, "Automatisches Positionsupdate", "Leider konnte keine Position bestimmt werden.");
			}			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
