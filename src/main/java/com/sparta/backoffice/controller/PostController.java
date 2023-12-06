package com.sparta.backoffice.controller;

import com.sparta.backoffice.dto.CommonResponseDto;
import com.sparta.backoffice.dto.PostResponseDto;
import com.sparta.backoffice.dto.PostRequestDto;
import com.sparta.backoffice.security.UserDetailsImpl;
import com.sparta.backoffice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<CommonResponseDto> createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            postService.createPost(requestDto, userDetails);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok().body(new CommonResponseDto("게시물 등록 완료",HttpStatus.OK.value()));
    }

    @GetMapping
    public List<PostResponseDto> getPostList() {
        return postService.getPostList();
    }

    // 추후 ResponseDto에 nickname 추가 필요
    @GetMapping("/{postId}")
    public ResponseEntity<CommonResponseDto> getPost(@PathVariable Long postId) {
        try {
            return ResponseEntity.ok().body(postService.getPost(postId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 추후 유저 인증 정보 추가 필요
    @PatchMapping("/{postId}")
    public ResponseEntity<CommonResponseDto> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto) {
        try {
            postService.updatePost(postId, postRequestDto);
            return ResponseEntity.ok().body(new CommonResponseDto("수정 완료", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 추후 유저 인증 정보 추가 필요
    @DeleteMapping("/{postId}")
    public ResponseEntity<CommonResponseDto> deletePost(@PathVariable Long postId) {
        try {
            postService.deletePost(postId);
            return ResponseEntity.ok().body(new CommonResponseDto("삭제 완료", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }
}