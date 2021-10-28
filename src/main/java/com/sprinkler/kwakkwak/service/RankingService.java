package com.sprinkler.kwakkwak.service;

import com.sprinkler.kwakkwak.domain.Profile;
import com.sprinkler.kwakkwak.domain.Ranking;
import com.sprinkler.kwakkwak.dto.RankingDetailDto;
import com.sprinkler.kwakkwak.dto.RankingDto;
import com.sprinkler.kwakkwak.repository.ProfileRepository;
import com.sprinkler.kwakkwak.repository.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class RankingService {

    private final RankingRepository rankingRepository;
    private final ProfileRepository profileRepository;

    // 일주일 랭킹 전체 출력
    @Transactional
    public List<RankingDetailDto> weekRanking() {
        List<Ranking> rankings = rankingRepository.findByOrderByWeekScoreDesc();

        List<RankingDetailDto> rankingDtos = new ArrayList<>();

        for (Ranking ranking : rankings) {
            Profile profile = profileRepository.findByUserInfo_Code(ranking.getUserInfo().getCode()).get();

            RankingDetailDto rankingDto = new RankingDetailDto();

            rankingDto.setUserName(profile.getUserName());
            rankingDto.setScore(ranking.getWeekScore());
            rankingDto.setCommit(ranking.getWeekCommit());

            rankingDtos.add(rankingDto);
        }

        return rankingDtos;
    }

    // 90일 랭킹 전체 출력
    @Transactional
    public List<RankingDetailDto> monthRanking() {
        List<Ranking> rankings = rankingRepository.findByOrderByMonthScoreDesc();

        List<RankingDetailDto> rankingDtos = new ArrayList<>();

        for (Ranking ranking : rankings) {
            Profile profile = profileRepository.findByUserInfo_Code(ranking.getUserInfo().getCode()).get();

            RankingDetailDto rankingDto = new RankingDetailDto();

            rankingDto.setUserName(profile.getUserName());
            rankingDto.setScore(ranking.getMonthScore());
            rankingDto.setCommit(ranking.getMonthCommit());

            rankingDtos.add(rankingDto);
        }

        return rankingDtos;
    }

    // 90일 탑 랭킹 출력
    @Transactional
    public List<RankingDto> monthTopRanking() {
        List<Ranking> rankings = rankingRepository.findTop5ByOrderByMonthScoreDesc();

        List<RankingDto> rankingDtos = new ArrayList<>();

        for (Ranking ranking : rankings) {
            Profile profile = profileRepository.findByUserInfo_Code(ranking.getUserInfo().getCode()).get();

            RankingDto rankingDto = new RankingDto();

            rankingDto.setUserName(profile.getUserName());
            rankingDto.setScore(ranking.getMonthScore());

            rankingDtos.add(rankingDto);
        }

        return rankingDtos;
    }

    // 일주일 탑 랭킹 출력
    @Transactional
    public List<RankingDto> weekTopRanking() {
        List<Ranking> rankings = rankingRepository.findTop5ByOrderByWeekScoreDesc();

        List<RankingDto> rankingDtos = new ArrayList<>();

        for (Ranking ranking : rankings) {
            Profile profile = profileRepository.findByUserInfo_Code(ranking.getUserInfo().getCode()).get();

            RankingDto rankingDto = new RankingDto();

            rankingDto.setUserName(profile.getUserName());
            rankingDto.setScore(ranking.getWeekScore());

            rankingDtos.add(rankingDto);
        }

        return rankingDtos;
    }

}
