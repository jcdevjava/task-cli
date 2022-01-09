package xyz.jcpalma.taskcli;

import org.jline.terminal.Terminal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import xyz.jcpalma.taskcli.helpers.ShellHelper;

@SpringBootApplication
@PropertySources(
	@PropertySource("classpath:shell.properties")
)
public class TaskCliApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskCliApplication.class, args);
	}

	@Bean
	public ShellHelper shellHelper(@Lazy Terminal terminal) {
		return new ShellHelper(terminal);
	}

}
