package xyz.jcpalma.taskcli;

import org.jline.reader.History;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.Parser;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.context.annotation.*;
import org.springframework.shell.jline.JLineShellAutoConfiguration;
import xyz.jcpalma.taskcli.helpers.InputReader;
import xyz.jcpalma.taskcli.helpers.ShellHelper;

@Configuration
@PropertySources(
    @PropertySource("classpath:shell.properties")
)
public class AppConfig {

    @Bean
    public ShellHelper shellHelper(@Lazy Terminal terminal) {
        return new ShellHelper(terminal);
    }

    @Bean
    public InputReader inputReader(
        @Lazy Terminal terminal,
        @Lazy Parser parser,
        JLineShellAutoConfiguration.CompleterAdapter completer,
        @Lazy History history
    ) {

        LineReaderBuilder builder = LineReaderBuilder.builder()
            .terminal(terminal)
            .completer(completer)
            .history(history)
            .highlighter(
                (LineReader reader, String buffer) ->
                    new AttributedString(
                        buffer,
                        AttributedStyle.DEFAULT.italic().foreground(AttributedStyle.BLUE)
                    )
            )
            .parser(parser);

        LineReader lineReader = builder.build();
        lineReader.unsetOpt(LineReader.Option.INSERT_TAB);
        return new InputReader(lineReader);
    }

}
