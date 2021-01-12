package sk2.Ticket_servis.Listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import sk2.Ticket_servis.repository.TicketRepository;
import sk2.Ticket_servis.service.MyService;

@Component
public class Consumer {
    private MyService service;
    @Autowired
    private TicketRepository ticketRepo;

    public Consumer(MyService purchaseService) {
        this.service = purchaseService;
    }

    @JmsListener(destination = "tickets.queue")
    public void consume(String flightId) {
        MyService.deleteFlightHandle(Long.parseLong(flightId), ticketRepo);
    }
}
