package com.example.coronalivestats;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class IRA extends AppCompatActivity {
    ImageButton btnCheck;
    EditText txtScore;
    TextView txtResult;
    int score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ir);

        btnCheck = findViewById(R.id.btnCheck);
        txtResult = findViewById(R.id.txtResult);
        txtScore = findViewById(R.id.txtScore);

        WebView webView = (WebView)findViewById(R.id.webViewIRA);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSfU3TnIWgp2w-resL_jlDI5tgA2ZkJ3zjr9bhKPBMdy5MCUvw/viewform?usp=sf_link");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    score = Integer.parseInt(txtScore.getText().toString());
                }
                catch (NumberFormatException nfe)
                {}
                if(score>23)
                    txtResult.setText("Put valid score.");
                else if(score>=0 && score<=6)
                    txtResult.setText("Low Risk");
                else if(score>=7 && score<=12)
                    txtResult.setText("Moderate Risk");
                else if(score>=13 && score<=23)
                    txtResult.setText("High Risk");
            }
        });

    }

}
