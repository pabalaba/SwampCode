package com.groda.discordbot;

import com.google.gson.JsonObject;
import com.groda.discordbot.config.Config;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import okio.Path;

public class Bot {
    public static void main(String[] args) {
        JsonObject file = new Config(Path.get("../../../config.json").toString()).loadConfig();
        JDABuilder.createDefault(file.get("token").getAsString())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new MyListener())
                .build();
    }
}