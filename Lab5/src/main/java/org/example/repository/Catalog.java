package org.example.repository;

import org.example.exceptions.CommandException;
import org.example.model.Item;

import java.awt.*;
import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Catalog implements Serializable {
    private String name;
    private List<Item> items = new ArrayList<>();

    public void add(Item item) {
        items.add(item);
    }

    public Item findById(String id)
    {
        return items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Item> getItems() {
        return items;
    }

    //De la compulsory
    /*
    public void openResource(Item item) throws CommandException {
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
    */


}
