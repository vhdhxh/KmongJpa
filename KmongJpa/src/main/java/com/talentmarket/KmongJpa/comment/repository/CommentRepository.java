package com.talentmarket.KmongJpa.comment.repository;

import com.talentmarket.KmongJpa.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long > {
}
