package xyz.emirdev.emirutils.punishutils;

import xyz.emirdev.emirutils.Utils;

import java.time.Duration;
import java.util.Date;

public class PunishDuration {
    private final String input;
    private final Duration duration;

    public PunishDuration(String duration) {
        this.input = duration;
        this.duration = Utils.parseDuration(duration);
    }

    public String getInput() {
        return input;
    }

    public Duration getDuration() {
        return duration;
    }

    public String getString() {
        return Utils.parseDurationToString(duration);
    }

    public String getFormatted() {
        return Utils.formatDate(Date.from(
                new Date().toInstant().plusSeconds(duration.toSeconds())
        ));
    }
}
