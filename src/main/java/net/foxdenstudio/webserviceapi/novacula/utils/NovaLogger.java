package net.foxdenstudio.webserviceapi.novacula.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge | FDSFDS-WSAPI
 */
@SuppressWarnings("unused")
public class NovaLogger {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private ArrayList<String> log;
    private Calendar time;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private String prefix, suffix, cleanupCode = ANSI_RESET;

    private final String ERROR = ANSI_RED + "NOVA ERROR: " + ANSI_RESET;
    private final String ERROR_SUFFIX = "!!";
    private final String CLEAR_LOG = ANSI_CYAN + "Log has been cleared!" + ANSI_RESET;

    public NovaLogger() {
        this(new ArrayList<>(), ANSI_PURPLE + "[Nova Web Server] " + ANSI_RESET, "");
    }

    public NovaLogger(ArrayList<String> pastLog) {

        this(pastLog, ANSI_PURPLE + "[Nova Web Server] " + ANSI_RESET, "");
    }

    public NovaLogger(String prefix) {
        this(new ArrayList<>(), prefix, "");
    }

    public NovaLogger(String prefix, String suffix) {
        this(new ArrayList<>(), prefix, suffix);
    }

    public NovaLogger(ArrayList<String> pastLog, String prefix) {
        this(pastLog, prefix, "");
    }

    public NovaLogger(ArrayList<String> pastLog, String prefix, String suffix) {
        this.time = Calendar.getInstance();
        this.log = pastLog;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void log(String message) {
        if (message != null) {
            String msg = "[" + sdf.format(this.time.getTime()) + "] " + prefix + message + suffix + cleanupCode;
            log.add(msg);
//            System.out.println(msg);
        }
    }

    public void logQuiet(String message) {
        if (message != null) {
            String msg = message + cleanupCode;
            log.add(msg);
//            System.out.println(msg);
        }
    }

    public void logError(String message) {
        if (message != null) {
            String msg = "[" + sdf.format(this.time.getTime()) + "] " + ERROR + message + ERROR_SUFFIX + cleanupCode;
            log.add(msg);
//            System.out.println(ANSI_RED + msg);
        }
    }

    public void logClear() {
        log.add(CLEAR_LOG);
        for (int i = 1; i < 50; i++)
            System.out.print("\n");
//        System.out.println(CLEAR_LOG);
    }

    public void logClear(int lines) {
        log.add(CLEAR_LOG);
        for (int i = 1; i < lines; i++)
            System.out.print("\n");
        System.out.println(CLEAR_LOG);
    }

    public boolean saveLog() {
        //TODO save the log with the NovaFile.Save() method
        try (FileWriter writer = new FileWriter("NovaOutputLog.log")) {
            for (String str : log) {
                writer.write(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public String getLog() {
        return log.toString();
    }

}
