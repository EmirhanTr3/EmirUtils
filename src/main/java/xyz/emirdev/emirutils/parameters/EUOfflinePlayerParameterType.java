package xyz.emirdev.emirutils.parameters;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.stream.MutableStringStream;
import xyz.emirdev.emirutils.EmirUtilsCommandException;

public final class EUOfflinePlayerParameterType implements ParameterType<BukkitCommandActor, OfflinePlayer> {

    @Override
    public OfflinePlayer parse(@NotNull MutableStringStream input, @NotNull ExecutionContext<BukkitCommandActor> context) {
        String name = input.readString();

        if (name.equals("self") || name.equals("me") || name.equals("@s"))
            return context.actor().requirePlayer();

        OfflinePlayer player = Bukkit.getOfflinePlayerIfCached(name);

        if (player == null) throw new EmirUtilsCommandException("%s is not a valid player.", name);

        return player;
    }
}