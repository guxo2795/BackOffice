package com.sparta.backoffice.entity;

import com.sparta.backoffice.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    /*
    추후 User 연관관계 설정 필요
    */

    public Post(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle() == null ? this.title : postRequestDto.getTitle();
        this.content = postRequestDto.getContent() == null ? this.content : postRequestDto.getContent();
    }
}
