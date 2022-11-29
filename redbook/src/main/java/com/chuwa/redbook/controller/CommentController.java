package com.chuwa.redbook.controller;

import com.chuwa.redbook.payload.CommentDto;
import com.chuwa.redbook.payload.CreateCommentDto;
import com.chuwa.redbook.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CreateCommentDto> createComment(@PathVariable(value = "postId") long id,
                                                          @Valid @RequestBody CreateCommentDto createCommentDto,
                                                          Principal principal) {
        return new ResponseEntity<>(commentService.createComment(id, createCommentDto, principal), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") Long postId){
        return commentService.getCommentByPostId(postId);
    }
    @GetMapping("/posts/{postId}/comments/count")
    public Integer getCommentsNumByPostId(@PathVariable(value = "postId") Long postId){
        return commentService.getCommentByPostId(postId).size();
    }

    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentsById(@PathVariable(value = "postId") Long postId,
                                                      @PathVariable(value = "id") Long commentId){
        CommentDto commentDto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CreateCommentDto> updateComment(@PathVariable(value = "postId") Long postId,
                                                          @PathVariable(value = "commentId") Long commentId,
                                                          @Valid @RequestBody CreateCommentDto createCommentDto,
                                                          Principal principal) {
        CreateCommentDto updateComment = commentService.updateComment(postId, commentId, createCommentDto, principal);
        return new ResponseEntity<>(updateComment, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,
                                                @PathVariable(value = "id") Long commentId,
                                                Principal principal) {
        commentService.deleteComment(postId, commentId, principal);

        return new ResponseEntity<>("Comment deleted Successfully", HttpStatus.OK);
    }

}
