package sk2.Flight_servis.service;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.jms.core.JmsMessageOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.client.RestTemplate;
import sk2.Flight_servis.entities.Flight;
import sk2.Flight_servis.entities.Plane;
import sk2.Flight_servis.forms.SearchFlightForm;
import sk2.Flight_servis.repository.FlightRepository;
import sk2.Flight_servis.repository.PlaneRepository;

import javax.jms.Queue;
import java.util.Collections;
import java.util.List;

public class MyService {


    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    Queue flightsQueue;

    @Autowired
    Queue ticketsQueue;

    @Autowired
    Queue usersQueue;


    public static ResponseEntity<String> checkUser(String url, String token) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println("Dobio response: " + response.getStatusCode());
        return response;
    }

    public static ResponseEntity<String> checkAdmin(String url, String token) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response;
    }

    public static ResponseEntity<List<Flight>> searchFlights (SearchFlightForm form, FlightRepository flightRepo) {
        List<Flight> flights = Collections.emptyList();

        if (form.getStartDestination() != null && form.getStartDestination() != "")
            flights = flightRepo.findAllByStartDestination(form.getStartDestination());
        if (form.getFinishDestination() != null && form.getFinishDestination() != "")
            flights = flightRepo.findAllByFinishDestination(form.getFinishDestination());
        if (form.getLength() != null)
            flights = flightRepo.findAllByLength(form.getLength());
        if (form.getPrice() != null)
            flights = flightRepo.findAllByPrice(form.getPrice());
        return new ResponseEntity<List<Flight>>(flights, HttpStatus.ACCEPTED);
    }

    public static ResponseEntity<List<Flight>> getFlightList ( FlightRepository flightRepo) {
        List<Flight> flights = Collections.emptyList();
        flights = flightRepo.findAll();
        System.out.println("Koliko ima letova: " + flights.size());
        return new ResponseEntity<List<Flight>>(flights, HttpStatus.ACCEPTED);
    }

    public static ResponseEntity<List<Flight>> getFlightListPaginable ( FlightRepository flightRepo, Pageable page) {
        List<Flight> flights = Collections.emptyList();
        flights = flightRepo.getFlights(page);
        System.out.println("USAO U ZAHTJEV");
        System.out.println("Koliko ima letova: " + flights.size());
        return new ResponseEntity<List<Flight>>(flights, HttpStatus.ACCEPTED);
    }

    public static ResponseEntity<List<Plane>> getPlaneList ( PlaneRepository planeRepo) {
        List<Plane> planes = Collections.emptyList();
        planes = planeRepo.findAll();
        System.out.println("Koliko ima letova: " + planes.size());
        return new ResponseEntity<List<Plane>>(planes, HttpStatus.ACCEPTED);
    }

    public static ResponseEntity<Integer> countFlights ( FlightRepository flightRepo) {
        Integer count = flightRepo.countFlights();
        return new ResponseEntity<Integer>(count, HttpStatus.ACCEPTED);
    }

    public static void deleteFlight (long id,  FlightRepository flightRepo) {
        Flight flight = flightRepo.findById(id);
        flight.setCanceled(true);
        flightRepo.flush();
        System.out.println("Provera da je let canceled:" + flight.getCanceled());
    }

}
