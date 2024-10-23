package xyz.emirdev.emirutils.parameters;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.parameter.PrioritySpec;
import revxrsal.commands.stream.MutableStringStream;
import xyz.emirdev.emirutils.punishutils.PunishReason;

import java.util.List;

public final class PunishReasonParameterType implements ParameterType<BukkitCommandActor, PunishReason> {

    @Override
    public PunishReason parse(@NotNull MutableStringStream input, @NotNull ExecutionContext<BukkitCommandActor> context) {
        String name = input.consumeRemaining();
        return new PunishReason(name, "Not Specified.");
    }

    @Override
    public @NotNull SuggestionProvider<@NotNull BukkitCommandActor> defaultSuggestions() {
        return (context) -> List.of("-s");
    }

    @Override
    public boolean isGreedy() {
        return true;
    }
}
