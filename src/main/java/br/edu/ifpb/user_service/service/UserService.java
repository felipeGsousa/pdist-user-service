package br.edu.ifpb.user_service.service;

import br.edu.ifpb.user_service.model.LikeDto;
import br.edu.ifpb.user_service.model.User;
import br.edu.ifpb.user_service.producers.LikeProducer;
import br.edu.ifpb.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeProducer likeProducer;

    public ResponseEntity<?> getUser(String id) {
        Optional<User> user = userRepository.findById("66cf9ec95717ee0652b677d4");

        if (user.isPresent()) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> likePost(String id, LikeDto likeDto){
        User user = userRepository.findById(id).get();
        String type = "";
        if (!user.getLikedPosts().isEmpty()) {
            type = user.getLikedPosts().get(likeDto.getId());
        }
        HashMap<String, Object> response = new HashMap<>();
        response.put("id", likeDto.getId());

        if (type == null) {
            user.likePost(likeDto.getId(), likeDto.getType());
            if (likeDto.getType().equals("like")) {
                response.put("addLike", true);
                response.put("subLike", false);
                response.put("addDislike", false);
                response.put("subDislike", false);
            } else {
                response.put("addLike", false);
                response.put("subLike", false);
                response.put("addDislike", true);
                response.put("subDislike", false);
            }
        } else if (type.equals(likeDto.getType())) {
            user.rmLikedPost(likeDto.getId());
            if (likeDto.getType().equals("like")) {
                response.put("addLike", false);
                response.put("subLike", true);
                response.put("addDislike", false);
                response.put("subDislike", false);
            } else {
                response.put("addLike", false);
                response.put("subLike", false);
                response.put("addDislike", false);
                response.put("subDislike", true);
            }
        } else {
            user.likePost(likeDto.getId(), likeDto.getType());
            if (likeDto.getType().equals("like")) {
                response.put("addLike", true);
                response.put("subLike", false);
                response.put("addDislike", false);
                response.put("subDislike", true);
            } else {
                response.put("addLike", false);
                response.put("subLike", true);
                response.put("addDislike", true);
                response.put("subDislike", false);
            }
        }
        Object likeResponse = likeProducer.likePost(response);

        userRepository.save(user);

        return new ResponseEntity<>(likeResponse, HttpStatus.OK);
    }

    public ResponseEntity<?> likeComment(String id, LikeDto likeDto){
        User user = userRepository.findById(id).get();
        String type = "";
        if (!user.getLikedPosts().isEmpty()) {
            type = user.getLikedPosts().get(likeDto.getId());
        }
        HashMap<String, Object> response = new HashMap<>();
        response.put("id", likeDto.getId());
        if (type == null) {
            user.likeComment(likeDto.getId(), likeDto.getType());
            if (likeDto.getType().equals("like")) {
                response.put("addLike", true);
                response.put("subLike", false);
                response.put("addDislike", false);
                response.put("subDislike", false);
            } else {
                response.put("addLike", false);
                response.put("subLike", false);
                response.put("addDislike", true);
                response.put("subDislike", false);
            }
        } else if (type.equals(likeDto.getType())) {
            if (likeDto.getType().equals("like")) {
                response.put("addLike", false);
                response.put("subLike", true);
                response.put("addDislike", false);
                response.put("subDislike", false);
            } else {
                response.put("addLike", false);
                response.put("subLike", false);
                response.put("addDislike", false);
                response.put("subDislike", true);
            }
            user.rmLikedComment(likeDto.getId());
        } else {
            if (likeDto.getType().equals("like")) {
                response.put("addLike", true);
                response.put("subLike", false);
                response.put("addDislike", false);
                response.put("subDislike", true);
            } else {
                response.put("addLike", false);
                response.put("subLike", true);
                response.put("addDislike", true);
                response.put("subDislike", false);
            }
            user.likeComment(likeDto.getId(), likeDto.getType());
        }

        Object likeResponse = likeProducer.likeComment(response);

        userRepository.save(user);

        return new ResponseEntity<>(likeResponse, HttpStatus.OK);
    }
}
