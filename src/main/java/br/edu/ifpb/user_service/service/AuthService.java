package br.edu.ifpb.user_service.service;

import br.edu.ifpb.user_service.model.LikeDto;
import br.edu.ifpb.user_service.model.User;
import br.edu.ifpb.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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

        Map<Object, Object> hashMap = redisTemplate.opsForHash().entries("user:"+ savedUser.getId());
        if (hashMap.isEmpty()){
            hashMap.put("id", savedUser.getId());
            hashMap.put("name", savedUser.getName());
            hashMap.put("email", savedUser.getEmail());
            hashMap.put("profilePicture", savedUser.getProfilePictureUrl());
            redisTemplate.opsForHash().putAll("user:"+savedUser.getId(),hashMap);
        }

        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }
}
