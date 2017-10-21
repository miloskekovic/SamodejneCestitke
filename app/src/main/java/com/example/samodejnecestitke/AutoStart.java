package com.example.samodejnecestitke;

/**
 * Created by milos_000 on 19.10.2017..
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class AutoStart extends BroadcastReceiver
{
    AlarmReceiver alarm = new AlarmReceiver();
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            alarm.setAlarm(context, NastavitveActivity.ura, NastavitveActivity.minuta);
        }
    }
}