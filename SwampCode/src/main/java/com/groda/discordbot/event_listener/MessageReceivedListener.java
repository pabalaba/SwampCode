package com.groda.discordbot.event_listener;

import com.groda.discordbot.api.event_listener.IEventListener;
import com.groda.discordbot.custom_command.CommandManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageReceivedListener extends ListenerAdapter implements IEventListener {

    /**
     * Handles the message received event from the JDA library
     * @param event the event that will be listened at
     */
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        handleEvent(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Event> void handleEvent(T event) {
        if(!(event instanceof MessageReceivedEvent messageReceivedEvent))
            return;

        Message message = messageReceivedEvent.getMessage();

        CommandManager.getCommandManager().runCommand(message.getContentRaw(), messageReceivedEvent);
    }
}
