package com.sprinkler.kwakkwak.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;


@Getter
@Setter
@NoArgsConstructor
public class ProfileDto {

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String userName;

    private String self;

    private String blog;

    private String github;

    private Long userCode;
}
