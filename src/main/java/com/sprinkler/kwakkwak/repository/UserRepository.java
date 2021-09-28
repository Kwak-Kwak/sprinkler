package com.sprinkler.kwakkwak.repository;

import java.util.Optional;
import com.sprinkler.kwakkwak.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByEmail(String email);
}
