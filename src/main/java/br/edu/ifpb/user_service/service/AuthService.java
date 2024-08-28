package br.edu.ifpb.user_service.service;

import br.edu.ifpb.user_service.model.LikeDto;
import br.edu.ifpb.user_service.model.User;
import br.edu.ifpb.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> addUser(User userData) {
        Optional<User> existingUser = userRepository.findByEmail(userData.getEmail());
        User user;

        if (existingUser.isPresent()) {
            user = existingUser.get();
            user.setName(userData.getName());
            user.setProfilePictureUrl(userData.getProfilePictureUrl());
        } else {
            user = new User();
            user.setGoogleId(userData.getGoogleId());
            user.setEmail(userData.getEmail());
            user.setName(userData.getName());
            user.setEmailVerified(userData.isEmailVerified());
            user.setProfilePictureUrl(userData.getProfilePictureUrl());
            user.setLikedComments(new HashMap<>());
            user.setLikedPosts(new HashMap<>());
        }

        User savedUser = userRepository.save(user);

        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }
}
