package xyz.emirdev.emirutils;

import org.simpleyaml.configuration.file.YamlFile;

import java.io.File;
import java.io.IOException;

public class ConfigHandler {
    private static final File DATA_FILE = new File(EmirUtils.get().getDataFolder(), "config.yml");

    private YamlFile yamlFile;

    public ConfigHandler() {
        loadDataFile();

        this.yamlFile.setComment("servername", "The server name to use in punishment messages.");
        this.yamlFile.addDefault("servername", "Server Name");

        saveFile();
    }

    public void loadDataFile() {
        this.yamlFile = new YamlFile(DATA_FILE);
        try {
            this.yamlFile.createOrLoadWithComments();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveFile() {
        try {
            this.yamlFile.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getServerName() {
        return this.yamlFile.getString("servername");
    }
}