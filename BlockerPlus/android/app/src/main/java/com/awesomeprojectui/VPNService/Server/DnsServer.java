package com.awesomeprojectui.VPNService.Server;

public class DnsServer extends AbstractDnsServer {

    private String id;

    public DnsServer(String address, int port) {
        super(address, port);
    }

    public String getId() {
        return id;
    }
}
