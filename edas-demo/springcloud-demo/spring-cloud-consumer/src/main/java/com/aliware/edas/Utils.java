package com.aliware.edas;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Utils {
    public static String localIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return e.getMessage();
        }
    }
}
