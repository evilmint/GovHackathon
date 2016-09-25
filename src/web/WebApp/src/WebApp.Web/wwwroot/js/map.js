﻿function initMap() {
    //var directionsService = new google.maps.DirectionsService;
    //var directionsDisplay = new google.maps.DirectionsRenderer;
    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 7,
        center: { lat: 51.1058285, lng: 17.0124618 }
    });
    //directionsDisplay.setMap(map);

    //directionsService.route({
    //    origin: "Opole",
    //    destination: "Wrocław",
    //    travelMode: 'DRIVING'
    //}, function (response, status) {
    //    if (status === 'OK') {
    //        directionsDisplay.setDirections(response);
    //    } else {
    //        window.alert('Directions request failed due to ' + status);
    //    }
    //});

    var iconBase = 'https://maps.google.com/mapfiles/kml/shapes/';
    var icons = {
        parking: {
            icon: iconBase + 'parking_lot_maps.png'
        },
        library: {
            icon: iconBase + 'library_maps.png'
        },
        info: {
            icon: iconBase + 'info-i_maps.png'
        }
    };

    var features = [
          {
              position: new google.maps.LatLng(-33.91721, 151.22630),
              type: 'info'
          }, {
              position: new google.maps.LatLng(-33.91539, 151.22820),
              type: 'library'
          }
    ];

    var marker;

    var data;

    for (var i = 0, feature; feature = features[i]; i++) {
        addMarker(feature);
    }

    function addMarker(feature) {
        marker = new google.maps.Marker({
            position: feature.position,
            icon: icons[feature.type].icon,
            map: map
        });
    }

    google.maps.event.addListener(map, 'click', function (event) {
        placeMarker(event.latLng);

    });

    function placeMarker(location) {
        marker.setMap(null);
        marker = new google.maps.Marker({
            position: location,
            map: map
        });
        sendRequest(location);
    }

    //google.maps.event.addListener(directionsDisplay, 'directions_changed', function () {
    //    computeTotalDistance(directionsDisplay.directions);
    //});
}

function computeTotalDistance(result) {
    var total = 0;
    var time = 0;
    var from = 0;
    var to = 0;
    var myroute = result.routes[0];
    for (var i = 0; i < myroute.legs.length; i++) {
        total += myroute.legs[i].distance.value;
        time += myroute.legs[i].duration.text;
        from = myroute.legs[i].start_address;
        to = myroute.legs[i].end_address;
    }
    time = time.replace('hours', 'H');
    time = time.replace('mins', 'M');
    total = total / 1000.
    $("#googleTime").text(time);
}

function calculateAndDisplayRoute(directionsService, directionsDisplay) {
    directionsService.route({
        origin: document.getElementById('start').value,
        destination: document.getElementById('end').value,
        travelMode: 'DRIVING'
    }, function (response, status) {
        if (status === 'OK') {
            directionsDisplay.setDirections(response);
        } else {
            window.alert('Directions request failed due to ' + status);
        }
    });
}

function sendRequest(location) {
    var request = buildUrl("http://localhost:64116/point", location.lat(), location.lng());
    getData(request);
}

function getData(getUrl) {
    $.ajax({
        url: getUrl,
        type: 'GET',
        success: function(result) {
            onSuccess(result);
        }
    });
}

function buildUrl(baseUrl, long, lat) {
    return baseUrl + "?latitude=" + long + "&longitude=" + lat;
}

function onSuccess(result) {
    $('#middleschool').text(result.middleschool.closest.name);

    $('#relic').text(result.relic.closest.name);

    $('#preschool').text(result.preschool.closest.name);

    $('#primaryschool').text(result.primaryschool.closest.name);

    $('#busStop').text(result.busStop.closest.name);

    $('#tramStop').text(result.tramStop.closest.name);
}

function onError() {

}

