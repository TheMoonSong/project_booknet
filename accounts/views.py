from django.shortcuts import render, redirect
from django.contrib.auth.views import LoginView, LogoutView
from .models import User

class Login(LoginView):
    pass

class Logout(LogoutView):
    pass

def signup(request):
    if request.method == 'POST':
        if request.POST['password1'] == request.POST['password2']:
            user = User.objects.create_user(username=request.POST['username'], password=request.POST['password1'])
            return redirect('/')
    if request.method == 'GET':
        return render(request, 'signup.html')
# Create your views here.
