package com.sparta.backoffice.service;

import com.sparta.backoffice.dto.PostResponseDto;
import com.sparta.backoffice.dto.PostRequestDto;
import com.sparta.backoffice.entity.Post;
import com.sparta.backoffice.entity.UserRoleEnum;
import com.sparta.backoffice.repository.PostRepository;
import com.sparta.backoffice.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void createPost(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {

        // 예외 처리
        if (postRequestDto.getTitle() == null) {
            throw new IllegalArgumentException("제목을 입력하세요.");
        } else if (postRequestDto.getContent() == null) {
            throw new IllegalArgumentException("내용을 입력하세요.");
        }

        // 받아온 정보로 post 객체 생성
        Post post = new Post(postRequestDto, userDetails);

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
        // 해당 게시물의 id와 일치하는지 검증 및 post 객체 생성
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 id의 게시물이 없습니다."));
        // DTO로 변환 후 반환
        return new PostResponseDto(post);
    }

    @Transactional
    public void updatePost(Long postId, PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        // 권한 검증 및 post 객체 생성
        Post post = checkPostIdAndUser(postId, userDetails);
        // 받아온 정보로 게시글 수정
        post.update(postRequestDto);
    }

    public void deletePost(Long postId, UserDetailsImpl userDetails) {
        // 권한 검증 및 post 객체 생성
        Post post = checkPostIdAndUser(postId, userDetails);
        // DB에서 삭제
        postRepository.delete(post);
    }


    // 검증 메서드
    private Post checkPostIdAndUser(Long postId, UserDetailsImpl userDetails) {
        // 해당 id의 게시물이 존재하는지 검증 및 post 객체 생성
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 id의 게시물이 없습니다."));

        // 현재 로그인한 사용자가 관리자인지 검증
        boolean isAdmin = userDetails.getUser().getRole().equals(UserRoleEnum.ADMIN);

        if (!Objects.equals(post.getUser().getId(), userDetails.getUser().getId()) && !isAdmin) {
            throw new IllegalArgumentException("게시물 작성자만 수정 및 삭제 가능합니다.");
        }

        return post;
    }
}

