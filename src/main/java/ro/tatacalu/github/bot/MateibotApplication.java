package ro.tatacalu.github.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MateibotApplication {

	public static void main(String[] args) {
		SpringApplication.run(MateibotApplication.class, args);
	}
}
