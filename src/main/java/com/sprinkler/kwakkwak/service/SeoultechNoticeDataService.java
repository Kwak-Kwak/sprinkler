package com.sprinkler.kwakkwak.service;

import com.sprinkler.kwakkwak.domain.Notice;
import com.sprinkler.kwakkwak.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SeoultechNoticeDataService {


    private final NoticeRepository noticeRepository;

    //전체 데이터 출력
    public List<Notice> getSeoultechNoticeDatas() {

        List<Notice> noticeList = noticeRepository.findAll();

        return noticeList;
    }

    // 특정 학사 공지 url 찾기
    public String getPostUrl(Long id) {

        Optional<Notice> noticeOptional = noticeRepository.findByPostId(id);

        if (noticeOptional.isPresent()) {
            return noticeOptional.get().getPostUrl();
        }

        return "error";
    }

}
