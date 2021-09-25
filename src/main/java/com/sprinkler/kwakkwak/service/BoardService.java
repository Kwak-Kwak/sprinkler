package com.sprinkler.kwakkwak.service;

import com.sprinkler.kwakkwak.domain.Post;
import com.sprinkler.kwakkwak.domain.UserInfo;
import com.sprinkler.kwakkwak.dto.CreatePostRequest;
import com.sprinkler.kwakkwak.repository.PostRepository;
import com.sprinkler.kwakkwak.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final UserInfoRepository userInfoRepository;
    private final PostRepository postRepository;

    @Transactional
    public void savePost(Long boardId, CreatePostRequest request) {
        Optional<UserInfo> userInfo = userInfoRepository.findById(request.getUserId());

        if (userInfo.isPresent()) {
            Post post = Post.builder()
                    .boardId(boardId)
                    .context(request.getContext())
                    .title(request.getTitle())
                    .userInfo(userInfo.get()).build();

            postRepository.save(post);
        }
    }
}
