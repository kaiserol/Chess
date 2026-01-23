package kaiserol.cli;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * The {@link CLIStyle} class enables text formatting in the console
 * using ANSI escape codes. It supports various styles and colors.
 * This class is useful for improving readability and the visual
 * appearance of console output.
 *
 * <br><br>
 * The class is declared as {@code final} to prevent inheritance.
 * Since all functionality is provided via static methods,
 * the class has a private constructor to prevent instantiation.
 */
public final class CLIStyle {
    // ANSI escape prefix for formatting
    private static final String ESC = "\033[";

    /**
     * Prints an example to the console.
     *
     * @param args The given arguments for this program.
     */
    public static void main(String[] args) {
        printExample(System.out::println);
    }

    /**
     * Prints a demonstration of the {@link CLIStyle} capabilities.
     * <p>
     * This method outputs a series of example texts showcasing all supported
     * text styles, foreground colors, background colors, and common combinations.
     * The output destination is abstracted via a {@link Consumer},
     * allowing the demo to be rendered to different targets (e.g. console or
     * logger).
     * </p>
     *
     * @param output a consumer that receives each formatted output element;
     *               typically {@code System.out::println}
     */
    public static void printExample(Consumer<Object> output) {
        // Example text
        String demoText = "Example text";

        output.accept("=== CLIStyle Demo ===");

        // All styles individually
        output.accept(CLIStyle.text(demoText).bold());
        output.accept(CLIStyle.text(demoText).dim());
        output.accept(CLIStyle.text(demoText).italic());
        output.accept(CLIStyle.text(demoText).underline());
        output.accept(CLIStyle.text(demoText).blink());
        output.accept(CLIStyle.text(demoText).inverted());
        output.accept(CLIStyle.text(demoText).hidden());
        output.accept(CLIStyle.text(demoText).strikethrough());

        // Combination of styles
        output.accept(CLIStyle.text(demoText).bold().underline().italic());

        // Text color RGB
        output.accept(CLIStyle.text(demoText).foreground(new Color(255, 0, 0))); // Red
        output.accept(CLIStyle.text(demoText).foreground(new Color(0, 255, 0))); // Green
        output.accept(CLIStyle.text(demoText).foreground(new Color(0, 0, 255))); // Blue

        // Background color RGB
        output.accept(CLIStyle.text(demoText).background(new Color(255, 255, 0))); // Yellow background
        output.accept(CLIStyle.text(demoText)
                .foreground(new Color(0, 0, 0))
                .background(new Color(255, 255, 0))); // Black on yellow

        // Combination of text color, background color and styles
        output.accept(CLIStyle.text(demoText)
                .foreground(new Color(255, 255, 255))
                .background(new Color(128, 0, 128))
                .bold()
                .underline()
                .italic());

        output.accept("=== End of Demo ===");
    }

    /**
     * Private constructor to prevent instantiation of this class.
     */
    private CLIStyle() {
        // Prevents instantiation of this class
    }

    /**
     * Entry method to create a new {@code Text} instance.
     *
     * @param text The text to be formatted.
     * @return A new {@code Text} instance.
     */
    public static Text text(String text) {
        return new Text(text);
    }

    /**
     * Enum {@code TextStyle} defines the available text styles.
     */
    public enum TextStyle {
        RESET(0),          // Resets all styles
        BOLD(1),           // Bold
        DIM(2),            // Dimmed
        ITALIC(3),         // Italic
        UNDERLINE(4),      // Underlined
        BLINK(5),          // Blinking
        INVERTED(7),       // Invert colors
        HIDDEN(8),         // Hidden
        STRIKETHROUGH(9);  // Strikethrough

        private final int ansiCode;

        /**
         * Constructor for TextStyle.
         *
         * @param ansiCode ANSI code of the style
         */
        TextStyle(int ansiCode) {
            this.ansiCode = ansiCode;
        }

        /**
         * Returns the ANSI code.
         *
         * @return ANSI code
         */
        public int getAnsiCode() {
            return this.ansiCode;
        }
    }

    /**
     * {@code Text} is used to incrementally build formatted text
     * with colors and styles.
     */
    public static class Text {

        /**
         * The text to be formatted
         */
        private final String text;

        /**
         * Text color
         */
        private Color foregroundColor;

        /**
         * Background color
         */
        private Color backgroundColor;

        /**
         * List of applied styles
         */
        private final List<TextStyle> styles;

        /**
         * Constructor for the Text class.
         *
         * @param text The text to be formatted.
         */
        private Text(String text) {
            this.text = text;
            this.foregroundColor = null;
            this.backgroundColor = null;
            this.styles = new ArrayList<>();
        }

        /**
         * Sets the text color.
         *
         * @param color The text color
         * @return The current {@code Text} instance
         */
        public Text foreground(Color color) {
            this.foregroundColor = color;
            return this;
        }

        /**
         * Sets the background color.
         *
         * @param color The background color
         * @return The current {@code Text} instance
         */
        public Text background(Color color) {
            this.backgroundColor = color;
            return this;
        }

        /**
         * Adds the "Bold" style.
         *
         * @return The current {@code Text} instance
         */
        public Text bold() {
            this.styles.add(TextStyle.BOLD);
            return this;
        }

        /**
         * Adds the "Dimmed" style.
         *
         * @return The current {@code Text} instance
         */
        public Text dim() {
            this.styles.add(TextStyle.DIM);
            return this;
        }

        /**
         * Adds the "Italic" style.
         *
         * @return The current {@code Text} instance
         */
        public Text italic() {
            this.styles.add(TextStyle.ITALIC);
            return this;
        }

        /**
         * Adds the "Underline" style.
         *
         * @return The current {@code Text} instance
         */
        public Text underline() {
            this.styles.add(TextStyle.UNDERLINE);
            return this;
        }

        /**
         * Adds the "Blinking" style.
         *
         * @return The current {@code Text} instance
         */
        public Text blink() {
            this.styles.add(TextStyle.BLINK);
            return this;
        }

        /**
         * Adds the "Invert colors" style.
         *
         * @return The current {@code Text} instance
         */
        public Text inverted() {
            this.styles.add(TextStyle.INVERTED);
            return this;
        }

        /**
         * Adds the "Hidden" style.
         *
         * @return The current {@code Text} instance
         */
        public Text hidden() {
            this.styles.add(TextStyle.HIDDEN);
            return this;
        }

        /**
         * Adds the "Strikethrough" style.
         *
         * @return The current {@code Text} instance
         */
        public Text strikethrough() {
            this.styles.add(TextStyle.STRIKETHROUGH);
            return this;
        }

        /**
         * Builds the formatted text with ANSI codes.
         *
         * @return Formatted text
         */
        public String build() {
            List<String> ansiCodes = new ArrayList<>();

            // Add styles
            for (TextStyle style : this.styles) {
                ansiCodes.add(String.valueOf(style.getAnsiCode()));
            }

            // Text color (RGB)
            if (this.foregroundColor != null) {
                ansiCodes.add("38;2;" +
                        this.foregroundColor.getRed() + ";" +
                        this.foregroundColor.getGreen() + ";" +
                        this.foregroundColor.getBlue());
            }

            // Background color (RGB)
            if (this.backgroundColor != null) {
                ansiCodes.add("48;2;" +
                        this.backgroundColor.getRed() + ";" +
                        this.backgroundColor.getGreen() + ";" +
                        this.backgroundColor.getBlue());
            }

            // Build escape sequence
            String prefix = ESC + String.join(";", ansiCodes) + "m";
            String reset = ESC + TextStyle.RESET.getAnsiCode() + "m";

            return prefix + this.text + reset;
        }

        /**
         * Returns the formatted text.
         *
         * @return The formatted text
         */
        @Override
        public String toString() {
            return build();
        }
    }
}