package govhackathon.pl.mobileapp.activities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

import govhackathon.pl.mobileapp.R;

class ListViewContainerAdapter extends BaseAdapter {

    Context context;
    ArrayList<ContainerObject> containers;

    private static LayoutInflater inflater = null;

    public ListViewContainerAdapter(Context context, ArrayList<ContainerObject> containers) {
        this.context = context;
        this.containers = containers;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        Log.d("test", "count is " + Integer.toString(containers.size()));
        return containers.size();
    }

    @Override
    public Object getItem(int position) {
        return containers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub


        ContainerObject containerObject = this.containers.get(position);

        return this.getLayoutForContainerObject(convertView, containerObject);
    }

    private View getLayoutForContainerObject(View convertView, ContainerObject containerObject) {
        View vi = convertView;

        if (vi == null) {
            switch (containerObject.key) {
                case "primaryschool": {
                    vi = inflater.inflate(R.layout.container_school, null);

                    TextView text = (TextView) vi.findViewById(R.id.text);
                    TextView headerText = (TextView) vi.findViewById(R.id.header);

                    headerText.setText("Szkoły podstawowe");

                    try {
                        int amount = containerObject.jsonObject.getInt("amountInRadius");

                        if (amount > 0) {
                            text.setText("W okolicy znajduje się " + Integer.toString(amount) + " szkół podstawowych. Najbliższa szkoła (" + containerObject.jsonObject.getJSONObject("closest").getString("name") + "): " + Integer.toString((int) (containerObject.jsonObject.getDouble("distance") * 100.0)) + " metrów.");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "middleschool": {
                    vi = inflater.inflate(R.layout.container_school, null);

                    TextView text = (TextView) vi.findViewById(R.id.text);
                    TextView headerText = (TextView) vi.findViewById(R.id.header);

                    headerText.setText("Szkoły ponadgimnazjalne");

                    try {
                        int amount = containerObject.jsonObject.getInt("amountInRadius");

                        if (amount > 0) {
                            text.setText("W okolicy znajduje się " + Integer.toString(amount) + " szkół ponadgimnazjalnych. Najbliższa szkoła (" + containerObject.jsonObject.getJSONObject("closest").getString("name") + "): " + Integer.toString((int) (containerObject.jsonObject.getDouble("distance") * 100.0)) + " metrów.");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "highschool": {
                    vi = inflater.inflate(R.layout.container_school, null);

                    TextView text = (TextView) vi.findViewById(R.id.text);
                    TextView headerText = (TextView) vi.findViewById(R.id.header);

                    headerText.setText("Szkoły średnie");

                    try {
                        int amount = containerObject.jsonObject.getInt("amountInRadius");

                        if (amount > 0) {
                            text.setText("W okolicy znajduje się " + Integer.toString(amount) + " szkół średnich. Najbliższa szkoła (" + containerObject.jsonObject.getJSONObject("closest").getString("name") + "): " + Integer.toString((int) (containerObject.jsonObject.getDouble("distance") * 100.0)) + " metrów.");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "preschool": {
                    vi = inflater.inflate(R.layout.container_school, null);

                    TextView text = (TextView) vi.findViewById(R.id.text);
                    TextView headerText = (TextView) vi.findViewById(R.id.header);

                    headerText.setText("Przedszkola");

                    try {
                        int amount = containerObject.jsonObject.getInt("amountInRadius");

                        if (amount > 0) {
                            text.setText("W okolicy znajduje się " + Integer.toString(amount) + " przedszkól. Najbliższe (" + containerObject.jsonObject.getJSONObject("closest").getString("name") + "): " + Integer.toString((int) (containerObject.jsonObject.getDouble("distance") * 100.0)) + " metrów.");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "busStop": {
                    vi = inflater.inflate(R.layout.container_bus_stop, null);

                    TextView text = (TextView) vi.findViewById(R.id.text);
                    TextView headerText = (TextView) vi.findViewById(R.id.header);

                    headerText.setText("Przystanki autobusowe");

                    try {
                        int amount = containerObject.jsonObject.getInt("amountInRadius");

                        if (amount > 0) {
                            text.setText("W okolicy znajduje się " + Integer.toString(amount) + " przystanków autobusowych. Najbliższy przystanek: " + Integer.toString((int) (containerObject.jsonObject.getDouble("distance") * 100.0)) + " metrów");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "relic": {
                    vi = inflater.inflate(R.layout.container_bus_stop, null);

                    TextView text = (TextView) vi.findViewById(R.id.text);
                    TextView headerText = (TextView) vi.findViewById(R.id.header);

                    headerText.setText("Zabytki");

                    try {
                        int amount = containerObject.jsonObject.getInt("amountInRadius");

                        if (amount > 0) {
                            text.setText("W okolicy znajduje się " + Integer.toString(amount) + " zabytków. Najbliższy zabytek (" + containerObject.jsonObject.getJSONObject("closest").getString("name") + "): " + Integer.toString((int) (containerObject.jsonObject.getDouble("distance") * 100.0)) + " metrów");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "tramStop": {
                    vi = inflater.inflate(R.layout.container_tram_stop, null);

                    TextView text = (TextView) vi.findViewById(R.id.text);
                    TextView headerText = (TextView) vi.findViewById(R.id.header);

                    headerText.setText("Przystanki tramwajowe");

                    try {
                        int amount = containerObject.jsonObject.getInt("amountInRadius");

                        if (amount > 0) {
                            text.setText("W okolicy znajduje się " + Integer.toString(containerObject.jsonObject.getInt("amountInRadius")) + " przystanków tramwajowych. Najbliższy przystanek: " + Integer.toString((int) (containerObject.jsonObject.getDouble("distance") * 100.0)) + " metrów");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                }
                case "partyability":
                    vi = inflater.inflate(R.layout.container_partyability, null);
                    break;
            }
        }

        return vi;
    }
}