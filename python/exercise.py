import csv
f=open('exercise.csv','r',encoding='utf8')
rd=csv.reader(f)
import cx_Oracle
import os
os.putenv('NLS_LANG', '.UTF8')
connection = cx_Oracle.connect('allight','allight','192.168.56.66/orcl')
cursor = connection.cursor()
# "Activity, Exercise or Sport (1 hour)",130 lb,155 lb,180 lb,205 lb,Calories per kg
for r in rd:
    print(r)
    ename=r[0]
    cal=r[5]
    # sql = 'insert into caldictionary(cdno,cdtype,cdcal,cdname,cdgram) values(cdno_seq.NEXTVAL,:1,:2,:3,:4)'
    # data = ('운동', cal, ename, 1)
    # cursor.execute(sql, data)

# cursor.close()
# connection.commit()
# connection.close()