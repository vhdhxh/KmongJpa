package com.talentmarket.KmongJpa.api;

import com.talentmarket.KmongJpa.Dto.ApiResponse;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatApi {
   private final ChatService chatService;

    //원래 플로우
    //문의하기 버튼을 누르면,채팅방 생성후, 채팅방 id를 넘겨준다.
    //바뀐 플로우
    //문의하기 버튼을 누르면, 상대와 나의 채팅 방이 있는지 확인하고, 없다면 만들어서 채팅방 id를 넘겨주고
    // 있다면 기존 채팅방 id를 넘겨준다.
    @PostMapping("/chat/{user2Id}")
    public ApiResponse createRoom(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long user2Id) {


        chatService.createRoom(principalDetails, user2Id);
        //만들어진 채팅방 roomId 를  매개변수에 넣어서 채팅방 인원들을 추가한다.

        return ApiResponse.ok(null);
    }
}
