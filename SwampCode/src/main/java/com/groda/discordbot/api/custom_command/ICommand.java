package com.groda.discordbot.api.custom_command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface ICommand {

    /**
     * Retrieves the command
     * @return the command
     */
    public String getCommand();

    /**
     * The command execution
     */
    public void run(MessageReceivedEvent event);
}
