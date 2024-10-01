package br.com.resolveai.melodia;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition
public class MelodiaApiJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MelodiaApiJavaApplication.class, args);
	}

}
