package sk2.Ticket_servis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk2.Ticket_servis.entities.Ticket;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Ticket findByUserId(long userId);
    boolean existsByUserId(long userId);
    List<Ticket> findAllByUserId(long userId);
    List<Ticket> findAllByFlightId(long flightId);
}
