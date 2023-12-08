package com.sparta.backoffice.entity;


import com.sparta.backoffice.dto.PwdUpdateRequestDto;
import com.sparta.backoffice.dto.UpdateUserRoleRequestDto;
import com.sparta.backoffice.dto.UserUpdateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;


    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;


    @Column(nullable = false)
    private String nickname;

    @Column
    private Integer age;

    @Column
    private String email;

    @Column
    private String userinfo;

    @Column
    private String userurl;


    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<LikePost> postLikes = new ArrayList<>();

    public User(String username, String password, String nickname, UserRoleEnum role, Integer age, String email, String userinfo, String userurl) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.nickname = nickname;
        this.age = age;
        this.email = email;
        this.userinfo = userinfo;
        this.userurl = userurl;
    }

    public void updateUser(UserUpdateRequestDto userUpdateRequestDto){
        this.nickname = userUpdateRequestDto.getNickname();
        this.age = userUpdateRequestDto.getAge();
        this.email = userUpdateRequestDto.getEmail();
        this.userinfo = userUpdateRequestDto.getUserinfo();
        this.userurl = userUpdateRequestDto.getUserurl();
    }

    public void updatePwd(String newPassword){
        this.password = newPassword;
    }

    public void updateRole(UpdateUserRoleRequestDto updateUserRoleRequestDto) {
        this.role = updateUserRoleRequestDto.getRole();
    }
}
