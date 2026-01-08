package pl.wynajem;

// glowna klasa startowa

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.wynajem.repositories.UzytkownikRepository;

@SpringBootApplication
public class WynajemAutApplication {
    
	public static void main(String[] args) {
		SpringApplication.run(WynajemAutApplication.class, args);
	}

}
