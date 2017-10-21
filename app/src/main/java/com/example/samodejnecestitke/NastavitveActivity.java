package com.example.samodejnecestitke;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.content.res.Resources;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class NastavitveActivity extends Activity {
	static Calendar calendar;
	TimePicker timePickerCasPreverjanja;
	private PendingIntent pendingIntent;
	static int ura = 17;
	static int minuta =0;
	SharedPreferences mPrefs;
	private PendingIntent alarmIntent;
	public  ArrayList<SpinnerModel> CustomListViewValuesArr = new ArrayList<SpinnerModel>();
    TextView output = null;
    CustomAdapter adapter;
    NastavitveActivity activity = null;
	ListView list;
	  String[] jeziki = {
	    "Slovenšèina",
	    "Crnogoršèina",
	    "Anglešèina"
	  } ;
	  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nastavitve);
		timePickerCasPreverjanja = (TimePicker) findViewById(R.id.timePickerCasPreverjanja);
		timePickerCasPreverjanja.setIs24HourView(true);
		mPrefs = getSharedPreferences("IDvalue",0);
		ura = mPrefs.getInt("shranjenaUraPreverjanja", 17);
		minuta = mPrefs.getInt("shranjenaMinutaPreverjanja", 0);
		timePickerCasPreverjanja.setCurrentHour(ura);
	    timePickerCasPreverjanja.setCurrentMinute(minuta);
	    activity  = this;


        
        Spinner  SpinnerExample = (Spinner)findViewById(R.id.spinnerJeziki);
         
        // Set data in arraylist
        setListData();
         
        // Resources passed to adapter to get image
        Resources res = getResources();
         
        // Create custom adapter object ( see below CustomAdapter.java )
        adapter = new CustomAdapter(activity, R.layout.spinner_rows, CustomListViewValuesArr,res);
         
        // Set adapter to spinner
        SpinnerExample.setAdapter(adapter);
         
        // Listener called when spinner item selected
        SpinnerExample.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                // your code here
                 
                // Get selected row data to show on screen
                //String Company    = ((TextView) v.findViewById(R.id.company)).getText().toString();
                //String CompanyUrl = ((TextView) v.findViewById(R.id.sub)).getText().toString();
                 
                //String OutputMsg = "Selected Company : \n\n"+Company+"\n"+CompanyUrl;
                //output.setText(OutputMsg);
                 
                //Toast.makeText(
                  //      getApplicationContext(),OutputMsg, Toast.LENGTH_LONG).show();
            }
 
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
 
        });
	}


	
	public void setListData()
    {
         
        // Now i have taken static values by loop.
        // For further inhancement we can take data by webservice / json / xml;
         
        for (int i = 0; i < jeziki.length; i++) {
             
            final SpinnerModel sched = new SpinnerModel();
                 
              /******* Firstly take data in model object ******/
               sched.setCompanyName(jeziki[i]);
               //sched.setImage("image"+i);
               //sched.setUrl("http:\\www."+i+".com");
                
            /******** Take Model Object in ArrayList **********/
            CustomListViewValuesArr.add(sched);
        }
         
    }
	
	public void setTime(View view) {
		int hour = timePickerCasPreverjanja.getCurrentHour();
		int minute = timePickerCasPreverjanja.getCurrentMinute();
		mPrefs = getSharedPreferences("IDvalue",0);
		SharedPreferences.Editor editor = mPrefs.edit();
		editor.putInt("shranjenaUraPreverjanja", hour); // value to store
		editor.putInt("shranjenaMinutaPreverjanja", minute); // value to store
		editor.commit();
		//calendar = Calendar.getInstance();
		//calendar.set(Calendar.HOUR_OF_DAY, mPrefs.getInt("shranjenaUraPreverjanja", 17));
		//calendar.set(Calendar.MINUTE, mPrefs.getInt("shranjenaMinutaPreverjanja", 0));
		AlarmReceiver ar = new AlarmReceiver();
		ar.setAlarm(this, hour, minute);


			//String str = mPrefs.getString("sound", "");
			//AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

			//calendar.set(2017, 10, 19, hour, minute+2);
			//calendar.set(minute, 35);
			//Intent alarmIntent = new Intent(this, AlarmReceiver.class);
			//pendingIntent  = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
			//calendar.set(Calendar.HOUR_OF_DAY, mPrefs.getInt("shranjenaUraPreverjanja", 17));
			//calendar.set(Calendar.MINUTE, mPrefs.getInt("shranjenaUraPreverjanja", 0));


			// With setInexactRepeating(), you have to use one of the AlarmManager interval
			// constants--in this case, AlarmManager.INTERVAL_DAY.

			//alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
			//Log.w("Alarm nastavljen", "Alarm nastavljen");




	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nastavitve, menu);
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
