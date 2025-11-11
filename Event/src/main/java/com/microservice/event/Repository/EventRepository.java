package com.microservice.event.Repository;

import com.microservice.event.Entity.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsById(Long id); // Pour ton EventClient
}
