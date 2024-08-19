package com.awesomeprojectui.VPNService.Utils;

import android.util.Log;

import com.blockerplus.blockerplus.MainApplication;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Configurations {
    private static File file;
    private ArrayList<Rule> hostsRules;
    private ArrayList<Rule> dnsmasqRules;

    public static Configurations load(File file) {
        Log.v("VPN Service","Load configurations from " + file.getAbsolutePath());
        Configurations.file = file;
        Configurations config = null;
        if (file.exists()) {
            Log.v("VPN Service","Load configurations from " + file.getAbsolutePath());
            try {
                Log.v("VPN Service","Load configurations from " + file.getAbsolutePath());
                config = MainApplication.parseJson(Configurations.class, new JsonReader(new FileReader(file)));
                Log.v("VPN Service","Load configuration successfully from " + file);
            } catch (Exception e) {
                Log.v("VPN Service","Load configuration failed from " + file);
            }
        }

        if (config == null) {
            Log.v("VPN Service","Load configuration failed. Generating default configurations.");
            config = new Configurations();
        }

        return config;
    }

    public Configurations() {
        Log.v("VPN Service","Configurations");
        //TODO: Initial config. Eg. Build-in rules
    }

    public ArrayList<Rule> getUsingRules() {
        if(hostsRules != null && hostsRules.size() > 0) {
           for (Rule rule : hostsRules) {
               if (rule.isUsing()) {
                   return hostsRules;
               }
           }
        }

        if(dnsmasqRules != null && dnsmasqRules.size() > 0) {
            for (Rule rule : dnsmasqRules) {
                if (rule.isUsing()) {
                    return dnsmasqRules;
                }
            }
        }
        return hostsRules;
    }
}

