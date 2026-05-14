package org.example.commands;

import org.example.exceptions.CommandException;

public interface Command {
    public void execute() throws CommandException;
}
