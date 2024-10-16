package xyz.emirdev.emirutils.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.annotation.Suggest;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import xyz.emirdev.emirutils.EmirUtils;
import xyz.emirdev.emirutils.PunishReason;
import xyz.emirdev.emirutils.Utils;

public class KickCommand {

    @Command("kick")
    @CommandPermission("emirutils.kick")
    public void kick(CommandSender sender, Player target, @Suggest({"-s"}) @Optional String reason) {
        if (target.hasPermission("emirutils.kick")) {
            Utils.sendError(sender, "You cannot kick %s!", target.getName());
            return;
        }

        PunishReason punishReason = new PunishReason(reason, "You have been kicked!");
        reason = punishReason.getReason();

        Utils.punishBroadcast("emirutils.kick", punishReason.isSilent(), "<#00bbaa>%s <#00eeaa>has been kicked by <#00bbaa>%s <#00eeaa>for <#00bbaa>%s", target.getName(), sender.getName(), reason);
        target.kick(Utils.format("""
                <red>You have been kicked from %s
                <red>Moderator: <gray>%s
                <red>Reason: <gray>%s
                """,
                EmirUtils.get().getPluginConfig().getServerName(), sender.getName(), reason
        ));
    }
}
