package com.sparta.backoffice.entity;

import com.sparta.backoffice.dto.PostRequestDto;
import com.sparta.backoffice.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<LikePost> postLikes;


    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();


    public Post(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.user = userDetails.getUser();
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle() == null ? this.title : postRequestDto.getTitle();
        this.content = postRequestDto.getContent() == null ? this.content : postRequestDto.getContent();
    }
}
