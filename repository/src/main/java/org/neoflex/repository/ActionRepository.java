package org.neoflex.repository;

import org.neoflex.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

public interface ActionRepository extends JpaRepository<Action,Long>, JpaSpecificationExecutor<Action> {
}
