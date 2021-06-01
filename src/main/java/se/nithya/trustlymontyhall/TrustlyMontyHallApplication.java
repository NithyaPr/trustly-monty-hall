package se.nithya.trustlymontyhall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TrustlyMontyHallApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrustlyMontyHallApplication.class, args);
	}

}
