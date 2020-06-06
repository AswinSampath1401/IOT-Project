import data_insertion 
from datetime import datetime

if(data_insertion.check("TN045689","65446464816")):
    print('Already data present')
    prevtime=data_insertion.getdatetime("TN045689","65446464816")
    print(prevtime)
    prevh,prevm,prevs=prevtime[0][0],prevtime[0][1],prevtime[0][2]
    then = prevh*60*60 + prevm*60 + prevs
    current = datetime.now()
    currh = current.hour
    currm=current.minute
    currs = current.second
    now = currh*60*60 + currm*60+ currs
    diff = now-then
    price = diff*0.1
    print(diff)
    data_insertion.delcars("TN045689","65446464816")
else:
    print('Data doesnt exist')
    