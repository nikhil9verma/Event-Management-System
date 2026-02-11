package com.example.Event_Manager.repository;

import com.example.Event_Manager.model.Event;
import com.example.Event_Manager.model.Registration;
import com.example.Event_Manager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration,Long> {

    boolean existsByStudentAndEvent(User student, Event event);
    long countByEvent(Event event);
    List<Registration> findByStudent(User student);

}
