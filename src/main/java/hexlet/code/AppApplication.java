package hexlet.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //добавляет в моделях дата_созд и дата_изм
public class AppApplication {
	private static final Logger log = LoggerFactory.getLogger(AppApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);

	}

}
