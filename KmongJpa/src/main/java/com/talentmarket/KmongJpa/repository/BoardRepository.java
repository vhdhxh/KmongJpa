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
    @Query("select b from Board b join fetch b.users where b.Id = :Id")
    Optional<Board> findBoard(Long Id);

    @Query("select b from Board b join fetch b.users")
    Page<Board> findBoard(Pageable pageable);

}
