package com.example.coronalivestats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Interpolator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
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
import com.scwang.wave.MultiWaveHeader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.IdentityHashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String Globl_stat_api = "https://thevirustracker.com/free-api?global=stats";
    // Global stats = https://thevirustracker.com/free-api?global=stats
    // Country-wise stats = https://thevirustracker.com/free-api?countryTotal=IN
    TextView  total_affected_countries, total_cases,  total_recovered,  total_unresolved,  total_deaths,  total_new_cases_today,  total_new_deaths_today,  total_active_cases,  total_serious_cases;
    Button btnHeatMap, btnAnyCountry, btnIRA, btnInteractiveMap;
    char[] alphabets = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
    CardView cardView;
    ImageButton btnRefresh;
    MultiWaveHeader waveView;

    TextView date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        total_affected_countries = findViewById(R.id.total_affected_countries);
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
        waveView = findViewById(R.id.waveView);

        String currdate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        date.setText(currdate);
        loadGlobalStats();

        waveView = findViewById(R.id.waveView);
        waveView.setStartColorId(R.color.White);
        waveView.setCloseColorId(R.color.colorAccent);
        waveView.setVelocity(65);
        waveView.setWaveHeight(30);
        waveView.setGradientAngle(20);
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

                    String total_affected_countries_txt = globalArray.getString("total_affected_countries");
                    animateTextView(0,Integer.parseInt(total_affected_countries_txt.toString()),total_affected_countries);

                    String total_cases_txt = globalArray.getString("total_cases");
                    animateTextView(0,Integer.parseInt(total_cases_txt.toString()),total_cases);

                    String total_recovered_txt = globalArray.getString( "total_recovered");
                    animateTextView(0,Integer.parseInt(total_recovered_txt.toString()),total_recovered);


                    String  total_unresolved_txt = globalArray.getString("total_unresolved");
                    animateTextView(0,Integer.parseInt(total_unresolved_txt.toString()),total_unresolved);

                    String total_deaths_txt = globalArray.getString("total_deaths");
                    animateTextView(0,Integer.parseInt(total_deaths_txt.toString()),total_deaths);

                    String total_new_cases_today_txt = globalArray.getString("total_new_cases_today");
                    animateTextView(0,Integer.parseInt(total_new_cases_today_txt.toString()),total_new_cases_today);

                    String total_new_deaths_today_txt = globalArray.getString("total_new_deaths_today");
                    animateTextView(0,Integer.parseInt(total_new_deaths_today_txt.toString()),total_new_deaths_today);

                    String total_active_cases_txt = globalArray.getString("total_active_cases");
                    animateTextView(0,Integer.parseInt(total_active_cases_txt.toString()),total_active_cases);

                    String total_serious_cases_txt = globalArray.getString("total_serious_cases");
                    animateTextView(0,Integer.parseInt(total_serious_cases_txt.toString()),total_serious_cases);


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
    public void animateTextView(int initialValue, int finalValue, final TextView  textview) {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(initialValue, finalValue);
        valueAnimator.setDuration(3000);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                textview.setText(valueAnimator.getAnimatedValue().toString());

            }
        });
        valueAnimator.start();

    }
}
