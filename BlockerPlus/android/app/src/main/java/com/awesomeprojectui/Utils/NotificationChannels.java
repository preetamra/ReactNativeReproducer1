package com.awesomeprojectui.Utils;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.blockerplus.blockerplus.MainActivity;
import com.blockerplus.blockerplus.R;

public class NotificationChannels {
    public static void addNotification(Context context) {
        Log.v("myNotificationChannel","Add Notification Channel");
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.play_store_512)
                        .setContentTitle("Notifications Example")
                        .setContentText("This is a notification message")
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);


        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //notification message will get at NotificationView
        notificationIntent.putExtra("message", "This is a notification message");

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
