package com.groda.discordbot;

import com.google.gson.JsonObject;
import com.groda.discordbot.api.custom_command.ICommand;
import com.groda.discordbot.api.event_listener.IEventListener;
import com.groda.discordbot.config.Config;
import com.groda.discordbot.custom_command.CommandManager;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.reflections.Reflections;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Bot {
    private static final String CONFIG_PATH = Bot.class.getClassLoader().getResource("config.json").getPath();
    public static final JsonObject CONFIG_FILE = new Config(CONFIG_PATH).loadConfig();
    public static final String PREFIX = CONFIG_FILE.get("prefix").getAsString();

    public static void main(String[] args) {
        JDABuilder.createDefault(CONFIG_FILE.get("token").getAsString())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(loadEventListeners(CONFIG_FILE.get("event_path").getAsString()).toArray())
                .build();

        CommandManager.getCommandManager().addCommands(loadCustomCommands(CONFIG_FILE.get("command_path").getAsString()));

    }

    /**
     * Scans and instantiates all classes within a specified package that implement the {@link IEventListener} interface.
     * <p>
     * This method uses the Reflections library to dynamically find all subclasses of {@link IEventListener} within
     * the specified package path. It then creates an instance of each identified class using their no-argument
     * constructor and collects them into a list.
     * </p>
     *
     * @param eventPath The package path where the method searches for classes implementing {@link IEventListener}.
     * @return A List of {@link IEventListener} instances, one for each class found in the specified package
     *         that implements the {@link IEventListener} interface. If no such classes are found, or if an error occurs
     *         during instantiation, this list may be empty.
     * @see Reflections#getSubTypesOf(Class) Method used from Reflections library to find implementing classes.
     */
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
                LoggerFactory.getLogger(Bot.class).trace(e.getMessage());
            }
        });

        return listeners;
    }

    /**
     * Scans and instantiates all classes within a specified package that implement the {@link ICommand} interface.
     * <p>
     * This method uses the Reflections library to dynamically find all subclasses of {@link ICommand} within
     * the specified package path. It then creates an instance of each identified class using their no-argument
     * constructor and collects them into a list.
     * </p>
     *
     * @param commandsPath The package path where the method searches for classes implementing {@link ICommand}.
     * @return A List of {@link ICommand} instances, one for each class found in the specified package
     *         that implements the {@link ICommand} interface. If no such classes are found, or if an error occurs
     *         during instantiation, this list may be empty.
     * @see Reflections#getSubTypesOf(Class) Method used from Reflections library to find implementing classes.
     */
    private static List<ICommand> loadCustomCommands(String commandsPath) {
        Reflections reflections = new Reflections(commandsPath);
        Set<Class<? extends ICommand>> classes = reflections.getSubTypesOf(ICommand.class);

        List<ICommand> commands = new ArrayList<>();
        classes.forEach(aClass -> {
            try {
                commands.add(aClass.getDeclaredConstructor().newInstance());
            } catch (InstantiationException |
                     IllegalAccessException |
                     InvocationTargetException |
                     NoSuchMethodException e) {
                LoggerFactory.getLogger(Bot.class).trace(e.getMessage());
            }
        });

        return commands;
    }
}