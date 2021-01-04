package sk2.Flight_servis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk2.Flight_servis.forms.*;
import sk2.Flight_servis.repository.FlightRepository;
import sk2.Flight_servis.repository.PlaneRepository;

@RestController
@RequestMapping("")
public class Controller {

    private FlightRepository flightRepo;
    private PlaneRepository planeRepo;

    @Autowired
    public Controller(FlightRepository flightRepo, PlaneRepository planeRepo) {
        this.flightRepo = flightRepo;
        this.planeRepo = planeRepo;
    }

    @GetMapping("/flight_list")
    public ResponseEntity<String> registerPost() {
        try {
            return new ResponseEntity<String>("Success", HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/search_flight")
    public ResponseEntity<String> registerPost(@RequestBody SearchFlightForm form) {
        try {
            return new ResponseEntity<String>("Success", HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add_flight")
    public ResponseEntity<String> addFlight(@RequestBody AddFlightForm form) {
        try {
            return new ResponseEntity<String>("Success", HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete_flight")
    public ResponseEntity<String> deleteFlight(@RequestBody DeleteFlightForm form) {
        try {
            return new ResponseEntity<String>("Success", HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add_plane")
    public ResponseEntity<String> addPlane(@RequestBody AddPlaneForm form) {
        try {
            return new ResponseEntity<String>("Success", HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete_plane")
    public ResponseEntity<String> deletePlane(@RequestBody DeletePlaneForm form) {
        try {
            return new ResponseEntity<String>("Success", HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }
    


}
