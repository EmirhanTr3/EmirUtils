package xyz.emirdev.emirutils.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.annotation.Suggest;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import xyz.emirdev.emirutils.EmirUtils;
import xyz.emirdev.emirutils.Utils;
import xyz.emirdev.emirutils.punishutils.HistoryEntry;
import xyz.emirdev.emirutils.punishutils.PunishType;
import xyz.emirdev.emirutils.punishutils.PunishUtils;

import java.util.Objects;

public class UnmuteCommand {

    @Command("unmute")
    @CommandPermission("emirutils.unmute")
    public void unban(CommandSender sender, OfflinePlayer target, @Suggest("-s") @Optional String extra) {
        if (!EmirUtils.get().getData().isMuted(target.getUniqueId())) {
            Utils.sendError(sender, "%s is not muted.", target.getName());
            return;
        }

        boolean silent = Objects.equals(extra, "-s");

        Utils.punishBroadcast(
                "emirutils.unban",
                silent,
                "<#00bbaa>%s <#00eeaa>has been unmuted by <#00bbaa>%s",
                target.getName(),
                sender.getName()
        );

        EmirUtils.get().getData().deleteMute(target.getUniqueId());

        EmirUtils.get().getData().addHistory(
                new HistoryEntry(
                        PunishUtils.generateId(),
                        PunishType.UNMUTE,
                        target.getUniqueId(),
                        sender instanceof Player player ? player.getUniqueId() : null,
                        null,
                        null,
                        System.currentTimeMillis()
                )
        );
    }
}
