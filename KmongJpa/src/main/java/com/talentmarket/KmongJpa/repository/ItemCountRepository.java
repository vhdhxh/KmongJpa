package com.talentmarket.KmongJpa.repository;

import com.talentmarket.KmongJpa.entity.Itemcount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCountRepository extends JpaRepository<Itemcount,Long> {
}
