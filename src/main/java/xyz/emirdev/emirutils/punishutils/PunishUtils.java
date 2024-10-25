package xyz.emirdev.emirutils.punishutils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PunishUtils {
    public static String generateId() {
        List<String> chars = new ArrayList<>();
        List<String> possibleChars = List.of("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".split(""));

        for (int i = 0; i < 10; i++) {
            chars.add(possibleChars.get(ThreadLocalRandom.current().nextInt(possibleChars.size())));
        }

        return String.join("", chars);
    }
}
