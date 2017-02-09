package alex.flappycenas;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Romeu Sousa on 09/02/2017.
 */

public class Amigo {
    private int x ,y;
    private int ySpeed = 15;
    private int altura,largura;
    private boolean morto;
    private Bitmap imagem;
    private Paint paint = new Paint();


    public Amigo(int x, Bitmap imagem, int altura, int largura){
        this.morto = false;
        this.imagem = imagem;
        this.altura = altura;
        this.largura = largura;
        this.x = x;
    }

    public void update(){
        y += ySpeed;
    }
    public void draw(Canvas canvas){

        Log.d("canvasAccel", String.valueOf(canvas.isHardwareAccelerated()));
        canvas.drawBitmap(imagem,x,y,null);


    }
    public Rect getRect(){
        return new Rect((int)x,(int)y, (int)(x + imagem.getWidth()) , (int)(y + imagem.getHeight() ));
    }

    public boolean isFora(int limite) {
        return y > limite;
    }


    public boolean getMorto() {
        return morto;
    }
    public void setMorto() {
        morto = true;
    }
}
