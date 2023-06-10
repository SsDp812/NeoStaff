package org.neoflex.repository;

import org.neoflex.model.ActionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Duration;
import java.util.List;

public interface ActionTypeRepository extends JpaRepository<ActionType,Long> {

    List<ActionType> findByIntervalIsNotNull();
}
