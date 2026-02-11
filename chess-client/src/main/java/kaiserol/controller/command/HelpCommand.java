package kaiserol.controller.command;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class HelpCommand extends Command {
    private final CommandRegistry registry;

    public HelpCommand(@NotNull Consumer<String> out, @NotNull Consumer<String> err, @NotNull CommandRegistry registry) {
        super(out, err);
        this.registry = registry;
    }

    @Override
    public void execute(@NotNull String[] args) {
        List<Command> sortedCommands = registry.getAllCommands().stream().sorted().toList();

        out.accept("=".repeat(80));
        out.accept("Available commands:");

        for (Command command : sortedCommands) {
            String result = ("  %-15s - %s").formatted(command.keyWord(), command.description());
            out.accept(result);

            Map<String, String> options = command.options();
            List<String> sortedOptions = options.keySet().stream().sorted(Command::compareTo).toList();

            for (String option : sortedOptions) {
                String description = options.get(option);
                out.accept("%-20s%s%n  %-20s%s".formatted(" ", option, " ", description));
            }
        }
        out.accept("=".repeat(80));
    }

    @Override
    public String keyWord() {
        return "help";
    }

    @Override
    public String description() {
        return "Displays all available commands.";
    }
}