package com.example.chasethetrace;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;

import javax.mail.MessagingException;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class BackgroundTimer extends TimerTask {
	static Hintergrundprozess HP;
	
	public BackgroundTimer (Hintergrundprozess hp) {
		//Referenz zum startenden Hintergrundprozess festlegen
		HP = hp;
	}
	
	/*
	 * 
	 * Logik, die bei "auslösen" des Timers ausgeführt wird
	 * 
	 */

	@Override
	public void run() { 
		Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		
		try {
			//Überprüfen, ob bis jetzt eine Position bestimmt werden konnte:
			if (HP.email.currentLocation != null){
				
				//Wenn eine Position bestimmt wurde, Email mit Link zu der Position und der aktuellen Uhrzeit senden:
				HP.email.sendEmail(HP.Email_adress, "Automatisches Positionsupdate", "Hallo " + HP.Parent_name + ", \n es ist jetzt " + sdf.format(cal.getTime()) + "Uhr und Ihr Kind befindet sich an folgendem Ort:\n http://www.maps.google.com/maps/?q=loc:" + HP.email.currentLocation + "\n Ihr ChaseTheTrace-Team");
				Log.v("Note", "Position:" + HP.email.currentLocation);

			} else {
				
				//Wenn keine Position bestimmt wurde, trotzdem eine Email senden: 
				HP.email.sendEmail(HP.Email_adress, "Automatisches Positionsupdate", "Hallo " + HP.Parent_name + ", \n es ist jetzt " + sdf.format(cal.getTime()) + "Uhr und leider konnte kein Standort ihres Kindes bestimmt werden. \n Ihr ChaseTheTrace-Team");
			}			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
}
