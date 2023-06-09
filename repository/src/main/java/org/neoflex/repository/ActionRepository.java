package org.neoflex.repository;

import org.neoflex.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;

public interface ActionRepository extends JpaRepository<Action,Long> {
    @Query("SELECT a FROM Action " +
            "JOIN ActionType at " +
            "WHERE at.interval <> null AND a.isDeleted = false")
    List<Action> findAllRepeatables();

    @Query("SELECT a FROM Action " +
            "JOIN ActionType at " +
            "WHERE at.isNotify = true AND a.isDeleted = false AND date <= :dateTime")
    List<Action> findAllNeedNotify(ZonedDateTime dateTime);
}
