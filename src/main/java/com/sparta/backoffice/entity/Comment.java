package com.sparta.backoffice.entity;

import com.sparta.backoffice.dto.CommentCreateRequestDto;
import com.sparta.backoffice.dto.CommentModifyRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CommentId;

    @Column
    private String text;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(CommentCreateRequestDto commentCreateRequestDto, User user, Post post) {
        this.text = commentCreateRequestDto.getText();
        this.user = user;
        this.post = post;
    }

    // 서비스 메서드
    public void setText(String text) {
        this.text = text;
    }

    public void update(CommentModifyRequestDto commentModifyRequestDto) {
        this.text = commentModifyRequestDto.getText();
    }
}
