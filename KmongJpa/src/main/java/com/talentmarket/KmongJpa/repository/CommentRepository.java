package com.talentmarket.KmongJpa.repository;

import com.talentmarket.KmongJpa.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long > {
}
