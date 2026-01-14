package kaiserol.controller.command;

import org.jetbrains.annotations.NotNull;

public abstract class Command implements Comparable<Command> {
    public abstract void execute() throws Exception;

    public abstract String keyword();

    public abstract String description();

    @Override
    public int compareTo(@NotNull Command o) {
        return String.CASE_INSENSITIVE_ORDER.compare(keyword(), o.keyword());
    }

    @Override
    public String toString() {
        return keyword();
    }
}