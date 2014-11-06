package com.example.chasethetrace;

import java.util.TimerTask;

import javax.mail.MessagingException;

public class BackgroundTimer extends TimerTask {
	static Hintergrundprozess HP;
	
	public BackgroundTimer (Hintergrundprozess hp) {
		HP = hp;
	}

	@Override
	public void run() { 
		try {
			if (HP.email.currentLocation != null){
				HP.email.sendEmail(HP.Email_adress, "Automatisches Positionsupdate", "http://www.maps.google.com/maps/?q=loc:" + HP.email.currentLocation);
			} else {
				HP.email.sendEmail(HP.Email_adress, "Automatisches Positionsupdate", "Leider konnte keine Position bestimmt werden.");
			}			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
}
