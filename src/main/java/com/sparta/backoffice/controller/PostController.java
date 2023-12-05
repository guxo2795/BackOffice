package com.sparta.backoffice.controller;

import com.sparta.backoffice.dto.CommonResponseDto;
import com.sparta.backoffice.dto.PostResponseDto;
import com.sparta.backoffice.dto.PostRequestDto;
import com.sparta.backoffice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<CommonResponseDto> createPost(@RequestBody PostRequestDto requestDto) {
        try {
            postService.createPost(requestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok().body(new CommonResponseDto("게시물 등록 완료",HttpStatus.OK.value()));
    }

    @GetMapping
    public List<PostResponseDto> getPostList() {
        return postService.getPostList();
    }
}