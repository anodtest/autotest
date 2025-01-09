package com.anodtester.Config;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {
    private static final Logger logger = LogManager.getLogger(LoggerUtil.class);

    // Method to log message directly to Allure report as a step
    public static void log(String stepMessage) {
        Allure.step(stepMessage); // Add the step directly to Allure report
        logger.info(stepMessage); // Optional: log to file as well
    }
}
