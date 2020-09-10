package com.healthcare.enrolmentservice.dependent;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DependentRepository extends JpaRepository<Dependent, UUID> {

}
