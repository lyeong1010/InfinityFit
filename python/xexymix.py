import urllib.request as request
from bs4 import BeautifulSoup
import cx_Oracle

#한글 지원 방법
import os
os.putenv('NLS_LANG', '.UTF8')

#연결에 필요한 기본 정보 (유저, 비밀번호, 데이터베이스 서버 주소)
connection = cx_Oracle.connect('allight','allight','192.168.56.66/orcl')

cursor = connection.cursor()

# 운동복
url='http://www.xexymix.com/'
recvd=request.urlopen(url).read().decode('euc-kr')
dom=BeautifulSoup(recvd,'html.parser',from_encoding='utf-8')
div=dom.find_all('div',{'class':"item-cont"})
i=127
for di in div:
    dl = di.find_all('dl', {'class': 'item-list'})
    for d in dl:
        i=i+1
        img=d.find('img',{'class':'MS_prod_img_s'})['rollover_onimg']
        # print(img)
        ul = d.find('ul')
        name=ul.find('li',{'class':'prd-name'}).text
        # price=int(ul.find('li', {'class': 'prd-price'}).text.replace(',',''))
        # print(i,'---',name)
        # ICATEGORY='운동악세사리'
        # CONO=2
        # brand='젝시믹스'
        # sql = 'insert into item(ino,iname,ICATEGORY,IPRICE,ICORP,CONO) values(ino_seq.NEXTVAL,:1,:2,:3,:4,:5)'
        # data = (name, ICATEGORY, price, brand, CONO)
        # cursor.execute(sql, data)
        print(i, "--", img)
        # sql = 'insert into image values(imgno_se q.NEXTVAL,:1,:2,:3)'
        # data = (i, 1, img)
        # cursor.execute(sql, data)
        # href = "http://www.xexymix.com" + d.find('a')['href']
        # recvd = request.urlopen(href)
        # dom = BeautifulSoup(recvd, 'html.parser', from_encoding='utf-8')
        # div = dom.find('div', {'class': "detail_st"})
        # di = div.find('div', {'class': 'image'})
        # deimg=di.find('img')['src']
        # deimg = di.find_all('img')
        # print(name,"---",deimg)
        # for dim in deimg:
        #     d=dim['src']
        #     # print(i,"---",name,"---",d)
        #     sql = 'insert into image values(imgno_seq.NEXTVAL,:1,:2,:3)'
        #     data = (i, 0, d)
        #     cursor.execute(sql, data)

#운동악세사리
# url='http://www.xexymix.com/shop/shopbrand.html?xcode=007&type=X'
# recvd=request.urlopen(url).read().decode('euc-kr')
# dom=BeautifulSoup(recvd,'html.parser',from_encoding='utf-8')
# div=dom.find('div',{'class':"item-wrap"})
# div2=div.find('div',{'class':"item-cont"})
# print(div2)

cursor.close()
connection.commit()
connection.close()