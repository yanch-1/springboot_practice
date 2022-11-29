package com.chuwa.redbook.service;

import com.chuwa.redbook.payload.CommentDto;
import com.chuwa.redbook.payload.CreateCommentDto;

import java.security.Principal;
import java.util.List;

public interface CommentService {
    CreateCommentDto createComment(long postId, CreateCommentDto createCommentDto, Principal principal);
    List<CommentDto> getCommentByPostId(long postId);
    List<CommentDto> getCommentsByUserId(long userId);
    CommentDto getCommentById(Long postId, Long commentId);
    CreateCommentDto updateComment(Long postId, Long commentId, CreateCommentDto createCommentDto, Principal principal);

    void deleteComment(Long postId, Long commentId, Principal principal);
}
