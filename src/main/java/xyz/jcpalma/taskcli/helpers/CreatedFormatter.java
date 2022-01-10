package xyz.jcpalma.taskcli.helpers;

import org.springframework.shell.table.Formatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreatedFormatter implements Formatter {

    private final String format;

    public CreatedFormatter() {
        this("yyyy-MM-dd");
    }

    public CreatedFormatter(String format) {
        this.format = format;
    }

    @Override
    public String[] format(Object value) {
        LocalDateTime date = (LocalDateTime) value;
        return new String[]{ date.format(DateTimeFormatter.ofPattern(format)) };
    }
}
