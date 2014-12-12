package com.example.chasethetrace;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;

import javax.mail.MessagingException;

public class BackgroundTimer extends TimerTask {
	static Hintergrundprozess HP;
	
	public BackgroundTimer (Hintergrundprozess hp) {
		HP = hp;
	}

	@Override
	public void run() { 
		Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		
		try {
			if (HP.email.currentLocation != null){
<<<<<<< Updated upstream
				HP.email.sendEmail(HP.Email_adress, "Automatisches Positionsupdate", "http://www.maps.google.com/maps/?q=loc:" + HP.email.currentLocation);
=======
				Log.v("Debug", "Position:" + HP.email.currentLocation);
				HP.email.sendEmail(HP.Email_adress, 
						"Automatisches Positionsupdate", 
						"Hallo " + HP.Parent_name + ", \n es ist jetzt " + sdf.format(cal.getTime()) + "Uhr und Ihr Kind befindet sich an folgendem Ort:\n http://www.maps.google.com/maps/?q=loc:" + HP.email.currentLocation + "\n Ihr ChaseTheTrace-Team");
			
>>>>>>> Stashed changes
			} else {
				HP.email.sendEmail(HP.Email_adress, "Automatisches Positionsupdate", "Leider konnte keine Position bestimmt werden.");
			}			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
}
