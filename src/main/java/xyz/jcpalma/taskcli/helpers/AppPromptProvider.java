package xyz.jcpalma.taskcli.helpers;


import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class AppPromptProvider implements PromptProvider {

    @Override
    public AttributedString getPrompt() {
        return new AttributedString("task-cli❯ ", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }
}
