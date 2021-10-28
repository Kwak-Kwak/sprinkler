package com.sprinkler.kwakkwak.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String self;

    private String blog;

    private String github;

    @OneToOne
    @JoinColumn(name="user_info_code")
    private UserInfo userInfo;

    @Builder
    public Profile(String userName, String self, String blog, String github,UserInfo userInfo) {
        this.userName = userName;
        this.self = self;
        this.blog = blog;
        this.github = github;
        this.userInfo = userInfo;
    }


}
