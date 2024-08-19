package com.awesomeprojectui.VPNService.Server;

import androidx.annotation.NonNull;

public class AbstractDnsServer implements Cloneable {
    public static final int DNS_SERVER_DEFAULT_PORT = 53;

    protected String address;
    protected int port;
    protected String hostAddress;

    public AbstractDnsServer(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public String getName() {
        return "";
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getRealName() {
        return isHttpsServer() ? address : address + ":" + port;
    }

    public boolean isHttpsServer() {
        return address.contains("/");
    }

    @NonNull
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (Exception ignored) {
        }
        return new AbstractDnsServer("", 0);
    }
}