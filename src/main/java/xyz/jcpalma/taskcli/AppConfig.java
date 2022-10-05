package xyz.jcpalma.taskcli;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.DefaultHighlighter;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.springframework.context.annotation.*;
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
    public InputReader inputReader(@Lazy Terminal terminal) {

        LineReaderBuilder builder = LineReaderBuilder.builder()
            .terminal(terminal)
            .highlighter( new DefaultHighlighter())
            .parser(new DefaultParser());

        LineReader lineReader = builder.build();
        lineReader.unsetOpt(LineReader.Option.INSERT_TAB);
        return new InputReader(lineReader);
    }

}
