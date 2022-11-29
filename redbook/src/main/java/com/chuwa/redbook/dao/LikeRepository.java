package com.chuwa.redbook.dao;

import com.chuwa.redbook.entity.PostLike;
import com.chuwa.redbook.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<PostLike, Long> {
    List<PostLike> findByPost(long postId);
    @Query("select l.user from PostLike l where l.post = :id")
    List<User> getUserByPost(@Param("id") long postId);

    long countByPost(long postId);

    @Transactional
    void deleteByPostAndUser(long postId,long userId);

    boolean existsByPostAndUser(long postId, long userId);
}
