package com.sparta.backoffice.service;

import com.sparta.backoffice.dto.CommentModifyRequestDto;
import com.sparta.backoffice.dto.PostRequestDto;
import com.sparta.backoffice.dto.UpdateUserRoleRequestDto;
import com.sparta.backoffice.dto.UserResponseDto;
import com.sparta.backoffice.entity.Comment;
import com.sparta.backoffice.entity.Post;
import com.sparta.backoffice.entity.User;
import com.sparta.backoffice.entity.UserRoleEnum;
import com.sparta.backoffice.repository.CommentRepository;
import com.sparta.backoffice.repository.PostRepository;
import com.sparta.backoffice.repository.UserRepository;
import com.sparta.backoffice.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BackOfficeService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public List<UserResponseDto> getUserList(UserDetailsImpl userDetails) {
        // 관리자인지 검증
        boolean isAdmin = userDetails.getUser().getRole().equals(UserRoleEnum.ADMIN);

        if (!isAdmin) {
            throw new IllegalArgumentException("관리자가 아닙니다.");
        }

        // 전체 유저 리스트 생성
        List<User> allUsers = userRepository.findAll();
        // 반환 타입의 리스트 생성
        List<UserResponseDto> userList = new ArrayList<>();

        // 반복문을 통해 모든 user를 반환 타입의 리스트에 담은 후 반환
        for (User user : allUsers) {
            userList.add(new UserResponseDto(user));
        }
        return userList;
    }

    @Transactional
    public void updateUserRole(Long userId, UpdateUserRoleRequestDto updateUserRoleRequestDto, UserDetailsImpl userDetails) {
        // 해당 userId의 유저가 존재하는지 검증
        User user = checkUserIdAndIsAdmin(userId, userDetails);

        // 유저 권한 수정
        user.updateRole(updateUserRoleRequestDto);
    }

    public void deleteUser(Long userId, UserDetailsImpl userDetails) {
        // 해당 userId의 유저가 존재하는지 검증
        User user = checkUserIdAndIsAdmin(userId, userDetails);

        // DB에서 삭제
        userRepository.delete(user);
    }

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

    public void adminDeleteComment(Long commentId, UserDetailsImpl userDetails) {
        // 권한 검증 및 comment 객체 생성
        Comment comment = checkCommentIdAndIsAdmin(commentId, userDetails);
        // DB에서 삭제
        commentRepository.delete(comment);
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

    // 유저 관련 검증 메서드
    private User checkUserIdAndIsAdmin(Long userId, UserDetailsImpl userDetails) {
        // 해당 userId의 유저가 존재하는지 검증
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 id의 사용자가 없습니다."));

        // 관리자가 맞는지 검증
        boolean isAdmin = userDetails.getUser().getRole().equals(UserRoleEnum.ADMIN);

        if (!isAdmin) {
            throw new IllegalArgumentException("관리자가 아닙니다.");
        }
        return user;
    }
}
