package alex.flappycenas;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
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
    private TextView primeiro;
    private TextView segundo;
    private TextView terceiro;
    private String jogador;
    private String pontuacao;

    private InterstitialAd interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        primeiro = (TextView) findViewById(R.id.textViewPrimeiro);
        segundo = (TextView) findViewById(R.id.textViewSegundo);
        terceiro = (TextView) findViewById(R.id.textViewTerceiro);
        Integer[] scores = MenuActivity.scores;
        primeiro.setText("Primeiro Lugar :" + String.valueOf(scores[0]));
        segundo.setText("Segundo Lugar :" + String.valueOf(scores[1]));
        terceiro.setText("Terceiro Lugar :" + String.valueOf(scores[2]));
        interstitial = new InterstitialAd(RecordsActivity.this);

        ActionBar ab = getSupportActionBar();
        ab.hide();


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

}
