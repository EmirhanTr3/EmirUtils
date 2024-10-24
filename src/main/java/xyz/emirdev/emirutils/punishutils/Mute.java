package xyz.emirdev.emirutils.punishutils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.UUID;

public class Mute {
    private final OfflinePlayer player;
    private final OfflinePlayer playerModerator;
    private final ConsoleCommandSender consoleModerator;
    private final Duration duration;
    private final String reason;
    private final Long mutedAt;

    public Mute(UUID player, UUID mod, Duration duration, String reason, Long mutedAt) {
        this.player = Bukkit.getOfflinePlayer(player);
        this.playerModerator = Bukkit.getOfflinePlayer(mod);
        this.consoleModerator = null;
        this.duration = duration;
        this.reason = reason;
        this.mutedAt = mutedAt;
    }

    public Mute(UUID player, Duration duration, String reason, Long mutedAt) {
        this.player = Bukkit.getOfflinePlayer(player);
        this.playerModerator = null;
        this.consoleModerator = Bukkit.getConsoleSender();
        this.duration = duration;
        this.reason = reason;
        this.mutedAt = mutedAt;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public @Nullable OfflinePlayer getPlayerModerator() {
        return playerModerator;
    }

    public @Nullable ConsoleCommandSender getConsoleModerator() {
        return consoleModerator;
    }

    public Duration getDuration() {
        return duration;
    }

    public String getReason() {
        return reason;
    }

    public Long getMutedAt() {
        return mutedAt;
    }
}
