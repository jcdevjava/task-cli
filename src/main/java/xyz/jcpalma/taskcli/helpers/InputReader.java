package xyz.jcpalma.taskcli.helpers;

import org.jline.reader.LineReader;

public class InputReader {

    private static final Character DEFAULT_MASK = '*';

    private final Character mask;
    private final LineReader reader;

    public InputReader(LineReader reader, Character mask) {
        this.reader = reader;
        this.mask = mask;
    }

    public InputReader(LineReader reader) {
        this(reader, DEFAULT_MASK);
    }

    public String read(String prompt, String defaultValue) {
        String input = reader.readLine(prompt);
        return input == null || input.isEmpty() ? defaultValue : input;
    }

    public String read(String prompt) {
        return read(prompt, "");
    }

    public String readSecret(String prompt, String defaultValue) {
        String input = reader.readLine(prompt, mask);
        return input == null || input.isEmpty() ? defaultValue : input;
    }

}
