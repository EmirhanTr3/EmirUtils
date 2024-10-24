package xyz.emirdev.emirutils.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import xyz.emirdev.emirutils.EmirUtils;
import xyz.emirdev.emirutils.Utils;
import xyz.emirdev.emirutils.handlers.DataHandler;
import xyz.emirdev.emirutils.punishutils.Mute;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class PlayerChatEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        DataHandler data = EmirUtils.get().getData();

        if (!data.isMuted(uuid)) return;
        Mute mute = data.getMute(uuid);
        long expiresAt = mute.getMutedAt() + mute.getDuration().toMillis();

        if (expiresAt < System.currentTimeMillis()) {
            data.deleteMute(uuid);
            return;
        };

        event.setCancelled(true);

        Utils.sendMessage(player,
                """
                <red>You are currently muted.
                <red>Moderator: <gray>%s
                <red>Reason: <gray>%s
                <red>Expires in: <gray>%s (%s)""",

                mute.getPlayerModerator() != null ? mute.getPlayerModerator().getName() : "CONSOLE",
                mute.getReason(),
                Utils.parseDurationToString(Duration.ofMillis(expiresAt - System.currentTimeMillis())),
                Utils.formatDate(Date.from(Instant.ofEpochMilli(expiresAt)))
        );
    }
}
