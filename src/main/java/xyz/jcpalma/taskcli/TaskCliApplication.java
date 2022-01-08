package xyz.jcpalma.taskcli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources(
	@PropertySource("classpath:shell.properties")
)
public class TaskCliApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskCliApplication.class, args);
	}

}
