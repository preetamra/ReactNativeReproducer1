package com.awesomeprojectui.VPNService.Utils;

public class Logger {

    private static StringBuffer buffer = null;

    public static void init() {
        if (buffer != null) {
            buffer.setLength(0);
        } else {
            buffer = new StringBuffer();
        }
    }
}
