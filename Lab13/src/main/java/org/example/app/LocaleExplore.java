package org.example.app;

import org.example.com.DisplayLocales;
import org.example.com.Info;
import org.example.com.SetLocale;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LocaleExplore {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Inițializare ResourceBundle bazat pe locale-ul implicit de la rulare
        ResourceBundle messages = loadMessages();

        while (true) {
            System.out.print(messages.getString("prompt") + " ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            String[] commandParts = input.split("\\s+");
            String command = commandParts[0].toLowerCase();

            switch (command) {
                case "locales":
                    DisplayLocales.execute(messages);
                    break;

                case "set":
                    if (commandParts.length > 1) {
                        SetLocale.execute(commandParts[1]);
                        messages = loadMessages();
                        String response = MessageFormat.format(messages.getString("locale.set"), Locale.getDefault().toString());
                        System.out.println(response);
                    } else {
                        System.out.println(messages.getString("invalid"));
                    }
                    break;

                case "info":
                    Locale targetInfoLocale = Locale.getDefault();
                    if (commandParts.length > 1) {
                        targetInfoLocale = Locale.forLanguageTag(commandParts[1]);
                    }
                    Info.execute(targetInfoLocale, messages);
                    break;

                default:
                    System.out.println(messages.getString("invalid"));
            }
        }

        scanner.close();
    }

    private static ResourceBundle loadMessages() {
        return ResourceBundle.getBundle("res.Messages", Locale.getDefault());
    }
}
