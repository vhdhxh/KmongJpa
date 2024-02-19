package com.talentmarket.KmongJpa.api;

import com.talentmarket.KmongJpa.Dto.ApiResponse;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.service.LikeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeApi {
    private final LikeService likeService;

    @PostMapping("api/v1/like/{boardId}")
    public ApiResponse LikeCount(@AuthenticationPrincipal PrincipalDetails principalDetails , @PathVariable Long boardId) {

        likeService.LikeCount(boardId,principalDetails);
        return ApiResponse.ok(null);
    }
}
