package org.example.commands;

import org.example.exceptions.CommandException;
import org.example.repository.Catalog;

public class ListCommand implements Command{
    private Catalog catalog;

    public ListCommand(Catalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public void execute() throws CommandException
    {
        System.out.println("Catalog Items");
        catalog.getItems().forEach(item ->
                System.out.println("Id: " + item.getId() + "\nTitle: " + item.getTitle() + "\nLocation: " + item.getLocation()));

        System.out.println("");
    }
}
