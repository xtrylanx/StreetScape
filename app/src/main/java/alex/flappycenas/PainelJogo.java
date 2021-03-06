package alex.flappycenas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;

/**
 * Created by user on 08-02-2017.
 */

public class PainelJogo extends SurfaceView implements SurfaceHolder.Callback {
    public int limiteX;
    private ThreadPrincipal threadPrincipal;
    private Fundo fundo;
    private Jogador jogador;

    private ArrayList<Inimigo> inimigos;
    private ArrayList<Amigo> amigos;
    private long tempoInicio;
    private long tempoFinal;
    private long intervalo;
    private int altura, largura;
    private Bitmap img[] = new Bitmap[4];
    private Bitmap amigo[] = new Bitmap[1];
    private Random r;
    private Paint paint = new Paint();
    private int pontuacao;
    private Boolean colision = false;
    private Boolean PontuacaoN = false;
    private Handler handler;


    public PainelJogo(Context context) {
        super(context);
        // para obter os eventos do dedo
        getHolder().addCallback(this);
        threadPrincipal = new ThreadPrincipal(getHolder(), this);
        // Falta a imagem
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        this.largura = size.x;
        this.altura = size.y;
        limiteX = largura;
        Log.d("size", "largura: " + String.valueOf(size.x) + "altura: " + String.valueOf(size.y)+ "limitex: " + String.valueOf(limiteX));
        pontuacao = 0;
        handler = new Handler();

        Bitmap bg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.estrada), largura, altura, false);
        fundo = new Fundo(bg);
        bg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.car), largura / 5, altura / 4, false);
        jogador = new Jogador(bg, altura / 8, largura / 4, limiteX);

        inimigos = new ArrayList<Inimigo>();
        amigos = new ArrayList<Amigo>();


        tempoInicio = System.currentTimeMillis();
        intervalo = 1000;
        img[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cone), largura / 5, (altura / 8), false);
        img[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.caixa), largura / 5, (altura / 8), false);
        img[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lixo), largura / 5, (altura / 8), false);
        img[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.oil), largura / 5, (altura / 8), false);
        amigo[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.nota), largura / 5, (altura / 8), false);
        r = new Random();
        // para que seja este ecra o que esta activo
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        threadPrincipal.setActivo(true);
        threadPrincipal.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                threadPrincipal.setActivo(false);
                ((MainActivity) getContext()).setResult(MenuActivity.PERDER_JOGO, new Intent().putExtra("pontuacao", pontuacao));
                ((MainActivity) getContext()).finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case ACTION_DOWN:
                jogador.mover(event.getX());
                return true;
            case ACTION_UP:
                return true;
        }
        return super.onTouchEvent(event);
    }

    public void update() {
        tempoFinal = System.currentTimeMillis() - tempoInicio;
        Log.d("speed", String.valueOf(intervalo));
        if (tempoFinal > intervalo) {
            Bitmap bg;


            if (pontuacao!= 0 && pontuacao%10 == 0) {
                pontuacao++;
                bg = amigo[0];
                criarAmigo(bg);
                tempoInicio = System.currentTimeMillis();
            } else {
                bg = img[r.nextInt(4)];
                criarInimigo(bg);
                tempoInicio = System.currentTimeMillis();
            }

        }
        fundo.update();
        for (Inimigo i : inimigos) {
            if (!i.getMorto()) {
                if (i.isFora(altura)) {
                    i.setMorto();
                    if(intervalo > 400){
                        intervalo-=10;
                    }

                    pontuacao++;
                }
                i.update();
            }
            if (colisao(i, jogador)) {
                threadPrincipal.setActivo(false);
                ((MainActivity) getContext()).setResult(MenuActivity.PERDER_JOGO, new Intent().putExtra("pontuacao", pontuacao));
               ((MainActivity) getContext()).finish();
            } else {
            }
        }
        jogador.update();
//Nivel


        for (Amigo a : amigos) {
            if (!a.getMorto()) {
                if (a.isFora(altura)) {
                    a.setMorto();
                    colision = false;
                }
                a.update();
            }
            if (colisaoA(a, jogador)) {
                if (colision.equals(false)) {
                    colision = true;
                    pontuacao += 5;
                    PontuacaoN = true;
                }

            }
        }


    }

    private void criarAmigo(Bitmap bg) {
        int x = limiteX / 4 * (r.nextInt(4));
        amigos.add(new Amigo(x, bg, largura / 5, (altura / 8)));
    }



    //Colisao dos objectos
    private boolean colisao(Inimigo i, Jogador j) {

        if (Rect.intersects(i.getRect(), j.getRect())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean colisaoNotaInimigo(Inimigo i, Amigo a) {

        if (Rect.intersects(i.getRect(), a.getRect())) {
            return true;
        } else {
            return false;
        }
    }

    //Colisao dos objectos
    private boolean colisaoA(Amigo i, Jogador j) {

        if (Rect.intersects(i.getRect(), j.getRect())) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void draw(Canvas canvas) {
        if (canvas != null) {
            fundo.draw(canvas);
            for (Inimigo i : inimigos) {
                i.draw(canvas);
            }
            for (Amigo a : amigos) {
                a.draw(canvas);
            }
            jogador.draw(canvas);

                paint.setColor(Color.WHITE);
                paint.setTextSize(100);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setFakeBoldText(true);
                canvas.drawText("" + pontuacao, canvas.getWidth() / 2, canvas.getHeight() / 2, paint);

        }
    }

    public void terminarJogo() {
        this.threadPrincipal.setActivo(false);
    }

    public void criarInimigo(Bitmap bg) {
        int x = limiteX / 4 * (r.nextInt(4));
        inimigos.add(new Inimigo(x, bg, largura / 5, (altura / 8)));
    }

}
