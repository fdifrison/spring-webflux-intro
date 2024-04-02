package org.fdifrison.webflux101.service;

public class SleepUtil {

    public  static  void sleepSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
