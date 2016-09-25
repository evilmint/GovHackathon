import json, codecs

DELIM = "|"
indir = './relics-json/'

fileHandle = codecs.open("out.txt", "r", "utf-8")

outFile = open('out2.txt', 'w')

for line in fileHandle:
    nazwa, lat, lon = line.split(DELIM)
    data = json.loads(open(indir + nazwa, 'r').read())
    outStr = ("%s%s%s%s%s%s%s%s%s%s%s%s%s\n" % (
        nazwa, DELIM, lat, DELIM, lon.strip(), DELIM, data['place_name'].strip().replace("\n"," ").replace("\r"," "), DELIM, ','.join(data['categories']),
        DELIM, data['description'].strip().replace("\n"," ").replace("\r"," "), DELIM, data["identification"].strip().replace("\n"," ").replace("\r"," "))).encode("UTF-8")
    outFile.write(outStr)

fileHandle.close()
