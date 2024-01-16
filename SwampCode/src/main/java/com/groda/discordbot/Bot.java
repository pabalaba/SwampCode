package com.groda.discordbot;

import com.google.gson.JsonObject;
import com.groda.discordbot.config.Config;
import com.groda.discordbot.event_listener.MessageReceivedListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Bot {
    private static final String CONFIG_PATH = Bot.class.getClassLoader().getResource("config.json").getPath();
    public static void main(String[] args) {
        JsonObject file = new Config(CONFIG_PATH).loadConfig();
        JDABuilder.createDefault(file.get("token").getAsString())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new MessageReceivedListener())
                .build();
    }
}