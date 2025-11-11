import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CertificateService } from 'src/app/Utilities/APIServices/certificate.service';
import { Certificate } from 'src/app/Utilities/Model/certificate';

@Component({
  selector: 'app-certificate-list',
  templateUrl: './certificate-list.component.html',
  styleUrls: ['./certificate-list.component.scss']
})

export class CertificateListComponent implements OnInit {
  certificates: Certificate[] = [];

  constructor(private certService: CertificateService, private router: Router) {}

  ngOnInit(): void {
    this.loadCertificates();
  }

  loadCertificates() {
    this.certService.getAll().subscribe(data => this.certificates = data);
  }

  deleteCertificate(id: number) {
    if (confirm('Confirmer la suppression ?')) {
      this.certService.delete(id).subscribe(() => this.loadCertificates());
    }
  }

  openPdf(cert: Certificate) {
    if (cert.pdfUrl) {
      window.open(cert.pdfUrl, '_blank');
    } else {
      alert('Aucun PDF disponible pour ce certificat.');
    }
  }

  goToCreate() {
    this.router.navigate(['/certificates/new']);
  }
}