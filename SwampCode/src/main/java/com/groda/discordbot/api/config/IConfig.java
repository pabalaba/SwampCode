package com.groda.discordbot.api.config;

import com.google.gson.JsonObject;

public interface IConfig {

    /**
     * This is used to load the information inside a json file.
     * @return the json object inside the json file.
     */
    JsonObject loadConfig();
}
