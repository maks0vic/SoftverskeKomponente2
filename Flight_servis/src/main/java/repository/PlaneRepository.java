package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Plane;

public interface PlaneRepository extends JpaRepository<Plane, Long>{

}
