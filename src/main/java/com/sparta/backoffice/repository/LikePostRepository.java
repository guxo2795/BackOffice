package com.sparta.backoffice.repository;

import com.sparta.backoffice.entity.Post;
import com.sparta.backoffice.entity.LikePost;
import com.sparta.backoffice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    List<LikePost> findAllByPost(Post post);

    LikePost findByPostAndUser(Post post, User user);
}
