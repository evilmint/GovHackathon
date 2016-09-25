from geopy.geocoders import Nominatim
import codecs
geolocator = Nominatim()

DELIM = "|"

fileHandle = codecs.open("file.txt", "r", "utf-8")

outFile = open('out.txt','w')

for line in fileHandle:
    nazwa, city, street, number, zip, _ = line.split(DELIM)
    address = "%s %s %s %s" % (street, number, city, zip)
    i=0
    if int(nazwa) < 4168:
        continue
    while i<3:
        try:
            geocode = geolocator.geocode(address)
            if geocode is not None:
                lat = geocode.latitude
                lon = geocode.longitude
                line = "%s%s%s%s%s\n" % (nazwa, DELIM, lat, DELIM, lon)
                outFile.write(line)
                print line
            break
        except Exception as e:
            i+=1
            print e

fileHandle.close()