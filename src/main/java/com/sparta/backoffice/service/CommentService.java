package com.sparta.backoffice.service;

import com.sparta.backoffice.dto.CommentCreateRequestDto;
import com.sparta.backoffice.dto.CommentModifyRequestDto;
import com.sparta.backoffice.dto.CommentResponseDto;
import com.sparta.backoffice.entity.Comment;
import com.sparta.backoffice.entity.Post;
import com.sparta.backoffice.entity.User;
import com.sparta.backoffice.repository.CommentRepository;
//import com.sparta.backoffice.repository.PostRepository;
import com.sparta.backoffice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    // postId 가져오기 위해 사용
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // 댓글 작성 API
    public CommentResponseDto createComment(Long postId,
                                            CommentCreateRequestDto commentCreateRequestDto,
                                            User user) {
        // postId 가져오기
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 게시물입니다."));


        if(commentCreateRequestDto.getText() == null){
            throw new IllegalArgumentException("내용을 입력하세요.");
        }
        Comment comment = new Comment(commentCreateRequestDto, user, post);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);

    }

    // 댓글 조회 API
    public List<CommentResponseDto> getComments(Long postId) {

        List<Comment> findCommentList = commentRepository.findAll();
        List<Comment> postCommentList = new ArrayList<>();

        // 요청받은 postId 와 db의 postId가 같다면 postId에 있는 댓글만 다 보여주기 (postId가 1일 때 1에 달린 댓글들만 볼 수 있도록)
        for (Comment c: findCommentList) {
            if(c.getPost().getId().equals(postId)){
                postCommentList.add(c);
            }else {
               throw new IllegalArgumentException("게시글이 없습니다.");
            }
        }

        return postCommentList.stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }

    // 댓글 수정 API
    @Transactional
    public CommentResponseDto modifyComment(Long postId,
                                            Long commentId,
                                            CommentModifyRequestDto commentModifyRequestDto,
                                            User user) throws AccessDeniedException {
        // 쓰이는 곳이 없네요..? URL에서 postId를 꼭 불러야되는 걸까요..
        Post post = postRepository.findById(postId).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 게시글 입니다."));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 댓글 입니다."));

        // 댓글 작성자와 유저id가 일치하면 업데이트 실행
        if(comment.getUser().getId().equals(user.getId())){
            comment.update(commentModifyRequestDto);
            commentRepository.save(comment);
        } else{
            throw new AccessDeniedException("댓글 작성자만 댓글을 수정할 수 있습니다.");
        }
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제 API
    @Transactional
    public CommentResponseDto deleteComment(Long postId,
                                            Long commentId,
                                            User user) throws AccessDeniedException {

        Comment comment = commentRepository.findById(commentId).orElseThrow(()->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if(comment.getUser().getId().equals(user.getId())){
            commentRepository.delete(comment);
        } else{
            throw new AccessDeniedException("댓글 작성자만 댓글을 삭제할 수 있습니다.");
        }
        return new CommentResponseDto(comment);
    }
}
