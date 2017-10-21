package com.example.samodejnecestitke;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsManager;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity {
	private PendingIntent pendingIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if( getIntent().getBooleanExtra("Exit me", false)){
		    finish();
		}
		setContentView(R.layout.activity_main);
		//getApplicationContext().deleteDatabase("MojiKontakti.db");
		//int i = 1;
		//scheduleAlarm();

		/*if(hour==1 && minute==2){
			preverjanjeCeJeDanesKomuRojstniDan(this);
		}*/
		
	}


	
	
	@Override
	public void onResume(){
		Toast.makeText(getApplicationContext(), "jebemu mater", Toast.LENGTH_LONG);

		//getApplicationContext().deleteDatabase("MojiKontakti.db");
		super.onResume();
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
		if (id == R.id.zapriAplikacijo) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this); 
			builder.setTitle("Samodejne cestitke"); 
			builder.setMessage("Ste prepričani da želite zapreti aplikacijo?");

			builder.setPositiveButton("Da", new DialogInterface.OnClickListener() 
			{ 
			public void onClick(DialogInterface dialog, int which) 
			{ 
			// System.exit(0); 
			// Home.this.finish(); 
			Intent intent = new Intent(Intent.ACTION_MAIN); 
			intent.addCategory(Intent.CATEGORY_HOME); 
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			startActivity(intent); 
			finish(); 
			} 
			}); 
			builder.setNegativeButton("Ne", new DialogInterface.OnClickListener(){ 
				public void onClick(DialogInterface dialog, int which){ 
					dialog.dismiss(); 
				} 
			});
			AlertDialog dialog = builder.create(); 
			dialog.show(); 
		}
		return super.onOptionsItemSelected(item);
	}
	
	public boolean pokaziVseKontakte(View view){
		Intent intent = new Intent(getApplicationContext(),com.example.samodejnecestitke.KontaktiActivity.class);
		startActivity(intent);

        return true;
	}
	
	public boolean pokaziNastavitve(View view){
		Intent intent = new Intent(getApplicationContext(),com.example.samodejnecestitke.NastavitveActivity.class);
		startActivity(intent);
        return true;
	}
	
	public boolean pokaziKoledar(View view){

		Calendar calendar = Calendar.getInstance();

		//Toast.makeText(this, satiS, Toast.LENGTH_LONG).show();;
		//preverjanjeCeJeDanesKomuRojstniDan(this);
		Intent intent = new Intent(getApplicationContext(),com.example.samodejnecestitke.MyCalendarActivity.class);
		startActivity(intent);
        return true;
	}
	

	
	public boolean ustvariNoviKontakt(View view){
        //Bundle dataBundle = new Bundle();
        //dataBundle.putInt("id", 0);
    	Intent intent = new Intent(getApplicationContext(),com.example.samodejnecestitke.DodajanjeNovegaKontakta.class);
    	//intent.putExtras(dataBundle);
    	startActivity(intent);
        return true;
    }

	
	public void posljiCestitkoZaRojstniDan(String telefonskaStevilka, String sporocilo){
		Log.w("posljiCestitko", telefonskaStevilka);
		Log.w("posljiCestitko", sporocilo);
	      try {
	         SmsManager smsManager = SmsManager.getDefault();
	         smsManager.sendTextMessage(telefonskaStevilka, null, sporocilo, null, null);
	         Log.w("posljiCestitko", "Sms bi mogel biti zdaj poslan");
	         Toast.makeText(getApplicationContext(), "SMS poslan.", Toast.LENGTH_LONG).show();
	      } catch (Exception e) {
	    	  Log.w("posljiCestitko", "NAPAKA!!!");
	    	  /*Toast.makeText(getApplicationContext(),
         	  "SMS ni poslan, poskusite znova.",
	         Toast.LENGTH_LONG).show();
	         e.printStackTrace();*/
	      }
	}

	
	
	
	
}
