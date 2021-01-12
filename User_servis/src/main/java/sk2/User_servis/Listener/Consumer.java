package sk2.User_servis.Listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import sk2.User_servis.services.MyService;

@Component
public class Consumer {
    private MyService service;

    public Consumer(MyService purchaseService) {
        this.service = purchaseService;
    }

    @JmsListener(destination = "users.queue")
    public void consume(String flightId) {
        //service.cancelTickets(Long.parseLong(flightId));
    }
}
