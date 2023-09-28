package ee.fakeplastictrees.blog.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import ee.fakeplastictrees.blog.core.exceptions.ResourceAlreadyExistsException;
import ee.fakeplastictrees.blog.user.controller.request.PostUsersRequest;
import ee.fakeplastictrees.blog.user.model.TokenDto;
import ee.fakeplastictrees.blog.user.model.User;
import ee.fakeplastictrees.blog.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static ee.fakeplastictrees.blog.core.Utils.builders;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${token.secret}")
    private String tokenSecret;

    @Value("${token.lifespan}")
    private Long tokenLifespan;

    @Value("${token.issuer}")
    private String tokenIssuer;

    public TokenDto createUser(PostUsersRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ResourceAlreadyExistsException(User.class, request.getUsername());
        }

        userRepository.save(builders().user().user()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build());

        return createToken(request.getUsername(), request.getPassword());
    }

    public TokenDto createToken(String username, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(token);

        return generateToken(username);
    }

    public Optional<DecodedJWT> decodeToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(tokenSecret);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(tokenIssuer)
                .build();

        DecodedJWT decodedToken = verifier.verify(token);
        if (decodedToken.getClaim("expirationDate").asLong() < System.currentTimeMillis()) {
            return Optional.empty();
        }

        return Optional.of(decodedToken);
    }

    private TokenDto generateToken(String username) {
        long expirationDate = System.currentTimeMillis() + tokenLifespan;

        String token = JWT.create()
                .withIssuer(tokenIssuer)
                .withClaim("username", username)
                .withClaim("expirationDate", expirationDate)
                .sign(Algorithm.HMAC256(tokenSecret));

        return builders().user().tokenDto()
                .token(token)
                .expirationDate(expirationDate)
                .build();
    }
}
