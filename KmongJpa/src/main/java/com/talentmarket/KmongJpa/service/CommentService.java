package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.Dto.CommentWriteDto;
import com.talentmarket.KmongJpa.entity.Board;
import com.talentmarket.KmongJpa.entity.Comment;
import com.talentmarket.KmongJpa.repository.BoardRepository;
import com.talentmarket.KmongJpa.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public void CommentWrite (CommentWriteDto commentWriteDto) {
       Board board = boardRepository.findById(commentWriteDto.getBoardId()).get();
       Comment comment = Comment.builder()
               .board(board)
               .contents(commentWriteDto.getContents())
               .build();
       commentRepository.save(comment);
    }
}
