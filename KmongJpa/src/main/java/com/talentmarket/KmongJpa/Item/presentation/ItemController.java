package com.talentmarket.KmongJpa.Item.presentation;

import com.talentmarket.KmongJpa.Item.application.UploadService;
import com.talentmarket.KmongJpa.auth.util.AuthPrincipal;
import com.talentmarket.KmongJpa.global.ApiResponse;
import com.talentmarket.KmongJpa.Item.application.dto.DetailResponse;
import com.talentmarket.KmongJpa.Item.application.dto.WriteRequest;
import com.talentmarket.KmongJpa.Item.application.ItemService;
import com.talentmarket.KmongJpa.user.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final UploadService uploadService;

    //게시글 쓰기
    @PostMapping("/api/v1/board")
    public ResponseEntity writeBoard (@RequestBody WriteRequest writeRequest , @AuthPrincipal Users user) {

        return ResponseEntity.status(HttpStatus.OK).body(itemService.WriteBoard(writeRequest,user));
    }

    //게시글 수정
    @PostMapping("/api/v1/board/{boardId}")
    public ResponseEntity writeBoard (@RequestBody WriteRequest writeRequest
            , @AuthPrincipal Users user
            , @PathVariable Long boardId) {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.UpdateBoard(writeRequest,boardId,user));
    }

    //게시글 삭제
    @DeleteMapping ("/api/v1/board/{boardId}")
    public ResponseEntity deleteBoard (
            @AuthPrincipal Users user
            , @PathVariable Long boardId) {
        itemService.DeleteBoard(boardId , user);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    //게시글 상세보기
    @GetMapping("/api/v1/board/{boardId}")
    public ApiResponse<DetailResponse> DetailBoard (

             @PathVariable("boardId") Long boardId) {

        return ApiResponse.ok(itemService.DetailBoard(boardId));
    }

   //게시글 페이징 반환

    @GetMapping("/api/v1/Item")
    public ApiResponse<Page> getPagingBoard (
             @PageableDefault(size = 6)Pageable pageable) {

        return ApiResponse.ok(itemService.DetailBoard(pageable));
    }

    @GetMapping("/api/v2/Item")
    public ApiResponse<Page> getPagingBoard2 (
             @PageableDefault(size = 6)Pageable pageable) {
        System.out.println(itemService.getClass());

        return ApiResponse.ok(itemService.DetailBoard2(pageable));
    }

    //test
    @PostMapping("/file")
    public ApiResponse file (@ModelAttribute FileDto fileDto) {
        System.out.println("file name = " +  fileDto.getName() + "file array = " + fileDto.getFiles());
        System.out.println(fileDto.getFiles().get(0).getOriginalFilename());
        List<MultipartFile> files = fileDto.getFiles();
        List<String> url = uploadService.fileUpload(files);
        return ApiResponse.ok(url);
    }
    @PostMapping("/file1")
    public ApiResponse file1 (@ModelAttribute FileDto fileDto) throws IOException {
        System.out.println("file name = " +  fileDto.getName() + "file array = " + fileDto.getFiles());
        System.out.println(fileDto.getFiles().get(0).getOriginalFilename());
        List<MultipartFile> files = fileDto.getFiles();
        List<String> urls = uploadService.normalUpload(files);
        return ApiResponse.ok(urls);
    }

    @GetMapping("/cache")
    public ApiResponse cacheTest() {
        return ApiResponse.ok(null);
    }
}
