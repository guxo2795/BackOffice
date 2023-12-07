package com.sparta.backoffice.repository;

import com.sparta.backoffice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
