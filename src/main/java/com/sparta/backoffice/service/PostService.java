package com.sparta.backoffice.service;

import com.sparta.backoffice.dto.PostResponseDto;
import com.sparta.backoffice.dto.PostRequestDto;
import com.sparta.backoffice.entity.Post;
import com.sparta.backoffice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 추후 유저 인증 정보 추가 필요
    public void createPost(PostRequestDto postRequestDto) {

        // 예외 처리
        if (postRequestDto.getTitle() == null) {
            throw new IllegalArgumentException("제목을 입력하세요.");
        } else if (postRequestDto.getContent() == null) {
            throw new IllegalArgumentException("내용을 입력하세요.");
        }

        // 받아온 정보로 post 객체 생성
        Post post = new Post(postRequestDto);

        // DB에 저장
        postRepository.save(post);
    }

    public List<PostResponseDto> getPostList() {
        // post 전체 리스트 생성
        List<Post> postList = postRepository.findAll();
        // 반환 타입의 리스트 생성
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        // 반복문을 통해 postList의 내용물을 반환 타입의 리스트에 담은 후 반환
        for (Post post : postList) {
            responseDtoList.add(new PostResponseDto(post));
        }
        return responseDtoList;
    }

    public PostResponseDto getPost(Long postId) {
        // 받아온 id와 일치하는 post 객체 생성, DTO로 변환 후 반환 및 예외 처리
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 id의 게시물이 없습니다."));
        return new PostResponseDto(post);
    }

    // 추후 유저 인증 정보 추가 필요
    @Transactional
    public void updatePost(Long postId, PostRequestDto postRequestDto) {
        // 받아온 id와 일치하는 post 객체 생성 및 예외 처리
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 id의 게시물이 없습니다."));

        /*
        추후 권한 검증 로직 구현 필요
        */

        // 받아온 정보로 게시글 수정
        post.update(postRequestDto);
    }
}

