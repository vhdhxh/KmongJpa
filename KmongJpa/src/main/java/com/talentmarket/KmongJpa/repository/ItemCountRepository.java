package com.talentmarket.KmongJpa.repository;

import com.talentmarket.KmongJpa.entity.Itemcount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ItemCountRepository extends JpaRepository<Itemcount,Long> {


    @Modifying
    @Query("update Itemcount i set i.itemcount = i.itemcount + 1 ")
   void plusCount ();

    @Modifying
    @Query("update Itemcount i set i.itemcount = i.itemcount - 1 ")
    void minusCount();
}
