package sk2.Flight_servis.controllers;

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
import java.util.List;

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
    public ResponseEntity<List<Flight>> getFlightList() {
        try {
            List<Flight> flights = flightRepo.findAll();
            return new ResponseEntity<List<Flight>>(flights, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Flight>>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/search_flight")
    public ResponseEntity<List<Flight>> searchFlight(@RequestBody SearchFlightForm form) {
        try {
            List<Flight> flights = null;
            if (form.getPlane() != null)
                flights = flightRepo.findAllByPlane(form.getPlane());
            if (form.getPlane() != null)
                flights = flightRepo.findAllByStartDestination(form.getStartDestination());
            if (form.getPlane() != null)
                flights = flightRepo.findAllByFinishDestination(form.getFinishDestination());
            if (form.getPlane() != null)
                flights = flightRepo.findAllByLength(form.getLength());
            if (form.getPlane() != null)
                flights = flightRepo.findAllByPrice(form.getPrice());
            return new ResponseEntity<List<Flight>>(flights, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Flight>>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add_flight")
    public ResponseEntity<String> addFlight(@RequestBody AddFlightForm form) {
        try {

            Flight flight = new Flight(form.getPlane(),form.getStartDestination(),
                    form.getFinishDestination(),form.getLength(), form.getPrice());

            flightRepo.saveAndFlush(flight);
            return new ResponseEntity<String>("Success, flight added", HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete_flight")
    public ResponseEntity<String> deleteFlight(@RequestBody DeleteFlightForm form) {
        try {

            long id = form.getId();
            flightRepo.deleteById(id);
            return new ResponseEntity<String>("Success, flight deleted", HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add_plane")
    public ResponseEntity<String> addPlane(@RequestBody AddPlaneForm form) {
        try {

            Plane plane = new Plane(form.getName(), form.getCapacity());

            planeRepo.saveAndFlush(plane);
            return new ResponseEntity<String>("Success, plane added", HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete_plane")
    public ResponseEntity<String> deletePlane(@RequestBody DeletePlaneForm form) {
        try {

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
