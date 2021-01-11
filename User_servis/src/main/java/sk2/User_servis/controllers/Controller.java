package sk2.User_servis.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sk2.User_servis.entities.CreditCard;
import sk2.User_servis.entities.User;
import sk2.User_servis.forms.CreditCardForm;
import sk2.User_servis.forms.RegistrationForm;
import sk2.User_servis.forms.UpdateDataForm;
import sk2.User_servis.repository.CreditCardRepository;
import sk2.User_servis.repository.UserRepository;
import static sk2.User_servis.security.SecurityConstants.*;

@RestController
@RequestMapping("")
public class Controller {

    private BCryptPasswordEncoder encoder;
    private UserRepository userRepo;
    private CreditCardRepository creditCardRepo;

    @Autowired
    public Controller(BCryptPasswordEncoder encoder, UserRepository userRepo, CreditCardRepository creditCardRepository) {
        this.encoder = encoder;
        this.userRepo = userRepo;
        this.creditCardRepo = creditCardRepository;
    }

    @GetMapping("/who_am_i")
    public ResponseEntity<String> whoAmI(@RequestHeader(value = HEADER_STRING) String token) {
        try {
            String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();

            User user = userRepo.findByEmail(email);
            if (user != null)
                return new ResponseEntity<String>(user.getFirstName() + " " + user.getLastName(), HttpStatus.ACCEPTED);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get_rank")
    public ResponseEntity<String> getRank(@RequestHeader(value = HEADER_STRING) String token) {
        try {
            // izvlacimo iz tokena subject koj je postavljen da bude email
            String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();

            User user = userRepo.findByEmail(email);

            return new ResponseEntity<String>(user.getRank().toString(), HttpStatus.FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerPost(@RequestBody RegistrationForm registrationForm) {
        try {
            User user = new User (registrationForm.getFirstName(), registrationForm.getLastName(), registrationForm.getEmail(),
                    encoder.encode(registrationForm.getPassword()), registrationForm.getPassportNumber());

            userRepo.saveAndFlush(user);
            return new ResponseEntity<String>("Success", HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update_data")
    public ResponseEntity<String> updateFirstName(@RequestHeader(value = HEADER_STRING) String token, @RequestBody UpdateDataForm updateDataForm) {
        try {
            String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();

            User user = userRepo.findByEmail(email);

            if (updateDataForm.getEmail() != null && updateDataForm.getEmail() != "") {
                user.setEmail(updateDataForm.getEmail());
                //send email
            }

            if (updateDataForm.getFirstName() != null && updateDataForm.getFirstName() != "")
                user.setFirstName(updateDataForm.getFirstName());

            if (updateDataForm.getLastName() != null && updateDataForm.getLastName() != "")
                user.setLastName(updateDataForm.getLastName());

            if (updateDataForm.getPassword() != null && updateDataForm.getPassword() != "")
                user.setPassword(encoder.encode(updateDataForm.getPassword()));

            if (updateDataForm.getPassportNumber() != null && updateDataForm.getPassportNumber() != "")
                user.setPassportNumber(updateDataForm.getPassportNumber());

            userRepo.saveAndFlush(user);

            return new ResponseEntity<String>("Success updated", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
