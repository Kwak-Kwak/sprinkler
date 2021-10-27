package com.sprinkler.kwakkwak.repository;

import com.sprinkler.kwakkwak.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    // user code로 profile 찾기
    Optional<Profile> findByUserInfo_Code(Long code);


    // username이 있는지 확인
    boolean existsByUserName(String userName);
}
