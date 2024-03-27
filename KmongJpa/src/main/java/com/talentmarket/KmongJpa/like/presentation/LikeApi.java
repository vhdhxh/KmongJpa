package com.talentmarket.KmongJpa.like.presentation;

import com.talentmarket.KmongJpa.global.ApiResponse;
import com.talentmarket.KmongJpa.like.application.dto.LikeResponse;
import com.talentmarket.KmongJpa.global.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.like.application.LikeService;
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
    public ApiResponse getLikeCount(@PathVariable("boardId") Long boardId){
       LikeResponse likeResponse = likeService.getLike(boardId);
        return ApiResponse.ok(likeResponse);
    }
}
