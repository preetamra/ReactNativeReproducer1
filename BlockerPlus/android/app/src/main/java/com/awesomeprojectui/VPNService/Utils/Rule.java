package com.awesomeprojectui.VPNService.Utils;

public class Rule {
    public static final int TYPE_HOSTS = 0;
    public static final int TYPE_DNAMASQ = 1;
    private boolean using;
    private String fileName;
    private int type;

    public String getFileName() {
        return fileName;
    }
    public boolean isUsing() {
        return using;
    }
    public int getType() {
        return type;
    }
}
