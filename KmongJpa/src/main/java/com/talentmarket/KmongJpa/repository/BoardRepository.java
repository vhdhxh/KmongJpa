package com.talentmarket.KmongJpa.repository;

import com.talentmarket.KmongJpa.entity.Board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findByTitle(String title);
    @Query("select b from Board b left join fetch b.users left join fetch b.comment left join fetch b.comment.users where b.Id = :Id")
    Optional<Board> findBoardAndComment(Long Id);

    @Query("select b from Board b join fetch b.users")
    Page<Board> findBoard(Pageable pageable);

//    @Query("select b from Board b where b.board_id = b.comment.board_id")
//    Optional<Board> findBoardAndComment();
}
