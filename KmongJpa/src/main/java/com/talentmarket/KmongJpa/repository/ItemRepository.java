package com.talentmarket.KmongJpa.repository;

import com.talentmarket.KmongJpa.entity.Item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item,Long> ,ItemRepositoryCustom{
    List<Item> findByTitle(String title);
    @Query("select I from Item I left join fetch I.users left join fetch I.comment left join fetch I.comment.users where I.Id = :Id")
    Optional<Item> findItemAndComment(@Param("Id") Long Id);


    //이 쿼리의 문제점 : 이쿼리는 count 쿼리와 아이템 리스트를 불러오는 쿼리 두번의 쿼리가 나간다.
    //count 쿼리는 데이터가 많아질수록 부담이 크다 .
    //또한 페이지가 뒤로갈수록 응답속도가 느려진다.
    @Query("select I from Item I join fetch I.users")
    Page<Item> findItem(Pageable pageable);
    List<Item> findAllByIdIn(List<Long> itemIds);

//    @Query("select b from Board b where b.board_id = b.comment.board_id")
//    Optional<Board> findBoardAndComment();
}
