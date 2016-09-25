function searchHouses() {
    var minprice = $('#minprice').val(),
        maxprice = $('#maxprice').val(),
        minarea = $('#minarea').val(),
        maxarea = $('#maxarea').val(),
        preSchools = $('#preSchools').val(),
        primarySchools = $('#primarySchools').val(),
        middleSchools = $('#middleSchools').val(),
        relics = $('#relics').val(),
        stops = $('#stops').val();

    var searchParams = [];

    if (minprice) {
        searchParams.push("minprice:" + minprice);
    }

    if (maxprice) {
        searchParams.push("maxprice:" + maxprice);
    }

    if (minarea) {
        searchParams.push("minarea:" + minarea);
    }

    if (maxarea) {
        searchParams.push("maxarea:" + maxarea);
    }

    if (preSchools) {
        searchParams.push("preschool:" + preSchools);
    }

    if (primarySchools) {
        searchParams.push("primaryschool:" + primarySchools);
    }

    if (middleSchools) {
        searchParams.push("middleschool:" + middleSchools);
    }

    if (relics) {
        searchParams.push("relic:" + relics);
    }

    if (stops) {
        searchParams.push("tramStop:" + stops);
        searchParams.push("busStop:" + stops);
    }

    var jqxhr = $.ajax({
        url: "/houses/search",
        type: "get",
        data: { searchParams: searchParams.join(";") }
    })
    .done(function (data) {
        console.info("success");

        //initMap();
        drawFromMarkers(data, 'schools');
    })
    .fail(function () {
        console.warn("error");
    })
    .always(function () {
        console.info("finished");
    });
};