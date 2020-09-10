package com.healthcare.enrolmentservice.dependent;

import com.healthcare.enrolmentservice.enrollee.Enrollee;
import com.healthcare.enrolmentservice.enrollee.EnrolleeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
public class DependentController {

    @Autowired
    private DependentRepository dependentRepository;

    @Autowired
    private EnrolleeRepository enrolleeRepository;

    @Autowired
    private DependentService dependentService;

    @GetMapping("/enrollees/{enrolleeID}/dependents")
    public ResponseEntity<Set<Dependent>> getAllDependents(@PathVariable(value = "enrolleeID") String enrolleeID) {

        Set<Dependent> dependents = dependentService.getAllDependentsOfEnrollee(enrolleeID);

        if(Objects.isNull(dependents)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dependents);
    }

    @PostMapping("/enrollees/{enrolleeID}/dependents")
    public ResponseEntity<Dependent> createDependent(@Validated @RequestBody Dependent dependent,
                                                     @PathVariable(value = "enrolleeID") String enrolleeID) {
        Dependent savedDependent = dependentService.createDependent(enrolleeID, dependent);
        if(Objects.isNull(savedDependent)) {
            return ResponseEntity.notFound().build();
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{enrolleeID}")
                .buildAndExpand(savedDependent.getId()).toUri();
        return  ResponseEntity.created(location).build();
    }

    @PutMapping("/enrollees/{enrolleeID}/dependents/{dependentID}")
    public ResponseEntity<Dependent> updateDependent(@PathVariable(value = "dependentID") String dpid,
                                                     @PathVariable(value = "enrolleeID") String enrid,
                                                     @RequestBody
                                                     Dependent dependent){
        if(Objects.isNull(dependentService.updateDependent(enrid, dpid, dependent)))
            return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/enrollees/{enrolleeID}/dependents/{dependentID}")
    public ResponseEntity<Dependent> deleteDependent(@PathVariable(value = "dependentID") String dpID,
                                @PathVariable(value = "enrolleeID") String enrID){

        return dependentService.deleteDependent(enrID, dpID) ? ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }


}
