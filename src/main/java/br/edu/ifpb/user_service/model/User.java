package br.edu.ifpb.user_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String googleId;
    private String name;
    private String email;
    private boolean emailVerified;
    private String profilePictureUrl;
    private HashMap<String, String> likedPosts;
    private HashMap<String, String> likedComments;

    public User() {
    }

    public String getId() {
        return id;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public HashMap<String, String> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(HashMap<String, String> likedPosts) {
        this.likedPosts = likedPosts;
    }

    public void likePost(String postId, String like) {
        this.likedPosts.put(postId, like);
    }

    public void rmLikedPost(String postId) {
        this.likedPosts.remove(postId);
    }

    public HashMap<String, String> getLikedComments() {
        return likedComments;
    }

    public void setLikedComments(HashMap<String, String> likedComments) {
        this.likedComments = likedComments;
    }

    public void likeComment(String commentId, String like) {
        this.likedComments.put(commentId, like);
    }

    public void rmLikedComment(String commentId) {
        this.likedComments.remove(commentId);
    }
}
