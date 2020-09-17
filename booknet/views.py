from django.shortcuts import render
from django.http import JsonResponse
import json, requests
import urllib.request

def index(request):
    return render(request, 'index.html')

def searchBook(request, query, _isbn=None):

    url = "https://openapi.naver.com/v1/search/book.json?"

    # 네이버 OpenAPI 신청 후 키 발급 가능 https://developers.naver.com/apps/#/register
    id = "J4k1lEwGVJ575y1ICGF6"
    secret = "zy67Ui2yNc"

    header = {"X-Naver-Client-Id": id, "X-Naver-Client-Secret": secret}

    queryStr = "query=" + query  # 검색어

    response = requests.get(url + queryStr, headers=header).json()
    print(response)

    return JsonResponse(response, content_type="utf-8")

def searchBook_adv(request, _isbn):

    url = "https://openapi.naver.com/v1/search/book_adv?"

    # 네이버 OpenAPI 신청 후 키 발급 가능 https://developers.naver.com/apps/#/register
    id = "J4k1lEwGVJ575y1ICGF6"
    secret = "zy67Ui2yNc"

    query = "d_isbn=" + str(_isbn)  # 검색어

    request = urllib.request.Request(url + query)
    request.add_header("X-Naver-Client-Id", id)
    request.add_header("X-Naver-Client-Secret", secret)

    response = urllib.request.urlopen(request)
    rescode = response.getcode()

    if (rescode == 200):
        response_body = response.read()
        data = json.loads(response_body.decode('utf-8'))
        print(data)
        return data #dict
    else:
        print("Error code:" + rescode)