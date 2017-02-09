package alex.flappycenas;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecordsActivity extends AppCompatActivity {
    private TextView recordes;
    private String jogador;
    private String pontuacao;
    private InterstitialAd interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        recordes = (TextView) findViewById(R.id.textViewPontuacao);
        guardaPontuacao(getIntent().getStringExtra("pontuacao"));
        lerRecordes();
        interstitial = new InterstitialAd(RecordsActivity.this);


        interstitial= new InterstitialAd(getApplicationContext());
        interstitial.setAdUnitId(getString(R.string.admob_interstetial_ad));
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitial.loadAd(adRequest);
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                if (interstitial.isLoaded()) {
                    interstitial.show();
                }
            }
        });

    }

    private void lerRecordes() {
        int ch;
        StringBuffer fileContent = new StringBuffer("");

        try {
            FileInputStream fis = getApplicationContext().openFileInput(MenuActivity.FILENAME_SCORES);
            try {
                while( (ch = fis.read()) != -1)
                    fileContent.append((char)ch);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String text = new String(fileContent);
        recordes.setText(text);

    }


    public void guardaPontuacao(String pontuacao) {
        try {
            FileOutputStream fos  = openFileOutput(MenuActivity.FILENAME_SCORES, Context.MODE_APPEND);
            fos.write(pontuacao.getBytes());
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
