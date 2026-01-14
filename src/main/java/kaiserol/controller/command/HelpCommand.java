package kaiserol.controller.command;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class HelpCommand extends Command {
    private final CommandRegistry registry;
    private final Consumer<String> output;

    public HelpCommand(@NotNull  CommandRegistry registry, @NotNull Consumer<String> output) {
        this.registry = registry;
        this.output = output;
    }

    @Override
    public void execute() {
        List<Command> sortedCommands = registry.getAllCommands().stream().sorted().toList();

        output.accept("=".repeat(40));
        output.accept("Available commands:");
        int indent = sortedCommands.stream()
                .mapToInt(command -> command.keyword().length())
                .max()
                .orElse(0);

        for (Command command : sortedCommands) {
            String result = ("  %-" + indent + "s - %s").formatted(command.keyword(), command.description());
            output.accept(result);
        }
        output.accept("=".repeat(40));
    }

    @Override
    public String keyword() {
        return "help";
    }

    @Override
    public String description() {
        return "Display all available commands";
    }
}