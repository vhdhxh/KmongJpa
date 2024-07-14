package com.talentmarket.KmongJpa.sms.presentation;

import com.talentmarket.KmongJpa.global.ApiResponse;
import com.talentmarket.KmongJpa.sms.application.CodeSender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CodeController {
    private final CodeSender codeSender;
    @PostMapping("/addCode")
    public ApiResponse addCode() {
        return ApiResponse.ok(codeSender.saveCode());
    }

    @GetMapping("/findCode")
    public ApiResponse findCode(@RequestParam(name = "id") Long id) {

        return ApiResponse.ok(codeSender.findCode(id));
    }
}
