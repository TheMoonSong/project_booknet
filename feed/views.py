from django.http import HttpResponseRedirect
from django.shortcuts import render, redirect
from django.views.generic.base import View
from django.views.generic.list import ListView
from django.views.generic.edit import CreateView, DeleteView, UpdateView
from django.views.generic.detail import DetailView
from django.urls import reverse
from .models import Feed
from .forms import FeedForm
from booknet.views import searchBook_adv
from urllib.parse import urlparse

class FeedList(ListView):   #display all the feeds
    model = Feed
    template_name_suffix = '_list'

def list_feed(request, _isbn):
    feed_list = Feed.objects.filter(isbn=_isbn)
    if len(feed_list) == 0:
        feed_list = None

    context = {'object_list' : feed_list, 'isbn':_isbn}

    bookdict = searchBook_adv(request, _isbn)['items']

    if len(bookdict) != 0:
        context['title'] = bookdict[0]['title']
        context['author'] = bookdict[0]['author']
        context['img_url'] = bookdict[0]['image']
        context['description'] = bookdict[0]['description']
    else:
        pass

    return render(request, 'feed/feed_list.html', context=context)

def my_feed(request):
    feed_list = Feed.objects.filter(author=request.user)
    if len(feed_list) == 0:
        feed_list = None
    context = {'object_list': feed_list}
    return render(request, 'feed/my_feed.html', context=context)

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

class FeedDelete(DeleteView):
    model = Feed
    success_url = '/feed/'

class FeedUpdate(UpdateView):
    model = Feed
    fields = ['text', 'image']
    template_name_suffix = '_update'
    success_url = '/feed/'

class FeedDetail(DetailView):
    model = Feed
    template_name_suffix = '_detail'

class LikeView(View):
    def get(self, request, *args, **kwargs):
        if not request.user.is_authenticated:   #비회원인 경우 로그인
            return redirect(reverse('accounts:signup'))
        else:
            user = request.user
            feed = Feed.objects.get(pk=kwargs['pk'])
            if user in feed.like.all():
                feed.like.remove(user)
            else:
                feed.like.add(user)
            referer_url = request.META.get('HTTP_REFERER')
            path = urlparse(referer_url).path
            return HttpResponseRedirect(path)
