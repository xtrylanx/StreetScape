package alex.flappycenas;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by user on 21-01-2017.
 */

public class Jogador {
    private Bitmap imagem;
    private float x, y;
    private int xSpeed;
    private boolean firstTime = true;
    private int altura, largura;
    private float offsetX;
    private float limite;

    private Paint paint = new Paint();

    public Jogador(Bitmap imagem, int altura, int largura, int limite) {
        this.imagem = imagem;
        this.altura = altura;
        this.largura = largura;
        this.limite = (float) limite;
        this.offsetX = (float) limite / 4;


    }

    public void update() {

    }

    public void draw(Canvas canvas) {
        if (firstTime) {
            // Para saltar de faixa em faixa
            this.xSpeed = largura;

            this.y = 15 * (canvas.getHeight() / 20);
            this.x = (canvas.getWidth() / 4);
            firstTime = false;
        }
        canvas.drawBitmap(imagem, x, y, null);


    }

    public void mover(float xFinal) {

        if (x - offsetX >= 0 || x + offsetX < limite) {
            if (xFinal > x+offsetX) {
                x += offsetX;
            } else if (xFinal < x) {
                x -= offsetX;
            }
        }

        Log.d("coords", "x: " + String.valueOf(x) + "\noffset: " + String.valueOf(offsetX) + "\nlimite: " + String.valueOf(limite) + "\nxFinal :" + String.valueOf(xFinal));


        /*if(x - offsetX < 0){
           x = offsetX/2;
       }
       if(x + offsetX > (limite-offsetX)){
           x = (limite - largura);
       }
        xFinal = xFinal - offsetX;
        if(x < xFinal){
            x+=xSpeed;
        } else if(x > xFinal){
            x-=xSpeed;
        }
*/
    }

    public Rect getRect() {
        return new Rect((int) x, (int) y, (int) (x + imagem.getWidth()), (int) (y + imagem.getHeight()));
    }
}
