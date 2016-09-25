import os, json
from geopy.geocoders import Nominatim
geolocator = Nominatim()

indir = './relics-json/'
DELIM = "|"

outFile = open('out.txt','w')


for root, dirs, filenames in os.walk(indir):
    for f in filenames:
        data = json.loads(open(indir+f, 'r').read())
        if not "place_name" in data or not u"wroc\u0142aw" in data["place_name"].lower():
            continue
        address = "%s %s" % (data['street'], data['place_name'])
        geocode = geolocator.geocode(address)
        if geocode is not None:
            lat = geocode.latitude
            lon = geocode.longitude
            line = "%s%s%s%s%s\n" % (f, DELIM, lat, DELIM, lon)
            outFile.write(line)
            print line