package kaiserol.controller.command;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandRegistry {
    private final Map<String, Command> commands = new HashMap<>();

    public void add(@NotNull Command command) {
        commands.put(command.keyWord().toLowerCase(), command);
    }

    public Optional<Command> get(@NotNull String keyWord) {
        return Optional.ofNullable(commands.get(keyWord.toLowerCase()));
    }

    public Collection<Command> getAllCommands() {
        return commands.values();
    }
}