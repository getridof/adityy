package com.healthcare.enrolmentservice.enrollee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
public class EnrolleeController {

    @Autowired
    private EnrolleeRepository enrolleeRepository;

    @Autowired
    private EnrolleeService enrolleeService;

    @PostMapping("/enrollees")
    public ResponseEntity<Enrollee> createEnrollee(@RequestBody final Enrollee enrollee) {

        Enrollee savedEnrollee = enrolleeService.createEnrollee(enrollee);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedEnrollee.getId()).toUri();

        return  ResponseEntity.created(location).build();
    }

    @PutMapping("/enrollees/{enrolleeID}")
    public ResponseEntity<Enrollee> updateEnrollee(@RequestBody final Enrollee enrollee,
                                                   @PathVariable("enrolleeID") String enrolleID) {

        Enrollee enrollee1 = enrolleeService.updateEnrollee(enrollee, enrolleID);

        if(Objects.isNull(enrollee1))
            return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/enrollees/{enrolleeID}")
    public ResponseEntity<Enrollee> deleteEnrollee(@PathVariable("enrolleeID") String enrolleeID) {
        enrolleeService.deleteEnrollee(enrolleeID);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/enrollees")
    public ResponseEntity<List<Enrollee>> getAllEnrolles() {
        return ResponseEntity.ok(enrolleeService.getAllEnrollee());
    }
}
