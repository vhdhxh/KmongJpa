package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.Dto.WriteRequest;
import com.talentmarket.KmongJpa.entity.Board;
import com.talentmarket.KmongJpa.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    //글쓰기
    public Long WriteBoard(WriteRequest request) {
        Board board = Board.createBoard(request);
        return  boardRepository.save(board).getId();

    }
}
