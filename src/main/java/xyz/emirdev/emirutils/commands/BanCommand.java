package xyz.emirdev.emirutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Default;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import xyz.emirdev.emirutils.EmirUtils;
import xyz.emirdev.emirutils.punishutils.PunishReason;
import xyz.emirdev.emirutils.Utils;

import java.util.Date;

public class BanCommand {

    @Command("ban")
    @CommandPermission("emirutils.ban")
    public void ban(CommandSender sender, OfflinePlayer target, @Default("") PunishReason reason) {
        EmirUtils.getLuckPerms().getUserManager().loadUser(target.getUniqueId()).thenAcceptAsync(user -> {
            if (user.getCachedData().getPermissionData().checkPermission("emirutils.ban").asBoolean()) {
                Utils.sendError(sender, "You cannot ban %s!", target.getName());
                return;
            }
            Utils.punishBroadcast("emirutils.ban", reason.isSilent(), "<#00bbaa>%s <#00eeaa>has been banned by <#00bbaa>%s <#00eeaa>for <#00bbaa>%s", target.getName(), sender.getName(), reason.getReason());

            Bukkit.getScheduler().runTask(EmirUtils.get(), () -> {
                target.ban(Utils.convertComponentToLegacyString(Utils.format("""
                        <red>You have been banned from %s
                        <red>Moderator: <gray>%s
                        <red>Length: <gray>Forever
                        <red>Reason: <gray>%s
                        """,
                        EmirUtils.get().getPluginConfig().getServerName(), sender.getName(), reason.getReason()
                )), (Date) null, null);
            });
        });
    }
}
