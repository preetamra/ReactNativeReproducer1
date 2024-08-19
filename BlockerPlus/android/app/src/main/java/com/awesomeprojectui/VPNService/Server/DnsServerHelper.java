package com.awesomeprojectui.VPNService.Server;

import com.blockerplus.blockerplus.MainApplication;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;

public class DnsServerHelper {
    public static HashMap<String, List<InetAddress>> domainCache = new HashMap<>();
    public static AbstractDnsServer getServerById(String id) {
        for (DnsServer server : MainApplication.DNS_SERVERS) {
            if (server.getId().equals(id)) {
                return server;
            }
        }

        return MainApplication.DNS_SERVERS.get(0);
    }

    public static void buildCache() {
        domainCache = new HashMap<>();
    }

}
