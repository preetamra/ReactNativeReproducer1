package com.blockerplus.blockerplus;

import static androidx.core.content.ContextCompat.getSystemService;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Browser;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.net.ConnectivityManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import com.awesomeprojectui.Utils.NotificationChannels;
import com.awesomeprojectui.VPNService.Service.BlockerPlusVPNService;
import com.blockerplus.blockerplus.MainActivity;
import com.blockerplus.blockerplus.MainApplication;
import com.blockerplus.blockerplus.Model.AppModel;
import com.blockerplus.blockerplus.Model.ItemModel;
import com.blockerplus.blockerplus.Model.NodeWithText;
import com.blockerplus.blockerplus.Model.TrackerPlusModel;
import com.blockerplus.blockerplus.Utils.Trie;
import com.blockerplus.blockerplus.databaseHandler.AppsDataBase;
import com.blockerplus.blockerplus.databaseHandler.BlackListDataBase;
import com.blockerplus.blockerplus.databaseHandler.WhiteListDataBase;
import com.blockerplus.blockerplus.databaseHandler.TrackerPlus;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.*;
import java.time.*;
import java.time.format.*;
import java.time.LocalDateTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.Duration;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BlockingService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onInterrupt() {
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onDestroy() {

    }

    @Override
    public void onCreate() {

    }

    @Override
    protected void onServiceConnected() {

    }
}