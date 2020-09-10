package com.healthcare.enrolmentservice.dependent;

import com.healthcare.enrolmentservice.enrollee.Enrollee;
import com.healthcare.enrolmentservice.enrollee.EnrolleeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
public class DependentController {

    @Autowired
    private DependentRepository dependentRepository;

    @Autowired
    private EnrolleeRepository enrolleeRepository;

    @GetMapping("/enrollees/{enrolleeID}/dependents")
    public ResponseEntity<Set<Dependent>> getAllDependents(@PathVariable(value = "enrolleeID") String enrolleeID) {

        Optional<Enrollee> enrollee = enrolleeRepository.findById(UUID.fromString(enrolleeID));

        if(enrollee.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(enrollee.get().getDependents());
    }

    @PostMapping("/enrollees/{enrolleeID}/dependents")
    public ResponseEntity<Dependent> createDependent(@Validated @RequestBody Dependent dependent,
                                                     @PathVariable(value = "enrolleeID") UUID enrolleeID) {
        Optional<Enrollee> enrollee = enrolleeRepository.findById(enrolleeID);
        if(enrollee.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Enrollee enrollee1 = enrollee.get();
        dependent.setEnrollee(enrollee1);
        Dependent savedDependent = dependentRepository.save(dependent);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{enrolleeID}")
                .buildAndExpand(savedDependent.getId()).toUri();
        return  ResponseEntity.created(location).build();
    }

    @PutMapping("/enrollees/{enrolleeID}/dependents/{dependentID}")
    public ResponseEntity<Dependent> updateDependent(@PathVariable(value = "dependentID") UUID dpid,
                                                     @PathVariable(value = "enrolleeID") UUID enrid,
                                                     @RequestBody
                                                     Dependent dependent){
        if(! enrolleeRepository.existsById(enrid)) {
            return ResponseEntity.notFound().build();
        }
        Optional<Dependent> optionalDependent = dependentRepository.findById(dpid);
        if(optionalDependent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        dependentRepository.save(dependent);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/enrollees/{enrolleeID}/dependents/{dependentID}")
    public ResponseEntity<Dependent> deleteDependent(@PathVariable(value = "dependentID") UUID dpID,
                                @PathVariable(value = "enrolleeID") UUID enrID){
        if(!enrolleeRepository.existsById(enrID) || !dependentRepository.existsById(dpID)) {
            return ResponseEntity.notFound().build();
        }
        dependentRepository.deleteById(dpID);
        return ResponseEntity.ok().build();
    }


}
