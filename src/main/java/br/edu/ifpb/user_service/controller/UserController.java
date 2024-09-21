package br.edu.ifpb.user_service.controller;

import br.edu.ifpb.user_service.model.LikeDto;
import br.edu.ifpb.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@RequestParam String id) {
        try {
            return userService.getUser(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/post/{id}")
    public ResponseEntity<?> likePost (@PathVariable String id, @RequestBody LikeDto likeDto) {
        try {
            return userService.likePost(id, likeDto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/comment/{id}")
    public ResponseEntity<?> likeComment (@PathVariable String id, @RequestBody LikeDto likeDto) {
        try {
            return userService.likeComment(id, likeDto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
