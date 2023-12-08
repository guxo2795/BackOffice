package com.sparta.backoffice.controller;

import com.sparta.backoffice.dto.*;
import com.sparta.backoffice.entity.User;
import com.sparta.backoffice.security.UserDetailsImpl;
import com.sparta.backoffice.service.BackOfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class BackOfficeController {

    private final BackOfficeService backOfficeService;

    // 유저 전체 목록 조회
    @GetMapping("/users")
    public ResponseEntity<?> getUserList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            List<UserResponseDto> userList = backOfficeService.getUserList(userDetails);
            return ResponseEntity.ok(userList);
        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 유저 권한 수정
    @PatchMapping("/users/{userId}")
    public ResponseEntity<CommonResponseDto> updateUserRole(@PathVariable Long userId, @RequestBody UpdateUserRoleRequestDto updateUserRoleRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            backOfficeService.updateUserRole(userId, updateUserRoleRequestDto, userDetails);
            return ResponseEntity.ok().body(new CommonResponseDto("사용자 권한 수정 완료", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 관리자 권한 게시글 수정
    @PatchMapping("/posts/{postId}")
    public ResponseEntity<CommonResponseDto> adminUpdatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            backOfficeService.adminUpdatePost(postId, postRequestDto, userDetails);
            return ResponseEntity.ok().body(new CommonResponseDto("수정 완료", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 관리자 권한 게시글 삭제
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<CommonResponseDto> adminDeletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            backOfficeService.adminDeletePost(postId, userDetails);
            return ResponseEntity.ok().body(new CommonResponseDto("삭제 완료", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 관리자 권한 댓글 수정
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommonResponseDto> adminUpdateComment(@PathVariable Long commentId, @RequestBody CommentModifyRequestDto commentModifyRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            backOfficeService.adminUpdateComment(commentId, commentModifyRequestDto, userDetails);
            return ResponseEntity.ok().body(new CommonResponseDto("수정 완료", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 관리자 권한 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<CommonResponseDto> adminDeleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            backOfficeService.adminDeleteComment(commentId, userDetails);
            return ResponseEntity.ok().body(new CommonResponseDto("삭제 완료", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
