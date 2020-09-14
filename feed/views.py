from django.shortcuts import render, redirect
from django.views.generic.list import ListView
from django.views.generic.edit import CreateView
from .models import Feed
from .forms import FeedForm

class FeedList(ListView):   #display all the feeds
    model = Feed
    template_name_suffix = '_list'

def list_feed(request, _isbn):
    feed_list = Feed.objects.filter(isbn=_isbn)
    context = {'object_list' : feed_list}
    return render(request, 'feed/feed_list.html', context=context)

def create_feed(request, _isbn):
    if request.method == 'GET':
        form = FeedForm()
        return render(request, 'feed/feed_create.html', {'form':form})
    if request.method == 'POST':
        new = Feed.objects.create(isbn=_isbn, author=request.user, text=request.POST['text'], image=request.FILES['image'])
        new.save()
        return redirect('/')

class FeedCreate(CreateView):
    model = Feed
    fields = ['text', 'image']
    template_name_suffix = '_create'
    success_url = '/'

    def from_valid(self, form):
        form.instance.author_id = self.request.user.id
        if form.is_valid():
            form.instance.save()
            return redirect('/')
        else:
            return self.render_to_response({'form':form})