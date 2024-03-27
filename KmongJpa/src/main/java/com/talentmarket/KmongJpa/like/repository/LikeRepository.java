package com.talentmarket.KmongJpa.like.repository;

import com.talentmarket.KmongJpa.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {



    List<Like> findLikesByItemId( Long id);

    Long countByItemId(Long itemId);
    Optional<Like> findLikesByItemIdAndUsersId(Long itemId ,Long userId);
    void deleteByUsersIdAndItemId(Long userId, Long itemId);
}
