package xyz.emirdev.emirutils.punishutils;

import xyz.emirdev.emirutils.Utils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        List<String> strings = new ArrayList<>();
        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        if (days    != 0) strings.add(days    + " " + (days    > 1 ? "days"    : "day"   ));
        if (hours   != 0) strings.add(hours   + " " + (hours   > 1 ? "hours"   : "hour"  ));
        if (minutes != 0) strings.add(minutes + " " + (minutes > 1 ? "minutes" : "minute"));
        if (seconds != 0) strings.add(seconds + " " + (seconds > 1 ? "seconds" : "second"));

        if (strings.isEmpty()) strings.add("0 second");

        return String.join(" ", strings);
    }

    public String getFormatted() {
        return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss z").format(Date.from(
                new Date().toInstant().plusSeconds(duration.toSeconds())
        ));
    }
}
