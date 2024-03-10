package com.talentmarket.KmongJpa.api;

import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebController {

    @GetMapping("/")
    public String main () {

        return "main";
    }

    @GetMapping({"/chat", "/chat/{roomId}/{nickname}"})
    public String myChat(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        model.addAttribute("user", principalDetails.getDto());

        return "chat";

    }
    @GetMapping("/user/account-update")
    public String accountupdate() {
        return "userupdate";
    }

//    @GetMapping("/user/profile")
//    public String profile(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
//        ProfileDTO profileDTO = profileService.selectProfile(principalDetails.getDto().getUser_id());
//        model.addAttribute("profile",profileDTO);
//        return "userprofile";
//    }

    @GetMapping("/board/write")
    public String write() {
        return "write";
    }

    @GetMapping("/board/{board_id}")
    public String detail(@PathVariable String board_id) {

        return "boarddetail";
    }

//    @GetMapping("/board/update/{board_id}")
//    public String update(@PathVariable String board_id, Model model) {
//
//        model.addAttribute("board",boardService.findPost(board_id));
//        return "update";
//    }
//
//    @GetMapping("/order/{id}")
//    public String order(@ModelAttribute("params") ItemDto itemDto) {
//
//        return "payments";
//    }
    @GetMapping("/user/order")
    public String getOrder() {
        return "order";
    }

    @GetMapping("/profile/{userid}")
    public String profile() {
        return "profile";
    }

    @GetMapping("/user/myBoards")
    public String myBoards() {return "myBoards";}

    @GetMapping("/user/myComments")
    public String myComments() {return "myComments";}

    @GetMapping("/user/mySales")
    public String mySales() {return "mySales";}

    @GetMapping("/uploadtest")
    public String uploadtest() {
        return "uploadtest";
    }
}
