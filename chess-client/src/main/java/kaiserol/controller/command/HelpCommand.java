package kaiserol.controller.command;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class HelpCommand extends Command {
    private final CommandRegistry registry;

    public HelpCommand(@NotNull Consumer<String> out, @NotNull Consumer<String> err, @NotNull CommandRegistry registry) {
        super(out, err);
        this.registry = registry;
    }

    @Override
    public void execute(String[] args) {
        List<Command> sortedCommands = registry.getAllCommands().stream().sorted().toList();

        out.accept("=".repeat(80));
        out.accept("Available commands:");

        for (Command command : sortedCommands) {
            String result = ("  %-15s - %s").formatted(command.keyword(), command.description());
            out.accept(result);

            List<String> sortedOptions = command.options().keySet().stream().sorted(Command::compareTo).toList();
            for (String opt : sortedOptions) {
                String desc = command.options().get(opt);
                out.accept("%-20s%s%n  %-20s%s".formatted(" ", opt, " ", desc));
            }
        }
        out.accept("=".repeat(80));
    }

    @Override
    public String keyword() {
        return "help";
    }

    @Override
    public String description() {
        return "Displays all available commands.";
    }
}