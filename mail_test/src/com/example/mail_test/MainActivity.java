package com.example.mail_test;

import java.util.Properties;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import javax.activation.DataHandler;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMultipart; 
import javax.mail.util.ByteArrayDataSource;

public class MainActivity extends Activity {
	
	public void sendEmail() throws AddressException, MessagingException {
		String host = "smtp.gmail.com";
		String address = "address@gmail.com";
		
		String from = "fromaddress@gmail.com";
		String pass = "password";
		String to="toaddress@gmail.com";
		
		Multipart multiPart;
		String finalString="";
		

		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", address);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		Log.i("Check", "done pops");
		Session session = Session.getDefaultInstance(props, null);
		DataHandler handler=new DataHandler(new ByteArrayDataSource(finalString.getBytes(),"text/plain" ));
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setDataHandler(handler);
		Log.i("Check", "done sessions");
		
		multiPart=new MimeMultipart();

		InternetAddress toAddress;
		toAddress = new InternetAddress(to);
		message.addRecipient(Message.RecipientType.TO, toAddress);
		Log.i("Check", "added recipient");
		message.setSubject("Send Auto-Mail");
		message.setContent(multiPart);
		message.setText("Demo For Sending Mail in Android Automatically");
		
		
		
		Log.i("check", "transport");
		Transport transport = session.getTransport("smtp");
		Log.i("check", "connecting");
		transport.connect(host,address , pass);
		Log.i("check", "wana send");
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		
		Log.i("check", "sent");

	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	try {
					sendEmail();
				} catch (AddressException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
    
    

    
}
