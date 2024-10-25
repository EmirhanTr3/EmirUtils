package xyz.emirdev.emirutils.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Default;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import xyz.emirdev.emirutils.EmirUtils;
import xyz.emirdev.emirutils.punishutils.HistoryEntry;
import xyz.emirdev.emirutils.punishutils.PunishReason;
import xyz.emirdev.emirutils.Utils;
import xyz.emirdev.emirutils.punishutils.PunishType;
import xyz.emirdev.emirutils.punishutils.PunishUtils;

public class KickCommand {

    @Command("kick")
    @CommandPermission("emirutils.kick")
    public void kick(CommandSender sender, Player target, @Default("") PunishReason reason) {
        if (target.hasPermission("emirutils.kick")) {
            Utils.sendError(sender, "You cannot kick %s!", target.getName());
            return;
        }

        Utils.punishBroadcast("emirutils.kick", reason.isSilent(), "<#00bbaa>%s <#00eeaa>has been kicked by <#00bbaa>%s <#00eeaa>for <#00bbaa>%s", target.getName(), sender.getName(), reason.getReason());
        target.kick(Utils.format("""
                <red>You have been kicked from %s
                <red>Moderator: <gray>%s
                <red>Reason: <gray>%s
                """,
                EmirUtils.get().getPluginConfig().getServerName(), sender.getName(), reason.getReason()
        ));

        EmirUtils.get().getData().addHistory(
                new HistoryEntry(
                        PunishUtils.generateId(),
                        PunishType.KICK,
                        target.getUniqueId(),
                        sender instanceof Player player ? player.getUniqueId() : null,
                        reason.getReason(),
                        null,
                        System.currentTimeMillis()
                )
        );
    }
}
