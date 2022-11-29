package com.chuwa.redbook.service;

import com.chuwa.redbook.payload.PostDto;

import java.security.Principal;
import java.util.List;

public interface UserService {
    List<PostDto> getPostsByUserId(long userId);

    PostDto getPostByPostId(long userId, long postId);

    PostDto updatePost(long userId, long postId, PostDto postDto, Principal principal);

    void deletePost(long userId, long postId, Principal principal);
}
