package com.example.chasethetrace;

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
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {
	
	/*
	 * 		Platz für die Funktionen!
	 * 
	 */
	
	public String getLocation(){
		String location = "";
		
		//Location Manager zur Verwaltung von Standorten:
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		
		
		return location;
		
	}
	
	
	@SuppressLint("NewApi")
	public static void sendEmail(String subject, String content) throws AddressException, MessagingException {
		
		//Internetzugriff aus der "Main Activity" erlauben:
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		
		//Festlegung der Variablen für smtp aus der Datenbank für die "Session":
		String host = "smtp.gmail.com";
		String address = "chasethetracenoreply@gmail.com";
		
		String from = "chasethetracenoreply@gmail.com";
		String pass = "cttaelns";
		String to="lasse.stettner@gmail.com";
		
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
		Log.v("Lol", "Email versendet.. Hoffe ich.");
	}

	
	/*
	 * 		OnCreate-Funktion, Festlegung jegliche Interaktion des Layouts!
	 * 
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button button = (Button) findViewById(R.id.button_send);
		button.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		        // Ausgeführte Logik bei Klick des Buttons
		    	try {
		    		sendEmail("TestMail", "TestMail!");
		    	}
		    	catch (Exception e){
		    		Log.v("Error beim Email-Senden:", e.toString());
		    	}
		    }
		});
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
