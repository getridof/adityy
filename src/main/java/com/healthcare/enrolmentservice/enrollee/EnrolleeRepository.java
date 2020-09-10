package com.healthcare.enrolmentservice.enrollee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnrolleeRepository extends JpaRepository<Enrollee, UUID> {
}
