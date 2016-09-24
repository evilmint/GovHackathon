#!/usr/bin/python

import urllib2
import json
import io

class Event:
	def __init__(self, dict):
		self.url = dict['offer']['pageLink'].replace("'", "''")
		self.startDate = dict['startDate']
		self.endDate = dict['endDate']
		self.lat = dict['location']['lattiude']
		self.lng = dict['location']['longitude']
		self.name = dict['offer']['title'].replace("'", "''")
                if 'longDescription' in dict['offer']:
                    self.description = dict['offer']['longDescription'].replace("'", "''")
                else:
                    self.description = ''
		self.type = dict['offer']['type']['name'].replace("'", "''")
		
	def __str__(self):
            return "INSERT INTO events (name, description, type, url, startDate, endDate, latitude, longitude) VALUES" + \
                    "('%s', '%s', '%s', '%s', '%s', '%s', %s, %s);" % \
                    ( self.name, self.description, self.type, self.url, self.startDate, self.endDate, str(self.lat), str(self.lng) )


def load(url):
        print(url)
	return json.load(urllib2.urlopen(url), encoding='utf8')

url = "http://go.wroclaw.pl/api/v1.0/events?key=1220421692038264438486011634662637610942"
data = load(url)

with io.open("./events.sql", "w", encoding="utf8") as f:
    while (len(data['items']) > 0):
            for eventDict in data['items']:
                    if 'lattiude' in eventDict['location']:
                            event = Event(eventDict)
                            f.write(event.__str__())
                            f.write(u"\n")
            data = load(data['next'])
