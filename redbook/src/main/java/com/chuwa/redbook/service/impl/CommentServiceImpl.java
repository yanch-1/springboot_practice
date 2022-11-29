package com.chuwa.redbook.service.impl;

import com.chuwa.redbook.dao.CommentRepository;
import com.chuwa.redbook.dao.PostRepository;
import com.chuwa.redbook.dao.security.UserRepository;
import com.chuwa.redbook.entity.Comment;
import com.chuwa.redbook.entity.Post;
import com.chuwa.redbook.entity.security.User;
import com.chuwa.redbook.exception.BlogAPIException;
import com.chuwa.redbook.exception.ResourceNotFoundException;
import com.chuwa.redbook.payload.CommentDto;
import com.chuwa.redbook.payload.CreateCommentDto;
import com.chuwa.redbook.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

//    @Override
//    public CommentDto createComment(long postId, CommentDto commentDto) {
//        //Comment comment = mapToEntity(commentDto);
//        Comment comment = modelMapper.map(commentDto, Comment.class);
//        // retrieve post entity by id
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
//        // set post to comment entity
//        comment.setPost(post);
//
//        Comment savedComment = commentRepository.save(comment);
//
////        return mapToDto(savedComment);
//        return modelMapper.map(savedComment, CommentDto.class);
//    }

    @Override
    public CreateCommentDto createComment(long postId, CreateCommentDto createCommentDto, Principal principal) {
        Comment comment = modelMapper.map(createCommentDto, Comment.class);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new BlogAPIException(HttpStatus.BAD_REQUEST, "User No Found"));
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);



        return modelMapper.map(savedComment, CreateCommentDto.class);
    }
    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        // retrieve comments by postId
        List<Comment> comments = commentRepository.findByPostId(postId);
        // convert list of comment entities to list of comment dto's
//        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return comments.stream().map(comment -> modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getCommentsByUserId(long userId) {
        List<Comment> comments = commentRepository.findByUserId(userId);
        return comments.stream().map(comment -> modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
//        return mapToDto(comment);
        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public CreateCommentDto updateComment(Long postId, Long commentId, CreateCommentDto createCommentDto, Principal principal) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        // 业务逻辑

        if (!principal.getName().equals(userRepository.findByCommentsId(commentId).get().getEmail())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "No access to others' posts");
        }
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }


        comment.setBody(createCommentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);

        return modelMapper.map(updatedComment, CreateCommentDto.class);
    }

    @Override
    public void deleteComment(Long postId, Long commentId, Principal principal) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        // 业务逻辑

        if (!principal.getName().equals(userRepository.findByCommentsId(commentId).get().getEmail())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "No access to others' posts");
        }
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        commentRepository.delete(comment);
    }

}
