package xyz.emirdev.emirutils.commands;

import io.papermc.paper.ban.BanListType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.annotation.Suggest;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import xyz.emirdev.emirutils.Utils;

import java.util.Objects;

public class UnbanCommand {

    @Command("unban")
    @CommandPermission("emirutils.unban")
    public void unban(CommandSender sender, OfflinePlayer target, @Suggest("-s") @Optional String extra) {
        if (!target.isBanned()) {
            Utils.sendError(sender, "%s is not banned.", target.getName());
            return;
        }

        boolean silent = Objects.equals(extra, "-s");

        Utils.punishBroadcast(
                "emirutils.unban",
                silent,
                "<#00bbaa>%s <#00eeaa>has been unbanned by <#00bbaa>%s",
                target.getName(),
                sender.getName()
        );

        Bukkit.getBanList(BanListType.PROFILE).pardon(target.getPlayerProfile());
    }
}