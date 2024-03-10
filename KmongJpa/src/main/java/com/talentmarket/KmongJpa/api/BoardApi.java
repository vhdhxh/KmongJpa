package com.talentmarket.KmongJpa.api;

import com.talentmarket.KmongJpa.Dto.ApiResponse;
import com.talentmarket.KmongJpa.Dto.DetailResponse;
import com.talentmarket.KmongJpa.Dto.WriteRequest;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardApi {
    private final ItemService itemService;

    //게시글 쓰기
    @PostMapping("/api/v1/board")
    public ResponseEntity writeBoard (@RequestBody WriteRequest writeRequest , @AuthenticationPrincipal PrincipalDetails principalDetails) {

        return ResponseEntity.status(HttpStatus.OK).body(itemService.WriteBoard(writeRequest,principalDetails));
    }

    //게시글 수정
    @PostMapping("/api/v1/board/{boardId}")
    public ResponseEntity writeBoard (@RequestBody WriteRequest writeRequest
            , @AuthenticationPrincipal PrincipalDetails principalDetails
            , @PathVariable Long boardId) {

        return ResponseEntity.status(HttpStatus.OK).body(itemService.UpdateBoard(writeRequest,boardId,principalDetails));
    }

    //게시글 삭제
    @DeleteMapping ("/api/v1/board/{boardId}")
    public ResponseEntity deleteBoard (
             @AuthenticationPrincipal PrincipalDetails principalDetails
            , @PathVariable Long boardId) {
        itemService.DeleteBoard(boardId , principalDetails);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    //게시글 상세보기
    @GetMapping("/api/v1/board/{boardId}")
    public ApiResponse<DetailResponse> DetailBoard (
             @AuthenticationPrincipal PrincipalDetails principalDetails
            , @PathVariable Long boardId) {

        return ApiResponse.ok(itemService.DetailBoard(boardId));
    }

   //게시글 페이징 반환

    @GetMapping("/api/v1/Item")
    public ApiResponse<Page> getPagingBoard (
            @AuthenticationPrincipal PrincipalDetails principalDetails
            , @PageableDefault(size = 6)Pageable pageable) {

        return ApiResponse.ok(itemService.DetailBoard(pageable));
    }
}
