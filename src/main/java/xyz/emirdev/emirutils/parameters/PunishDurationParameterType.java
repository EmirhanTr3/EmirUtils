package xyz.emirdev.emirutils.parameters;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.stream.MutableStringStream;
import xyz.emirdev.emirutils.EmirUtilsCommandException;
import xyz.emirdev.emirutils.punishutils.PunishDuration;

public final class PunishDurationParameterType implements ParameterType<BukkitCommandActor, PunishDuration> {

    @Override
    public PunishDuration parse(@NotNull MutableStringStream input, @NotNull ExecutionContext<BukkitCommandActor> context) {
        String name = input.readString();
        PunishDuration punishDuration = new PunishDuration(name);
        if (punishDuration.getDuration() == null) throw new EmirUtilsCommandException("%s is not a valid duration.", punishDuration.getInput());

        return punishDuration;
    }
}
