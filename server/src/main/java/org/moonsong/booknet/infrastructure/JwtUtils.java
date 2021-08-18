package org.moonsong.booknet.infrastructure;

import io.jsonwebtoken.*;
import org.moonsong.booknet.config.YamlPropertySourceFactory;
import org.moonsong.booknet.exception.InvalidTokenException;
import org.moonsong.booknet.exception.TokenExpiredException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
@PropertySource(value = "classpath:/jwt.yml", factory = YamlPropertySourceFactory.class)
public class JwtUtils {
    private final String secretKey;
    private final long validityInMilliseconds;
    private final JwtParser jwtParser;

    public JwtUtils(
            @Value("${jwt.secret-key}")
            final String secretKey,
            @Value("${jwt.expiration-milli-seconds}")
            final long validityInMilliseconds) {
        this.secretKey = secretKey; // todo 운영환경의 설정 값은 서브모듈로 감추기
        this.validityInMilliseconds = validityInMilliseconds;
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
