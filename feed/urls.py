from django.urls import path
from .views import list_feed, create_feed, FeedList

app_name = "feed"

urlpatterns = [
    path('', FeedList.as_view(), name='feedList'),
    path('<int:_isbn>', list_feed, name='list_feed'),
    path('<int:_isbn>/create', create_feed, name='create_feed'),
]