package service;

import org.springframework.stereotype.Service;

import repository.FlightRepository;

@Service
public class FlightService {

	private FlightRepository flightRepository;

	public FlightService(FlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}
	
	public void addFlight() {
		
	}
}
