from django.db import models
from accounts.models import User
import os

def image_upload_to(instance, filename):
    base_path = os.path.abspath('media')
    path = os.path.join(base_path, str(instance.isbn))
    name, ext = filename.split('.')
    print(os.path.join(path, '%s.%s'%(name, ext)))
    return os.path.join(path, '%s.%s'%(name, ext))

class Feed(models.Model):
    author = models.ForeignKey(User, on_delete=models.CASCADE, related_name='user')
    text = models.TextField(blank=True)
    isbn = models.IntegerField()
    image = models.ImageField(upload_to='timeline')
    created = models.DateTimeField(auto_now_add=True)

# Create your models here.
