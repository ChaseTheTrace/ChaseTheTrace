package com.example.chasethetrace;

import javax.mail.MessagingException;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Hintergrundprozess extends IntentService {
	public static String Email_adress;  
	private static final int INTERVAL = 30000;
	Email email;
	
	public Hintergrundprozess() {
		super("ChaseTheTrace Hintergrundprozess");
		email = new Email(this);
	}
	
	public void onCreate(){}

	public void onDestroy(){}
	
	@Override
	public int onStartCommand(Intent workIntent, int flags, int startId) {
		Email_adress = workIntent.getStringExtra("passed_email");
		while (true) {
			try {
				Thread.sleep(INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		Log.v("Debug", "Durchlauf!");
		try {
			email.sendEmail(Email_adress, "Automatisches Positionsupdate", "");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		}
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		
	}
}
