from django.urls import path
from .views import list_feed, create_feed, FeedList, FeedDelete, FeedUpdate

app_name = "feed"

urlpatterns = [
    path('', FeedList.as_view(), name='feedList'),
    path('<int:_isbn>', list_feed, name='list_feed'),
    path('<int:_isbn>/create', create_feed, name='create'),
    path('<int>/delete/<int:pk>', FeedDelete.as_view(), name='delete'),
    path('<int>/update/<int:pk>', FeedUpdate.as_view(), name='update'),

]