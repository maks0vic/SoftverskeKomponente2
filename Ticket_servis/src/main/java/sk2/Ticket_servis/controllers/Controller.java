package sk2.Ticket_servis.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sk2.Ticket_servis.entities.Ticket;
import sk2.Ticket_servis.forms.BoughtTicketsForm;
import sk2.Ticket_servis.forms.BuyTicketForm;
import sk2.Ticket_servis.repository.TicketRepository;
import sk2.Ticket_servis.service.MyService;

import static sk2.Ticket_servis.service.Constants.*;


import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("")
public class Controller {

    private TicketRepository ticketRepo;

    @Autowired
    public Controller(TicketRepository ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    @PostMapping("/buy_ticket")
    public ResponseEntity<String> buyTicket(@RequestHeader(value = HEADER_STRING) String token, @RequestBody BuyTicketForm ticketForm) {
        try {
            String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();

            ResponseEntity<String> res = MyService.checkUser("http://localhost:8080/who_am_i", token);
            if (res.getStatusCode() == HttpStatus.ACCEPTED) {
                System.out.println("Usao u buy_ticket");
                Ticket ticket = new Ticket(ticketForm.getUserId(), ticketForm.getFlightId(),
                        new Date());

                ticketRepo.saveAndFlush(ticket);
                return new ResponseEntity<String>("Ticket bought", HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/bought_tickets")
    public ResponseEntity<List<Ticket>> boughtTickets(@RequestHeader(value = HEADER_STRING) String token, @RequestBody BoughtTicketsForm ticketForm) {

        String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                .verify(token.replace(TOKEN_PREFIX, "")).getSubject();
        try {
            System.out.println("Usao u bought_tickets");
            ResponseEntity<String> res = MyService.checkUser("http://localhost:8080/who_am_i", token);
            if (res.getStatusCode() == HttpStatus.ACCEPTED) {
                if (ticketRepo.existsByUserId(ticketForm.getUserId())) {
                    List<Ticket> tickets = ticketRepo.findAllByUserId(ticketForm.getUserId());

                    return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.ACCEPTED);
                }
                return new ResponseEntity<List<Ticket>>(Collections.emptyList(), HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Ticket>>(HttpStatus.BAD_REQUEST);
        }
    }
}


//izvadi logiku iz kontrolera, prebaci u service