package com.sprinkler.kwakkwak.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String github;

    @OneToOne
    @JoinColumn(name="user_info_code")
    private UserInfo userInfo;

}
