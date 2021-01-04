package sk2.Ticket_servis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk2.Ticket_servis.entities.Ticket;
import sk2.Ticket_servis.forms.BoughtTicketsForm;
import sk2.Ticket_servis.forms.BuyTicketForm;
import sk2.Ticket_servis.repository.TicketRepository;

import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("")
public class Controller {

    private TicketRepository ticketRepo;

    @Autowired
    public Controller(TicketRepository ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    @PostMapping("/buy_ticket")
    public ResponseEntity<String> buyTicket(@RequestBody BuyTicketForm ticketForm) {
        try {
            System.out.println("Usao u buy_ticket");
            Ticket ticket = new Ticket(ticketForm.getUserId(), ticketForm.getFlightId(),
                    new Date());

            ticketRepo.saveAndFlush(ticket);
            return new ResponseEntity<String>("Ticket bought", HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/bought_tickets")
    public ResponseEntity<String> boughtTickets(@RequestBody BoughtTicketsForm ticketForm) {
        try {
            System.out.println("Usao u bought_tickets");

            if (ticketRepo.existsByUserId(ticketForm.getUserId())) {
                Ticket ticket = ticketRepo.findByUserId(ticketForm.getUserId());
                return new ResponseEntity<String>("Bought ticket: " + ticket.getUserId()
                        + " " + ticket.getFlightId() + " " + ticket.getDate().toString(), HttpStatus.ACCEPTED);
            }

            return new ResponseEntity<String>("Bought ticket: empty", HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }
}
