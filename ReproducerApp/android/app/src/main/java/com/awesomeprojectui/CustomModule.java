package com.blockerplus.blockerplus;

import static android.content.Context.MODE_PRIVATE;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.net.VpnService;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import com.awesomeprojectui.Utils.NotificationChannels;
import com.awesomeprojectui.VPNService.Service.BlockerPlusVPNService;
import com.blockerplus.blockerplus.BlockingService;
import com.blockerplus.blockerplus.MainActivity;
import com.blockerplus.blockerplus.Model.AppModel;
import com.blockerplus.blockerplus.Model.ItemModel;
import com.blockerplus.blockerplus.Model.UserModal;
import com.blockerplus.blockerplus.Model.TrackerPlusModel;

import com.blockerplus.blockerplus.databaseHandler.AppsDataBase;
import com.blockerplus.blockerplus.databaseHandler.UserDataBase;
import com.blockerplus.blockerplus.databaseHandler.WhiteListDataBase;
import com.blockerplus.blockerplus.databaseHandler.BlackListDataBase;
import com.blockerplus.blockerplus.databaseHandler.TrackerPlus;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.waseemsabir.betterypermissionhelper.BatteryPermissionHelper;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomModule extends ReactContextBaseJavaModule {

    private FirebaseCrashlytics FirebaseCrashlytics;
    CustomModule(ReactApplicationContext context) {
    }

    @NonNull
    @Override
    public String getName() {
        return "CustomModule";
    }
}