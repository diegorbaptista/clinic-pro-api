package com.clinicpro.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.clinicpro.api.domain.user.User;
import com.clinicpro.api.infra.security.dto.JSONWebTokenDTO;
import com.clinicpro.api.infra.security.error.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalUnit;
import java.util.Date;

@Service
public class JSONWebTokenService {

    @Value("${app.config.security.jwt.secret}")
    private String jwtKeySecret;

    @Value("${app.config.security.jwt.issuer}")
    private String jwtIssuer;

    public JSONWebTokenDTO createToken(User user) throws JWTCreationException {
        Algorithm algorithm = Algorithm.HMAC256(jwtKeySecret);
        return new JSONWebTokenDTO(JWT.create()
                .withIssuer(jwtIssuer)
                .withSubject(user.getUsername())
                .withClaim("userID", user.getId())
                .withExpiresAt(getExpiration())
                .sign(algorithm));
    }

    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtKeySecret);
            DecodedJWT jwt = JWT.require(algorithm)
                    .withIssuer(jwtIssuer)
                    .build()
                    .verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException jwtVerificationException) {
            throw new UnauthorizedException();
        }
    }

    private Instant getExpiration() {
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(OffsetDateTime.now().getOffset());
    }
}
