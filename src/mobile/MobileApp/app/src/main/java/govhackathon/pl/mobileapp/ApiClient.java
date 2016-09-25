package govhackathon.pl.mobileapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import govhackathon.pl.mobileapp.activities.ApiDataMarker;

public class ApiClient {
    public static ApiData retrieveDataFromUrl(final String url) throws IOException, JSONException {
        ApiData apiData = new ApiData();

        String responseBody = retrieveApiString(url);

        JSONArray markersArray = new JSONArray(responseBody);

        for (int i = 0; i < markersArray.length(); ++i) {
            JSONObject marker = markersArray.getJSONObject(i);

            ApiDataMarker dataMarker = new ApiDataMarker();

            dataMarker.setDescription(marker.getString("description"));
            dataMarker.setLat(marker.getDouble("latitude"));
            dataMarker.setLon(marker.getDouble("longitude"));
            dataMarker.setName(marker.getString("name"));
            dataMarker.setType(marker.getString("type"));

            apiData.markers.add(dataMarker);
        }

        return apiData;
    }

    public static String retrieveApiString(final String url) throws IOException, JSONException {
        Log.d("test", "x2.1");
        URLConnection connection = new URL(url).openConnection();
        Log.d("test", "x2.2");
        InputStream response = connection.getInputStream();

        if (response == null) {
            Log.d("test", "InputStream response is null");
        } else {
            Scanner scanner = new Scanner(response).useDelimiter("\\A");
            Log.d("test", "x2.4");
            return scanner.hasNext() ? scanner.next() : "";
        }
        Log.d("test", "x2.3");
        return "";


    }

    public static ApiData retrieveBoundingMarkerDataFromUrl(String url) throws IOException, JSONException {
        ApiData apiData = new ApiData();
        Log.d("test", "x1");
        String responseBody = retrieveApiString(url);
        Log.d("test", "x2");
        JSONArray markersArray = new JSONArray(responseBody);
        Log.d("test", "x3");
        for (int i = 0; i < markersArray.length(); ++i) {
            JSONObject marker = markersArray.getJSONObject(i);
            Log.d("test", "x4");
            ApiDataMarker dataMarker = new ApiDataMarker();
            Log.d("test", "x5");
            dataMarker.setDescription("");
            dataMarker.setLat(marker.getDouble("latitude"));
            Log.d("test", "x6");
            dataMarker.setLon(marker.getDouble("longitude"));
            Log.d("test", "x7");
            dataMarker.setName(marker.getString("name"));
            Log.d("test", "x8");
            dataMarker.setType(marker.getString("type"));
            Log.d("test", "x9");
            Log.d("test", "Retrieve new: " + marker.getString("name"));

            apiData.markers.add(dataMarker);
        }

        return apiData;
    }
}
