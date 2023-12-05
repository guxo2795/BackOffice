package com.sparta.backoffice.service;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.sparta.backoffice.dto.CommentCreateRequestDto;
import com.sparta.backoffice.dto.CommentModifyRequestDto;
import com.sparta.backoffice.dto.CommentResponseDto;
import com.sparta.backoffice.entity.Comment;
import com.sparta.backoffice.repository.CommentRepository;
//import com.sparta.backoffice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    // postId 가져오기 위해 사용
    /*private final PostRepository postRepository;*/

    private final CommentRepository commentRepository;

    // 댓글 작성 API
    public CommentResponseDto createComment(Long postId,
                                            CommentCreateRequestDto commentCreateRequestDto) {
        // postId 가져오기
//        Optional<Post> post = postRepository.findById(postId);

        Comment comment = new Comment();
        comment.setText(commentCreateRequestDto.getText());

        commentRepository.save(comment);
        return new CommentResponseDto(comment);

    }

    // 댓글 조회 API
    public List<CommentResponseDto> getComment(Long postId) {

        List<Comment> findCommentList = commentRepository.findAll();
        List<Comment> postCommentList = new ArrayList<>();
        // 요청받은 postId 와 db의 postId가 같다면 거기에 맞는 댓글을 다 보여주기
//        for (Comment c: findCommentList) {
//            if(c.getPost().getPostId().equals(postId)){
//                postCommentList.add(c);
//            }
//        }

        return findCommentList.stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }

    // 댓글 수정 API
    @Transactional
    public CommentResponseDto modifyComment(Long postId,
                                            Long commentId,
                                            CommentModifyRequestDto commentModifyRequestDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글 입니다."));

        comment.update(commentModifyRequestDto);
        // 요청받은 postId와 db의 postId가 같아야 하고

        // 수정은 자기 자신이 작성한 것이어야 수정이 가능함
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제 API

    @Transactional
    public CommentResponseDto deleteComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        commentRepository.delete(comment);
        return new CommentResponseDto(comment);
    }
}
