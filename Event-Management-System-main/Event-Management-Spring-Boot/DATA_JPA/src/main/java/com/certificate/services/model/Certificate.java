package com.certificate.services.model;

import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "certificate") // ⚠️ nom de table en minuscule
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long eventId;    // ID de l'événement lié
    private Integer userId;  // ID du participant ou organisateur
    private String userName;
    private String eventTitle;
    private String pdfUrl;   // lien vers le PDF généré
}
