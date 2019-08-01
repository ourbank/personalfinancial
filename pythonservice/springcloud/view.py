from django.http import HttpResponse
import json
from . import utlis
from . import BiMM
import jieba as jb
import jieba.posseg as pg

def getcutword(request):

    raw = request.GET.get('raw')
    words_dic = utlis.getWordDic()
    word_list = BiMM.cut_words(raw, words_dic)
    result = "/".join(word_list)
    return HttpResponse(result)


def health(request):
    response = {'status': 'UP'}
    return HttpResponse(json.dumps(response), content_type="application/json;charset=utf-8")



def jiebacut(request):

    raw = request.GET.get('raw')
    jb.load_userdict('./user_dict.txt')
    word_list = pg.cut(raw)
    list =[]
    for word, flag in word_list:
        element = {}
        element[flag] = word
        list.append(element)
    return HttpResponse(json.dumps(list, ensure_ascii=False), content_type="application/json;charset=utf-8")
