package com.sparta.backoffice.controller;

import com.sparta.backoffice.dto.CommentCreateRequestDto;
import com.sparta.backoffice.dto.CommentResponseDto;
import com.sparta.backoffice.entity.Comment;
import com.sparta.backoffice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성 API
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long postId,
                                                            @RequestBody CommentCreateRequestDto commentCreateRequestDto){

        CommentResponseDto commentResponseDto = commentService.createComment(postId, commentCreateRequestDto);
        return ResponseEntity.status(201).body(commentResponseDto);
    }

    // 댓글 조회 API
    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponseDto>> getComment(Long postId){
        List<CommentResponseDto> commentResponseDto = commentService.getComment(postId);
        return ResponseEntity.status(201).body(commentResponseDto);
    }
}
