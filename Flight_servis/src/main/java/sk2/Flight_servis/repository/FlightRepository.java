package sk2.Flight_servis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sk2.Flight_servis.entities.Flight;

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
    @Query(value = "SELECT * FROM flight", nativeQuery = true)
    List<Flight> getFlights(Pageable pageable);
    @Query(value = "SELECT COUNT(*) FROM flight", nativeQuery = true)
    Integer countFlights();
    List<Flight> findAll();

}
