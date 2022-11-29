package com.chuwa.redbook.controller;

import com.chuwa.redbook.payload.LikeDto;
import com.chuwa.redbook.payload.LikeResponseDto;
import com.chuwa.redbook.service.LikePoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/v1/posts")
public class LikeController {
    @Autowired
    private LikePoService likePoService;

    @PostMapping("/like")
    public ResponseEntity<LikeDto> createLike(@Valid @RequestBody LikeDto likeDto,
                                              Principal principal) {
        return new ResponseEntity<>(likePoService.createLike(likeDto, principal), HttpStatus.CREATED);
    }


    @GetMapping("/likes/{postId}")
    public LikeResponseDto getLikesByPostId(@PathVariable(value = "postId") Long postId) {
        return likePoService.getLikesByPostId(postId);
    }

    @PutMapping("/unlike")
    public ResponseEntity<String> unLike(@Valid @RequestBody LikeDto likeDto,
                                          Principal principal) {
        likePoService.unlikePost(likeDto, principal);
        return new ResponseEntity<>("Unlike the Post Successfully", HttpStatus.OK);
    }
}
