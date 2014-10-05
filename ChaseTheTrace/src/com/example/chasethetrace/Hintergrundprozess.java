package com.example.chasethetrace;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class Hintergrundprozess extends Service{
	public IBinder onBind(Intent intent) {
        //Irrelevant, gehört zu boundServices
        return null;
    }
 
    @Override
    public void onCreate() {
        Log.v("Hintergrundprozess", "Hintergrundprozess erstellt.");
    }
 
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	Log.v("Hintergrundprozess", "Hintergrundprozess gestartet.");

    	
    	
        return START_STICKY;
    }
 
    @Override
    public void onDestroy() {
    	Log.v("Hintergrundprozess", "Hintergrundprozess zerstört.");
    }
}
