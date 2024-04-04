package com.talentmarket.KmongJpa.user.repository;


import com.talentmarket.KmongJpa.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {

    Optional<Users> findByEmail(String email);

    Optional<Users> findByEmailAndNameAndPhone(String email, String name, String phone);

    Optional<Users> findByProviderAndProviderId(String provider, Long id);

    boolean existsByProviderAndProviderId(String provider,Long id);


}
