from rest_framework import viewsets
from .models import Invoice
from .serializers import InvoiceSerializer
from rest_framework.decorators import action
from rest_framework.response import Response
from reportlab.pdfgen import canvas
from django.http import FileResponse
import io
import datetime

class InvoiceViewSet(viewsets.ModelViewSet):
    queryset = Invoice.objects.all().order_by('-issued_date')
    serializer_class = InvoiceSerializer

    # 🔹 Générer le PDF d’une facture
    @action(detail=True, methods=['get'])
    def generate_pdf(self, request, pk=None):
        invoice = self.get_object()
        buffer = io.BytesIO()
        p = canvas.Canvas(buffer)
        p.setFont("Helvetica-Bold", 18)
        p.drawString(200, 800, "Invoice Document")
        p.setFont("Helvetica", 12)
        p.drawString(50, 750, f"Invoice ID: {invoice.id}")
        p.drawString(50, 730, f"User: {invoice.user.username}")
        p.drawString(50, 710, f"Amount: ${invoice.amount}")
        p.drawString(50, 690, f"Status: {invoice.status}")
        p.drawString(50, 670, f"Issued on: {invoice.issued_date.strftime('%Y-%m-%d')}")

        p.drawString(50, 630, "Thank you for your booking!")
        p.showPage()
        p.save()

        buffer.seek(0)
        return FileResponse(buffer, as_attachment=True, filename=f"invoice_{invoice.id}.pdf")

    # 🔹 Marquer comme payé
    @action(detail=True, methods=['post'])
    def mark_paid(self, request, pk=None):
        invoice = self.get_object()
        invoice.status = 'PAID'
        invoice.paid_date = datetime.datetime.now()
        invoice.save()
        return Response({'status': 'Invoice marked as paid'})
