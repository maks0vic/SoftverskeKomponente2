package sk2.User_servis.services;

import com.auth0.jwt.JWT;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static sk2.User_servis.security.SecurityConstants.*;
import static sk2.User_servis.security.SecurityConstants.TOKEN_PREFIX;

public  class MyService {

    public static String  resendJWT (String email) {

        String token = JWT.create().withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        return token;

    }
}
