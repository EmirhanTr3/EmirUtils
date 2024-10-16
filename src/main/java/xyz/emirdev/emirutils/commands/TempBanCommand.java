package xyz.emirdev.emirutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.annotation.Suggest;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import xyz.emirdev.emirutils.EmirUtils;
import xyz.emirdev.emirutils.PunishReason;
import xyz.emirdev.emirutils.Utils;

import java.time.Instant;
import java.util.Date;

public class TempBanCommand {

    @Command("tempban")
    @CommandPermission("emirutils.tempban")
    public void ban(CommandSender sender, OfflinePlayer target, String duration, @Suggest({"-s"}) @Optional String reason) {
        Utils.broadcast("sender: %s", sender.getName());
        Utils.broadcast("target: %s", target.getName());
        Utils.broadcast("duration: %s", Utils.parseDuration(duration).toString());
        Utils.broadcast("reason: %s", reason);
//        EmirUtils.getLuckPerms().getUserManager().loadUser(target.getUniqueId()).thenAcceptAsync(user -> {
//            if (user.getCachedData().getPermissionData().checkPermission("emirutils.ban").asBoolean()) {
//                Utils.sendError(sender, "You cannot ban %s!", target.getName());
//                return;
//            }
//
//            PunishReason punishReason = new PunishReason(reason, "You have been banned!");
//
//            Utils.punishBroadcast("emirutils.ban", punishReason.isSilent(), "<#00bbaa>%s <#00eeaa>has been banned by <#00bbaa>%s <#00eeaa>for <#00bbaa>%s", target.getName(), sender.getName(), punishReason.getReason());
//
//            Bukkit.getScheduler().runTask(EmirUtils.get(), () -> {
//                target.ban(Utils.convertComponentToLegacyString(Utils.format("""
//                        <red>You have been banned from %s
//                        <red>Moderator: <gray>%s
//                        <red>Length: <gray>Forever
//                        <red>Reason: <gray>%s
//                        """,
//                        EmirUtils.get().getPluginConfig().getServerName(), sender.getName(), punishReason.getReason()
//                )), (Date) null, null);
//            });
//        });
    }
}