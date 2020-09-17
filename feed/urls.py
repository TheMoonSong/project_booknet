from django.urls import path
from .views import *

app_name = "feed"

urlpatterns = [
    path('', FeedList.as_view(), name='feedList'),
    path('<int:_isbn>', list_feed, name='list_feed'),
    path('myfeed', my_feed, name='myfeed'),
    path('<int:_isbn>/create', create_feed, name='create'),
    path('<int>/delete/<int:pk>', FeedDelete.as_view(), name='delete'),
    path('<int>/update/<int:pk>', FeedUpdate.as_view(), name='update'),
    path('<int>/detail/<int:pk>', FeedDetail.as_view(), name='detail'),

]