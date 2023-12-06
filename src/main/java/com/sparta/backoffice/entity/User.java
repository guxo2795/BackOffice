package com.sparta.backoffice.entity;

import com.sparta.backoffice.dto.PwdUpdateRequestDto;
import com.sparta.backoffice.dto.UserUpdateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column
    private String nickname;

    @Column
    private Integer age;

    @Column
    private String email;

    @Column
    private String userinfo;

    @Column
    private String userurl;

    public User(String username, String password, String nickname, Integer age, String email, String userinfo, String userurl) {
        this.username = username;
        this.password = password;
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
}
