package at.davl.movie.auth.services;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {

    // private static final String SECRET_KEY = "E86367EA1C1395326A7A772459492"; // 0880
    private static final String SECRET_KEY = "iqduy8q94s2oB1FvFepVbu0EcKm0SLutC14wjBrExeXBEtZSmS/WZiwEn6eDn96C";

    public String extractUsername(String token) {
        /**
        * It sends to func extractClaim() with parameters: token and claims.
        * And it should parse all claims and find username
        * Subject can be a username or in our case an email.
         */
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * extract single claim that we parse
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    /**
     *  Extract information from JWT
     */
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                // important not JWT -> JWS
                // https://stackoverflow.com/questions/61016123/io-jsonwebtoken-unsupportedjwtexception-signed-claims-jwss-are-not-supported
                //.parseClaimsJwt(token)
                .parseClaimsJws(token)
                .getBody();

        /*
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

         */
        /*
        // Depricated
        return Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
         */
    }

    /**
     *  generate Token
     */
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            // extraClaims is a details like isAccountNonLocked() and so on
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                // .compact -> generate and return the token
                // convert all info to String
                .compact();
        /*
        return Jwts
                .builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                // create and return jwt token
                .signWith(getSignInKey()) //
                .compact();
         */
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        // it is an algorithm that we use to decode key
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // check if token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // get expiration date from token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}

