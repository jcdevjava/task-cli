package xyz.jcpalma.taskcli.helpers;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Value;

import java.util.Objects;

public class ShellHelper {

    @Value("${shell.out.info}")
    public String infoColor;

    @Value("${shell.out.success}")
    public String successColor;

    @Value("${shell.out.warning}")
    public String warningColor;

    @Value("${shell.out.error}")
    public String errorColor;

    private final Terminal terminal;

    public ShellHelper(Terminal terminal) {
        this.terminal = terminal;
    }

    private String getMessageColored(String message, FontColor color) {
        return (new AttributedStringBuilder()).append(message, AttributedStyle.DEFAULT.foreground(color.getCode())).toAnsi();
    }

    public String getMessageInfo(String message) {
        return getMessageColored(message, FontColor.valueOf(infoColor));
    }

    public String getMessageSuccess(String message) {
        return getMessageColored(message, FontColor.valueOf(successColor));
    }

    public String getMessageWarning(String message) {
        return getMessageColored(message, FontColor.valueOf(warningColor));
    }

    public String getMessageError(String message) {
        return getMessageColored(message, FontColor.valueOf(errorColor));
    }

    public void print(String message, FontColor color) {
        String output = Objects.nonNull(color) ? getMessageColored(message, color) : message;
        terminal.writer().println(output);
        terminal.flush();
    }

    public void print(String message) {
        print(message, null);
    }

    public void printInfo(String message) {
        print(message, FontColor.valueOf(infoColor));
    }

    public void printSuccess(String message) {
        print(message, FontColor.valueOf(successColor));
    }

    public void printWarning(String message) {
        print(message, FontColor.valueOf(warningColor));
    }

    public void printError(String message) {
        print(message, FontColor.valueOf(errorColor));
    }

}
