package com.talentmarket.KmongJpa.api;

import com.talentmarket.KmongJpa.Dto.ApiResponse;
import com.talentmarket.KmongJpa.Dto.LikeResponse;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.service.LikeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeApi {
    private final LikeService likeService;

    @PostMapping("api/v1/like/{boardId}")
    public ApiResponse like(@AuthenticationPrincipal PrincipalDetails principalDetails , @PathVariable("boardId") Long boardId) {

        likeService.like(boardId,principalDetails);
        return ApiResponse.ok(null);
    }
    @DeleteMapping("api/v1/like/{boardId}")
    public ApiResponse disLike(@PathVariable Long boardId ,@AuthenticationPrincipal PrincipalDetails principalDetails) {
        likeService.disLike(boardId, principalDetails.getDto());

        return ApiResponse.ok(null);
    }

    @GetMapping("api/v1/like/{boardId}")
    public ApiResponse getLikeCount(@PathVariable Long boardId){
       LikeResponse likeResponse = likeService.getLike(boardId);
        return ApiResponse.ok(likeResponse);
    }
}
