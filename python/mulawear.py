import urllib.request as request
from bs4 import BeautifulSoup
import cx_Oracle
import os
os.putenv('NLS_LANG', '.UTF8')
connection = cx_Oracle.connect('allight','allight','192.168.56.66/orcl')
cursor = connection.cursor()

# 남자
# url='http://www.mulawear.com/shop/shopbrand.html?xcode=032&type=Y'
# recvd=request.urlopen(url).read().decode('euc-kr')
# dom=BeautifulSoup(recvd,'html.parser',from_encoding='utf-8')
# div=dom.find('div',{'class':"useBannerPrdCont"})
# dl=div.find_all('dl',{'class':'item-list'})
# i=0
# for d in dl:
#     i=i+1
#     img=d.find('img',{'class':'MS_prod_img_s'})['rollover_onimg']
    # print(img)
    # result1=request.urlopen(img)
    # with open('test1.jpg','wb') as f:
    #     f.write(result1.contents)
    # break
    # ul = d.find('ul')
    # name = ul.find('li', {'class': 'prd-name'}).text
    # brand=ul.find('li',{'class':'prd-brand'}).text
    # price=int(ul.find('li', {'class': 'prd-price'}).text.strip().strip('원').replace(',',''))
    # # print(name)
    # print(brand)
    # print(type(price))
    # ICATEGORY='운동복'
    # CONO=1
    # sql = 'insert into item(ino,iname,ICATEGORY,IPRICE,ICORP,CONO) values(ino_seq.NEXTVAL,:1,:2,:3,:4,:5)'
    # data = (name, ICATEGORY, price, brand, CONO)
    # cursor.execute(sql, data)
    # -----------------이미지 어떡하쥡
    # sql = 'insert into image values(imgno_seq.NEXTVAL,:1,:2,:3)'
    # data = (i, 1,img)
    # cursor.execute(sql, data)
    # href = "http://www.mulawear.com/" + d.find('a')['href']
    # recvd = request.urlopen(href).read().decode('euc-kr')
    # dom = BeautifulSoup(recvd, 'html.parser', from_encoding='utf-8')
    # div=dom.find('div',{'class':"detail_wrap"})
    # p=div.find_all('p',{'class':'detailimg'})
    # for q in p:
    #     dimg=q.find('img')['src'] #상세이미지
    #     # print(dimg)
    #     sql = 'insert into image values(imgno_seq.NEXTVAL,:1,:2,:3)'
    #     data = (i, 0, dimg)
    #     cursor.execute(sql, data)

#여자
# url='http://www.mulawear.com/shop/shopbrand.html?xcode=004&type=Y'
# recvd=request.urlopen(url).read().decode('euc-kr')
# dom=BeautifulSoup(recvd,'html.parser',from_encoding='utf-8')
# # print(dom)
# div=dom.find('div',{'class':"useBannerPrdCont"})
# dl=div.find_all('dl',{'class':'item-list'})
# i=47
# for d in dl:
#     i=i+1
#     img=d.find('img',{'class':'MS_prod_img_s'})['rollover_onimg']
#     # print(img)
#     ul = d.find('ul')
#     name = ul.find('li', {'class': 'prd-name'}).text
#     brand=ul.find('li',{'class':'prd-brand'}).text
#     price = int(ul.find('li', {'class': 'prd-price'}).text.strip().strip('원').replace(',', ''))
#     ICATEGORY='운동복'
#     CONO=1
#     sql = 'insert into item(ino,iname,ICATEGORY,IPRICE,ICORP,CONO) values(ino_seq.NEXTVAL,:1,:2,:3,:4,:5)'
#     data = (name, ICATEGORY, price, brand, CONO)
#     cursor.execute(sql, data)
    # print(name)
    # print(brand)
    # print(price)
    # -----------------이미지 어떡하쥡
    # sql = 'insert into image values(imgno_seq.NEXTVAL,:1,:2,:3)'
    # data = (i,1, img)
    # cursor.execute(sql, data)
    # print(i,"--",name)
    # href = "http://www.mulawear.com/" + d.find('a')['href']
    # recvd = request.urlopen(href).read().decode('euc-kr')
    # dom = BeautifulSoup(recvd, 'html.parser', from_encoding='utf-8')
    # div=dom.find('div',{'class':"detail_wrap"})
    # p=div.find_all('p',{'class':'detailimg'})
    # for q in p:
    #     dimg=q.find('img')['src'] #상세이미지
        # print(dimg)

# 악세사리
url='http://www.mulawear.com/shop/shopbrand.html?xcode=019&type=Y'
recvd=request.urlopen(url).read().decode('euc-kr')
dom=BeautifulSoup(recvd,'html.parser',from_encoding='utf-8')
# print(dom)
div=dom.find('div',{'class':"useBannerPrdCont"})
dl=div.find_all('dl',{'class':'item-list'})
i=95
for d in dl:
    img=d.find('img',{'class':'MS_prod_img_s'})['rollover_onimg']
    i = i + 1
    # print(img)
    ul = d.find('ul')
    name = ul.find('li', {'class': 'prd-name'}).text
#     brand=ul.find('li',{'class':'prd-brand'}).text
#     price = int(ul.find('li', {'class': 'prd-price'}).text.replace('SOLD OUT','').strip().strip('원').replace(',', ''))
#     # print(name)
#     # print(brand)
#     # print(price)
#     ICATEGORY='운동악세사리'
#     CONO=1
#     sql = 'insert into item(ino,iname,ICATEGORY,IPRICE,ICORP,CONO) values(ino_seq.NEXTVAL,:1,:2,:3,:4,:5)'
#     data = (name, ICATEGORY, price, brand, CONO)
#     cursor.execute(sql, data)
#     print(i,"--",name)
#     sql = 'insert into image values(imgno_seq.NEXTVAL,:1,:2,:3)'
#     data = (i, 1, img)
#     cursor.execute(sql, data)
    href = "http://www.mulawear.com/" + d.find('a')['href']
    recvd = request.urlopen(href).read().decode('euc-kr')
    dom = BeautifulSoup(recvd, 'html.parser', from_encoding='utf-8')
    ul=dom.find_all('ul',{'class':"thumb-list"})
    for u in ul:
        li = u.find('li')
        dimg=li.find('img')['src'] #상세이미지
        # print(name,'---',dimg)
        sql = 'insert into image values(imgno_seq.NEXTVAL,:1,:2,:3)'
        data = (i, 0, dimg)
        cursor.execute(sql, data)

cursor.close()
connection.commit()
connection.close()