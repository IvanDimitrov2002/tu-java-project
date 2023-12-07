package tu.social.project.component.jwt;

import org.springframework.stereotype.Component;
import tu.social.project.entity.UserEntity;

import java.util.HashMap;

@Component
public class UserJwtTokenGenerator {
    private final JwtGenerator jwtGenerator;

    public UserJwtTokenGenerator(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    public String generateToken(UserEntity userEntity) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("email", userEntity.getEmail());
        claims.put("username", userEntity.getUsername());
        return this.jwtGenerator.generateToken(userEntity.getId(), claims);
    }

    public String getUserIdFromToken(String token) {
        return this.jwtGenerator.verifyToken(token).getSubject();
    }
}
