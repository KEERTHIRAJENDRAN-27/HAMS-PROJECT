package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.UserInfo;

import java.util.List;
import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByName(String username);

}
