package xyz.emirdev.emirutils.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Default;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import xyz.emirdev.emirutils.EmirUtils;
import xyz.emirdev.emirutils.Utils;
import xyz.emirdev.emirutils.punishutils.PunishDuration;
import xyz.emirdev.emirutils.punishutils.PunishReason;

public class MuteCommand {

    @Command("mute")
    @CommandPermission("emirutils.mute")
    public void mute(CommandSender sender, OfflinePlayer target, PunishDuration duration, @Default("") PunishReason reason) {
        EmirUtils.getLuckPerms().getUserManager().loadUser(target.getUniqueId()).thenAcceptAsync(user -> {
            if (user.getCachedData().getPermissionData().checkPermission("emirutils.mute").asBoolean()) {
                Utils.sendError(sender, "You cannot mute %s!", target.getName());
                return;
            }

            Utils.punishBroadcast(
                    "emirutils.mute",
                    reason.isSilent(),
                    "<#00bbaa>%s <#00eeaa>has been muted by <#00bbaa>%s <#00eeaa>for <#00bbaa>%s <#00eeaa>for reason <#00bbaa>%s",
                    target.getName(),
                    sender.getName(),
                    duration.getString(),
                    reason.getReason()
            );

            if (sender instanceof Player player) {
                EmirUtils.get().getData().setMute(
                        target.getUniqueId(),
                        player.getUniqueId(),
                        duration.getDuration(),
                        reason.getReason()
                );
            } else {
                EmirUtils.get().getData().setMute(
                        target.getUniqueId(),
                        duration.getDuration(),
                        reason.getReason()
                );
            }
        });
    }
}
