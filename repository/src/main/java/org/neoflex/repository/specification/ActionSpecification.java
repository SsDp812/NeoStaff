package org.neoflex.repository.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.neoflex.model.Action;
import org.neoflex.model.ActionType;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class ActionSpecification {
    public static Specification<Action> getAllRepeatables(ZonedDateTime dateTime) {
        return (root, query, cb) -> {
            Join<Action, ActionType> joinActionType = root.join("type");

            ZonedDateTime startDay = dateTime.truncatedTo(ChronoUnit.DAYS);
            ZonedDateTime startNextDay = startDay.plusDays(1);

            Predicate isNeedRepeat = cb.isNotNull(joinActionType.get("interval"));
            Predicate isNotDeleted = cb.equal(root.get("isDeleted"), false);
            Predicate isDateToday = cb.between(root.get("date"), startDay, startNextDay);

            return query.where(cb.and(isNeedRepeat, isNotDeleted, isDateToday))
                    .getRestriction();
        };
    }
    public static Specification<Action> getAllNeedNotify(ZonedDateTime dateTime) {
        return (root, query, cb) -> {
            Join<Action, ActionType> joinActionType = root.join("type");

            ZonedDateTime startDay = dateTime.truncatedTo(ChronoUnit.DAYS);
            ZonedDateTime startNextDay = startDay.plusDays(1);

            Predicate isNeedNotify = cb.equal(joinActionType.get("isNotify"), true);
            Predicate isNotDeleted = cb.equal(root.get("isDeleted"), false);
            Predicate isThatDate = cb.between(root.get("date"), startDay, startNextDay);

            return query.where(cb.and(isNeedNotify, isNotDeleted, isThatDate))
                    .getRestriction();
        };
    }
}