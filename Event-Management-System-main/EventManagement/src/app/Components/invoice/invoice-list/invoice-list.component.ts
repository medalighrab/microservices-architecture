import { Component, OnInit } from '@angular/core';
import { Invoice } from 'src/app/Utilities/Model/Invoice';
import { InvoiceService } from '../invoice.service';
import jsPDF from 'jspdf';
import 'jspdf-autotable';

import autoTable from 'jspdf-autotable';
@Component({
  selector: 'app-invoice-list',
  templateUrl: './invoice-list.component.html',
  styleUrls: ['./invoice-list.component.scss']
})
export class InvoiceListComponent implements OnInit {
  invoices: Invoice[] = [];
  loading = false;

  constructor(private invoiceService: InvoiceService) {}

  ngOnInit(): void {
    this.loadInvoices();
  }

  loadInvoices(): void {
    this.loading = true;
    this.invoiceService.getAll().subscribe({
      next: (data) => {
        this.invoices = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur lors du chargement', err);
        this.loading = false;
      }
    });
  }

  deleteInvoice(id: number): void {
    if (confirm('Supprimer cette facture ?')) {
      this.invoiceService.delete(id).subscribe(() => {
        this.loadInvoices();
      });
    }
  }

  // 🔹 Génération PDF
  generatePDF(invoice: Invoice): void {
  const doc = new jsPDF();

  // Logo
  const logoUrl = 'assets/logo.png';
  const img = new Image();
  img.src = logoUrl;
  img.onload = () => {
    doc.addImage(img, 'PNG', 10, 10, 50, 20);

    // Entête
    doc.setFontSize(18);
    doc.text('Facture', 105, 40, { align: 'center' });

    doc.setFontSize(12);
    doc.text(`Invoice ID: ${invoice.id}`, 14, 60);
    doc.text(`Utilisateur: ${invoice.user.username}`, 14, 68);
    doc.text(`Date d’émission: ${invoice.issued_date}`, 14, 76);
    doc.text(`Statut: ${invoice.status}`, 14, 84);

    // Tableau
    autoTable(doc, {
      startY: 100,
      head: [['Description', 'Montant']],
      body: [['Facture pour votre réservation', `${invoice.amount} DT`]],
    });

    // Pied de page
    doc.setFontSize(10);
    doc.text('Merci pour votre réservation !', 105, 280, { align: 'center' });

    doc.save(`invoice_${invoice.id}.pdf`);
  };
}
}
