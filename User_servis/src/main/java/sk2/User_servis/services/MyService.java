package sk2.User_servis.services;

import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static sk2.User_servis.security.SecurityConstants.*;
import static sk2.User_servis.security.SecurityConstants.TOKEN_PREFIX;

@Service
public  class MyService {

    @Autowired
    EmailSenderService ess;

    public static String  resendJWT (String email) {

        String token = JWT.create().withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        return token;

    }

    public void sendEmail (String email) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(email);
        smm.setSubject("Sk2 mail reg.");
        smm.setText("You are registered. Thank you");
        ess.sendEmail(smm);
    }

    public void flightDeleteMail (String email, long flightId) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(email);
        smm.setSubject("Sk2 mail reg.");
        smm.setText("Your flight is canceled! FlightId: "+ flightId);
        ess.sendEmail(smm);
    }

    public void flightDeleteHandle (String email, long flightId) {
        flightDeleteMail(email, flightId);
        //oduzimamo milje

    }
}
