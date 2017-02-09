package alex.flappycenas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MenuActivity extends AppCompatActivity {
    public final static int PERDER_JOGO = 1;
    public final static String FILENAME_SCORES = "scores.txt";
    private int pontuacao;

    private InterstitialAd interstitial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        interstitial = new InterstitialAd(MenuActivity.this);
        setContentView(R.layout.activity_menu);
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
        ActionBar ab = getSupportActionBar();
        ab.hide();
        reiniciaPontuacao();
    }



    public void play(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, PERDER_JOGO);

    }


    public void off(View view) {
        finish();
    }

    public void showRecords(View view) {
        Intent intent = new Intent(this, RecordsActivity.class);
        intent.putExtra("pontuacao", String.valueOf(pontuacao));
        startActivity(intent);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PERDER_JOGO) {
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
            pontuacao = data.getIntExtra("pontuacao", -1);
        }
    }

    public void reiniciaPontuacao() {
        pontuacao = 0;
    }


}
