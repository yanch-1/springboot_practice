package com.chuwa.redbook.dao.impl;

import com.chuwa.redbook.entity.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PostJPQLRepositoryImplTest {
    @Autowired
    private PostJPQLRepositoryImpl repository;

    private Post post = new Post(
            null,
            "title test",
            "description test",
            "content test",
            null,
            null
    );
    @Test
    void getAllPostWithJPQL() {
        List<Post> posts = repository.getAllPostWithJPQL();
        System.out.println(posts);
    }

    @Test
    void insertPost() {
        post.setTitle(post.getTitle() + LocalDateTime.now());
        Post savedPost = repository.insertPost(post);
        System.out.println(savedPost);
    }

    @Test
    void updatePost() {
        post.setId(1L);
        post.setTitle(post.getTitle() + LocalDateTime.now());
        Post savedPost = repository.insertPost(post);
        System.out.println(savedPost);
    }

    @Test
    void getPostById() {
        Post postById = repository.getPostById(1L);
        System.out.println(postById);
    }

    @Test
    void deleteById() {
        post.setTitle(post.getTitle() + LocalDateTime.now());
        Post savedPost = repository.insertPost(post);
        System.out.println(savedPost);
        Long id = savedPost.getId();
        repository.deleteById(id);
        Post postById = repository.getPostById(id);
        System.out.println(postById);
    }

    @Test
    void insertData() {
    }
}