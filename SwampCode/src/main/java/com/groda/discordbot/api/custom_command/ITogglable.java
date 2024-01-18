package com.groda.discordbot.api.custom_command;

public interface ITogglable {

    /**
     * Retrieves the state of the command
     * @return the state of the command
     */
    public boolean getState();

    /**
     * Switch the state of the command
     */
    public void switchState();
}
