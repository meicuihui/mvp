# mvp
　sample android mvp
　useing volley gson to complete mvp
 import requests
from urllib.request import urlopen
import os

from lxml import etree
html = requests.get("http://image.baidu.com/search/index?tn=baiduimage&ct=201326592&lm=-1&cl=2&ie=gbk&word=%CD%BC%C6%AC&fr=ala&ala=1&alatpl=others&pos=0")
html.encoding = 'utf-8'
html=etree.HTML(html.content)
print(html)
img_list=html.xpath('//img/@src')
for n,i in enumerate(img_list):
    i='http://'+i.replace(u'//','')
    print(i)
    img_r=requests.get(i)
    open(str(n)+'.png','wb+').write(img_r.content)
    print(img_list)

```
