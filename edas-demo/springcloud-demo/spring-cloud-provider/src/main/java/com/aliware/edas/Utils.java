package com.aliware.edas;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Utils {
    private final static String SERVICE_NAME_KEY = "SERVICE_NAME";
    public static String localIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return e.getMessage();
        }
    }

    public static String currentServiceName() {
        return System.getenv(SERVICE_NAME_KEY);
    }
}
