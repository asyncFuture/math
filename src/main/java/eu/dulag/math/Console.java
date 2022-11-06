package eu.dulag.math;

import org.fusesource.jansi.Ansi;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class Console {

    public enum Color {

        DEFAULT(Ansi.ansi().reset(), 'r'),

        WHITE(Ansi.ansi().fgDefault(), 'f'), BLACK(Ansi.ansi().fgBlack(), '0'),

        RED(Ansi.ansi().fgBrightRed(), 'c'), DARK_RED(Ansi.ansi().fgRed(), '4'),

        GREEN(Ansi.ansi().fgBrightGreen(), 'a'), DARK_GREEN(Ansi.ansi().fgGreen(), '2'),

        BLUE(Ansi.ansi().fgBrightBlue(), '9'), DARK_BLUE(Ansi.ansi().fgBlue(), '1'),

        YELLOW(Ansi.ansi().fgBrightYellow(), 'e'), GOLD(Ansi.ansi().fgYellow(), '6'),

        CYAN(Ansi.ansi().fgBrightCyan(), 'b'), DARK_CYAN(Ansi.ansi().fgCyan(), '3'),

        PURPLE(Ansi.ansi().fgBrightMagenta(), 'd'), DARK_PURPLE(Ansi.ansi().fgMagenta(), '5'),

        GRAY(Ansi.ansi().fgBrightBlack(), '8');

        final Ansi ansi;
        final char prefix;

        Color(Ansi ansi, char prefix) {
            this.ansi = ansi;
            this.prefix = prefix;
        }

        public static String text(String text) {
            for (Color value : Color.values()) {
                text = text.replace("§" + value.prefix, value.ansi.toString());
            }
            return text;
        }

        @Override
        public String toString() {
            return ansi.toString();
        }
    }

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private String prompt = null;
    private String line;

    public void run(String prompt, Consumer<String> consumer) {
        try {
            prompt(prompt, true);
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                consumer.accept(line);
                prompt();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Console prompt(String prompt, boolean flush) {
        this.prompt = prompt;
        if (flush) prompt();
        return this;
    }

    public Console prompt() {
        System.out.print(Ansi.ansi().eraseLine().a(Color.text(prompt)).reset());
        return this;
    }

    public Console println(String string) {
        System.out.println(Ansi.ansi().eraseLine().a(Color.text(string)).reset());
        return this;
    }

    public Console beep() {
        Toolkit.getDefaultToolkit().beep();
        return this;
    }

    public void clear() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}