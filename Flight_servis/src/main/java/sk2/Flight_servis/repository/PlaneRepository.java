package sk2.Flight_servis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk2.Flight_servis.entities.Plane;

@Repository
public interface PlaneRepository extends JpaRepository<Plane, Long> {
}
