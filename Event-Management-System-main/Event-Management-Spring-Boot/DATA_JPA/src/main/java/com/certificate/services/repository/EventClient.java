package com.certificate.services.repository;

import com.certificate.services.dto.EventDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "EVENT")
public interface EventClient {
    @GetMapping("/event/getEvent/{id}")
    EventDTO getEventById(@PathVariable("id") Integer id);
}

