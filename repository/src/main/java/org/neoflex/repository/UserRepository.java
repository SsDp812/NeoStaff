package org.neoflex.repository;

import org.neoflex.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByJiraAccountId(String jiraAccountId);
    Optional<User> findByEmail(String email);
}
