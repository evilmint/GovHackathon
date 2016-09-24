function initMap() {
    var directionsService = new google.maps.DirectionsService;
    var directionsDisplay = new google.maps.DirectionsRenderer;
    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 7,
        center: { lat: 51.1058285, lng: 17.0124618 }
    });
    directionsDisplay.setMap(map);

    directionsService.route({
        origin: "Opole",
        destination: "Wrocław",
        travelMode: 'DRIVING'
    }, function (response, status) {
        if (status === 'OK') {
            directionsDisplay.setDirections(response);
        } else {
            window.alert('Directions request failed due to ' + status);
        }
    });

    google.maps.event.addListener(directionsDisplay, 'directions_changed', function () {
        computeTotalDistance(directionsDisplay.directions);
    });
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