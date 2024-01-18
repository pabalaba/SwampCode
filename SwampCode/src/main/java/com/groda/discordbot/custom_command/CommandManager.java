package com.groda.discordbot.custom_command;

import com.groda.discordbot.api.custom_command.ICommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {

    private static CommandManager commandManager = new CommandManager();

    private Map<String, ICommand> commandMap = new HashMap<>();

    /**
     * Return the instance of the singleton
     * @return the instance
     */
    public static CommandManager getCommandManager() {
        return commandManager;
    }

    /**
     * Checks whether the message contains a command.
     * If it does it runs the body of the command
     *
     * @param message the message
     * @param event the event
     */
    public void runCommand(String message, MessageReceivedEvent event){
        String commandText = message.split(" ")[0];

        ICommand command = commandMap.get(commandText.toLowerCase());

        if(command == null)
            return;

        command.run(event);
    }

    /**
     * Adds a single command to the map of commands
     * @param command the command to add
     */
    public void addCommand(ICommand command){
        commandMap.put(command.getCommand(),command);
    }

    /**
     * Adds a list of commands to the map of commands
     * @param commands the list of commands
     */
    public void addCommands(List<ICommand> commands){
        commands.forEach(this::addCommand);
    }

}
