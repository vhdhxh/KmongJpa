package com.talentmarket.KmongJpa.repository;

import com.talentmarket.KmongJpa.entity.Item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findByTitle(String title);
    @Query("select I from Item I left join fetch I.users left join fetch I.comment left join fetch I.comment.users where I.Id = :Id")
    Optional<Item> findItemAndComment(@Param("Id") Long Id);


    @Query("select I from Item I join fetch I.users")
    Page<Item> findItem(Pageable pageable);
    List<Item> findAllByIdIn(List<Long> itemIds);

//    @Query("select b from Board b where b.board_id = b.comment.board_id")
//    Optional<Board> findBoardAndComment();
}
