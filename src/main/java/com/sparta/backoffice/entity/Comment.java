package com.sparta.backoffice.entity;

import com.sparta.backoffice.dto.CommentModifyRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CommentId;

    @Column
    private String text;

//    @ManyToOne
//    @JoinColumn(name = "post_id")
//    private Post post;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    // 서비스 메서드
    public void setText(String text) {
        this.text = text;
    }

    public void update(CommentModifyRequestDto commentModifyRequestDto) {
        this.text = commentModifyRequestDto.getText();
    }
}
