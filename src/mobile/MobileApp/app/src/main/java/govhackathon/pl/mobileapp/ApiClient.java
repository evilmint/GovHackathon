package govhackathon.pl.mobileapp;

import android.animation.ArgbEvaluator;
import android.graphics.Color;

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

            if (marker.has("description")) {
                dataMarker.setDescription(marker.getString("description"));
            }

            dataMarker.setLat(marker.getDouble("latitude"));
            dataMarker.setLon(marker.getDouble("longitude"));

            if (marker.has("name")) {
                dataMarker.setName(marker.getString("name"));
            }

            if (marker.has("type")) {
                dataMarker.setType(marker.getString("type"));
            }

            apiData.markers.add(dataMarker);
        }

        return apiData;
    }

    public static ApiData retrieveHousingDataFromUrl(final String url) throws IOException, JSONException {
        ApiData apiData = new ApiData();

        String responseBody = retrieveApiString(url);

        JSONArray markersArray = new JSONArray(responseBody);

        double lowestScore = 0.0;
        double highestScore = 0.0;

        for (int i = 0; i < markersArray.length(); ++i) {
            JSONObject marker = markersArray.getJSONObject(i);

            ApiDataMarker dataMarker = new ApiDataMarker();

            if (marker.has("description")) {
                dataMarker.setDescription(marker.getString("description"));
            }

            dataMarker.setLat(marker.getDouble("latitude"));
            dataMarker.setLon(marker.getDouble("longitude"));

            if (marker.has("name")) {
                dataMarker.setName(marker.getString("name"));
            }

            if (marker.has("price")) {
                dataMarker.setPrice(marker.getInt("price"));
            }

            if (marker.has("type")) {
                dataMarker.setType(marker.getString("type"));
            }

            if (marker.has("imageUrl")) {
                dataMarker.setLink(marker.getString("imageUrl"));
            }

            double score = marker.getDouble("score");

            if (score < lowestScore)
                lowestScore = score;

            if (score > highestScore)
                highestScore = score;

            dataMarker.setScore(score);

            apiData.markers.add(dataMarker);
        }

        for (ApiDataMarker marker : apiData.markers) {
            double currentScore = marker.getScore() / highestScore;

            int markerColor = (Integer)new ArgbEvaluator().evaluate((float)currentScore, 0x00ff00, 0xff0000);
            float hsv[] = new float[3];

            Color.colorToHSV(markerColor, hsv);

            marker.setHue(hsv[0]);
        }

        return apiData;
    }

    public static String retrieveApiString(final String url) throws IOException, JSONException {
        URLConnection connection = new URL(url).openConnection();
        InputStream response = connection.getInputStream();

        if (response != null) {
            Scanner scanner = new Scanner(response).useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }

        return "";
    }

    public static ApiData retrieveBoundingMarkerDataFromUrl(String url) throws IOException, JSONException {
        ApiData apiData = new ApiData();

        String responseBody = retrieveApiString(url);
        JSONArray markersArray = new JSONArray(responseBody);

        for (int i = 0; i < markersArray.length(); ++i) {
            JSONObject marker = markersArray.getJSONObject(i);

            ApiDataMarker dataMarker = new ApiDataMarker();

            dataMarker.setDescription("");
            dataMarker.setLat(marker.getDouble("latitude"));
            dataMarker.setLon(marker.getDouble("longitude"));
            dataMarker.setName(marker.getString("name"));
            dataMarker.setType(marker.getString("type"));

            apiData.markers.add(dataMarker);
        }

        return apiData;
    }
}
