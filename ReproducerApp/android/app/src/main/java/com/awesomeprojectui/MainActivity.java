package com.blockerplus.blockerplus;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.VpnService;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint;
import com.facebook.react.defaults.DefaultReactActivityDelegate;

import org.devio.rn.splashscreen.SplashScreen;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends ReactActivity {
  private MixpanelAPI mp;
  private static MainActivity instance = null;
  public static final String LAUNCH_ACTION = "org.BlockerPlus.activity.MainActivity.LAUNCH_ACTION";
  public static final int LAUNCH_ACTION_ACTIVATE = 1;
  private static final String PREFS_NAME = "MyPrefsFile";
  private ProgressBar progressBar;
  SharedPreferences sharedPreferences = null;
//  SharedPreferences sharedPreferences = getReactApplicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
  SharedPreferences.Editor editor = null;
  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    SplashScreen.show(this);
    super.onCreate(null);
    // Replace with your Project Token
    mp = MixpanelAPI.getInstance(this, "90ddadf9e1dce47111c15b931491e551",false);
    instance = this;
    sharedPreferences = getApplicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    editor = sharedPreferences.edit();
  }

  @Override
  protected String getMainComponentName() {
    return "AwesomeProjectUI";
  }

  /**
   * Returns the instance of the {@link ReactActivityDelegate}. Here we use a util class {@link
   * DefaultReactActivityDelegate} which allows you to easily enable Fabric and Concurrent React
   * (aka React 18) with two boolean flags.
   */
  @Override
  protected ReactActivityDelegate createReactActivityDelegate() {
    return new DefaultReactActivityDelegate(
        this,
        getMainComponentName(),
        // If you opted-in for the New Architecture, we enable the Fabric Renderer.
        DefaultNewArchitectureEntryPoint.getFabricEnabled(), // fabricEnabled
        // If you opted-in for the New Architecture, we enable Concurrent React (i.e. React 18).
        DefaultNewArchitectureEntryPoint.getConcurrentReactEnabled() // concurrentRootEnabled
        );
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
  }

  public void activateVpn(){
    Log.v("VPN Service", "activateVpn");
    Intent intent = VpnService.prepare(com.blockerplus.blockerplus.MainApplication.getInstance());
    if(intent != null) {
      startActivityForResult(intent, 0);
    }else {
      onActivityResult(0, Activity.RESULT_OK, null);
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    Log.v("VPN Service", "onActivityResult"+requestCode+resultCode);
    Log.v("VPN Service", "onActivityResult"+Activity.RESULT_OK+requestCode);
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK) {
      if(requestCode == 0)
      {
        com.blockerplus.blockerplus.MainApplication.activateService(com.blockerplus.blockerplus.MainApplication.getInstance());
      } else if (requestCode == 1) {
        Log.v("VPN Service", "Device Admin "+editor);
        if(editor != null)
        {
          editor.putString("BlockPreventUninstallState","true");
          editor.commit();
        }
        Log.v("VPN Service","get Block Prevent Uninstall State"+sharedPreferences.getString("BlockPreventUninstallState","false").equals("true"));
      }
    }
  }

  @Override
  public void onNewIntent(Intent intent) {
    int launchAction = intent.getIntExtra(LAUNCH_ACTION, 0);
    Log.v("VPN Service", "onNewIntent" + launchAction);
    if(launchAction == LAUNCH_ACTION_ACTIVATE){
      this.activateVpn();
    }
    super.onNewIntent(intent);
  }
}
