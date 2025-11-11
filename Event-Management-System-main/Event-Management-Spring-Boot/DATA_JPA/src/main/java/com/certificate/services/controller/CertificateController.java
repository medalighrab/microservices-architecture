package com.certificate.services.controller;
import com.certificate.services.service.CertificateService;
import com.certificate.services.model.Certificate;
import lombok.RequiredArgsConstructor;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;


@RestController
@RequestMapping("/api/certificates")

@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class CertificateController {

    private final CertificateService service;

    @GetMapping
    public List<Certificate> getAll() {
        return service.getAll();
    }

    @GetMapping("/user/{userId}")
    public List<Certificate> getByUser(@PathVariable Long userId) { // Long
        return service.getByUser(userId);
    }

    @GetMapping("/{id}")
    public Certificate getById(@PathVariable Long id) {
        return service.getById(id).orElseThrow(() -> new RuntimeException("Certificat introuvable"));
    }

    @PostMapping
    public Certificate create(@RequestBody Certificate cert) {
        return service.create(cert);
    }

    @PutMapping("/{id}")
    public Certificate update(@PathVariable Long id, @RequestBody Certificate cert) {
        return service.update(id, cert);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/pdf/{fileName}")
    public ResponseEntity<byte[]> getPdf(@PathVariable String fileName) {
        byte[] pdf = service.getPdfFile(fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename(fileName).build());
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
    @Value("${welcome.message}") private String welcomeMessage;
    @GetMapping ("/welcome") public String welcome () { return welcomeMessage;
        }
}
