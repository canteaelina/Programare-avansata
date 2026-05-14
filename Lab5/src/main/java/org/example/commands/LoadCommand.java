package org.example.commands;

import org.example.exceptions.CommandException;
import org.example.repository.Catalog;

import java.io.*;

public class LoadCommand implements Command{
    private String filePath;
    private Catalog catalog;

    public LoadCommand(String filePath) {
        this.filePath = filePath;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    @Override
    public void execute() throws CommandException
    {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            this.catalog = (Catalog) ois.readObject();
            System.out.println("Catalog incarcat cu succes din: " + filePath);
        } catch (Exception e) {
            throw new CommandException("Eroare la incarcarea catalogului", e);
        }
    }
}
