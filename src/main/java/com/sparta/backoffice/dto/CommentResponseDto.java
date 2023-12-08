package com.sparta.backoffice.dto;

import com.sparta.backoffice.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto extends CommonResponseDto {

    private Long commentId;
    private String nickname;    // User에서 가져온다?
    private String text;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        // user구현 시 닉네임 추가
        this.nickname = comment.getUser().getNickname();
        this.text = comment.getText();
        this.createAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
