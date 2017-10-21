package com.example.samodejnecestitke;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.PowerManager;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsManager;
import android.text.format.Time;
import android.util.Log;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    TimePicker casPreverjanja;
    SharedPreferences mPrefs;
    @Override
    public void onReceive(Context context, Intent intent) {
        //String message = "Hellooo, alrm worked ----";
        //Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        //MainActivity ma = new MainActivity();
        //ma.preverjanjeCeJeDanesKomuRojstniDan(context);
        //SmsManager smsManager = SmsManager.getDefault();
        //smsManager.sendTextMessage("070790332", null, "Test1 prestavljen", null, null);
        //Log.w("posljiCestitko", "Sms bi mogel biti zdaj poslan");
        //Toast.makeText(context, "SMS poslan.", Toast.LENGTH_LONG).show();
        //Intent intent2 = new Intent(context, MainActivity.class);
        //intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //context.startActivity(intent2);
        preverjanjeCeJeDanesKomuRojstniDan(context);
        //Toast.makeText(context, "SMS poslan.", Toast.LENGTH_LONG).show();
    }

    public void setAlarm(Context context, int ura, int minuta){
        Log.d("Carbon","Alrm SET !!");

        // get a Calendar object with current time
        // add 30 seconds to the calendar object
        //cal.add(Calendar.SECOND, 30);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        //long minute = (calendar.getTimeInMillis() / (1000 * 60)) % 60;
        //long hour = (calendar.getTimeInMillis() / (1000 * 60 * 60)) % 24;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, ura);
        cal.set(Calendar.MINUTE, minuta);
        //cal.add(Calendar.SECOND, 30);
        // Get the AlarmManager service

        AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, sender);
        Toast.makeText(context, "Ura preverjanja je bila shranjena.", Toast.LENGTH_LONG).show();
    }

    public void preverjanjeCeJeDanesKomuRojstniDan(Context context){
        DBHelper mydb = new DBHelper(context);
        ArrayList<Contact> vsiKontakti = mydb.getAllContacts();
        ArrayList<String[]> podatkiOsebKiImajoDanesRojstniDan = new ArrayList<String[]>();
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        int dan = today.monthDay;             // Day of the month (1-31)
        int mesec = today.month+1;              // Month (0-11)
        for(int i=0; i<vsiKontakti.size(); i++){
            Cursor rs = mydb.getData(i);
            rs.moveToFirst();
            int danRojstva = rs.getInt(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_DANROJSTVA));
            int mesecRojstva = rs.getInt(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_MESECROJSTVA));
            String telefonskaStevilkaSlavljenca;
            String sporociloCestitke;
            if(mesecRojstva == mesec && danRojstva == dan){
                telefonskaStevilkaSlavljenca = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_TELEFONSKASTEVILKA));
                sporociloCestitke = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_SPOROCILOZAROJSTNIDAN));
                String [] podatki = new String [3];
                podatki[0] = telefonskaStevilkaSlavljenca;
                podatki [1] = sporociloCestitke;
                podatkiOsebKiImajoDanesRojstniDan.add(podatki);
            }
            String vera = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_VERA));
            if((mesec == 1 && dan == 7 && vera.equals("Pravoslavni")) || (mesec == 12 && dan == 25 && vera.equals("Katolik"))){
                telefonskaStevilkaSlavljenca = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_TELEFONSKASTEVILKA));
                sporociloCestitke = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_SPOROCILOZABOZIC));
                String [] podatki = new String [3];
                podatki[0] = telefonskaStevilkaSlavljenca;
                podatki [1] = sporociloCestitke;
                podatkiOsebKiImajoDanesRojstniDan.add(podatki);
            }
        }

        //Toast.makeText(this, Integer.toString(mesec), Toast.LENGTH_SHORT).show();
        for(String [] podatkiSlavljenca : podatkiOsebKiImajoDanesRojstniDan){
            posljiCestitkoZaRojstniDan(podatkiSlavljenca[0], podatkiSlavljenca[1]);
        }
        mydb.close();
        //Toast.makeText(this, rojstniDnevi.get(1).toString().split(".")[1], Toast.LENGTH_SHORT).show();
    }

    public void posljiCestitkoZaRojstniDan(String telefonskaStevilka, String sporocilo){
        Log.w("posljiCestitko", telefonskaStevilka);
        Log.w("posljiCestitko", sporocilo);
        try {
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> parts = smsManager.divideMessage(sporocilo);
            smsManager.sendMultipartTextMessage(telefonskaStevilka, null, parts, null, null);

            //smsManager.sendTextMessage(telefonskaStevilka, null, sporocilo, null, null);
            Log.w("posljiCestitko", "Sms bi mogel biti zdaj poslan");
        } catch (Exception e) {
            Log.w("posljiCestitko", "NAPAKA!!!");
	    	  /*Toast.makeText(getApplicationContext(),
         	  "SMS ni poslan, poskusite znova.",
	         Toast.LENGTH_LONG).show();
	         e.printStackTrace();*/
        }
    }


}
