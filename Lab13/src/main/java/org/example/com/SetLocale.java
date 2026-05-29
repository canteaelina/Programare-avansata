package org.example.com;

import java.util.Locale;

public class SetLocale {
    public static void execute(String languageTag) {
        Locale newLocale = Locale.forLanguageTag(languageTag);
        Locale.setDefault(newLocale);
    }
}