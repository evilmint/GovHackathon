from geopy.geocoders import Nominatim
geolocator = Nominatim()

DELIM = "||"

fileHandle = open('file.txt', 'r')

outFile = open('out.txt','w')

for line in fileHandle:
    nazwa, address = line.split(DELIM)
    lat = geolocator.geocode(address).latitude
    lon = geolocator.geocode(address).longitude
    outFile.write("%s%s%s%s%s\n" % (nazwa, DELIM, lat, DELIM, lon))

fileHandle.close()