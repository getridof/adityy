package com.healthcare.enrolmentservice.enrollee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EnrolleeService {

    @Autowired
    private EnrolleeRepository enrolleeRepository;

    public Enrollee createEnrollee(Enrollee enrollee){
        return enrolleeRepository.save(enrollee);
    }

    public Enrollee updateEnrollee(Enrollee enrollee, String enrolleID) {
        Optional<Enrollee> enrolleeOptional = enrolleeRepository.findById(UUID.fromString(enrolleID));

        if(enrolleeOptional.isEmpty())
            return  null;

        enrollee.setId(UUID.fromString(enrolleID));
        return  enrolleeRepository.save(enrollee);

    }

    public void deleteEnrollee(String enrollee) {
        enrolleeRepository.deleteById(UUID.fromString(enrollee));
    }

    public List<Enrollee> getAllEnrollee(){
        return enrolleeRepository.findAll();
    }

}
