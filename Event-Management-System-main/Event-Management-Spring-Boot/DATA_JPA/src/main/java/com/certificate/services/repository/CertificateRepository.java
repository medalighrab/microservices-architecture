package com.certificate.services.repository;
import com.certificate.services.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    List<Certificate> findByUserId(Integer userId); // <- Integer

}


