package sk2.Flight_servis.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import sk2.Flight_servis.entities.Flight;
import sk2.Flight_servis.entities.Plane;
import sk2.Flight_servis.forms.*;
import sk2.Flight_servis.repository.FlightRepository;
import sk2.Flight_servis.repository.PlaneRepository;

import javax.jms.Queue;
import java.util.Collections;
import java.util.List;

import static sk2.Flight_servis.service.Constants.*;

@RestController
@RequestMapping("")
public class Controller {

    private FlightRepository flightRepo;
    private PlaneRepository planeRepo;

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    Queue ticketsQueue;

    @Autowired
    public Controller(FlightRepository flightRepo, PlaneRepository planeRepo) {
        this.flightRepo = flightRepo;
        this.planeRepo = planeRepo;
    }

    @GetMapping("/flight_list")
    public ResponseEntity<List<Flight>> getFlightList(@RequestHeader(value = HEADER_STRING) String token) {
        try {
            String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();

            List<Flight> flights = flightRepo.findAll();
            return new ResponseEntity<List<Flight>>(flights, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Flight>>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/search_flight")
    public ResponseEntity<List<Flight>> searchFlight(@RequestHeader(value = HEADER_STRING) String token,@RequestBody SearchFlightForm form) {
        String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                .verify(token.replace(TOKEN_PREFIX, "")).getSubject();

        try {
            List<Flight> flights = Collections.emptyList();
            if (form.getStartDestination() != null)
                flights = flightRepo.findAllByStartDestination(form.getStartDestination());
            if (form.getFinishDestination() != null)
                flights = flightRepo.findAllByFinishDestination(form.getFinishDestination());
            if (form.getLength() != null)
                flights = flightRepo.findAllByLength(form.getLength());
            if (form.getPrice() != null)
                flights = flightRepo.findAllByPrice(form.getPrice());
            return new ResponseEntity<List<Flight>>(flights, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Flight>>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add_flight")
    public ResponseEntity<String> addFlight(@RequestHeader(value = HEADER_STRING) String token, @RequestBody AddFlightForm form) {
        try {

            String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();

            Flight flight = new Flight(form.getPlaneId(),form.getStartDestination(),
                    form.getFinishDestination(),form.getLength(), form.getPrice());

            flightRepo.saveAndFlush(flight);
            return new ResponseEntity<String>("Success, flight added", HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete_flight")
    public ResponseEntity<String> deleteFlight(@RequestHeader(value = HEADER_STRING) String token,@RequestBody DeleteFlightForm form) {
        try {

            String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();

            long id = form.getId();
            try{
                Boolean b = flightRepo.deleteById(id);
                System.out.println(b);
            } catch (Exception e ){
                e.printStackTrace();
                return new ResponseEntity<String>("Fail, plane not deleted", HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<String>("Success, flight deleted", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add_plane")
    public ResponseEntity<String> addPlane(@RequestHeader(value = HEADER_STRING) String token,@RequestBody AddPlaneForm form) {
        try {

            String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();

            Plane plane = new Plane(form.getName(), form.getCapacity());

            planeRepo.saveAndFlush(plane);
            return new ResponseEntity<String>("Success, plane added", HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete_plane")
    public ResponseEntity<String> deletePlane(@RequestHeader(value = HEADER_STRING) String token,@RequestBody DeletePlaneForm form) {
        try {

            String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();

            long id = form.getId();
            try{
                Boolean b = planeRepo.deleteById(id);
                System.out.println(b);
            } catch (Exception e ){
                e.printStackTrace();
                return new ResponseEntity<String>("Fail, plane not deleted", HttpStatus.ACCEPTED);
            }

            return new ResponseEntity<String>("Success, plane deleted", HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }
}
