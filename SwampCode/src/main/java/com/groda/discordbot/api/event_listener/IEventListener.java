package com.groda.discordbot.api.event_listener;

import net.dv8tion.jda.api.events.Event;

public interface IEventListener {

    /**
     * The handler for events
     * @param event the event to handle
     * @param <T> the type of the event
     */
    public <T extends Event> void handleEvent(T event);

}
