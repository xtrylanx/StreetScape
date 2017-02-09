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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
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
            pontuacao = data.getIntExtra("pontuacao", -1);
        }
    }

    public void reiniciaPontuacao() {
        pontuacao = 0;
    }


}
