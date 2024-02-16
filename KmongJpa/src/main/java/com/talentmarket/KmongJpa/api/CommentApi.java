package com.talentmarket.KmongJpa.api;

import com.talentmarket.KmongJpa.Dto.CommentWriteDto;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentApi {
    private final CommentService commentService;

    @PostMapping("api/v1/comment")
    public void writeComment(@RequestBody CommentWriteDto commentWriteDto , @AuthenticationPrincipal PrincipalDetails principalDetails) {
        commentService.CommentWrite(commentWriteDto , principalDetails);
    }

}
