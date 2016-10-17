package com.adobe.connector.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

public class PerformanceLogger {

    /**
     * Slf4J Logger
     */
    private static final Logger log = LoggerFactory.getLogger(PerformanceLogger.class);

    private static final ThreadLocal<Stack<Monitor>> monitors = new ThreadLocal();

    private static final PerformanceLogger instance = new PerformanceLogger();

    private PerformanceLogger() {
    }

    public static PerformanceLogger getLogger() {
        return instance;
    }

    public void start() {
        monitors.get().add(new Monitor());
    }

    public long stopWithoutLogging() {
        return monitors.get().pop().timeSpent();
    }

    public long stop(int logId, String message, String... additionalParameters) {
        long timeSpent = monitors.get().pop().timeSpent();
        if (log.isInfoEnabled()) {
            StringBuilder additional = new StringBuilder();
            for (String parameter : additionalParameters) {
                additional.append(parameter).append("|");
            }
            log.info(logId + "|" + message + "|" + timeSpent + "|" + additional.toString());
        }

        return timeSpent;
    }

    public static void enable() {
        monitors.set(new Stack<Monitor>());
    }

    public static void clean() {
        monitors.remove();
    }

}

class Monitor {

    private long start = 0L;

    Monitor() {
        this.start = System.currentTimeMillis();
    }

    long timeSpent() {
        return System.currentTimeMillis() - start;
    }
}
