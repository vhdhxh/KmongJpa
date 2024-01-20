package com.talentmarket.KmongJpa.repository;


import com.talentmarket.KmongJpa.Dto.LoginDto;
import com.talentmarket.KmongJpa.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {

    Users findAllByEmail(String email);


}
