package com.sparta.backoffice.service;

import com.sparta.backoffice.dto.CommentCreateRequestDto;
import com.sparta.backoffice.dto.CommentResponseDto;
import com.sparta.backoffice.entity.Comment;
import com.sparta.backoffice.repository.CommentRepository;
//import com.sparta.backoffice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    // postId 가져오기 위해 사용
    /*private final PostRepository postRepository;*/

    private final CommentRepository commentRepository;

    public CommentResponseDto createComment(Long postId,
                                            CommentCreateRequestDto commentCreateRequestDto) {
        // postId 가져오기
//        Optional<Post> post = postRepository.findById(postId);

        Comment comment = new Comment();
        comment.setText(commentCreateRequestDto.getText());

        commentRepository.save(comment);
        return new CommentResponseDto(comment);

    }
}
