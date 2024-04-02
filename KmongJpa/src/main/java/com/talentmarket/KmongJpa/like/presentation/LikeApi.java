package com.talentmarket.KmongJpa.like.presentation;

import com.talentmarket.KmongJpa.auth.util.AuthPrincipal;
import com.talentmarket.KmongJpa.global.ApiResponse;
import com.talentmarket.KmongJpa.like.application.dto.LikeResponse;
import com.talentmarket.KmongJpa.like.application.LikeService;
import com.talentmarket.KmongJpa.user.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeApi {
    private final LikeService likeService;

    @PostMapping("api/v1/like/{boardId}")
    public ApiResponse like(@AuthPrincipal Users user , @PathVariable("boardId") Long boardId) {

        likeService.like(boardId,user);
        return ApiResponse.ok(null);
    }
    @DeleteMapping("api/v1/like/{boardId}")
    public ApiResponse disLike(@PathVariable Long boardId ,@AuthPrincipal Users user) {
        likeService.disLike(boardId, user);

        return ApiResponse.ok(null);
    }

    @GetMapping("api/v1/like/{boardId}")
    public ApiResponse getLikeCount(@PathVariable("boardId") Long boardId){
       LikeResponse likeResponse = likeService.getLike(boardId);
        return ApiResponse.ok(likeResponse);
    }
}
