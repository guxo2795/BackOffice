package com.sparta.backoffice.service;

import com.sparta.backoffice.dto.CommentModifyRequestDto;
import com.sparta.backoffice.dto.PostRequestDto;
import com.sparta.backoffice.entity.Comment;
import com.sparta.backoffice.entity.Post;
import com.sparta.backoffice.entity.UserRoleEnum;
import com.sparta.backoffice.repository.CommentRepository;
import com.sparta.backoffice.repository.PostRepository;
import com.sparta.backoffice.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BackOfficeService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void adminUpdatePost(Long postId, PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        // 권한 검증 및 post 객체 생성
        Post post = checkPostIdAndIsAdmin(postId, userDetails);
        // 게시글 수정
        post.update(postRequestDto);
    }

    public void adminDeletePost(Long postId, UserDetailsImpl userDetails) {
        // 권한 검증 및 post 객체 생성
        Post post = checkPostIdAndIsAdmin(postId, userDetails);
        // DB에서 삭제
        postRepository.delete(post);
    }

    @Transactional
    public void adminUpdateComment(Long commentId, CommentModifyRequestDto commentModifyRequestDto, UserDetailsImpl userDetails) {
        // 권한 검증 및 comment 객체 생성
        Comment comment = checkCommentIdAndIsAdmin(commentId, userDetails);
        // 댓글 수정
        comment.update(commentModifyRequestDto);
    }

    // 게시글 관련 검증 메서드
    private Post checkPostIdAndIsAdmin(Long postId, UserDetailsImpl userDetails) {
        // 해당 id의 게시물이 존재하는지 검증 및 post 객체 생성
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 id의 게시물이 없습니다."));

        // 현재 로그인 한 사용자가 관리자인지 검증
        boolean isAdmin = userDetails.getUser().getRole().equals(UserRoleEnum.ADMIN);

        if (!isAdmin) {
            throw new IllegalArgumentException("관리자가 아닙니다.");
        }

        return post;
    }

    // 댓글 관련 검증 메서드
    private Comment checkCommentIdAndIsAdmin(Long commentId, UserDetailsImpl userDetails) {
        // 해당 id의 댓글이 존재하는지 검증 및 comment 객체 생성
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 id의 댓글이 없습니다."));

        // 현재 로그인 한 사용자가 관리자인지 검증
        boolean isAdmin = userDetails.getUser().getRole().equals(UserRoleEnum.ADMIN);

        if (!isAdmin) {
            throw new IllegalArgumentException("관리자가 아닙니다.");
        }
        return comment;
    }
}
