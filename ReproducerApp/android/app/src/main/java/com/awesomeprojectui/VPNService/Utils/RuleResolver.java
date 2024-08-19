package com.awesomeprojectui.VPNService.Utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class RuleResolver implements Runnable{
    private static int status;
    public static final int MODE_HOSTS = 0;
    public static final int MODE_DNSMASQ = 1;
    public static final int STATUS_LOADED = 0;
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_NOT_LOADED = 2;
    public static final int STATUS_PENDING_LOAD = 3;
    private static int mode = MODE_HOSTS;
    private static String[] hostsFiles;
    private static String[] dnsmasqFiles;
    private static boolean shutdown = false;
    private static HashMap<String, String> rulesA = new HashMap<>();
    private static HashMap<String, String> rulesAAAA = new HashMap<>();
    public RuleResolver() {
        Log.v("VPN Service", "RuleResolver");
        status = STATUS_NOT_LOADED;
        hostsFiles = new String[0];
        dnsmasqFiles = new String[0];
        shutdown = false;
    }

    public static void startLoadHosts(String[] loadFile) {
        hostsFiles = loadFile;
        mode = MODE_HOSTS;
        status = STATUS_PENDING_LOAD;
    }

    public static void startLoadDnsmasq(String[] loadPath) {
        dnsmasqFiles = loadPath;
        mode = MODE_DNSMASQ;
        status = STATUS_PENDING_LOAD;
    }

    public static void clear() {
        rulesA = new HashMap<>();
        rulesAAAA = new HashMap<>();
    }

    private void load() {
        try{
            Log.v("VPN Service", "RuleResolver Load");
            status = STATUS_LOADING;
            rulesA = new HashMap<>();
            rulesAAAA = new HashMap<>();

            if(mode == MODE_HOSTS)
            {
                Log.v("VPN Service","Loading hosts");
                for (String hostsFile : hostsFiles) {
                    Log.v("VPN Service","Loading hosts from " + hostsFile);
                    File file = new File(hostsFile);
                    if (file.canRead()) {
                        Log.v("VPN Service","Loading hosts from " + file);
                        FileInputStream stream = new FileInputStream(file);
                        BufferedReader dataIO = new BufferedReader(new InputStreamReader(stream));
                        String strLine;
                        String[] data;
                        int count = 0;
                        while ((strLine = dataIO.readLine()) != null) {
                            Log.v("VPN Service","Loading hosts from " + strLine);
                            if (!strLine.equals("") && !strLine.startsWith("#")) {
                                Log.v("VPN Service","Loading hosts from " + strLine);
                                data = strLine.split("\\s+");
                                if (strLine.contains(":")) {
                                    rulesAAAA.put(data[1], data[0]);
                                } else if (strLine.contains(".")) {
                                    rulesA.put(data[1], data[0]);
                                }
                                count++;
                            }
                        }

                        dataIO.close();
                        stream.close();

                        Log.v("VPN Service","Loaded " + count + " rules");
                    }
                }
            }else if (mode == MODE_DNSMASQ) {
                for (String dnsmasqFile : dnsmasqFiles) {
                    File file = new File(dnsmasqFile);
                    if (file.canRead()) {
                        Log.v("VPN Service","Loading DNSMasq configuration from " + file);
                        FileInputStream stream = new FileInputStream(file);
                        BufferedReader dataIO = new BufferedReader(new InputStreamReader(stream));
                        String strLine;
                        String[] data;
                        int count = 0;
                        while ((strLine = dataIO.readLine()) != null) {
                            if (!strLine.equals("") && !strLine.startsWith("#")) {
                                data = strLine.split("/");
                                if (data.length >= 2 && data[0].equals("address=")) {
                                    if (data.length == 2) {
                                        rulesA.put(data[1], "0.0.0.0");
                                        count++;
                                        continue;
                                    }
                                    if (data[1].startsWith(".")) {
                                        data[1] = data[1].substring(1);
                                    }
                                    if (strLine.contains(":")) {
                                        rulesAAAA.put(data[1], data[2]);
                                    } else if (strLine.contains(".")) {
                                        rulesA.put(data[1], data[2]);
                                    } else {
                                        rulesA.put(data[1], "0.0.0.0");
                                    }
                                    count++;
                                }
                            }
                        }

                        dataIO.close();
                        stream.close();

                        Log.v("VPN Service","Loaded " + count + " rules");
                    }
                }
            }
        }catch (Exception e) {
            Log.e("VPN Service", "RuleResolver Load Error: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try{
            Log.v("VPN Service", "RuleResolver Run "+shutdown+" status "+status);
            while(!shutdown){
                if (status == STATUS_PENDING_LOAD) {
                    Log.v("VPN Service", "RuleResolver Run Pending Load");
                    load();
                }
                Thread.sleep(1000);
            }
        }catch (Exception e) {
            Log.e("VPN Service", "RuleResolver Error: " + e.getMessage());
        }
    }
}
