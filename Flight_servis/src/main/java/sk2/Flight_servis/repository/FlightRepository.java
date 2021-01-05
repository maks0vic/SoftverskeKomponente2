package sk2.Flight_servis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk2.Flight_servis.entities.Flight;
import sk2.Flight_servis.entities.Plane;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    Flight findById(long id);
    boolean existsById(long id);
    Boolean deleteById(long id);
    List<Flight> findAllByPlaneId(long planeId);
    List<Flight> findAllByStartDestination(String startDestination);
    List<Flight> findAllByFinishDestination(String finishDestination);
    List<Flight> findAllByLength(Integer length);
    List<Flight> findAllByPrice(BigDecimal price);
    List<Flight> findAll();

}
