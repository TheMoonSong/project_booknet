from django.db import models
from accounts.models import User
import os

def image_upload_to(instance, filename):
    pass

class Feed(models.Model):
    author = models.ForeignKey(User, on_delete=models.CASCADE, related_name='user')
    text = models.TextField(blank=True)
    isbn = models.IntegerField()
    image = models.ImageField(upload_to='timeline')
    created = models.DateTimeField(auto_now_add=True)

# Create your models here.
