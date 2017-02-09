package alex.flappycenas;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

/**
 * Created by user on 08-02-2017.
 */
public class ThreadPrincipal extends Thread{
    private static final int FPS = 60;

    private SurfaceHolder surfaceHolder;
    private PainelJogo painelJogo;
    private Inimigo inimigo;
    private static Canvas canvas;
    private boolean activo = false;


    public ThreadPrincipal(SurfaceHolder surfaceHolder, PainelJogo painelJogo){
        super();
        this.surfaceHolder = surfaceHolder;
        this.painelJogo = painelJogo;
    }
    @Override
    public void run() {
        long tempoInicio;
        long tempoEspera;
        long tempoMilisegundos;
        long tempoObjetivo = 1000/FPS;
        long tempoTotal = 0;
        double fpsMedio;
        int frame = 0;

        while(activo){
            tempoInicio = System.currentTimeMillis();
            canvas = null;

            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.painelJogo.update();
                    this.painelJogo.draw(canvas);

                }
            } catch(Exception e){}

            if(canvas != null){
                try{
                    surfaceHolder.unlockCanvasAndPost(canvas);
                } catch(Exception e){}
            }
            tempoMilisegundos = (System.currentTimeMillis() - tempoInicio);
            // para que o jogo espere para fazer os 30 fps
            tempoEspera = tempoObjetivo - tempoMilisegundos;
            try{
                this.sleep(tempoEspera);
            } catch(Exception e){}

            tempoTotal += System.currentTimeMillis() - tempoInicio;
            frame ++;
            if(frame == FPS) {
                fpsMedio = 1000 / (tempoTotal/frame);
                //System.out.println("FPS Esperado: " + FPS + "\nFPS Real: " + fpsMedio);
                frame = 0;
                tempoTotal = 0;
            }


        }
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean getActivo() {
        return activo;
    }
}
