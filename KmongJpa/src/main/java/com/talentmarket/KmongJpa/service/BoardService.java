package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.Dto.BoardPagingResponse;
import com.talentmarket.KmongJpa.Dto.DetailResponse;
import com.talentmarket.KmongJpa.Dto.RegisterRequest;
import com.talentmarket.KmongJpa.Dto.WriteRequest;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.entity.Board;
import com.talentmarket.KmongJpa.exception.CustomException;
import com.talentmarket.KmongJpa.exception.ErrorCode;
import com.talentmarket.KmongJpa.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public DetailResponse DetailBoard(Long boardId) {
       Board board = boardRepository.findBoardAndComment(boardId)
               .orElseThrow(()->new CustomException(ErrorCode.BOARD_NOT_FOUND));

       return DetailResponse.ToDto(board);
    }

//    게시글 페이징 반환
    @Transactional(readOnly = true)
    public Page<BoardPagingResponse> DetailBoard(Pageable pageable) {
     Page<Board> boards = boardRepository.findBoard(pageable);
        Page<BoardPagingResponse> map = boards.map(b->BoardPagingResponse.builder()
                .writer(b.getUsers().getName()).price(b.getPrice()).title(b.getTitle()).thumbnail(b.getThumbnail()).build());
        return map;
    }

    
}
