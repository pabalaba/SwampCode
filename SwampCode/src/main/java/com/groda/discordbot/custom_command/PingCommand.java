package com.groda.discordbot.custom_command;

import com.groda.discordbot.Bot;
import com.groda.discordbot.api.custom_command.ICommand;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand implements ICommand {
    private final String COMMAND = String.format("%s%s",
            Bot.PREFIX,
            Bot.CONFIG_FILE.get("commands").getAsJsonObject().get("ping").getAsString());

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCommand() {
        return this.COMMAND;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(MessageReceivedEvent event) {

        if (event.getAuthor().isBot())
            return;

        MessageChannel channel = event.getChannel();

        channel.sendMessage("pong!").queue();
    }


}
