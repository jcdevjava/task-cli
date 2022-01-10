package xyz.jcpalma.taskcli.helpers;

import org.springframework.shell.table.Formatter;

public class CompletedFormatter implements Formatter {
    @Override
    public String[] format(Object value) {

        Boolean completed = (Boolean) value;
        return new String[]{completed ? "Yes" : "No"};
    }
}
