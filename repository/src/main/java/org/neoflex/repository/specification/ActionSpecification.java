package org.neoflex.repository.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.neoflex.model.Action;
import org.neoflex.model.ActionType;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;

public class ActionSpecification {
    public static Specification<Action> getAllRepeatables() {
        return (root, query, cb) -> {
            Join<Action, ActionType> joinActionType = root.join("actionType");

            Predicate intervalNotNull = cb.isNotNull(joinActionType.get("interval"));
            Predicate isNotDeleted = cb.equal(root.get("isDeleted"), false);

            return query.where(cb.and(intervalNotNull, isNotDeleted))
                    .getRestriction();
        };
    }
    public static Specification<Action> getAllNeedNotify(ZonedDateTime dateTime) {
        return (root, query, cb) -> {
            Join<Action, ActionType> joinActionType = root.join("actionType");

            Predicate isNeedNotify = cb.equal(joinActionType.get("isNotify"), true);
            Predicate isNotDeleted = cb.equal(root.get("isDeleted"), false);
            Predicate isNeedNotificationNow = cb.lessThanOrEqualTo(root.get("date"), dateTime);

            return query.where(cb.and(isNeedNotify, isNotDeleted, isNeedNotificationNow))
                    .getRestriction();
        };
    }
}