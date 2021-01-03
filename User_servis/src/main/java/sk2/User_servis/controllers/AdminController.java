package sk2.User_servis.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sk2.User_servis.entities.Admin;
import sk2.User_servis.entities.CreditCard;
import sk2.User_servis.entities.User;
import sk2.User_servis.forms.CreditCardForm;
import sk2.User_servis.repository.AdminRepository;
import sk2.User_servis.repository.CreditCardRepository;
import sk2.User_servis.repository.UserRepository;

import static sk2.User_servis.security.SecurityConstants.*;
@RestController
@RequestMapping("")
public class AdminController {

    private BCryptPasswordEncoder encoder;
    private UserRepository userRepo;
    private CreditCardRepository creditCardRepo;
    private AdminRepository adminRepo;

    @Autowired
    public AdminController(BCryptPasswordEncoder encoder, UserRepository userRepo, CreditCardRepository creditCardRepo,
                      AdminRepository adminRepo) {
        this.encoder = encoder;
        this.userRepo = userRepo;
        this.creditCardRepo = creditCardRepo;
        this.adminRepo = adminRepo;
        addAdmins();
    }

    public void addAdmins() {
        Admin admin1 = new Admin(1, "gogi", encoder.encode("pass1"));
        Admin admin2 = new Admin(2, "suki", encoder.encode("pass2"));
        adminRepo.saveAndFlush(admin1);
        adminRepo.saveAndFlush(admin2);
    }

    @GetMapping("/is_admin")
    public ResponseEntity<String> isAdmin(@RequestHeader(value = HEADER_STRING) String token) {
        try {
            System.out.println("USAO U ISADMIN");
            // izvlacimo iz tokena subject koj je postavljen da bude email
            String username = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();
            //System.out.println("NASAO GOGIJA " + username);
            Admin admin = adminRepo.findByUsername(username);

            return new ResponseEntity<>(admin.getUsername(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @PostMapping("/add_flight")
//    public ResponseEntity<String> addFlight(@RequestHeader(value = HEADER_STRING) String token, @RequestBody CreditCardForm creditCardForm) {
//        try {
//            // izvlacimo iz tokena subject koj je postavljen da bude email
//            String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
//                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();
//
//            User user = userRepo.findByEmail(email);
//
//            CreditCard creditCard = new CreditCard(creditCardForm.getFirstName(), creditCardForm.getLastName(),
//                    creditCardForm.getCardNumber(), creditCardForm.getSecurityNumber());
//
//            //user.addCreditCard();
//            creditCardRepository.saveAndFlush(creditCard);
//
//            return new ResponseEntity<String>("Flight " + , HttpStatus.ACCEPTED);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PostMapping("/delete_flight")
//    public ResponseEntity<String> deleteFlight(@RequestHeader(value = HEADER_STRING) String token, @RequestBody CreditCardForm creditCardForm) {
//        try {
//            // izvlacimo iz tokena subject koj je postavljen da bude email
//            String username = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
//                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();
//
//            Admin admin = adminRepo.findByUsername(username);
//
//            String flightName =
//
//            //user.addCreditCard();
//            creditCardRepository.saveAndFlush(creditCard);
//
//            return new ResponseEntity<String>("Success", HttpStatus.ACCEPTED);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
}
