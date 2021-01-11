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
import sk2.Flight_servis.service.MyService;

import javax.jms.Queue;
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
    Queue flightsQueue;

    @Autowired
    Queue ticketsQueue;

    @Autowired
    public Controller(FlightRepository flightRepo, PlaneRepository planeRepo) {
        this.flightRepo = flightRepo;
        this.planeRepo = planeRepo;
    }

    @CrossOrigin
    @GetMapping("/flight_list")
    public ResponseEntity<List<Flight>> getFlightList(@RequestHeader(value = HEADER_STRING) String token) {
        try {
            String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();

            ResponseEntity<String> res = MyService.checkUser("http://localhost:8080/who_am_i", token);
            ResponseEntity<String> res2 = MyService.checkAdmin("http://localhost:8080/is_admin", token);
            if (res.getStatusCode() == HttpStatus.ACCEPTED || res2.getStatusCode() == HttpStatus.ACCEPTED) {
            //if (true){
                ResponseEntity<List<Flight>> flights = MyService.getFlightList(flightRepo);
                return flights;
            }
            System.out.println("Nije lepo autorizovan" + email);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Flight>>(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @GetMapping("/plane_list")
    public ResponseEntity<List<Plane>> getPlaneList(@RequestHeader(value = HEADER_STRING) String token) {
        try {
            String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();

//            ResponseEntity<String> res = MyService.checkUser("http://localhost:8080/who_am_i", token);
//            ResponseEntity<String> res2 = MyService.checkAdmin("http://localhost:8080/is_admin", token);
//            if (res.getStatusCode() == HttpStatus.ACCEPTED || res2.getStatusCode() == HttpStatus.ACCEPTED) {
                if (true){
                ResponseEntity<List<Plane>> planes = MyService.getPlaneList(planeRepo);
                return planes;
            }
            System.out.println("Nije lepo autorizovan" + email);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Plane>>(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @PostMapping("/search_flight")
    public ResponseEntity<List<Flight>> searchFlight(@RequestHeader(value = HEADER_STRING) String token, @RequestBody SearchFlightForm form) {
        String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                .verify(token.replace(TOKEN_PREFIX, "")).getSubject();

        try {
            ResponseEntity<String> res = MyService.checkUser("http://localhost:8080/who_am_i", token);
            if (res.getStatusCode() == HttpStatus.ACCEPTED) {
                ResponseEntity<List<Flight>> flights = MyService.searchFlights(form, flightRepo);
                return flights;
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Flight>>(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @PostMapping("/add_flight")
    public ResponseEntity<String> addFlight(@RequestHeader(value = HEADER_STRING) String token, @RequestBody AddFlightForm form) {
        try {
            String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();

            ResponseEntity<String> res = MyService.checkAdmin("http://localhost:8080/is_admin", token);
            if (res.getStatusCode() == HttpStatus.ACCEPTED) {
                Flight flight = new Flight(form.getPlaneId(), form.getStartDestination(),
                        form.getFinishDestination(), form.getLength(), form.getPrice());

                flightRepo.saveAndFlush(flight);
                return new ResponseEntity<String>("Success, flight added", HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @PostMapping("/delete_flight")
    public ResponseEntity<String> deleteFlight(@RequestHeader(value = HEADER_STRING) String token, @RequestBody DeleteFlightForm form) {
        try {

            String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();

            jmsTemplate.convertAndSend(flightsQueue,email);

            ResponseEntity<String> res = MyService.checkAdmin("http://localhost:8080/is_admin", token);
            if (res.getStatusCode() == HttpStatus.ACCEPTED) {
                long id = form.getId();
                try {
                    Boolean b = flightRepo.deleteById(id);
                    System.out.println(b);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new ResponseEntity<String>("Fail, plane not deleted", HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<String>("Success, flight deleted", HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @PostMapping("/add_plane")
    public ResponseEntity<String> addPlane(@RequestHeader(value = HEADER_STRING) String token, @RequestBody AddPlaneForm form) {
        try {

            String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();

            ResponseEntity<String> res = MyService.checkAdmin("http://localhost:8080/is_admin", token);
            if (res.getStatusCode() == HttpStatus.ACCEPTED) {
                Plane plane = new Plane(form.getName(), form.getCapacity());
                planeRepo.saveAndFlush(plane);
                return new ResponseEntity<String>("Success, plane added", HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);


        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @PostMapping("/delete_plane")
    public ResponseEntity<String> deletePlane(@RequestHeader(value = HEADER_STRING) String token, @RequestBody DeletePlaneForm form) {
        try {

            String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();

            ResponseEntity<String> res = MyService.checkAdmin("http://localhost:8080/is_admin", token);
            if (res.getStatusCode() == HttpStatus.ACCEPTED) {
                long id = form.getId();
                try {
                    Boolean b = planeRepo.deleteById(id);
                    System.out.println(b);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new ResponseEntity<String>("Fail, plane not deleted", HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<String>("Success, plane deleted", HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);


        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }
}
