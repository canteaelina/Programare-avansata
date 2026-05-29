package org.example.com;

import java.text.DateFormatSymbols;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;

public class Info {
    public static void execute(Locale targetLocale, ResourceBundle messages) {
        Locale enLocale = Locale.ENGLISH;

        String infoMsg = MessageFormat.format(messages.getString("info"), targetLocale.toString());
        System.out.println(infoMsg);

        // Țara (Nume engleză + Nume nativ)
        System.out.println("\tCountry: " + targetLocale.getDisplayCountry(enLocale) +
                " (" + targetLocale.getDisplayCountry(targetLocale) + ")");

        // Limba
        System.out.println("\tLanguage: " + targetLocale.getDisplayLanguage(enLocale) +
                " (" + targetLocale.getDisplayLanguage(targetLocale) + ")");

        // Moneda
        try {
            Currency currency = Currency.getInstance(targetLocale);
            System.out.println("\tCurrency: " + currency.getCurrencyCode() +
                    " (" + currency.getDisplayName(enLocale) + ")");
        } catch (IllegalArgumentException e) {
            // Se aruncă dacă locale-ul nu are o țară specificată (ex: doar "ro" în loc de "ro-RO")
            System.out.println("\tCurrency: N/A");
        }

        DateFormatSymbols dfs = new DateFormatSymbols(targetLocale);
        String[] weekdays = Arrays.stream(dfs.getWeekdays())
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
        System.out.println("\tWeek Days: " + String.join(", ", weekdays));

        String[] months = Arrays.stream(dfs.getMonths())
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
        System.out.println("\tMonths: " + String.join(", ", months));

        DateTimeFormatter dtfEn = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(enLocale);
        DateTimeFormatter dtfLoc = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(targetLocale);
        System.out.println("\tToday: " + LocalDate.now().format(dtfEn) +
                " (" + LocalDate.now().format(dtfLoc) + ")");
    }
}
