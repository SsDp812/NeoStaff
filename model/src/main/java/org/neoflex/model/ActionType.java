package org.neoflex.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.Interval;
import org.hibernate.annotations.CollectionId;
import org.neoflex.model.converter.DurationConverter;

import java.time.Duration;
import java.time.Period;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "action_types")
public class ActionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "interval")
    @Convert(converter = DurationConverter.class)
    private Duration interval;

    @Column(name = "is_notify")
    private Boolean isNotify;
}
