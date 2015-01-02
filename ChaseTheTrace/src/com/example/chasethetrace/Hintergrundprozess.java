package com.example.chasethetrace;

import java.util.Timer;

import javax.mail.MessagingException;

import android.app.IntentService;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;

public class Hintergrundprozess extends IntentService {
	public String Email_adress = "";  
	private static final int INTERVAL = 30000;		//Intervall in ms, in dem die Email für automatische Positionsupdates gesendet werden sollen
	public String Parent_name;
	Email email;
	DateFormat df;
	boolean firstOpen;
	
	public Hintergrundprozess() {
			
		//Name des Dienstes für die androidinterne Identifikation festlegen:
		super("ChaseTheTrace Hintergrundprozess");
	}
	
	@Override
	//Beim Start des Hintergrundprozesses:
	public void onCreate() {
		firstOpen = true;
		super.onCreate();
		
		Log.v("Note", "Hintergrundprozess gestartet!");
		email=new Email();
		email.startLocationListener(getApplicationContext());

		//Den BackgroundTimer initialisieren:
		BackgroundTimer Task = new BackgroundTimer(this);
		Timer Timer = new Timer();
		Log.v("Note", "Hintergrundprozess gestartet!");

		
		//Dem Timer mit dem BackgroundTimer und dem INTERVAL starten:
		Timer.schedule(Task, INTERVAL, INTERVAL);
		Log.v("Note", "Timer gestartet!");

	}

	@Override
	//Beim Senden eines Intents an den Hintergrundprozess:
	protected void onHandleIntent(Intent workIntent) {
		Log.v("Note", "HandleIntent gestartet!");
		
		if (firstOpen == true){
			//Wird nur von der OnCreate()-Methode aus der MainActivity ausgeführt (deshalb keine Email senden)!
			//Die lokalen Variablen mit den übergebenen Werten überschreiben:
			Email_adress = workIntent.getStringExtra("passed_email");
			Parent_name = workIntent.getStringExtra("passed_name");
			firstOpen = false;
		} else {
			//Bei Sendung eines Intents eine Email senden, entweder mit Link zur Position des Kindes oder ohne:
			try {
				if (email.currentLocation != null){
					email.sendEmail(Email_adress, 
							"Ihr Kind möchte abgeholt werden!", 
							"Hilfe! \n Achtung, Ihr Kind lässt von seinem Endgerät um Hilfe rufen! Die letzte bekannte Position ist: \n http://www.maps.google.com/maps/?q=loc:" + email.currentLocation + "\n Ihr ChaseTheTrace-Team"); 
				} else {
					email.sendEmail(Email_adress, "Ihr Kind möchte abgeholt werden!", "Leider konnte keine Position bestimmt werden.");
				}			
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}
}
