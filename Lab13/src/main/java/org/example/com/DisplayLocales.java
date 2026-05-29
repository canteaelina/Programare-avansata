package org.example.com;

import java.util.Locale;
import java.util.ResourceBundle;

public class DisplayLocales {
    public static void execute(ResourceBundle messages) {
        System.out.println(messages.getString("locales"));
        Locale[] availableLocales = Locale.getAvailableLocales();
        for (Locale locale : availableLocales) {
            System.out.println(locale.toString() + " (" + locale.getDisplayName() + ")");
        }
    }
}