package xyz.emirdev.emirutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Default;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import xyz.emirdev.emirutils.EmirUtils;
import xyz.emirdev.emirutils.punishutils.*;
import xyz.emirdev.emirutils.Utils;

public class TempBanCommand {

    @Command("tempban")
    @CommandPermission("emirutils.tempban")
    public void tempban(CommandSender sender, OfflinePlayer target, PunishDuration duration, @Default("") PunishReason reason) {
        EmirUtils.getLuckPerms().getUserManager().loadUser(target.getUniqueId()).thenAcceptAsync(user -> {
            if (user.getCachedData().getPermissionData().checkPermission("emirutils.tempban").asBoolean()) {
                Utils.sendError(sender, "You cannot tempban %s!", target.getName());
                return;
            }

            Utils.punishBroadcast(
                    "emirutils.tempban",
                    reason.isSilent(),
                    "<#00bbaa>%s <#00eeaa>has been temporarily banned by <#00bbaa>%s <#00eeaa>for <#00bbaa>%s <#00eeaa>for reason <#00bbaa>%s",
                    target.getName(),
                    sender.getName(),
                    duration.getString(),
                    reason.getReason()
            );

            Bukkit.getScheduler().runTask(EmirUtils.get(), () -> {
                target.ban(Utils.convertComponentToLegacyString(Utils.format("""
                        <red>You have been banned from %s
                        <red>Moderator: <gray>%s
                        <red>Length: <gray>%s (%s)
                        <red>Reason: <gray>%s
                        """,
                        EmirUtils.get().getPluginConfig().getServerName(),
                        sender.getName(),
                        duration.getString(),
                        duration.getFormatted(),
                        reason.getReason()
                )), duration.getDuration(), null);
            });

            EmirUtils.get().getData().addHistory(
                    new HistoryEntry(
                            PunishUtils.generateId(),
                            PunishType.TEMPBAN,
                            target.getUniqueId(),
                            sender instanceof Player player ? player.getUniqueId() : null,
                            reason.getReason(),
                            duration.getDuration(),
                            System.currentTimeMillis()
                    )
            );
        });
    }
}
