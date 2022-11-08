package com.chuwa.redbook;

import com.chuwa.redbook.dao.CommentRepository;
import com.chuwa.redbook.dao.PostRepository;
import com.chuwa.redbook.entity.Comment;
import com.chuwa.redbook.entity.Post;
import com.chuwa.redbook.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



import java.util.ArrayList;
import java.util.List;

/**
 * Reference: https://www.baeldung.com/jpa-cascade-types
 *
 */
@SpringBootTest
class CascadeTest {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;


    @Test
    public void persistTest(){
        Post post = new Post();
        post.setTitle("cas-add-p2");
        post.setContent("this is cas-add content");
        post.setDescription("this is cas-add description");

        Comment comment1 = new Comment();
        comment1.setName("p2-c1");
        comment1.setEmail("co01@asd.com");
        comment1.setBody("c01 body is here");
        comment1.setPost(post);

        Comment comment2 = new Comment();
        comment2.setName("p2-c2");
        comment2.setEmail("co02@asd.com");
        comment2.setBody("c02 body is here");
        comment2.setPost(post);

        Comment comment3 = new Comment();
        comment3.setName("p2-c3");
        comment3.setEmail("co03@asd.com");
        comment3.setBody("c03 body is here");
        comment3.setPost(post);

        List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);
        comments.add(comment3);
        post.setCommentList(comments);

        /**
         * persist test
         *
         * case1: post0, comment0, t1 -> post written only
         * case2: post0, comment0, t2 -> none
         * case3: post1, comment0, t1 -> all added
         * case4: post1, comment0, t2 -> no
         * case5: post0, comment1, t1 -> post yes, comment no
         * case6: post0, comment1, t2 -> all added
         * case7: post1, comment1, t1/t2 -> all added
         */

        //t1
        postRepository.save(post);

        //t2
        //postRepository.save(post);
        //commentRepository.saveAll(comments);

    }

    @Test
    public void deleteTest(){
        /**
         * (cannot delete post before comments because fk constraint, even no cascade.REMOVE on both side)
         * delete comments but post,
         */

        //add data to delete
        Post post = new Post();
        post.setTitle("cas-add-p3");
        post.setContent("this is cas-add content");
        post.setDescription("this is cas-add description");

        Comment comment1 = new Comment();
        comment1.setName("p3-c1");
        comment1.setEmail("co01@asd.com");
        comment1.setBody("c01 body is here");
        comment1.setPost(post);

        Comment comment2 = new Comment();
        comment2.setName("p3-c2");
        comment2.setEmail("co02@asd.com");
        comment2.setBody("c02 body is here");
        comment2.setPost(post);

        List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);
        post.setCommentList(comments);

        postRepository.save(post);
        commentRepository.saveAll(comments);


        /**
         * delete test
         *
         * case1: post0, comment0, t1 -> fail
         * case2: post0, comment0, t2 -> all deleted
         * case3: post1, comment0, t1 -> all deleted
         * case4: post1, comment0, t2 -> comments gone, post left
         * case5: post0, comment1, t1 -> fail
         * case6: post0, comment1, t2 -> all deleted
         * case7: post1, comment1, t1/t2 -> all deleted
         */
        //postRepository.delete(postRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException("Post")));

        //t1
//        postRepository.delete(post);
        //t2
        commentRepository.deleteAll(comments);


        //TODO
        //Assert post-test and post-test both have value
    }

    @Test
    public void orphanTest(){
        Post post = new Post();
        post.setTitle("cas-add-p3");
        post.setContent("this is cas-add content");
        post.setDescription("this is cas-add description");

        Comment comment1 = new Comment();
        comment1.setName("p3-c1");
        comment1.setEmail("co01@asd.com");
        comment1.setBody("c01 body is here");
        comment1.setPost(post);

        Comment comment2 = new Comment();
        comment2.setName("p3-c2");
        comment2.setEmail("co02@asd.com");
        comment2.setBody("c02 body is here");
        comment2.setPost(post);

        List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);
        post.setCommentList(comments);

        postRepository.save(post);
        //commentRepository.saveAll(comments);

        /**
         * only in 1 to 1 or 1 to many
         * true: data in table is null
         * false: data still here
         */

        //post.setTitle("cas-add-updated");
        //comments.get(0).setName("c1-updated");
        //comments.get(1).setName("c2-updated");

        comments.remove(0);
        postRepository.save(post);

    }

    @Test
    public void updateTest(){
        Post post = new Post();
        post.setTitle("cas-add-p3");
        post.setContent("this is cas-add content");
        post.setDescription("this is cas-add description");

        Comment comment1 = new Comment();
        comment1.setName("p3-c1");
        comment1.setEmail("co01@asd.com");
        comment1.setBody("c01 body is here");
        comment1.setPost(post);

        Comment comment2 = new Comment();
        comment2.setName("p3-c2");
        comment2.setEmail("co02@asd.com");
        comment2.setBody("c02 body is here");
        comment2.setPost(post);

        List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);
        post.setCommentList(comments);

        postRepository.save(post);
        commentRepository.saveAll(comments);

        post.setTitle("cas-add-updated");
        comments.get(0).setName("c1-updated");
        comments.get(1).setName("c2-updated");

        /**
         * merge
         * case1: p, t1 -> update
         * case2: p, t2 -> post no, comments yes
         * case3: c, t1 -> update
         * case4: c, t2 -> post no, com yes
         * case5: pc, t12 -> update
         * case6: null, no cascade
         */

        //t1
        postRepository.save(post);
        //t2
        //commentRepository.saveAll(comments);
    }

    @Test
    void refreshTest(){
        Post post = new Post();
        post.setTitle("cas-add-p3");
        post.setContent("this is cas-add content");
        post.setDescription("this is cas-add description");

        Comment comment1 = new Comment();
        comment1.setName("p3-c1");
        comment1.setEmail("co01@asd.com");
        comment1.setBody("c01 body is here");
        comment1.setPost(post);

        Comment comment2 = new Comment();
        comment2.setName("p3-c2");
        comment2.setEmail("co02@asd.com");
        comment2.setBody("c02 body is here");
        comment2.setPost(post);

        List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);
        post.setCommentList(comments);

        postRepository.save(post);
        commentRepository.saveAll(comments);

//        post.setTitle("cas-add-refreshed");
//        comments.get(0).setName("c1-refreshed");

        //打印post
        //改db数据
        postRepository.save(post);
        //打印post
    }

    @Test
    void fetchTest(){
        Post post = new Post();
        post.setTitle("cas-add-p3");
        post.setContent("this is cas-add content");
        post.setDescription("this is cas-add description");

        Comment comment1 = new Comment();
        comment1.setName("p3-c1");
        comment1.setEmail("co01@asd.com");
        comment1.setBody("c01 body is here");
        comment1.setPost(post);

        Comment comment2 = new Comment();
        comment2.setName("p3-c2");
        comment2.setEmail("co02@asd.com");
        comment2.setBody("c02 body is here");
        comment2.setPost(post);

        List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);
        post.setCommentList(comments);

        postRepository.save(post);
        commentRepository.saveAll(comments);

        postRepository.findById(1L);

        /**
         * EAGER: select post and comments
         * LAZY: post only
         */
    }
}
