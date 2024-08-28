package br.edu.ifpb.user_service.service;

import br.edu.ifpb.user_service.model.LikeDto;
import br.edu.ifpb.user_service.model.User;
import br.edu.ifpb.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> likePost(String id, LikeDto likeDto){
        User user = userRepository.findById(id).get();
        String type = user.getLikedPosts().get(likeDto.getId());

        if (type == null) {
            user.likePost(likeDto.getId(), likeDto.getType());
        } else if (type.equals(likeDto.getType())) {
            user.rmLikedPost(likeDto.getId());
        } else {
            user.likePost(likeDto.getId(), likeDto.getType());
        }

        User savedUser = userRepository.save(user);

        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    public ResponseEntity<?> likeComment(String id, LikeDto likeDto){
        User user = userRepository.findByEmail(id).get();
        String type = user.getLikedComments().get(likeDto.getId());

        if (type == null) {
            user.likeComment(likeDto.getId(), likeDto.getType());
        } else if (type.equals(likeDto.getType())) {
            user.rmLikedComment(likeDto.getId());
        } else {
            user.likeComment(likeDto.getId(), likeDto.getType());
        }

        User savedUser = userRepository.save(user);

        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

}
