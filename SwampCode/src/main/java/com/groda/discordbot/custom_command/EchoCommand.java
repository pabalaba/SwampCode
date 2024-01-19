package com.groda.discordbot.custom_command;

import com.groda.discordbot.Bot;
import com.groda.discordbot.api.custom_command.ICommand;
import com.groda.discordbot.api.custom_command.ITogglable;
import com.groda.discordbot.handler.VoiceHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class EchoCommand implements ICommand, ITogglable {

    private final String COMMAND = String.format("%s%s",
            Bot.PREFIX,
            Bot.CONFIG_FILE.get("commands").getAsJsonObject().get("echo").getAsString());

    private boolean state = false;

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

        switchState();

        MessageChannel messageChannel = event.getChannel();

        Member member = event.getMember();

        GuildVoiceState voiceState = member.getVoiceState();

        AudioChannel channel = voiceState.getChannel();

        if(channel == null){
            messageChannel.sendMessage("You are not connected to an audio channel").queue();
            switchState();
            return;
        }

        if(getState())
            connectTo(channel, messageChannel);
        else
            disconnectFrom(event, messageChannel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getState() {
        return this.state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void switchState() {
        this.state = !this.state;
    }

    /**
     * Handles the connection to a voice channel
     * @param audioChannel the audio channel
     * @param messageChannel the text channel where the command was performed
     */
    private void connectTo(AudioChannel audioChannel, MessageChannel messageChannel){
        if(!(audioChannel instanceof VoiceChannel voiceChannel)){
            messageChannel.sendMessage("The channel is not a voice channel").queue();
            switchState();
            return;
        }

        messageChannel.sendMessage(String.format("I am connecting to %s", voiceChannel.getName())).queue();

        Guild guild = voiceChannel.getGuild();

        AudioManager audioManager = guild.getAudioManager();

        VoiceHandler voiceHandler = new VoiceHandler();

        audioManager.setSendingHandler(voiceHandler);

        audioManager.setReceivingHandler(voiceHandler);

        audioManager.openAudioConnection(voiceChannel);

    }

    /**
     * Handles the disconnection from a voice channel
     * @param event the event that started the disconnection
     * @param messageChannel the message channel where the command was sent
     */
    private void disconnectFrom(MessageReceivedEvent event, MessageChannel messageChannel){

        AudioChannel audioChannel = event.getGuild().getSelfMember().getVoiceState().getChannel();

        if(!(audioChannel instanceof VoiceChannel voiceChannel)){
            messageChannel.sendMessage("The channel is not a voice channel").queue();
            return;
        }

        event.getGuild().getAudioManager().closeAudioConnection();

        messageChannel.sendMessage(String.format("I am disconnecting from %s", voiceChannel.getName())).queue();

    }

}
