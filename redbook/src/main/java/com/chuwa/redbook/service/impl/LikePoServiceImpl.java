package com.chuwa.redbook.service.impl;

import com.chuwa.redbook.dao.LikeRepository;
import com.chuwa.redbook.dao.PostRepository;
import com.chuwa.redbook.dao.security.UserRepository;
import com.chuwa.redbook.entity.Post;
import com.chuwa.redbook.entity.PostLike;
import com.chuwa.redbook.entity.security.User;
import com.chuwa.redbook.exception.BlogAPIException;
import com.chuwa.redbook.exception.ResourceNotFoundException;
import com.chuwa.redbook.payload.LikeDto;
import com.chuwa.redbook.payload.LikeResponseDto;
import com.chuwa.redbook.service.LikePoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class LikePoServiceImpl implements LikePoService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public LikeDto createLike(LikeDto likeDto, Principal principal) {
        PostLike like = modelMapper.map(likeDto, PostLike.class);
        Long userId = like.getUser().getId();

        if(!userRepository.findById(userId).equals(userRepository.findByEmail(principal.getName()))){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Provided User ID Invalid");
        }

        Long postId = like.getPost().getId();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        like.setPost(post);
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new BlogAPIException(HttpStatus.BAD_REQUEST, "User No Found"));
        like.setUser(user);

        like.setStatus(1L);
        PostLike savedLike = likeRepository.save(like);
        return modelMapper.map(savedLike, LikeDto.class);
    }
    @Override
    public void unlikePost(LikeDto likeDto, Principal principal) {
        PostLike like = modelMapper.map(likeDto, PostLike.class);
        Long userId = like.getUser().getId();

        if(!userRepository.findById(userId).equals(userRepository.findByEmail(principal.getName()))){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Provided User ID Invalid");
        }

        Long postId = like.getPost().getId();
        if (!likeRepository.existsByPostAndUser(postId, userId))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "The Post is NOT Liked Before");


        likeRepository.deleteByPostAndUser(postId,userId);
    }
    @Override
    public LikeResponseDto getLikesByPostId(long postId) {
        LikeResponseDto likeResponseDto = new LikeResponseDto();
        long count = likeRepository.countByPost(postId);
        List<PostLike> likes = likeRepository.findByPost(postId);
        List<User> users = likeRepository.getUserByPost(postId);

        likeResponseDto.setDetails(likes);
        likeResponseDto.setTotal(count);
        likeResponseDto.setUsers(users);

        return likeResponseDto;
    }


}
