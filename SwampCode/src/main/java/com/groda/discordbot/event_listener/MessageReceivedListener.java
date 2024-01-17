package com.groda.discordbot.event_listener;

import com.groda.discordbot.api.event_listener.IEventListener;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
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

    @Override
    public <T extends Event> void handleEvent(T event) {
        if(!(event instanceof MessageReceivedEvent messageReceivedEvent))
            return;

        if (messageReceivedEvent.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = messageReceivedEvent.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals("!ping")) {
            MessageChannel channel = messageReceivedEvent.getChannel();
            channel.sendMessage("Pong!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
    }
}
