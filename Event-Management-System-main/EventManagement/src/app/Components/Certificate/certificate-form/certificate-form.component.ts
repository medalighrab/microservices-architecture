import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CertificateService } from 'src/app/Utilities/APIServices/certificate.service';
import { Certificate } from 'src/app/Utilities/Model/certificate';

@Component({
  selector: 'app-certificate-form',
  templateUrl: './certificate-form.component.html',
  styleUrls: ['./certificate-form.component.scss']
})
export class CertificateFormComponent {
  certificate: Certificate = {
    eventId: 0,
    userId: 0,
    userName: '',
    eventTitle: ''
  };

  constructor(private certService: CertificateService, private router: Router) {}


  save() {
    this.certService.create(this.certificate).subscribe(() => {
      alert('Certificat créé avec succès !');
      this.router.navigate(['/certificates']);
    });
  }
}
