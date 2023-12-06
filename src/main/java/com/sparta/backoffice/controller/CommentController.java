package com.sparta.backoffice.controller;

import com.sparta.backoffice.dto.CommentCreateRequestDto;
import com.sparta.backoffice.dto.CommentModifyRequestDto;
import com.sparta.backoffice.dto.CommentResponseDto;
import com.sparta.backoffice.dto.CommonResponseDto;
import com.sparta.backoffice.entity.Comment;
import com.sparta.backoffice.security.UserDetailsImpl;
import com.sparta.backoffice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성 API
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommonResponseDto> createComment(@PathVariable Long postId,
                                                            @RequestBody CommentCreateRequestDto commentCreateRequestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails){

        try {
            commentService.createComment(postId, commentCreateRequestDto, userDetails.getUser());
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok().body(new CommonResponseDto("댓글 등록 완료", HttpStatus.OK .value()));
    }

    // 댓글 조회 API
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long postId){
        List<CommentResponseDto> commentResponseDto = commentService.getComments(postId);
        return ResponseEntity.status(201).body(commentResponseDto);
    }

    // 댓글 수정 API
    @PatchMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommonResponseDto> modifyComment(@PathVariable Long postId,
                                                            @PathVariable Long commentId,
                                                            @RequestBody CommentModifyRequestDto commentModifyRequestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            commentService.modifyComment(postId, commentId, commentModifyRequestDto, userDetails.getUser());
        } catch (AccessDeniedException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto("댓글 작성자만 수정 가능합니다.", HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok().body(new CommonResponseDto("댓글 수정 완료", HttpStatus.OK.value()));
    }

    // 댓글 삭제 API
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommonResponseDto> deleteComment(@PathVariable Long postId,
                                                            @PathVariable Long commentId,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails){

        try {
            commentService.deleteComment(postId, commentId, userDetails.getUser());
        } catch (AccessDeniedException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto("댓글 작성자만 삭제 가능합니다.", HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok().body(new CommonResponseDto("댓글 삭제 완료", HttpStatus.OK.value()));
    }
}
