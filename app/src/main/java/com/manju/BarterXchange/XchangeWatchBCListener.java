package com.manju.BarterXchange;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by mkodandx on 11/1/2015.
 */
public class XchangeWatchBCListener extends BroadcastReceiver {
    int notifyID=1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, XchangeWatchService.class);
        context.startService(serviceIntent);
    }

}