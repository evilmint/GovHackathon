from geopy.geocoders import Nominatim
geolocator = Nominatim()

DELIM = "||"

fileHandle = open('in.txt', 'r')

outFile = open('out.txt','w')

for line in fileHandle:
    nazwa, lat, lon = line.split(DELIM)
    location = geolocator.reverse("%s, %s" % (lat, lon))
    outFile.write(("%s%s%s\n" % (nazwa, DELIM, location.raw)).encode("UTF-8"))

fileHandle.close()