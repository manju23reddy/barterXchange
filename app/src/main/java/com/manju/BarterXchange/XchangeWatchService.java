package com.manju.BarterXchange;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

/**
 * Created by mkodandx on 11/1/2015.
 */
public class XchangeWatchService extends IntentService implements Constants.DownloadNotifier{


    XchangeSharedPrefsHelper mSavedDate = null;

    private float mPreviousQuotes = 0;

    private boolean mIsWaiting = false;


    public XchangeWatchService()
    {
        super(Constants.XCHANGE_SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mSavedDate = new XchangeSharedPrefsHelper(getApplicationContext());
        mPreviousQuotes = mSavedDate.getePreviousQuote();
        getQuotes();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    public void getQuotes()
    {
        XchangeWatchHttpGetter downloader = new XchangeWatchHttpGetter(getApplicationContext(), this);
        if ((null != mSavedDate && null  != mSavedDate.getSharedPrefsData()) && mSavedDate.getSharedPrefsData().getActive() == true) {
            downloader.getQuotes(String.format(Constants.YAHOO_CONVERSION_URL,  mSavedDate.getSharedPrefsData().getFrom()+ mSavedDate.getSharedPrefsData().getTo()));
            mIsWaiting = true;
            while(true == mIsWaiting)
            {
                continue;
            }
            new XchangeWatchAlarmManager(this).setAlarm();
        }
        else{

        }
    }

    @Override
    public void quotesDownloadNotifier(Bundle aData)
    {
        if (null != aData){
            int result_code = aData.getInt(Constants.RESULT_CODE);
            if (result_code == Constants.RESULT_CODE_SUCCESS) {
                String Quotes = aData.getString(Constants.DESTN_CUR_QUOTE);
                float currentQuotes = Float.parseFloat(Quotes);
                if (mPreviousQuotes != currentQuotes ){
                    mPreviousQuotes = currentQuotes;
                    if (null != mSavedDate && true == mSavedDate.getSharedPrefsData().getActive() ) {
                        if (currentQuotes < mSavedDate.getSharedPrefsData().getMin() ){
                            showNotification("Xchange Rate : "+Quotes+". Min target : "+mSavedDate.getSharedPrefsData().getMin());
                        }
                        else if (currentQuotes > mSavedDate.getSharedPrefsData().getMax()){
                            showNotification("Xchange Rate : "+Quotes +". Max target :"+mSavedDate.getSharedPrefsData().getMax());
                        }
                        else{

                        }
                    }
                }
                mSavedDate.updatePreviousQuote(mPreviousQuotes);
            }
            mIsWaiting = false;
        }
    }

    public void showNotification(String text)
    {
        String XchangeWatch = "";
        // Set the notification text and send the notification



        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, BarterXChange.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.conversion_icon)
                .setContentTitle(this.getResources().getString(R.string.Notification_title))
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(0, notification);
    }
}
