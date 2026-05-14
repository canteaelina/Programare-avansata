package org.example.commands;

import org.example.exceptions.CommandException;
import org.example.repository.Catalog;

import java.io.*;

public class SaveCommand implements Command{
    private Catalog catalog;
    private String filePath;

    public SaveCommand(Catalog catalog, String filePath) {
        this.catalog = catalog;
        this.filePath = filePath;
    }

    @Override
    public void execute() throws CommandException
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(catalog);
            System.out.println("Catalog salvat cu succes in: " + filePath);
        } catch (IOException e) {
            throw new CommandException("Eroare la salvarea catalogului", e);
        }
    }
}
