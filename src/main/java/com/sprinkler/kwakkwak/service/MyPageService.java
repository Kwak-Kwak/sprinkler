package com.sprinkler.kwakkwak.service;

import com.sprinkler.kwakkwak.domain.Post;
import com.sprinkler.kwakkwak.domain.Profile;
import com.sprinkler.kwakkwak.domain.UserInfo;
import com.sprinkler.kwakkwak.dto.MypageProfileDto;
import com.sprinkler.kwakkwak.dto.PostDto;
import com.sprinkler.kwakkwak.repository.PostRepository;
import com.sprinkler.kwakkwak.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MyPageService {

    private final ProfileRepository profileRepository;
    private final PostRepository postRepository;

    @Transactional
    public MypageProfileDto getprofile(UserInfo userInfo) {
        Profile profile = profileRepository.findByUserInfo_Code(userInfo.getCode()).get();

        MypageProfileDto dto = MypageProfileDto.builder()
                .blog(profile.getBlog())
                .self(profile.getSelf())
                .github(profile.getGithub())
                .userName(profile.getUserName()).build();

        return dto;


    }




    @Transactional
    public List<PostDto> getPostList(UserInfo userInfo) {
        List<Post> postList = postRepository.findByUserInfo_Code(userInfo.getCode());

        List<PostDto> postDtoList = new ArrayList<>();

        for (Post post : postList) {
            PostDto postDto = PostDto.builder()
                    .commentNum(post.getCommentNum())
                    .heartNum(post.getHeartNum())
                    .context(post.getContext())
                    .postId(post.getId())
                    .title(post.getTitle())
                    .userName(profileRepository.findByUserInfo_Code(post.getUserInfo().getCode()).get().getUserName())
                    .createdAt(post.getCreatedAt().toLocalDate())
                    .build();

            postDtoList.add(postDto);
        }

        return postDtoList;
    }


}
