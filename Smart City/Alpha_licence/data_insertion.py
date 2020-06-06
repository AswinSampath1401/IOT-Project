import sqlite3
from datetime import datetime
con = sqlite3.connect("cars.db")
cursor=con.cursor()

def printval():
    command3 = """SELECT * FROM licence"""
    try:
        cursor.execute(command3)
        rows = cursor.fetchall()
        for row in rows:
            print(row)
    except:
        print('Failed')



def check(p1,m1):
    cursor.execute("SELECT prevhour,prevmin,prevseconds FROM licence WHERE mobno=?",(m1,))
    rows = cursor.fetchall()
    if(len(rows)==0):
        return False
    return True

def getdatetime(p1,m1):
    cursor.execute("SELECT prevhour,prevmin,prevseconds FROM licence WHERE mobno=?",(m1,))
    rows = cursor.fetchall()
    return rows

def insertvalues(p1,m1):
    now = datetime.now()
    h=now.hour
    m=now.minute
    s=now.second
    params = (p1,m1,h,m,s)
    #command = """INSERT INTO licence values('p1','m1',?,?,?)"""
    #
    #cursor.execute('insert into licence(plate,mobno,then_hour,then_min,then_seconds) values(%s, %s, %s, %s, %s)', (p1,m1,h,m,s))
    cursor.execute("INSERT INTO licence VALUES(?,?,?, ?, ?)", params)
    con.commit()
    printval()

def delcars(p1,m1):
    cursor.execute("DELETE FROM licence WHERE mobno=?",(m1,))
    print('Car Exit..................') 

#command1 = """CREATE TABLE IF NOT EXISTS licence(plate TEXT,mobno TEXT,created_at DATETIME DEFAULT CURRENT_TIMESTAMP) """
command1 = """CREATE TABLE IF NOT EXISTS licence(plate TEXT,mobno TEXT,prevhour INTEGER,prevmin INTEGER,prevseconds INTEGER)"""
cursor.execute(command1)




