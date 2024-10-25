package xyz.emirdev.emirutils.punishutils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HistoryEntry {
    private final String id;
    private final PunishType type;
    private final OfflinePlayer player;
    private final OfflinePlayer playerModerator;
    private final ConsoleCommandSender consoleModerator;
    private final String reason;
    private final Duration duration;
    private final Long issuedAt;

    public HistoryEntry(String id, PunishType type, UUID player, UUID mod, String reason, Duration duration, Long issuedAt) {
        this.id = id;
        this.type = type;
        this.player = Bukkit.getOfflinePlayer(player);
        this.playerModerator = mod != null ? Bukkit.getOfflinePlayer(mod) : null;
        this.consoleModerator = mod == null ? Bukkit.getConsoleSender() : null;
        this.reason = reason;
        this.duration = duration;
        this.issuedAt = issuedAt;
    }

    public String getId() {
        return id;
    }

    public PunishType getType() {
        return type;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public OfflinePlayer getPlayerModerator() {
        return playerModerator;
    }

    public ConsoleCommandSender getConsoleModerator() {
        return consoleModerator;
    }

    public String getReason() {
        return reason;
    }

    public Duration getDuration() {
        return duration;
    }

    public Long getIssuedAt() {
        return issuedAt;
    }

    public Map<String, Map<String, Object>> toMap() {
        Map<String, Map<String, Object>> parentMap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        map.put("type", type.toString());
        map.put("player", player.getUniqueId().toString());
        map.put("mod", playerModerator != null ? playerModerator.getUniqueId().toString() : "CONSOLE");
        map.put("reason", reason);
        map.put("duration", duration != null ? duration.toString() : null);
        map.put("issuedat", issuedAt);
        parentMap.put(id, map);
        return parentMap;
    }

    public static HistoryEntry fromMap(Map<String, Map<String, Object>> parentMap) {
        String id = parentMap.keySet().stream().findFirst().get();
        Map<String, Object> map = parentMap.get(id);
        return new HistoryEntry(
                id,
                PunishType.valueOf((String) map.get("type")),
                UUID.fromString((String) map.get("player")),
                !map.get("mod").equals("CONSOLE") ? UUID.fromString((String) map.get("mod")) : null,
                (String) map.get("reason"),
                map.get("duration") != null ? Duration.parse((String) map.get("duration")) : null,
                (Long) map.get("issuedat")
        );
    }
}
