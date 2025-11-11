package com.microservice.event.Service;

import com.microservice.event.Entity.Event;

import java.util.List;
import java.util.Optional;

public interface IEventService {
    List<Event> getAllEvents();
    Optional<Event> getEventById(Long id);
    Event createEvent(Event event);
    boolean eventExists(Long id);
}
