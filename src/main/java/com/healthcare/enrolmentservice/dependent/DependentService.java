package com.healthcare.enrolmentservice.dependent;


import com.healthcare.enrolmentservice.enrollee.Enrollee;
import com.healthcare.enrolmentservice.enrollee.EnrolleeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class DependentService {

    @Autowired
    private EnrolleeRepository enrolleeRepository;

    @Autowired
    private DependentRepository dependentRepository;

    public Set<Dependent> getAllDependentsOfEnrollee(String enrID) {
        UUID enrolleeUUID = UUID.fromString(enrID);
        Optional<Enrollee> enrollee = enrolleeRepository.findById(enrolleeUUID);

        if(enrollee.isEmpty()) {
            return null;
        }
        return enrollee.get().getDependents();
    }

    public Dependent createDependent(String enrID, Dependent dependent){
        UUID enrolleeUUID = UUID.fromString(enrID);
        Optional<Enrollee> enrollee = enrolleeRepository.findById(enrolleeUUID);
        if(enrollee.isEmpty()) {
            return null;
        }
        Enrollee enrollee1 = enrollee.get();
        dependent.setEnrollee(enrollee1);
        return dependentRepository.save(dependent);
    }

    public Dependent updateDependent(String enrID, String depID, Dependent dependent) {

        UUID enrUUID = UUID.fromString(enrID);
        UUID depUUID = UUID.fromString(depID);

        Optional<Enrollee> enrollee = enrolleeRepository.findById(enrUUID);

        if(enrollee.isEmpty()) {
            return null;
        }

        Optional<Dependent> optionalDependent = dependentRepository.findById(depUUID);
        if(optionalDependent.isEmpty()) {
            return null;
        }
        dependent.setId(depUUID);
        dependent.setEnrollee(enrollee.get());
        return dependentRepository.save(dependent);
    }

    public boolean deleteDependent(String enrID, String depID){
        UUID enrUUID = UUID.fromString(enrID);
        UUID depUUID = UUID.fromString(depID);
        if(!enrolleeRepository.existsById(enrUUID) || !dependentRepository.existsById(depUUID)) {
            return false;
        }
        dependentRepository.deleteById(depUUID);
        return true;
    }
}
