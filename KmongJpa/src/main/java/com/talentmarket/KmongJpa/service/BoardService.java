package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.Dto.WriteRequest;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.entity.Board;
import com.talentmarket.KmongJpa.exception.CustomException;
import com.talentmarket.KmongJpa.exception.ErrorCode;
import com.talentmarket.KmongJpa.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    //글쓰기
    @Transactional
    public Long WriteBoard(WriteRequest request, PrincipalDetails principalDetails) {
        Board board = Board.createBoard(request , principalDetails);
        return  boardRepository.save(board).getId();

    }
    //게시글 수정
    @Transactional
    public Long UpdateBoard(WriteRequest writeRequest,  Long boardId) {
       Board board = boardRepository.findById(boardId)
               .orElseThrow(()->new CustomException(ErrorCode.BOARD_NOT_FOUND));
         return board.updateBoard(writeRequest);
    }
    //게시글 삭제
    @Transactional
    public void DeleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()->new CustomException(ErrorCode.BOARD_NOT_FOUND));
        boardRepository.delete(board);
    }

    //게시글 상세보기
    @Transactional(readOnly = true)
    public WriteRequest DetailBoard(Long boardId) {
       Board board = boardRepository.findById(boardId)
               .orElseThrow(()->new CustomException(ErrorCode.BOARD_NOT_FOUND));

       return WriteRequest.ToDto(board);
    }

    
}
