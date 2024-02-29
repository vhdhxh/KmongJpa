package com.talentmarket.KmongJpa.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    EMAIL_DUPLICATED(HttpStatus.BAD_REQUEST,"중복된 이메일입니다."),
    USER_WITHDRAWLED(HttpStatus.BAD_REQUEST,"이미 탈퇴된 회원입니다."),
    USER_NOT_LOGIN(HttpStatus.BAD_REQUEST,"로그인이 필요합니다."),
    ITEM_NOT_FOUND(HttpStatus.BAD_REQUEST,"상품을 찾을 수 없습니다."),
    COUNTED_LIKE(HttpStatus.BAD_REQUEST,"이미 찜 등록한 게시글입니다."),
    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST,"댓글을 찾을 수 없습니다"),
    NOT_COUNTED_LIKE(HttpStatus.BAD_REQUEST,"찜등록한 게시물이 아닙니다"),
    CART_NOT_FOUND(HttpStatus.BAD_REQUEST,"등록된 장바구니가 없습니다"),
    STOCK_SOLD_OUT(HttpStatus.BAD_REQUEST,"품절된 제품입니다");

    private HttpStatus httpStatus;
    private String message;
}
