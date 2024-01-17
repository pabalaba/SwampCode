package com.groda.discordbot.event_listener;

import com.groda.discordbot.api.event_listener.IEventListener;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageUpdatedListener extends ListenerAdapter implements IEventListener {

    /**
     * Handles the message received event from the JDA library
     *
     * @param event the event that will be listened at
     */
    @Override
    public void onMessageUpdate(@NotNull MessageUpdateEvent event) {
        handleEvent(event);
    }

    @Override
    public <T extends Event> void handleEvent(T event) {
        if (!(event instanceof MessageUpdateEvent messageUpdateEvent))
            return;

        if (messageUpdateEvent.getAuthor().isBot()) return;

        MessageChannel channel = messageUpdateEvent.getChannel();
        channel.sendMessage("LEzzo hai modificato mesagio").queue();
    }
}
