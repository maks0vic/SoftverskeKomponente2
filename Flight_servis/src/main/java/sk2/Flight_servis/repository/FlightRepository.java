package sk2.Flight_servis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk2.Flight_servis.entities.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    Flight findById(long id);
    boolean existsById(long id);
    boolean deleteById(long id);
}
