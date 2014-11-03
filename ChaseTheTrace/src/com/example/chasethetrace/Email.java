package com.example.chasethetrace;

import java.text.DecimalFormat;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class Email {
	String currentLocation;
	
	public Email(Context context){
		startLocationListener(context);
	}
	
	@SuppressLint("NewApi")
	public void sendEmail(String emailadress, String subject, String content) throws AddressException, MessagingException {		
		//Festlegung der Variablen für smtp aus der Datenbank für die "Session":
		String host = "smtp.gmail.com";
		String address = "chasethetracenoreply@gmail.com";
		
		String from = "chasethetracenoreply@gmail.com";
		String pass = "cttaelns";
		String to = emailadress;
		
		Multipart multiPart;
		String finalString="";
		
		//Eigenschaften (Properties) für die session festlegen:
		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", address);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props, null);
		
		//Session und Datahandler (zur Übertragung der Daten) in einer 
		//Mime-Message zusammenfassen:
		DataHandler handler=new DataHandler(new ByteArrayDataSource(finalString.getBytes(),"text/plain" ));
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setDataHandler(handler);
		
		multiPart=new MimeMultipart();

		//Empfängeradresse in Internetadresse umwandeln und der Mime-Message als 
		//Eigenschaft anhängen:
		InternetAddress toAddress;
		toAddress = new InternetAddress(to);
		message.addRecipient(Message.RecipientType.TO, toAddress);
		
		//Betreff, eigentlicher Emailinhalt und der Verweis auf den hier verwendeten
		//multiPart werden der Mime-Message übergeben:
		message.setSubject(subject);
		message.setContent(multiPart);
		message.setText(content);
		
		
		//Transport initiieren:
		Transport transport = session.getTransport("smtp");
		
		//Transportbefehl mit dem Email Server verbinden:
		transport.connect(host,address , pass);
		
		//Transportbefehl ausführen:
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		Log.v("Lol", "Email versendet!");
	}
	
	public void startLocationListener (Context context){
		//Location-Listener registrieren:
		
				LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
				
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
	
	public String convertToString(Location position){
		DecimalFormat df = new DecimalFormat("#.###");
		String locationString = df.format(position.getLatitude()) + "," + df.format(position.getLongitude());
		return locationString;
	}
	
	
	

}
