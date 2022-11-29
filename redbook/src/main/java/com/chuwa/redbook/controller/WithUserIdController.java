package com.chuwa.redbook.controller;

import com.chuwa.redbook.payload.CommentDto;
import com.chuwa.redbook.payload.PostDto;
import com.chuwa.redbook.service.CommentService;
import com.chuwa.redbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class WithUserIdController {
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;


    @GetMapping("/{userId}/posts")
    public List<PostDto> getPostsByUserId(@PathVariable(value = "userId") Long userId) {
        return userService.getPostsByUserId(userId);
    }

    @GetMapping("/{userId}/comments")
    public List<CommentDto> getCommentsByUserId(@PathVariable(value = "userId") Long userId) {
        return commentService.getCommentsByUserId(userId);
    }

    @GetMapping("/{userId}/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(value = "userId") Long userId,
                                               @PathVariable(value = "postId") Long postId) {

        return new ResponseEntity<>(userService.getPostByPostId(userId, postId), HttpStatus.OK);
    }

    @PutMapping("/{userId}/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable(value = "userId") Long userId,
                                              @PathVariable(value = "postId") Long postId,
                                              @Valid @RequestBody PostDto postDto,
                                              Principal principal) {
        PostDto updatePost = userService.updatePost(userId, postId, postDto, principal);
        return new ResponseEntity<>(updatePost, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable(value = "userId") Long userId,
                                             @PathVariable(value = "postId") Long postId,
                                             Principal principal) {
        userService.deletePost(userId, postId, principal);
        return new ResponseEntity<>("Post deleted Successfully", HttpStatus.OK);
    }
}
