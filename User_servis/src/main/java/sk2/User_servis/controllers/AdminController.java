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
        Admin admin1 = new Admin("gogi", encoder.encode("pass1"));
        Admin admin2 = new Admin("suki", encoder.encode("pass2"));
        adminRepo.saveAndFlush(admin1);
        adminRepo.saveAndFlush(admin2);
    }

    @GetMapping("/is_admin")
    public ResponseEntity<String> isAdmin(@RequestHeader(value = HEADER_STRING) String token) {
        try {
            String username = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();

            Admin admin = adminRepo.findByUsername(username);
            if (admin != null)
                return new ResponseEntity<String>(admin.getUsername(), HttpStatus.ACCEPTED);
            else
                return  new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
