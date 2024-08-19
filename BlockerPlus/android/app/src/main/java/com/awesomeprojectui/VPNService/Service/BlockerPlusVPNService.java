package com.awesomeprojectui.VPNService.Service;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.VpnService;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import com.awesomeprojectui.VPNService.Server.AbstractDnsServer;
import com.awesomeprojectui.VPNService.Server.DnsServerHelper;
import com.awesomeprojectui.VPNService.Utils.RuleResolver;
import com.blockerplus.blockerplus.MainActivity;
import com.blockerplus.blockerplus.MainApplication;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

public class BlockerPlusVPNService extends VpnService implements Runnable {
    public static final String ACTION_ACTIVATE = "com.BlockerPlus.BlockerPlusVpnService.ACTION_ACTIVATE";
    public static final String ACTION_DEACTIVATE = "org.BlockerPlus.BlockerPlusVpnService.ACTION_DEACTIVATE";
    private static boolean activated = false;
    public static AbstractDnsServer primaryServer;
    public static AbstractDnsServer secondaryServer;
    private static InetAddress aliasPrimary;
    private static InetAddress aliasSecondary;
    public static boolean isActivated() {
        return activated;
    }
    private Thread mThread = null;
    private boolean running = false;
    private ParcelFileDescriptor descriptor;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onRevoke() {
        super.onRevoke();
        stopThread();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
            switch (intent.getAction())
            {
                case ACTION_ACTIVATE:
                    activated= true;
                    MainApplication.initRuleResolver();
                    startThread();
                    return START_NOT_STICKY;
                case ACTION_DEACTIVATE:
                    stopThread();
                    return START_NOT_STICKY;
            }
        }
        return START_NOT_STICKY;
    }

    private static int getPendingIntent(int flag) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_IMMUTABLE | flag : flag;
    }

    @Override
    public void run() {
       try{
           //DnsServerHelper.buildCache();
           Builder builder = new Builder()
                   .setSession("BlockerPlusVPN")
                   .setConfigureIntent(PendingIntent.getActivity(this, 0,
                           new Intent(this, MainActivity.class),
                           getPendingIntent(PendingIntent.FLAG_ONE_SHOT)));
             //TODO add WhiteList and BlackList logic her
//           if (Daedalus.getPrefs().getBoolean("settings_app_filter_switch", false)) {
//               ArrayList<String> apps = Daedalus.configurations.getAppObjects();
//               if (apps.size() > 0) {
//                   boolean mode = Daedalus.getPrefs().getBoolean("settings_app_filter_mode_switch", false);
//                   for (String app : apps) {
//                       try {
//                           if (mode) {
//                               builder.addDisallowedApplication(app);
//                           } else {
//                               builder.addAllowedApplication(app);
//                           }
//                           Logger.debug("Added app to list: " + app);
//                       } catch (PackageManager.NameNotFoundException e) {
//                           Logger.error("Package Not Found:" + app);
//                       }
//                   }
//               }
//           }

           String format = null;

           for (String prefix : new String[]{"10.0.0", "192.0.2", "198.51.100", "203.0.113", "192.168.50"}) {
               try {
                   builder.addAddress(prefix + ".1", 24);
               } catch (IllegalArgumentException e) {
                   continue;
               }

               format = prefix + ".%d";
               break;
           }

           byte[] ipv6Template = new byte[]{32, 1, 13, (byte) (184 & 0xFF), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

           try {
               InetAddress addr = Inet6Address.getByAddress(ipv6Template);
               Log.v("VPN Service", "configure: Adding IPv6 address" + addr);
               builder.addAddress(addr, 120);
           } catch (Exception e) {
               Log.v("VPN Service", "Error adding IPv6 address: " + e.getMessage());

               ipv6Template = null;
           }

           aliasPrimary = InetAddress.getByName(primaryServer.getAddress());
           aliasSecondary = InetAddress.getByName(secondaryServer.getAddress());

           Log.v("VPN Service", "configure: Adding DNS server" + aliasPrimary);
           Log.v("VPN Service", "configure: Adding DNS server" + aliasSecondary);

           builder.addDnsServer(aliasPrimary).addDnsServer(aliasSecondary);

           descriptor = builder.establish();

           while (running) {
               Thread.sleep(1000);
           }
       }catch (InterruptedException ignored) {
       }catch (Exception e){
            e.printStackTrace();
       }finally {
           stopThread();
       }
    }

    private void stopThread() {
        Log.d("VPN Service", "stopThread");
        activated = false;
        boolean shouldRefresh = false;
        try{
            if (this.descriptor != null) {
                this.descriptor.close();
                this.descriptor = null;
            }
            if (mThread != null) {
                running = false;
                shouldRefresh = true;
                mThread.interrupt();
                mThread = null;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        stopSelf();

        if(shouldRefresh){
            RuleResolver.clear();
        }
    }

    private void startThread() {
        if (this.mThread == null) {
            this.mThread = new Thread(this, "BlockerPlusVpn");
            this.running = true;
            this.mThread.start();
        }
    }
}
