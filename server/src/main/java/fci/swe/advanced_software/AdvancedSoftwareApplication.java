package fci.swe.advanced_software;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AdvancedSoftwareApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvancedSoftwareApplication.class, args);
	}

}
