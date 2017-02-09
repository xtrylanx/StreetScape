package alex.flappycenas;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*// Fazer desaparecer o titulo
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Para meter o jogo em fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        */
        ActionBar ab = getSupportActionBar();
        ab.hide();
        setContentView(new  PainelJogo(this));
    }
}
