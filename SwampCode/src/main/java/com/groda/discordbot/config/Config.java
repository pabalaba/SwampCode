package com.groda.discordbot.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.groda.discordbot.api.config.IConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Config implements IConfig {

    private String file_path;

    /**
     * This is the constructor of the class Config.
     * @param file_path is the parameter containing the path
     *                  to the file requested.
     */
    public Config(String file_path) {
        this.file_path = file_path;
    }

    @Override
    public JsonObject loadConfig() {
        Gson gson = new Gson();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file_path));
            return gson.fromJson(reader, JsonObject.class);
        }catch (IOException e){
            System.out.println("[SwampCode][Core]There has been an error while reading the config file.");
        }
        return null;
    }
}
