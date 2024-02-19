package com.talentmarket.KmongJpa.repository;

import com.talentmarket.KmongJpa.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {

    @Query("SELECT l FROM Like l WHERE l.board.id = :id")
    Optional<Like> findLikesByBoardId(@Param("id") Long id);
}
