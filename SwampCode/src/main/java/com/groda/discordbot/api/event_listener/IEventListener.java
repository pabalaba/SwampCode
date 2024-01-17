package com.groda.discordbot.api.event_listener;

import net.dv8tion.jda.api.events.Event;

public interface IEventListener {

    public <T extends Event> void handleEvent(T event);

}
