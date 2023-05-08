package uz.test.abitur.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import uz.test.abitur.dtos.auth.TokenResponse;
import uz.test.abitur.enums.TokenType;

import java.security.Key;
import java.util.Date;

import static uz.test.abitur.enums.TokenType.*;

@Service
public class JwtUtils {

    @Value("${jwt.access.token.secret.key}")
    private String accessTokenSecretKey;
    @Value("${jwt.access.token.expiry}")
    private long expiryInMinutes;

    @Value("${jwt.refresh.token.expiry}")
    private long refreshTokenExpiry;
    @Value("${jwt.refresh.token.secret.key}")
    public String refreshTokenSecretKey;

    public TokenResponse generateToken(@NonNull String username) {
        TokenResponse tokenResponse = new TokenResponse();
        generateAccessToken(username, tokenResponse);
        generateRefreshToken(username, tokenResponse);
        return tokenResponse;
    }

    public TokenResponse generateRefreshToken(@NonNull String username, @NonNull TokenResponse tokenResponse) {
        tokenResponse.setRefreshTokenExpiry(new Date(System.currentTimeMillis() + refreshTokenExpiry));
        String refreshToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(tokenResponse.getRefreshTokenExpiry())
                .signWith(signKey(REFRESH), SignatureAlgorithm.HS256)
                .compact();
        tokenResponse.setRefreshToken(refreshToken);
        return tokenResponse;
    }

    public TokenResponse generateAccessToken(@NonNull String username, @NonNull TokenResponse tokenResponse) {
        tokenResponse.setAccessTokenExpiry(new Date(System.currentTimeMillis() + expiryInMinutes));
        String accessToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(tokenResponse.getAccessTokenExpiry())
                .signWith(signKey(ACCESS), SignatureAlgorithm.HS512)
                .compact();
        tokenResponse.setAccessToken(accessToken);
        return tokenResponse;
    }

    public boolean isTokenValid(@NonNull String token, TokenType tokenType) {
        try {
            Date expiration = getExpiry(token,tokenType);
            return expiration.after(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUsername(@NonNull String token, TokenType tokenType) {
        Claims claims = getClaims(token, tokenType);
        return claims.getSubject();
    }

    private Claims getClaims(String token, TokenType tokenType) {
        return Jwts.parserBuilder()
                .setSigningKey(signKey(tokenType))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key signKey(TokenType tokenType) {
        byte[] bytes = Decoders.BASE64.decode(tokenType.equals(ACCESS) ? accessTokenSecretKey : refreshTokenSecretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    public Date getExpiry(String token, TokenType tokenType) {
        Claims claims = getClaims(token, tokenType);
        return claims.getExpiration();
    }
}
