package com.example.samodejnecestitke;



        import android.app.Service;
        import android.content.Intent;
        import android.os.IBinder;

public class YourService extends Service
{
    AlarmReceiver alarm = new AlarmReceiver();
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        alarm.setAlarm(this, NastavitveActivity.ura, NastavitveActivity.minuta);
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        alarm.setAlarm(this, NastavitveActivity.ura, NastavitveActivity.minuta);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
