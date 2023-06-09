package org.neoflex.repository;

import org.neoflex.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByJiraAccountId(String jiraAccountId);

}
