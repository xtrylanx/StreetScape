package alex.flappycenas;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by user on 21-01-2017.
 */

public class Fundo {

    private Bitmap imagem;
    private int x ,y;
    private int ySpeed = 15;

    public Fundo(Bitmap imagem){
        this.imagem = imagem;
    }

    public void update(){
        y += ySpeed;
        if(y > imagem.getHeight()){
            y = 0;
        }
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(imagem,x,y,null);
        if(y > 0){
            canvas.drawBitmap(imagem,x , y - imagem.getHeight(),null);
        }
    }
}
