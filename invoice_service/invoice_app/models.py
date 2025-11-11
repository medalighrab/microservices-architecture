from django.db import models
from django.contrib.auth.models import User

class Invoice(models.Model):
    STATUS_CHOICES = [
        ('UNPAID', 'Unpaid'),
        ('PAID', 'Paid'),
        ('CANCELLED', 'Cancelled'),
    ]

    user = models.ForeignKey(User, on_delete=models.CASCADE, related_name="invoices")
    booking_id = models.IntegerField()  # ou ForeignKey si tu as le modèle Booking dans un autre microservice
    amount = models.DecimalField(max_digits=10, decimal_places=2)
    status = models.CharField(max_length=20, choices=STATUS_CHOICES, default='UNPAID')
    issued_date = models.DateTimeField(auto_now_add=True)
    paid_date = models.DateTimeField(null=True, blank=True)
    pdf_file = models.FileField(upload_to='invoices/', null=True, blank=True)
    
    

    def __str__(self):
        return f"Invoice #{self.id} - {self.user.username}"
