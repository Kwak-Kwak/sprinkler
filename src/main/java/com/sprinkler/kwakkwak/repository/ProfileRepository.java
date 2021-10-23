package com.sprinkler.kwakkwak.repository;

import com.sprinkler.kwakkwak.domain.Profile;
import com.sprinkler.kwakkwak.domain.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUserInfo_Code(Long code);
}
