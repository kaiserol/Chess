package kaiserol.controller.command;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

public abstract class Command implements Comparable<Command> {
    public abstract void execute(String[] args) throws Exception;

    public abstract String keyword();

    public abstract String description();

    public @NotNull Map<String, String> options() {
        return Collections.emptyMap();
    }

    @Override
    public int compareTo(@NotNull Command o) {
        return compareTo(keyword(), o.keyword());
    }

    @Override
    public String toString() {
        return keyword();
    }

    public static boolean matchesOptions(@NotNull String[] args, @NotNull String[] options) {
        if (args.length != options.length) return false;
        for (int i = 0; i < args.length; i++) {
            String arg = normalize(args[i]);
            String regex = options[i]; // No need to normalize regex
            if (!arg.matches(regex)) return false;
        }
        return true;
    }

    public static int compareTo(@NotNull String keyword1, @NotNull String keyword2) {
        String k1 = normalize(keyword1);
        String k2 = normalize(keyword2);

        int result = k1.compareToIgnoreCase(k2);
        if (result != 0) {
            return result;
        }

        return keyword1.compareToIgnoreCase(keyword2);
    }

    private static String normalize(String keyword) {
        return keyword.toLowerCase().replaceAll("\\s+", " ");
    }
}