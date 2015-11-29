package net.foxdenstudio.webserviceapi.novacula.utils;

import java.io.File;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge->FDS-WSAPI
 */
public class NovaInfo {

    public static String getClientInfo() {
        String processors = "[+] Available processors: " + Runtime.getRuntime().availableProcessors();
        long totalMemory = Runtime.getRuntime().maxMemory();
        String maxMemory = "[+] Maximum memory: " + (totalMemory == Long.MAX_VALUE ? "no limit" : totalMemory);
        String freeMemory = "[+] Free memory: " + Runtime.getRuntime().freeMemory();
        String availibleMemory = "[+] Total memory available to JVM: " + Runtime.getRuntime().totalMemory();

        return NovaLogger.ANSI_GREEN + "The Server has...\n" + NovaLogger.ANSI_CYAN + "\t" + processors + " cores.\n\t" + maxMemory + " Bytes.\n\t" + freeMemory + " Bytes.\n\t" + availibleMemory + " Bytes";
    }

    public static String getFileInfo() {
        String info = NovaLogger.ANSI_GREEN + "The Server's File Info is...\n" + NovaLogger.ANSI_CYAN;
        File[] roots = File.listRoots();

        for (File root : roots) {
            info += "\t[+] File system root: " + root.getAbsolutePath() + "\n";
            info += "\t[+] Total space: " + root.getTotalSpace() + " Bytes.\n";
            info += "\t[+] Free space: " + root.getFreeSpace() + " Bytes.\n";
            info += "\t[+] Usable space: " + root.getUsableSpace() + " Bytes";
        }
        return info;
    }
}