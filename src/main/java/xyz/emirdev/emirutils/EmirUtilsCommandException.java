package xyz.emirdev.emirutils;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.exception.SendableException;

public class EmirUtilsCommandException extends SendableException {
    public Object[] args;

    public EmirUtilsCommandException(String message, Object... args) {
        super(message);
        this.args = args;
    }

    public void sendTo(@NotNull CommandActor actor) {
        if (!this.getMessage().isEmpty()) {
            actor.error(Utils.convertComponentToLegacyString(
                    Utils.getPrefix().append(
                            Utils.format("<#ee4444>" + getMessage(), args)
                    )
            ));
        }
    }
}
