package alex.flappycenas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MenuActivity extends AppCompatActivity {
    public final static int PERDER_JOGO = 1;
    public final static String FILENAME_SCORES = "scores";
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


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PERDER_JOGO) {
            pontuacao = data.getIntExtra("pontuacao", -1);
            if (pontuacao != -1) {
                Toast.makeText(this, "A sua pontuacao foi gadada: " + String.valueOf(pontuacao), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void reiniciaPontuacao() {
        pontuacao=0;
    }

    public void guardaPontuacao() {
        String toSave = String.valueOf(pontuacao);
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME_SCORES, Context.MODE_PRIVATE);
            fos.write(toSave.getBytes());
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
