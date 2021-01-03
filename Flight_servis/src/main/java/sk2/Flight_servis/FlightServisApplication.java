package sk2.Flight_servis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FlightServisApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightServisApplication.class, args);
	}

}
