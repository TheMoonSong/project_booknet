package org.moonsong.booknet.infrastructure;

import io.jsonwebtoken.*;
import org.moonsong.booknet.exception.InvalidTokenException;
import org.moonsong.booknet.exception.TokenExpiredException;

import java.util.Date;
import java.util.Map;

public class JwtUtils {
    private final String secretKey;
    private final long validityInMilliseconds;
    private final JwtParser jwtParser;

    public JwtUtils() {
        this.secretKey = "Temporal Secret Key"; // todo 서브모듈을 통해 설정 파일을 감추고 사인키를 주입받기
        this.validityInMilliseconds = 86400000; // todo 서브모듈을 통해 설정 파일을 감추고 유효 시간을 주입받기
        this.jwtParser = Jwts.parser().setSigningKey(secretKey);
    }

    public String createToken(Map<String, Object> payload) {
        Claims claims = Jwts.claims(payload);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public void validateToken(String token) {
        try {
            jwtParser.parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException();
        } catch (JwtException e) {
            throw new InvalidTokenException();
        }
    }

    public static PayloadBuilder payloadBuilder() {
        return new PayloadBuilder();
    }

    public static class PayloadBuilder {
        private final Claims claims;

        private PayloadBuilder() {
            this.claims = Jwts.claims();
        }

        public PayloadBuilder setSubject(String subject) {
            claims.setSubject(subject);
            return this;
        }

        public Map<String, Object> build() {
            return claims;
        }
    }
}
