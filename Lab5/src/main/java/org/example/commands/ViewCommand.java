package org.example.commands;

import org.example.exceptions.CommandException;
import org.example.model.Item;

import java.awt.*;
import java.io.File;
import java.net.URI;

public class ViewCommand implements Command{
    private Item item;

    public ViewCommand(Item item) {
        this.item = item;
    }

    @Override
    public void execute() throws CommandException
    {
        if (item == null || item.getLocation() == null) {
            throw new CommandException("Locatia sau resursa este null");
        }

        try {
            if (!Desktop.isDesktopSupported()) {
                throw new CommandException("Clasa Desktop nu este suportata");
            }

            Desktop desktop = Desktop.getDesktop();
            String location = item.getLocation();

            //diferentiam intre URL si fisier local
            if (location.startsWith("http://") || location.startsWith("https://"))
            {
                desktop.browse(new URI(location));
            } else
            {
                File file = new File(location);
                if (!file.exists())
                {
                    throw new CommandException("Fișierul local nu a fost gasit: " + location);
                }
                desktop.open(file);
            }
        } catch (Exception e)
        {
            // Prindem erorile si aruncam exceptia custom
            throw new CommandException("Nu am putut deschide resursa: " + item.getTitle(), e);
        }
    }
}
