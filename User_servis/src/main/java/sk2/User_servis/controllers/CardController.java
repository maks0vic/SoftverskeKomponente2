package sk2.User_servis.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk2.User_servis.entities.CreditCard;
import sk2.User_servis.entities.User;
import sk2.User_servis.forms.CreditCardForm;
import sk2.User_servis.repository.CreditCardRepository;
import sk2.User_servis.repository.UserRepository;

import java.util.Collections;
import java.util.List;

import static sk2.User_servis.security.SecurityConstants.*;

@RestController
@RequestMapping("")
public class CardController {

    private CreditCardRepository creditCardRepo;
    private UserRepository userRepo;

    @Autowired
    public CardController(CreditCardRepository creditCardRepository, UserRepository userRepo) {
        this.creditCardRepo = creditCardRepository;
        this.userRepo = userRepo;
    }

    @PostMapping("/add_card")
    public ResponseEntity<String> addCard(@RequestHeader(value = HEADER_STRING) String token, @RequestBody CreditCardForm creditCardForm) {
        try {
            String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();

            User user = userRepo.findByEmail(email);

            CreditCard creditCard = new CreditCard(user.getId(), creditCardForm.getFirstName(), creditCardForm.getLastName(),
                    creditCardForm.getCardNumber(), creditCardForm.getSecurityNumber());

            creditCardRepo.saveAndFlush(creditCard);

            return new ResponseEntity<String>("Success", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getCards")
    public ResponseEntity<List<CreditCard>> getCards(@RequestHeader(value = HEADER_STRING) String token){
        try {
            // izvlacimo iz tokena subject koj je postavljen da bude email
            String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();

            User user = userRepo.findByEmail(email);
            long userId = user.getId();

            List<CreditCard> cards = creditCardRepo.findByUserId(userId);

            if (cards == null)
                return new ResponseEntity<List<CreditCard>>(Collections.emptyList(), HttpStatus.NO_CONTENT);

            return new ResponseEntity<List<CreditCard>>(cards, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
