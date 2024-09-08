package br.edu.ifpb.user_service.controller;

import br.edu.ifpb.user_service.model.User;
import br.edu.ifpb.user_service.service.AuthService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

    @Autowired
    private AuthService authService;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @PostMapping(path = "/google")
    public ResponseEntity<?> authenticateUser(@RequestBody String token) {
        try {
            System.out.println(token);
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory)
                    .setAudience(Collections.singletonList(clientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                String userId = payload.getSubject();
                String email = payload.getEmail();
                boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");

                User user = new User();

                user.setGoogleId(userId);
                user.setEmail(email);
                user.setEmailVerified(emailVerified);
                user.setName(name);
                user.setProfilePictureUrl(pictureUrl);

                return authService.addUser(user);
            } else {
                return new ResponseEntity<>("Invalid Token", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Authentication error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
