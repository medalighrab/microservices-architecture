package com.certificate.services.service;

import com.certificate.services.dto.EventDTO;
import com.certificate.services.repository.CertificateRepository;
import com.certificate.services.repository.EventClient;
import com.certificate.services.model.Certificate;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CertificateService {

    private final CertificateRepository repository;
    private final EventClient eventClient; // 🔹 final + Lombok va générer le constructeur

    private final String STORAGE_PATH = "certificates/";

    public List<Certificate> getAll() {
        return repository.findAll();
    }

    public List<Certificate> getByUser(Long userId) {
        return repository.findByUserId(userId.intValue());
    }

    public Optional<Certificate> getById(Long id) {
        return repository.findById(id);
    }

    // ✅ Création du certificat avec récupération des infos de l'événement
    public Certificate create(Certificate cert) {
        try {
            // 🔹 Conversion Long → Integer pour compatibilité Feign
            Integer eventId = cert.getEventId().intValue();

            // 🔹 Appel du microservice d'événement via Feign
            EventDTO event = eventClient.getEventById(eventId);

            if (event == null) {
                throw new RuntimeException("Événement introuvable pour l'ID : " + cert.getEventId());
            }

            // 🔹 Génération du PDF
            String fileName = "certificate_" + System.currentTimeMillis() + ".pdf";
            Path directory = Path.of(STORAGE_PATH);
            if (!Files.exists(directory)) Files.createDirectories(directory);

            String filePath = STORAGE_PATH + fileName;

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, BaseColor.BLUE);
            Font textFont = new Font(Font.FontFamily.HELVETICA, 14);

            document.add(new Paragraph("Certificate of Participation", titleFont));
            document.add(new Paragraph("\n\nThis certificate is awarded to:", textFont));
            document.add(new Paragraph(cert.getUserName(), new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD)));

            document.add(new Paragraph("\nFor participating in the event:", textFont));
            document.add(new Paragraph(event.getName(), new Font(Font.FontFamily.HELVETICA, 16)));

            document.add(new Paragraph("\nCategory: " + event.getCategory(), textFont));
            document.add(new Paragraph("Venue: " + event.getVenue(), textFont));
            document.add(new Paragraph("Date: " + event.getDate() + " at " + event.getTime(), textFont));

            document.add(new Paragraph("\n\nGenerated on: " + java.time.LocalDate.now(), textFont));
            document.add(new Paragraph("\n\nSignature: ______________________", textFont));

            document.close();

            // 🔹 Enregistrer les infos
            cert.setEventTitle(event.getName());
            cert.setPdfUrl("/api/certificates/pdf/" + fileName);
            repository.save(cert);

            return cert;

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du certificat PDF", e);
        }
    }

    public Certificate update(Long id, Certificate updated) {
        return repository.findById(id).map(existing -> {
            existing.setEventTitle(updated.getEventTitle());
            existing.setUserName(updated.getUserName());
            existing.setUserId(updated.getUserId());
            existing.setEventId(updated.getEventId());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Certificat non trouvé"));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public byte[] getPdfFile(String fileName) {
        try {
            Path path = Path.of(STORAGE_PATH + fileName);
            return Files.readAllBytes(path);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier PDF", e);
        }
    }
}
