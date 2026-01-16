package kaiserol.controller.command;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class HelpCommand extends Command {
    private final CommandRegistry registry;
    private final Consumer<String> output;

    public HelpCommand(@NotNull CommandRegistry registry, @NotNull Consumer<String> output) {
        this.registry = registry;
        this.output = output;
    }

    @Override
    public void execute(String[] args) {
        List<Command> sortedCommands = registry.getAllCommands().stream().sorted().toList();

        output.accept("=".repeat(80));
        output.accept("Available commands:");

        for (Command command : sortedCommands) {
            String result = ("  %-15s - %s").formatted(command.keyword(), command.description());
            output.accept(result);

            List<String> sortedOptions = command.options().keySet().stream().sorted(Command::compareTo).toList();
            for (String opt : sortedOptions) {
                String desc = command.options().get(opt);
                output.accept("%-20s%s%n  %-20s%s".formatted(" ", opt, " ", desc));
            }
        }
        output.accept("=".repeat(80));
    }

    @Override
    public String keyword() {
        return "help";
    }

    @Override
    public String description() {
        return "Displays all available commands";
    }
}