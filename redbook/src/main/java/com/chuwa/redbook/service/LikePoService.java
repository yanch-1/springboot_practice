package com.chuwa.redbook.service;

import com.chuwa.redbook.payload.LikeDto;
import com.chuwa.redbook.payload.LikeResponseDto;

import java.security.Principal;

public interface LikePoService {
    LikeDto createLike(LikeDto likeDto, Principal principal);

    LikeResponseDto getLikesByPostId(long postId);

    void unlikePost(LikeDto likeDto, Principal principal);
}
