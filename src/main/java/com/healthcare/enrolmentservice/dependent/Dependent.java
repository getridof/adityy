package com.healthcare.enrolmentservice.dependent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.healthcare.enrolmentservice.enrollee.Enrollee;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "Dependent")
@Table(name = "dependent")
@NoArgsConstructor
public class Dependent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private LocalDate dateOfBirth;

    @ManyToOne( fetch = FetchType.LAZY)
    private Enrollee enrollee;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dependent )) return false;
        return id != null && id.equals(((Dependent) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
