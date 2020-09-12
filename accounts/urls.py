from django.urls import path
from django.contrib.auth.views import LoginView, LogoutView
from .views import Login, Logout, signup

app_name = "accounts"

urlpatterns = [
    path('login/', Login.as_view(), name='login'),
    path('logout/', Logout.as_view(), name='logout'),
    path('signup/', signup, name='signup'),
]