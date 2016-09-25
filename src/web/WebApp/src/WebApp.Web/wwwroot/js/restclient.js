function getData(getUrl) {
    $.ajax({
        url: getUrl,
        type: 'GET',
        success: onSuccess(data)
    });
}

function buildUrl(baseUrl, long, lat) {
    return baseUrl + "?Latitude=" + long + "&Longitude=" + lat;
}

function onSuccess(data) {
    result = data;
}

function onError() {

}

var result;