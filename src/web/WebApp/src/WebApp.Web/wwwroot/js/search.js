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

    var searchParams = "";

    if (minprice) {
        searchParams = searchParams + "minprice:" + minprice;
    }

    if (maxprice) {
        searchParams = searchParams + ";maxprice:" + maxprice;
    }

    if (minarea) {
        searchParams = searchParams + ";minarea:" + minarea;
    }

    if (maxarea) {
        searchParams = searchParams + ";maxarea:" + maxarea;
    }

    if (preSchools) {
        searchParams = searchParams + ";preschool:" + preSchools;
    }

    if (primarySchools) {
        searchParams = searchParams + ";primaryschool:" + primarySchools;
    }

    if (middleSchools) {
        searchParams = searchParams + ";middleschool:" + middleSchools;
    }

    if (relics) {
        searchParams = searchParams + ";relic:" + relics;
    }

    if (stops) {
        searchParams = searchParams + ";stop:" + stop;
    }

    var jqxhr = $.ajax({
        url: "http://govhak.azurewebsites.net/houses/search",
        type: "get",
        data: { searchParams: searchParams }
    })
    .done(function (data) {
        sonsole.info("success");



    })
    .fail(function () {
        console.warn("error");
    })
    .always(function () {
        consile.info("finished");
    });
};