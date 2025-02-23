package com.tsg.application.rest.service;

import com.tsg.application.rest.exception.ResourceNotFoundException;
import com.tsg.application.rest.model.Post;
import com.tsg.application.rest.model.User;
import com.tsg.application.rest.repository.PostRepository;
import com.tsg.application.rest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
    
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Post> getPostsByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Post getPostById(Long id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado con id: " + id));
    }

    @Transactional
    public Post createPost(Post post, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + userId));
        
        post.setUser(user);
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(Long id, Post postDetails) {
        Post post = getPostById(id);
        
        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        
        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post no encontrado con id: " + id);
        }
        postRepository.deleteById(id);
    }
}