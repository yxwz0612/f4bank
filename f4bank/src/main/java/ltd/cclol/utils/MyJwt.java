package ltd.cclol.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Component
public class MyJwt {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.duration}")
    private long duration;

    public String createToken(String username){
        Date date = new Date();
        date.setTime(date.getTime()+duration);
        String token = JWT.create()
                .withClaim("username", username)
                .withExpiresAt(date)
                .sign(Algorithm.HMAC256(secret));
        return token;
    }
    public boolean checkTokenFromToken(String token){
        try{
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            verifier.verify(token);
        }catch (JWTVerificationException e){
            return false;
        }
        return true;
    }
    public boolean checkTokenFromHeader(Map httpHeaders){
        try{
            String token = httpHeaders.get("token").toString();
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            verifier.verify(token);
        }catch (JWTVerificationException e){
            return false;
        }
        return true;
    }

    public String getUserNameFromRequest(HttpServletRequest request){
        String token = request.getHeader("token");
        if (token != null){
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            String username = verifier.verify(token).getClaim("username").toString();
            return username;
        }
        return null;
    }

    public String getUserNameFromToken(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        String username = verifier.verify(token).getClaim("username").toString();
        return username;
    }

    public void printInfoFromToken(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        Map<String, Claim> claims = verifier.verify(token).getClaims();
        claims.forEach((s, claim) -> {
            System.out.println(s+":"+claim.toString());
        });
    }
}
