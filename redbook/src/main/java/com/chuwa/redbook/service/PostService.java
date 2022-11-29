package com.chuwa.redbook.service;

import com.chuwa.redbook.payload.PostDto;
import com.chuwa.redbook.payload.PostResponse;

import java.security.Principal;
import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, Principal principal);
//    List<PostDto> getAllPost();
    PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);
//    PostDto getPostById(long id);
//    PostDto updatePost(PostDto postDto, long id);
//    void deletePostById(long id);
}
