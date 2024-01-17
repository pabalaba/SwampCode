package com.groda.discordbot;

import com.google.gson.JsonObject;
import com.groda.discordbot.api.event_listener.IEventListener;
import com.groda.discordbot.config.Config;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Bot {
    private static final String CONFIG_PATH = Bot.class.getClassLoader().getResource("config.json").getPath();

    public static void main(String[] args) {
        JsonObject file = new Config(CONFIG_PATH).loadConfig();
        JDABuilder.createDefault(file.get("token").getAsString())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(loadEventListeners(file.get("event_path").getAsString()).toArray())
                .build();
    }


    private static List<IEventListener> loadEventListeners(String eventPath) {
        Reflections reflections = new Reflections(eventPath);
        Set<Class<? extends IEventListener>> classes = reflections.getSubTypesOf(IEventListener.class);

        List<IEventListener> listeners = new ArrayList<>();

        classes.forEach(aClass -> {
            try {
                listeners.add(aClass.getDeclaredConstructor().newInstance());
            } catch (InstantiationException |
                     IllegalAccessException |
                     InvocationTargetException |
                     NoSuchMethodException e) {
                e.printStackTrace();
            }
        });

        return listeners;
    }
}