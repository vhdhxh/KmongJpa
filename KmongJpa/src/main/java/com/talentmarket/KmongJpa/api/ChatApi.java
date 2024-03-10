package com.talentmarket.KmongJpa.api;

import com.talentmarket.KmongJpa.Dto.*;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatApi {
   private final ChatService chatService;
   private final SimpMessagingTemplate template;
    @MessageMapping("/room/{roomId}/{nickname}")
    //StompConfig에 적힌 setApplicationDestinationPrefixes  /chat/send 제외해서 작성한다.
    public void message(@RequestBody SendRequest req, @DestinationVariable Long roomId, @DestinationVariable String nickname) throws Exception {
        //채팅을 치면 url에 적힌 roomId, nickname 과 클라이언트에서 받은 json데이터를 chatDTO 로받고 DB에 저장한다.

              SendResponse sendResponse = chatService.sendMessage(req,nickname);

        template.convertAndSend("/chat/receive/" + nickname, sendResponse);
        template.convertAndSend("/chat/receive/room/" + roomId + "/" + nickname, sendResponse);
        // StompConfig에 적힌 enableSimpleBroker 도 포함해서 작성해야한다.
    }


    //원래 플로우
    //문의하기 버튼을 누르면,채팅방 생성후, 채팅방 id를 넘겨준다.
    //바뀐 플로우
    //문의하기 버튼을 누르면, 상대와 나의 채팅 방이 있는지 확인하고, 없다면 만들어서 채팅방 id를 넘겨주고
    // 있다면 기존 채팅방 id를 넘겨준다.
    @PostMapping("api/chat/{user2Id}")
    public ApiResponse createRoom(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable("user2Id") Long user2Id) {


       Long RoomId = chatService.createRoom(principalDetails, user2Id);
        //만들어진 채팅방 roomId 를  매개변수에 넣어서 채팅방 인원들을 추가한다.

        return ApiResponse.ok(RoomId);
    }

    //로그인 한 유저의 채팅 목록을 불러옵니다
    @GetMapping("/api/chatList")
    public ApiResponse getChatList (@AuthenticationPrincipal PrincipalDetails principalDetails) {
       List<ChatListResponse> chatListResponses = chatService.getChatList(principalDetails);
        return ApiResponse.ok(chatListResponses);
    }
    //유저의 채팅방의 메세지 기록을 불러옵니다
    @GetMapping("/api/chat/{roomId}")
    public ApiResponse getChat (@AuthenticationPrincipal PrincipalDetails principalDetails
                              , @RequestBody ChatRequest request
                              , @PathVariable Long roomId) {
        List<ChatResponse> chatResponses = chatService.getChat(principalDetails,request,roomId);
        return ApiResponse.ok(chatResponses);
    }
}
