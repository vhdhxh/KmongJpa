package com.talentmarket.KmongJpa.repository;

import com.talentmarket.KmongJpa.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findByTitle(String title);
}
