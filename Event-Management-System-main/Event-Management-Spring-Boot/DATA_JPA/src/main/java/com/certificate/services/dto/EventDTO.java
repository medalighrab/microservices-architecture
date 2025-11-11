package com.certificate.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {

    private Integer eventId;
    private String name;
    private String category;
    private String venue;
    private LocalDate date;
    private LocalTime time;
    private Integer price;
    private String description;
    private Long userId;   // ✅ Long localement (ok)
}