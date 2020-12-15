package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long>{

	
}
