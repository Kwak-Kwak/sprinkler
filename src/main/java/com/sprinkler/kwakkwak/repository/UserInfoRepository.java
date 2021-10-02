package com.sprinkler.kwakkwak.repository;

import com.sprinkler.kwakkwak.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
}
