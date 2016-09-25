package govhackathon.pl.mobileapp;

import java.util.LinkedList;
import java.util.List;

import govhackathon.pl.mobileapp.activities.ApiDataContainer;
import govhackathon.pl.mobileapp.activities.ApiDataMarker;

public class ApiData {
    public List<ApiDataMarker> markers = new LinkedList<>();
    public List<ApiDataContainer> containers = new LinkedList<>();
}
