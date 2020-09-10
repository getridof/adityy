package com.healthcare.enrolmentservice.enrollee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class EnrolleeController {

    @Autowired
    private EnrolleeRepository enrolleeRepository;

    @PostMapping("/enrollees")
    public ResponseEntity<Enrollee> createEnrollee(@RequestBody final Enrollee enrollee) {

        Enrollee savedEnrollee = enrolleeRepository.save(enrollee);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedEnrollee.getId()).toUri();

        return  ResponseEntity.created(location).build();
    }

    @PutMapping("/enrollees/{enrolleeID}")
    public ResponseEntity<Enrollee> updateEnrollee(@RequestBody final Enrollee enrollee,
                                                   @PathVariable("enrolleeID") UUID enrolleID) {

        Optional<Enrollee> enrolleeOptional = enrolleeRepository.findById(enrolleID);

        if(enrolleeOptional.isEmpty())
            return ResponseEntity.notFound().build();

        enrollee.setId(enrolleID);
        enrolleeRepository.save(enrollee);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/enrollees/{enrolleeID}")
    public ResponseEntity<Enrollee> deleteEnrollee(@PathVariable UUID enrolleeID) {
        enrolleeRepository.deleteById(enrolleeID);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/enrollees")
    public ResponseEntity<List<Enrollee>> getAllEnrolles() {
        return ResponseEntity.ok(enrolleeRepository.findAll());
    }
}
