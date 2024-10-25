package xyz.emirdev.emirutils.handlers;

import org.simpleyaml.configuration.file.YamlFile;
import xyz.emirdev.emirutils.EmirUtils;
import xyz.emirdev.emirutils.punishutils.HistoryEntry;
import xyz.emirdev.emirutils.punishutils.Mute;
import xyz.emirdev.emirutils.punishutils.PunishType;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

public class DataHandler {
    private static final File DATA_FILE = new File(EmirUtils.get().getDataFolder(), "data.yml");

    private YamlFile yamlFile;

    public DataHandler() {
        loadFile();

        this.yamlFile.setHeader("""
                DO NOT MODIFY THIS FILE MANUALLY!!!
                THIS FILE SHOULD ONLY BE WRITTEN BY EMIRUTILS!""");

        saveFile();
    }

    public void loadFile() {
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

    public boolean isMuted(UUID uuid) {
        return this.yamlFile.contains("mutes." + uuid);
    }

    public Mute getMute(UUID uuid) {
        if (!isMuted(uuid)) return null;
        String mod = this.yamlFile.getString("mutes." + uuid + ".mod");

        if (mod.equals("CONSOLE")) {
            return new Mute(
                    uuid,
                    Duration.parse(this.yamlFile.getString("mutes." + uuid + ".duration")),
                    this.yamlFile.getString("mutes." + uuid + ".reason"),
                    this.yamlFile.getLong("mutes." + uuid + ".mutedat")
            );
        } else {
            return new Mute(
                    uuid,
                    UUID.fromString(mod),
                    Duration.parse(this.yamlFile.getString("mutes." + uuid + ".duration")),
                    this.yamlFile.getString("mutes." + uuid + ".reason"),
                    this.yamlFile.getLong("mutes." + uuid + ".mutedat")
            );
        }
    }

    public void setMute(UUID uuid, UUID mod, Duration duration, String reason) {
        this.yamlFile.set("mutes." + uuid + ".mod", mod.toString());
        this.yamlFile.set("mutes." + uuid + ".duration", duration.toString());
        this.yamlFile.set("mutes." + uuid + ".reason", reason);
        this.yamlFile.set("mutes." + uuid + ".mutedat", System.currentTimeMillis());
        saveFile();
        loadFile();
    }

    public void setMute(UUID uuid, Duration duration, String reason) {
        this.yamlFile.set("mutes." + uuid + ".mod", "CONSOLE");
        this.yamlFile.set("mutes." + uuid + ".duration", duration.toString());
        this.yamlFile.set("mutes." + uuid + ".reason", reason);
        this.yamlFile.set("mutes." + uuid + ".mutedat", System.currentTimeMillis());
        saveFile();
        loadFile();
    }

    public void deleteMute(UUID uuid) {
        this.yamlFile.set("mutes." + uuid, null);
        saveFile();
        loadFile();
    }

    public void addHistory(HistoryEntry entry) {
        List<Map<?, ?>> history = this.yamlFile.getMapList("history");
        history.add(entry.toMap());
        this.yamlFile.set("history", history);
        saveFile();
        loadFile();
    }

    public List<HistoryEntry> getHistory(UUID uuid) {
        List<HistoryEntry> historyEntryList = new ArrayList<>();
        List<Map<?, ?>> history = this.yamlFile.getMapList("history");

        for (Map<?, ?> parentMap : history) {
            Map<String, Object> map = (Map<String, Object>) parentMap.get(parentMap.keySet().stream().findFirst().get());
            String player = (String) map.get("player");
            if (player.equals(uuid.toString())) {
                historyEntryList.add(HistoryEntry.fromMap((Map<String, Map<String, Object>>) parentMap));
            }
        }

        return historyEntryList;
    }
}
