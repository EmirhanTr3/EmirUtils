package xyz.emirdev.emirutils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.time.Duration;
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

        Pattern pattern = Pattern.compile("([0-9]+)(mo|[smhdwy])");
        Matcher matcher = pattern.matcher(string.toLowerCase());

        if (!matcher.hasMatch()) return null;

        while (matcher.find()) {
            long number = Integer.parseInt(matcher.group(1));
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
}
