package com.blockerplus.blockerplus;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.awesomeprojectui.VPNService.Server.AbstractDnsServer;
import com.awesomeprojectui.VPNService.Server.DnsServer;
import com.awesomeprojectui.VPNService.Server.DnsServerHelper;
import com.awesomeprojectui.VPNService.Service.BlockerPlusVPNService;
import com.awesomeprojectui.VPNService.Utils.Configurations;
import com.awesomeprojectui.VPNService.Utils.Logger;
import com.awesomeprojectui.VPNService.Utils.Rule;
import com.awesomeprojectui.VPNService.Utils.RuleResolver;
import com.facebook.react.PackageList;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint;
import com.facebook.react.defaults.DefaultReactNativeHost;
import com.facebook.soloader.SoLoader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
// import com.microsoft.codepush.react.CodePush;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {
    private Thread mResolver;
    private static MainApplication instanceApp;
    public static String rulePath;
    public static String logPath;
    private static String configPath;
    public static Configurations configurations;

    public static final List<DnsServer> DNS_SERVERS = new ArrayList<DnsServer>() {{
        add(new DnsServer("185.228.168.10", R.string.server_twnic_primary));
        add(new DnsServer("185.228.169.11", R.string.server_twnic_secondary));
    }};
    private final ReactNativeHost mReactNativeHost =
            new DefaultReactNativeHost(this) {

                @Override
                public boolean getUseDeveloperSupport() {
                    return BuildConfig.DEBUG;
                }

                @Override
                protected List<ReactPackage> getPackages() {
                    @SuppressWarnings("UnnecessaryLocalVariable")
                    List<ReactPackage> packages = new PackageList(this).getPackages();
                    // Packages that cannot be autolinked yet can be added manually here, for example:
                    packages.add(new MyAppPackage());
                    //packages.add(new SplashScreenReactPackage());
                    //packages.add(new ReactNativePushNotificationPackage());
                    return packages;
                }

                @Override
                protected String getJSMainModuleName() {
                    return "index";
                }

                @Override
                protected boolean isNewArchEnabled() {
                    return BuildConfig.IS_NEW_ARCHITECTURE_ENABLED;
                }

                @Override
                protected Boolean isHermesEnabled() {
                    return BuildConfig.IS_HERMES_ENABLED;
                }
            };

  @Override
  public ReactNativeHost getReactNativeHost() {
    return mReactNativeHost;
  }

    private void initDirectory(String dir) {
        File directory = new File(dir);
        if (!directory.isDirectory()) {
            Log.v("VPN Service",dir + " is not a directory. Delete result: " + directory.delete());
        }
        if (!directory.exists()) {
            Log.v("VPN Service",dir + " does not exist. Create result: " + directory.mkdirs());
        }
    }

    public static <T> T parseJson(Class<T> beanClass, JsonReader reader) throws JsonParseException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(reader, beanClass);
    }

  private void initData() {
      Log.v("VPN Service","initData");
      if (getExternalFilesDir(null) != null) {
          Log.v("VPN Service","initData getExternalFilesDir not null");
          rulePath = getExternalFilesDir(null).getPath() + "/rules/";
//          logPath = getExternalFilesDir(null).getPath() + "/logs/";
          configPath = getExternalFilesDir(null).getPath() + "/config.json";

            initDirectory(rulePath);
//          initDirectory(logPath);
      }

      if (configPath != null) {
          Log.v("VPN Service","initData configPath not null load Configurations");
          configurations = Configurations.load(new File(configPath));
      } else {
          Log.v("VPN Service","initData configPath null create new Configurations");
          configurations = new Configurations();
      }
  }

@Override
  public void onCreate() {
    Log.v("VPN Service", "onCreate");
    super.onCreate();
    SoLoader.init(this, /* native exopackage */ false);
    if (BuildConfig.IS_NEW_ARCHITECTURE_ENABLED) {
      // If you opted-in for the New Architecture, we load the native entry point for this app.
      DefaultNewArchitectureEntryPoint.load();
    }
    ReactNativeFlipper.initializeFlipper(this, getReactNativeHost().getReactInstanceManager());

    instanceApp = this;
    Logger.init();
    mResolver = new Thread(new RuleResolver());
    mResolver.start();
    initData();
  }

    public static MainApplication getInstance() {
        return instanceApp;
    }

    public static void activateService(Context context) {
      Log.v("VPN Service","activateService 1");
        activateService(context, false);
    }

    public static Intent getServiceIntent(Context context) {
        return new Intent(context, BlockerPlusVPNService.class);
    }

    public static void deactivateService(Context context) {
        context.startService(getServiceIntent(context).setAction(BlockerPlusVPNService.ACTION_DEACTIVATE));
        context.stopService(getServiceIntent(context));
    }

    public static void initRuleResolver() {
        ArrayList<String> pendingLoad = new ArrayList<>();
        ArrayList<Rule> usingRules = configurations.getUsingRules();
        if (usingRules != null && usingRules.size() > 0) {
            for (Rule rule : usingRules) {
                if (rule.isUsing()) {
                    pendingLoad.add(rulePath + rule.getFileName());
                }
            }
            if (pendingLoad.size() > 0) {
                String[] arr = new String[pendingLoad.size()];
                pendingLoad.toArray(arr);
                switch (usingRules.get(0).getType()) {
                    case Rule.TYPE_HOSTS:
                        RuleResolver.startLoadHosts(arr);
                        break;
                    case Rule.TYPE_DNAMASQ:
                        RuleResolver.startLoadDnsmasq(arr);
                        break;
                }
            } else {
                RuleResolver.clear();
            }
        } else {
            RuleResolver.clear();
        }
    }

    public static void activateService(Context context, boolean forceForeground) {
        Log.v("VPN Service","activateService 2");
        BlockerPlusVPNService.primaryServer = (AbstractDnsServer) DNS_SERVERS.get(0);
        BlockerPlusVPNService.secondaryServer = (AbstractDnsServer) DNS_SERVERS.get(1);

        //Log.v("VPN Service","activateService primaryServer "+BlockerPlusVPNService.primaryServer.getRealName());
        //Log.v("VPN Service","activateService secondaryServer "+BlockerPlusVPNService.secondaryServer.getRealName());

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
        {
            //Start Foreground Service
//            context.startForegroundService(MainApplication.getServiceIntent(context).setAction(BlockerPlusVPNService.ACTION_ACTIVATE));
        }else{
            //Start Background Service
//            context.startService(MainApplication.getServiceIntent(context).setAction(BlockerPlusVPNService.ACTION_ACTIVATE));
        }
        context.startService(MainApplication.getServiceIntent(context).setAction(BlockerPlusVPNService.ACTION_ACTIVATE));
    }
}
