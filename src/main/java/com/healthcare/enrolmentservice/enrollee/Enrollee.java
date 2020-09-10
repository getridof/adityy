package com.healthcare.enrolmentservice.enrollee;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.healthcare.enrolmentservice.dependent.Dependent;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity(name = "Enrollee")
@Table(name = "enrollee")
@NoArgsConstructor
public class Enrollee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ENROL_ID")
    private UUID id;

    private String name;
    private boolean activationStatus;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfBirth;

    private String phoneNumber;

    @OneToMany(mappedBy = "enrollee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("enrollee")
    private Set<Dependent> dependents = new HashSet<>();

}
