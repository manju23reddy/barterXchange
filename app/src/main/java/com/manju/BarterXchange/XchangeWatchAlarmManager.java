package com.manju.BarterXchange;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by mkodandx on 11/1/2015.
 */
public class XchangeWatchAlarmManager {
    private Context mContext = null;

    public XchangeWatchAlarmManager(Context aContext)
    {
        mContext = aContext;
    }

    public void setAlarm()
    {
        Calendar cal = Calendar.getInstance();
        Long when = cal.getTimeInMillis() + Constants.DELAY_IN_MINUTES;
        Intent intent = new Intent(mContext, XchangeWatchBCListener.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, Constants.APP_INTENT_KEY, intent, 0);
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,when,20000,pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, when, pendingIntent);

    }

    public void stopAlarm()
    {
        Intent intentstop = new Intent(mContext, XchangeWatchBCListener.class);
        PendingIntent senderstop = PendingIntent.getBroadcast(mContext,
                Constants.APP_INTENT_KEY, intentstop, 0);
        AlarmManager alarmManagerstop = (AlarmManager)  mContext.getSystemService(Context.ALARM_SERVICE);

        alarmManagerstop.cancel(senderstop);
    }
}
