package govhackathon.pl.mobileapp.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import govhackathon.pl.mobileapp.R;

public class DetailViewActivity extends AppCompatActivity {

    public static ApiDataMarker apiDataMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = (TextView)findViewById(R.id.textView);

        if (apiDataMarker != null) {
            textView.setText(apiDataMarker.getDescription());

            if (getActionBar() != null) {
                setTitle(apiDataMarker.getName());
                getActionBar().setTitle(apiDataMarker.getName());
            }
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
