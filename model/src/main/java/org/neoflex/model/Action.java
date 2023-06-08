package org.neoflex.model;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "actions")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User employee;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private ActionType type;

    private ZonedDateTime date;

    @Column(name = "comment")
    private String comment;

    private Period interval;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
