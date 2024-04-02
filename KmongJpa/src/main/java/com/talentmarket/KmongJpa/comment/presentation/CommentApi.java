package com.talentmarket.KmongJpa.comment.presentation;

import com.talentmarket.KmongJpa.auth.util.AuthPrincipal;
import com.talentmarket.KmongJpa.global.ApiResponse;
import com.talentmarket.KmongJpa.comment.application.dto.CommentWriteDto;
import com.talentmarket.KmongJpa.comment.application.CommentService;
import com.talentmarket.KmongJpa.user.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentApi {
    private final CommentService commentService;

    @PostMapping("api/v1/comment")
    public ApiResponse writeComment(@RequestBody CommentWriteDto commentWriteDto , @AuthPrincipal Users user) {
        commentService.CommentWrite(commentWriteDto , user);
        return ApiResponse.ok(null);
    }

    @PostMapping("api/v1/comment/{commentId}")
    public ApiResponse updateComment(@RequestBody CommentWriteDto commentWriteDto
            ,  @AuthPrincipal Users user
    , @PathVariable Long commentId){
        commentService.commentUpdate(commentWriteDto,user,commentId);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("api/v1/comment/{commentId}")
    public ApiResponse deleteComment(@PathVariable Long commentId ,@AuthPrincipal Users user){
        commentService.commentDelete(user,commentId);
        return ApiResponse.ok(null);
    }
}
