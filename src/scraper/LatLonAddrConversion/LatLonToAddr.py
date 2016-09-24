from geopy.geocoders import Nominatim
geolocator = Nominatim()

DELIM = "||"

fileHandle = open('out.txt', 'r')

outFile = open('out2.txt','w')

for line in fileHandle:
    nazwa, lat, lon = line.split(DELIM)
    location = geolocator.reverse("%s, %s" % (lat, lon))
    outFile.write(("%s%s%s\n" % (nazwa, DELIM, location.raw)).encode("UTF-8"))

fileHandle.close()