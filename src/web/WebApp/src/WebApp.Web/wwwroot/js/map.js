var schoolMarkers;
var map;
var markerPosition;
var globalResult;
var directionsDisplay;


function startGuidance(from, to) {
    var directionsService = new google.maps.DirectionsService;
    if (directionsDisplay != undefined) {
        directionsDisplay.setMap(null);
    }
    directionsDisplay = new google.maps.DirectionsRenderer;
    directionsDisplay.setMap(map);
    directionsDisplay.setOptions({ suppressMarkers: true }); directionsDisplay.setOptions({ suppressMarkers: true });

    google.maps.event.addListener(directionsDisplay, 'directions_changed', function () {
        computeTotalDistance(directionsDisplay.directions);
    });

    directionsService.route({
        origin: from,
        destination: to,
        travelMode: 'DRIVING'
    }, function (response, status) {
        if (status === 'OK') {
            directionsDisplay.setDirections(response);
        } else {
            window.alert('Directions request failed due to ' + status);
        }
    });

}

function initMap() {
    //var directionsService = new google.maps.DirectionsService;
    //var directionsDisplay = new google.maps.DirectionsRenderer;
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 11,
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

    schoolMarkers = [];

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
        markerPosition = location;
        marker.setMap(null);
        marker = new google.maps.Marker({
            position: location,
            map: map
        });
        sendRequest(location);
    }

    $(".panel-primary .panel-heading")
        .click(function () {
            var wasVisible = $(this).parent().find(".panel-body").is(":visible");
            $(".panel-body").hide();
            if (wasVisible) {
                $(this).parent().find(".panel-body").hide();
            } else {
                $(this).parent().find(".panel-body").show();
                var id = $(this).parent().find("p").first().attr("id");
                drawFromClosest(globalResult[id], 'schools');
            }
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
    $("#googleTime").show();
    $("#googleTimeText").text(time);
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
    globalResult = result;
    drawFromClosest(result.primaryschool, 'schools');

    $('#middleschool').text(result.middleschool.closest.name);
    $('#middleschoolStreet').text(result.middleschool.closest.www || "");
    $('#middleschoolDistance').text(Math.round(result.middleschool.distance * 100) / 100 + " km");

    $('#relic').text(result.relic.closest.name);
    $('#relicDistance').text(Math.round(result.relic.distance * 100) / 100 + " km");

    $('#preschool').text(result.preschool.closest.name);
    $('#preschoolStreet').text(result.middleschool.closest.www || "");
    $('#preschoolDistance').text(Math.round(result.preschool.distance * 100) / 100 + " km");

    $('#primaryschool').text(result.primaryschool.closest.name);
    $('#primaryschoolStreet').text(result.middleschool.closest.www || "");
    $('#primaryschoolDistance').text(Math.round(result.primaryschool.distance * 100) / 100 + " km");

    $('#busStop').text(result.busStop.closest.name);

    $('#tramStop').text(result.tramStop.closest.name);
}

function drawFromClosest(item, type) {
    $.each(schoolMarkers, function (index, value) {
        schoolMarkers[index].setMap(null);
    });
    schoolMarkers = [];
    for (i = 0; i < 3; i++) {
        var myLatlng = new google.maps.LatLng(item.closestThree[i].latitude,
            item.closestThree[i].longitude);
        var localMarker = new google.maps.Marker({
            position: myLatlng,
            map: map,
            icon: (i === 0 ? '/images/'+type+'.png' : '/images/'+type+'_grey.png'),
            title: item.closestThree[i].name
        });
        schoolMarkers.push(localMarker);
    }
    startGuidance(markerPosition.lat() + ", " + markerPosition.lng(),
        item.closest.latitude + ", " + item.closest.longitude);

    var newBoundary = new google.maps.LatLngBounds();

    for (index in schoolMarkers) {
        var position = schoolMarkers[index].position;
        newBoundary.extend(position);
    }
    //    newBoundary.extend(markerPosition);
    map.fitBounds(newBoundary);
}

function drawFromMarkers(items, type) {
    $.each(schoolMarkers, function (index, value) {
        schoolMarkers[index].setMap(null);
    });
    schoolMarkers = [];
    for (i = 0; i < items.length; i++) {
        var myLatlng = new google.maps.LatLng(items[i].latitude,
            items[i].longitude);
        var localMarker = new google.maps.Marker({
            position: myLatlng,
            map: map,
            icon: ('/images/' + type + '.png'),
            title: items[i].name
        });
        schoolMarkers.push(localMarker);
    }

    var newBoundary = new google.maps.LatLngBounds();

    for (index in schoolMarkers) {
        var position = schoolMarkers[index].position;
        newBoundary.extend(position);
    }

    map.fitBounds(newBoundary);
}

function onError() {

}

