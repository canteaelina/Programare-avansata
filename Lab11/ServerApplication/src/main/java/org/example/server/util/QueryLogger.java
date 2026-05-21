package org.example.server.util;

import java.io.IOException;
import java.util.logging.*;

public class QueryLogger {
    private static final Logger logger = Logger.getLogger(QueryLogger.class.getName());

    static {
        try {
            // Loghează în consolă
            ConsoleHandler consoleHandler = new ConsoleHandler();
            // Loghează și în fișierul "jpql_execution.log"
            FileHandler fileHandler = new FileHandler("jpql_execution.log", true);
            fileHandler.setFormatter(new SimpleFormatter());

            logger.addHandler(consoleHandler);
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false); // Elimină duplicatele de afișare
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logExecutionTime(String queryName, long startTime, long endTime) {
        long duration = endTime - startTime;
        logger.info("[EXECUTION TIME] JPQL Query '" + queryName + "' a rulat in " + duration + " ms.");
    }

    public static void logException(String queryName, Exception e) {
        logger.severe("[EXCEPTION] Eroare in timpul rularii '" + queryName + "': " + e.getMessage());
    }
}