package com.awesomeprojectui.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.blockerplus.blockerplus.MainActivity;
import com.blockerplus.blockerplus.MainApplication;

public class BootBroadcastReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("BootBroadcastReceiver", "onReceive");
    }
}
