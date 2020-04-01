package com.example.coronalivestats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.IdentityHashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String Globl_stat_api = "https://thevirustracker.com/free-api?global=stats";
    // Global stats = https://thevirustracker.com/free-api?global=stats
    // Country-wise stats = https://thevirustracker.com/free-api?countryTotal=IN
    TextView total_cases,  total_recovered,  total_unresolved,  total_deaths,  total_new_cases_today,  total_new_deaths_today,  total_active_cases,  total_serious_cases;
    Button btnHeatMap, btnAnyCountry, btnIRA, btnInteractiveMap;
    CardView cardView;
    ImageButton btnRefresh;

    TextView date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        total_cases = findViewById(R.id.total_cases);
        total_recovered = findViewById(R.id.total_recovered);
        total_unresolved = findViewById(R.id.total_unresolved);
        total_deaths = findViewById(R.id.total_deaths);
        total_new_cases_today = findViewById(R.id.total_new_cases_today);
        total_new_deaths_today = findViewById(R.id.total_new_deaths_today);
        total_active_cases = findViewById(R.id.total_active_cases);
        total_serious_cases = findViewById(R.id.total_serious_cases);
        btnHeatMap = findViewById(R.id.btnHeatMap);
        btnAnyCountry = findViewById(R.id.btnAnyCountry);
        cardView = findViewById(R.id.cardView);
        btnRefresh = findViewById(R.id.btnRefresh);
        btnInteractiveMap = findViewById(R.id.btnInteractiveMap);
        btnIRA = findViewById(R.id.btnIRA);
        date = findViewById(R.id.date);

        String currdate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        date.setText(currdate);
        loadGlobalStats();
        total_serious_cases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnHeatMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),HeatMapWebview.class);
                startActivity(next);
            }
        });

        btnAnyCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),SearchCountry.class);
                startActivity(next);
            }
        });
        btnIRA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),IRA.class);
                startActivity(next);
            }
        });

        btnInteractiveMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), IMap.class);
                startActivity(next);
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Fetching latest Data :)", Toast.LENGTH_SHORT).show();
                loadGlobalStats();
            }
        });

    }

    private void loadGlobalStats() {
        //getting the progressbar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Globl_stat_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //hiding the progressbar after completion
                progressBar.setVisibility(View.INVISIBLE);
                cardView.setVisibility(View.VISIBLE);




                try {
                    //getting the whole json object from the response
                    JSONObject obj = new JSONObject(response);
                    JSONArray result = obj.getJSONArray("results");
                    JSONObject globalArray = result.getJSONObject(0);
                    String total_cases_txt = globalArray.getString("total_cases");
                    total_cases.setText(total_cases_txt);

                    String total_recovered_txt = globalArray.getString( "total_recovered");
                    total_recovered.setText(total_recovered_txt);

                    String  total_unresolved_txt = globalArray.getString("total_unresolved");
                    total_unresolved.setText(total_unresolved_txt);

                    String total_deaths_txt = globalArray.getString("total_deaths");
                    total_deaths.setText(total_deaths_txt);

                    String total_new_cases_today_txt = globalArray.getString("total_new_cases_today");
                    total_new_cases_today.setText(total_new_cases_today_txt);

                    String total_new_deaths_today_txt = globalArray.getString("total_new_deaths_today");
                    total_new_deaths_today.setText(total_new_deaths_today_txt);

                    String total_active_cases_txt = globalArray.getString("total_active_cases");
                    total_active_cases.setText(total_active_cases_txt);

                    String total_serious_cases_txt = globalArray.getString("total_serious_cases");
                    total_serious_cases.setText(total_serious_cases_txt);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

}
