package com.talentmarket.KmongJpa.api;

import com.talentmarket.KmongJpa.Dto.WriteRequest;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.repository.BoardRepository;
import com.talentmarket.KmongJpa.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardApi {
    private final BoardService boardService;

    @PostMapping("/api/v1/board")
    public ResponseEntity writeBoard (@RequestBody WriteRequest writeRequest , @AuthenticationPrincipal PrincipalDetails principalDetails) {

        return ResponseEntity.status(HttpStatus.OK).body(boardService.WriteBoard(writeRequest,principalDetails));
    }

}
