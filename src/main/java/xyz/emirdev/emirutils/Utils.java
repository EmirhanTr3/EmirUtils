package xyz.emirdev.emirutils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static Component getPrefix() {
        return format("<gradient:#00eeaa:#00aaaa><bold>EmirUtils<reset> <dark_gray>» ");
    }

    public static Component format(String string, Object... args) {
        return MiniMessage.miniMessage().deserialize(String.format(string, args));
    }

    public static void sendMessage(CommandSender sender, String string, Object... args) {
        sender.sendMessage(getPrefix().append(format(string, args)));
    }

    public static void sendError(CommandSender sender, String string, Object... args) {
        sender.sendMessage(getPrefix().append(format("<#ee4444>" + string, args)));
    }

    public static void broadcast(String string, Object... args) {
        Bukkit.broadcast(getPrefix().append(format(string, args)));
    }

    public static void punishBroadcast(String permission, boolean silent, String string, Object... args) {
        if (silent) {
            Component message = getPrefix().append(format(string, args)).append(format(" <dark_gray>(Silent)"));
            Bukkit.broadcast(message, permission);
            Bukkit.getConsoleSender().sendMessage(message);
        } else {
            broadcast(string, args);
        }
    }

    public static String convertComponentToLegacyString(Component component) {
        return LegacyComponentSerializer.legacySection().serialize(component);
    }

    public static Duration parseDuration(String string) {
        Duration duration = Duration.ZERO;

        if (!string.matches("(?:([0-9]+)(mo|[smhdwy]))*")) return null;

        Pattern pattern = Pattern.compile("([0-9]+)(mo|[smhdwy])");
        Matcher matcher = pattern.matcher(string.toLowerCase());

        while (matcher.find()) {
            long number = Long.parseLong(matcher.group(1));
            String type = matcher.group(2);
            duration = switch (type) {
                case "s" -> duration.plusSeconds(number);
                case "m" -> duration.plusMinutes(number);
                case "h" -> duration.plusHours(number);
                case "d" -> duration.plusDays(number);
                case "w" -> duration.plusDays(7 * number);
                case "mo" -> duration.plusDays(30 * number);
                case "y" -> duration.plusDays(365 * number);
                default -> duration;
            };
        }

        return duration;
    }

    public static String parseDurationToString(Duration duration) {
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

    public static String formatDate(Date date) {
        return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss z").format(date);
    }
}
