package com.sprinkler.kwakkwak.controller;

import com.sprinkler.kwakkwak.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class RankingController {

    private final RankingService rankingService;

    // 첫 랭킹 화면 출력
    @GetMapping("/ranking")
    public String ranking(Model model) {

        model.addAttribute("monthRanking", rankingService.monthTopRanking());
        model.addAttribute("weekRanking", rankingService.weekTopRanking());

        return "ranking";
    }

    // 90일 정원사 랭킹 자세히 보기
    @GetMapping("/ranking/month")
    public String rankingMonthDetail(Model model) {
        model.addAttribute("title", "90일 정원사");
        model.addAttribute("rankingList", rankingService.monthRanking());

        return "ranking-detail";
    }

    // 일주일 정원사 자세히보기
    @GetMapping("/ranking/week")
    public String rankingWeekDetail(Model model) {
        model.addAttribute("title", "일주일 정원사");
        model.addAttribute("rankingList", rankingService.weekRanking());

        return "ranking-detail";
    }
}
