package com.example.coronalivestats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

public class SearchCountry extends AppCompatActivity {

    String Country_stat_api = "https://thevirustracker.com/free-api?countryTotal=";
    // Global stats = https://thevirustracker.com/free-api?global=stats
    // Country-wise stats = https://thevirustracker.com/free-api?countryTotal=IN

    AutoCompleteTextView txtSearchCountry;
    ImageButton imgbtnSearch;
    String CountryCode;
    CardView cardView1;
    ProgressBar progressBar1;
    MultiWaveHeader waveView1;
    TextView tBoxCountryName,_total_cases, _danger_rank, _total_recovered,  _total_unresolved,  _total_deaths,  _total_new_cases_today,  _total_new_deaths_today,  _total_active_cases,  _total_serious_cases;

    private String[] countryName = {"Afghanistan", "Albania", "Algeria", "Angola", "Argentina", "Armenia", "Australia", "Austria",
            "Azerbaijan", "Bahamas", "Bangladesh", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina",
            "Botswana", "Brazil", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Ivory Coast",
            "Central African Republic", "Chad", "Chile", "China", "Colombia", "Congo", "Democratic Republic of Congo", "Costa Rica", "Croatia",
            "Cuba", "Cyprus", "Czechia", "Denmark", "Diamond Princess", "Djibouti", "Dominican Republic", "DR Congo", "Ecuador", "Egypt", "El Salvador",
            "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands", "Fiji", "Finland", "France", "French Guiana", "French Southern Territories", "Gabon",
            "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Greenland", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras",
            "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan",
            "Kazakhstan", "Kenya", "Korea", "Kosovo", "Kuwait", "Kyrgyzstan", "Lao", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Lithuania",
            "Luxembourg", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Mali", "Mauritania", "Mexico", "Moldova", "Mongolia", "Montenegro", "Morocco",
            "Mozambique", "Myanmar", "Namibia", "Nepal", "Netherlands", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea",
            "Norway", "Oman", "Pakistan", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Puerto Rico",
            "Qatar", "Republic of Kosovo", "Romania", "Russia", "Rwanda", "Saudi Arabia", "Senegal", "Serbia", "Sierra Leone", "Singapore", "Slovakia", "Slovenia",
            "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Svalbard and Jan Mayen", "Swaziland",
            "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Trinidad and Tobago", "Tunisia",
            "Turkey", "Turkmenistan", "UAE", "Uganda", "United Kingdom", "Ukraine", "USA", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam",
            "Western Sahara", "Yemen", "Zambia", "Zimbabwe"};
    private String[] countryCode = {"AF", "AL", "DZ", "AO", "AR", "AM", "AU", "AT", "AZ", "BS", "BD", "BY", "BE", "BZ", "BJ", "BT", "BO", "BA", "BW",
            "BR", "BN", "BG", "BF", "BI", "KH", "CM", "CA", "CI", "CF", "TD", "CL", "CN", "CO", "CG", "CD", "CR", "HR", "CU", "CY", "CZ", "DK", "DP",
            "DJ", "DO", "CD", "EC", "EG", "SV", "GQ", "ER", "EE", "ET", "FK", "FJ", "FI", "FR", "GF", "TF", "GA", "GM", "GE", "DE", "GH", "GR", "GL",
            "GT", "GN", "GW", "GY", "HT", "HN", "HK", "HU", "IS", "IN", "ID", "IR", "IQ", "IE", "IL", "IT", "JM", "JP", "JO", "KZ", "KE", "KP", "XK",
            "KW", "KG", "LA", "LV", "LB", "LS", "LR", "LY", "LT", "LU", "MK", "MG", "MW", "MY", "ML", "MR", "MX", "MD", "MN", "ME", "MA", "MZ", "MM",
            "NA", "NP", "NL", "NC", "NZ", "NI", "NE", "NG", "KP", "NO", "OM", "PK", "PS", "PA", "PG", "PY", "PE", "PH", "PL", "PT", "PR", "QA", "XK",
            "RO", "RU", "RW", "SA", "SN", "RS", "SL", "SG", "SK", "SI", "SB", "SO", "ZA", "KR", "SS", "ES", "LK", "SD", "SR", "SJ", "SZ", "SE", "CH",
            "SY", "TW", "TJ", "TZ", "TH", "TL", "TG", "TT", "TN", "TR", "TM", "AE", "UG", "GB", "UA", "US", "UY", "UZ", "VU", "VE", "VN", "EH", "YE",
            "ZM", "ZW"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_country);
        txtSearchCountry = findViewById(R.id.txtSearchCountry);
        imgbtnSearch = findViewById(R.id.imgbtnSearch);
        progressBar1 = findViewById(R.id.progressBar1);
        _danger_rank = findViewById(R.id.danger_rank);
        _total_cases = findViewById(R.id._total_cases);
        _total_recovered = findViewById(R.id._total_recovered);
        _total_unresolved = findViewById(R.id._total_unresolved);
        _total_deaths = findViewById(R.id._total_deaths);
        _total_new_cases_today = findViewById(R.id._total_new_cases_today);
        _total_new_deaths_today = findViewById(R.id._total_new_deaths_today);
        _total_active_cases = findViewById(R.id._total_active_cases);
        _total_serious_cases = findViewById(R.id._total_serious_cases);
        tBoxCountryName = findViewById(R.id.tBoxCountryName);
        waveView1 = findViewById(R.id.waveView1);
        cardView1 = findViewById(R.id.cardView1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, countryName);
        txtSearchCountry.setThreshold(1);
        txtSearchCountry.setAdapter(adapter);

        waveView1 = findViewById(R.id.waveView1);
        waveView1.setStartColorId(R.color.White);
        waveView1.setCloseColorId(R.color.colorAccent);
        waveView1.setVelocity(65);
        waveView1.setWaveHeight(30);
        waveView1.setGradientAngle(20);

        imgbtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp_C_Name = txtSearchCountry.getText().toString();
                if(temp_C_Name.isEmpty())
                {}
                if(temp_C_Name!=null)
                {
                    cardView1.setVisibility(View.GONE);
                    progressBar1.setVisibility(View.GONE);
                    for(int i=0;i<countryName.length;i++)
                    {
                        if(temp_C_Name.contentEquals(countryName[i]))
                        {
                            CountryCode = countryCode[i];
                            Country_stat_api = Country_stat_api + CountryCode;
                            break;
                        }
                        else
                            continue;
                    }
                    loadCountryStats();

                }
            }
        });
    }

    private void loadCountryStats() {
        //getting the progressbar


        //making the progressbar visible
        progressBar1.setVisibility(View.VISIBLE);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Country_stat_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //hiding the progressbar after completion
                progressBar1.setVisibility(View.INVISIBLE);
                cardView1.setVisibility(View.VISIBLE);



                try {
                    //getting the whole json object from the response
                    JSONObject obj = new JSONObject(response);
                    JSONArray result = obj.getJSONArray("countrydata");
                    JSONObject globalArray = result.getJSONObject(0);

                    tBoxCountryName.setText(txtSearchCountry.getText().toString());

                    String dangerRank_txt = globalArray.getString("total_danger_rank");
                    animateTextView(0,Integer.parseInt(dangerRank_txt.toString()),_danger_rank);

                    String total_cases_txt = globalArray.getString("total_cases");
                    animateTextView(0,Integer.parseInt(total_cases_txt.toString()),_total_cases);

                    String total_recovered_txt = globalArray.getString( "total_recovered");
                    animateTextView(0,Integer.parseInt(total_recovered_txt.toString()),_total_recovered);

                    String  total_unresolved_txt = globalArray.getString("total_unresolved");
                    animateTextView(0,Integer.parseInt(total_unresolved_txt.toString()),_total_unresolved);

                    String total_deaths_txt = globalArray.getString("total_deaths");
                    animateTextView(0,Integer.parseInt(total_deaths_txt.toString()),_total_deaths);

                    String total_new_cases_today_txt = globalArray.getString("total_new_cases_today");
                    animateTextView(0,Integer.parseInt(total_new_cases_today_txt.toString()),_total_new_cases_today);

                    String total_new_deaths_today_txt = globalArray.getString("total_new_deaths_today");
                    animateTextView(0,Integer.parseInt(total_new_deaths_today_txt.toString()),_total_new_deaths_today);

                    String total_active_cases_txt = globalArray.getString("total_active_cases");
                    animateTextView(0,Integer.parseInt(total_active_cases_txt.toString()),_total_active_cases);

                    String total_serious_cases_txt = globalArray.getString("total_serious_cases");
                    animateTextView(0,Integer.parseInt(total_serious_cases_txt.toString()),_total_serious_cases);


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

    private void animateTextView(int initialValue, int finalValue, final TextView textview) {

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
