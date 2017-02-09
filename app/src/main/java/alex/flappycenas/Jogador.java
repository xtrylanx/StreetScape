package alex.flappycenas;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.Toast;

/**
 * Created by user on 21-01-2017.
 */

public class Jogador {
    private Bitmap imagem;
    private float x,y;
    private int xSpeed;
    private boolean firstTime = true;
    private int altura,largura;
    private float offsetX;
    private int limite;

    private Paint paint = new Paint();

    public Jogador(Bitmap imagem, int altura, int largura,int limite, int largura1) {
        this.imagem = imagem;
        this.altura = altura;
        this.largura = largura;
        this.offsetX = (float)largura / 2;
        this.limite = limite;


    }
    public void update(){

    }
    public void draw(Canvas canvas){
        if(firstTime){
            // Para saltar de faixa em faixa
            this.xSpeed = largura / 5;

            this.y =9 * (canvas.getWidth()/10);
            this.x = canvas.getHeight()/2;
            firstTime = false;
        }
        canvas.drawBitmap(imagem,x,y,null);


    }

    public void mover(float xFinal) {
       if(x - offsetX < 0){
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

    }
    public Rect getRect(){
        return new Rect((int)x,(int)y, (int)(x + imagem.getWidth()) , (int)(y + imagem.getHeight()));
    }
}
