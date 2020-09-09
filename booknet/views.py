from django.shortcuts import render
from django.http import JsonResponse
import json, requests


def index(request):
    return render(request, 'index.html')

def searchBook(request, query):
    url = "https://openapi.naver.com/v1/search/book.json?"

    # 네이버 OpenAPI 신청 후 키 발급 가능 https://developers.naver.com/apps/#/register
    id = "J4k1lEwGVJ575y1ICGF6"
    secret = "zy67Ui2yNc"

    header = {"X-Naver-Client-Id": id, "X-Naver-Client-Secret": secret}

    queryStr = "query=" + query  # 검색어

    response = {"테스트":"test"}#requests.get(url + queryStr, headers=header).json()
    print(response)

    return JsonResponse(response, content_type="utf-8")
