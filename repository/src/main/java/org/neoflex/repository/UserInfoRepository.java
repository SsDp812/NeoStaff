package org.neoflex.repository;

import org.neoflex.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
}
