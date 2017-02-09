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

import java.io.BufferedReader;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class MenuActivity extends AppCompatActivity {
    public final static int PERDER_JOGO = 1;
    public final static String FILENAME_SCORES_PATH = "/scores.txt";
    private int pontuacao;
    public static Integer[] scores = new Integer[3];

    private InterstitialAd interstitial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        interstitial = new InterstitialAd(MenuActivity.this);
        setContentView(R.layout.activity_menu);
        interstitial = new InterstitialAd(getApplicationContext());
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
        inicializarArrayScores();
    }

    @Override
    protected void onResume() {
        super.onResume();
        leScores();

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
        startActivity(intent);
    }

    public Integer[] getScores() {
        return scores;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        pontuacao = data.getIntExtra("pontuacao", -1);
        adicionaAListaScore(pontuacao);
        guardaScores();
        interstitial = new InterstitialAd(getApplicationContext());
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

    public static void adicionaAListaScore(int pontuacao) {
        if (scores[2] < pontuacao) {
            scores[2] = pontuacao;
        }
        Arrays.sort(scores, new Comparator<Integer>() {
            @Override
            public int compare(Integer x, Integer y) {
                return y - x;
            }
        });

    }

    private void reiniciaPontuacao() {
        pontuacao = 0;
    }

    private void inicializarArrayScores() {
        pontuacao = 0;
        scores[0] = 0;
        scores[1] = 0;
        scores[2] = 0;

    }

    private void guardaScores() {

        try {
            File outputFile = new File(getFilesDir() + FILENAME_SCORES_PATH);
            OutputStream outStream = new FileOutputStream(outputFile);
            OutputStreamWriter osWriter = new OutputStreamWriter(outStream);
            for (Integer i : scores) {
                osWriter.write(String.valueOf(i));
                osWriter.write("\n");

            }
            osWriter.close();

        } catch (IOException OE) {
            OE.getStackTrace();

        }

    }

    private void leScores() {
        int j = 0;
        File file = new File(getFilesDir() + FILENAME_SCORES_PATH);
        if (file.exists()) {
            BufferedReader rd;
            try {
                rd = new BufferedReader(new FileReader(getFilesDir() + FILENAME_SCORES_PATH));
                String line = null;
                while ((line = rd.readLine()) != null) {
                    scores[j] = Integer.parseInt(line);
                    if (j > 3) {
                        break;
                    }
                    j++;
                }
                rd.close();
            } catch (final IOException e) {
                System.out.println("Error reading file");
                throw new RuntimeException("Error reading file : " + e.getMessage(), e);
            }

        }
    }
}
