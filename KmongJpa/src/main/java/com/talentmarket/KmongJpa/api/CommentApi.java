package com.talentmarket.KmongJpa.api;

import com.talentmarket.KmongJpa.Dto.ApiResponse;
import com.talentmarket.KmongJpa.Dto.CommentWriteDto;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentApi {
    private final CommentService commentService;

    @PostMapping("api/v1/comment")
    public ApiResponse writeComment(@RequestBody CommentWriteDto commentWriteDto , @AuthenticationPrincipal PrincipalDetails principalDetails) {
        commentService.CommentWrite(commentWriteDto , principalDetails);
        return ApiResponse.ok(null);
    }

    @PostMapping("api/v1/comment/{commentId}")
    public ApiResponse updateComment(@RequestBody CommentWriteDto commentWriteDto
            , @AuthenticationPrincipal PrincipalDetails principalDetails
    , @PathVariable Long commentId){
        commentService.commentUpdate(commentWriteDto,principalDetails,commentId);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("api/v1/comment/{commentId}")
    public ApiResponse deleteComment(@PathVariable Long commentId ,@AuthenticationPrincipal PrincipalDetails principalDetails){
        commentService.commentDelete(principalDetails,commentId);
        return ApiResponse.ok(null);
    }
}
