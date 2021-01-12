package sk2.Ticket_servis.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk2.Ticket_servis.entities.Ticket;
import sk2.Ticket_servis.repository.TicketRepository;

import java.util.List;

@Service
public class MyService {
//    @Autowired
//
//    public cancel

    public static ResponseEntity<String> checkUser(String url, String token) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response;
    }

    public static void deleteFlightHandle (long flightId, TicketRepository ticketRepo) {
        List<Ticket> tickets = ticketRepo.findAllByFlightId(flightId);
        System.out.println("Primio flightId" + flightId);
        System.out.println("Koliko ticketa sam nasao za brisanje: " + tickets.size());
        for (int i =0; i<tickets.size(); i++) {
            Ticket t = tickets.get(i);
            t.setCanceled(true);
            System.out.println(t.getCanceled());
            ticketRepo.saveAndFlush(t);
        }

    }
}
