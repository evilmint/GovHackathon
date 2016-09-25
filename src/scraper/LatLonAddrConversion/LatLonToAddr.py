from geopy.geocoders import Nominatim
from time import sleep

geolocator = Nominatim()

DELIM = "|"

fileHandle = open('in.txt', 'r')

outFile = open('out.txt', 'w')

for line in fileHandle:
    nazwa, lat, lon = line.split(DELIM)
    if int(nazwa) < 1380:
        continue
    location = geolocator.reverse("%s, %s" % (lat, lon))
    try:
        address = "%s" % (location.raw['address']['bus_stop'])
    except:
        address = location.raw["display_name"]
    lineString = ("%s%s%s\n" % (nazwa, DELIM, address)).encode("UTF-8")
    outFile.write(lineString)
    print lineString
    sleep(0.2)

fileHandle.close()
