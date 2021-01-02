package sk2.User_servis.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sk2.User_servis.entities.User;
import sk2.User_servis.forms.RegistrationForm;
import sk2.User_servis.repository.UserRepository;
import static sk2.User_servis.security.SecurityConstants.*;

@RestController
@RequestMapping("")
public class Controller {

    private BCryptPasswordEncoder encoder;
    private UserRepository userRepo;

    @Autowired
    public Controller(BCryptPasswordEncoder encoder, UserRepository userRepo) {
        this.encoder = encoder;
        this.userRepo = userRepo;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerPost(@RequestBody RegistrationForm registrationForm) {
        try {
            User user = new User (registrationForm.getFirstName(), registrationForm.getLastName(), registrationForm.getEmail(),
                    encoder.encode(registrationForm.getPassword()));

            userRepo.saveAndFlush(user);
            return new ResponseEntity<String>("Success", HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/whoAmI")
    public ResponseEntity<String> whoAmI(@RequestHeader(value = HEADER_STRING) String token) {
        try {
            // izvlacimo iz tokena subject koj je postavljen da bude email
            String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();

            User user = userRepo.findByEmail(email);

            return new ResponseEntity<>(user.getFirstName() + " " + user.getLastName(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
