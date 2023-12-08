package com.sparta.backoffice.controller;

import com.sparta.backoffice.dto.CommonResponseDto;
import com.sparta.backoffice.dto.PostRequestDto;
import com.sparta.backoffice.security.UserDetailsImpl;
import com.sparta.backoffice.service.BackOfficeService;
import com.sparta.backoffice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class BackOfficeController {

    private final BackOfficeService backOfficeService;

    // 관리자모드 게시글 삭제
    @PatchMapping("/posts/{postId}")
    public ResponseEntity<CommonResponseDto> adminUpdatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            backOfficeService.adminUpdatePost(postId, postRequestDto, userDetails);
            return ResponseEntity.ok().body(new CommonResponseDto("수정 완료", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

}
