import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { InvoiceService } from '../invoice.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Invoice } from 'src/app/Utilities/Model/Invoice';


@Component({
  selector: 'app-invoice-form',
  templateUrl: './invoice-form.component.html',
  styleUrls: ['./invoice-form.component.scss']
})
export class InvoiceFormComponent implements OnInit {
  invoiceForm!: FormGroup;
  invoiceId?: number;

  constructor(
    private fb: FormBuilder,
    private invoiceService: InvoiceService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.invoiceId = this.route.snapshot.params['id'];

   this.invoiceForm = this.fb.group({
  user: [1, Validators.required],      // ID utilisateur existant
  booking_id: [1, Validators.required], // ID réservation
  amount: [0, [Validators.required, Validators.min(0)]],
  status: ['UNPAID', Validators.required] // Valeur valide
});


    if (this.invoiceId) {
      this.invoiceService.getById(this.invoiceId).subscribe(invoice => {
        this.invoiceForm.patchValue(invoice);
      });
    }
  }

  onSubmit(): void {
    if (this.invoiceForm.invalid) return;

   const data = this.invoiceForm.value;

if (this.invoiceId) {
  this.invoiceService.update(this.invoiceId, data).subscribe(() => {
    this.router.navigate(['/invoice']);
  });
} else {
  this.invoiceService.create(data).subscribe(() => {
    this.router.navigate(['/invoice']);
  });
}
  }
}
