package com.example.danny.checkers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MyCanvas extends View {

    Paint paint;

    public MyCanvas(Context context){

        super(context);
        paint = new Paint();

    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        int s = 135;
        int num = s / 12;
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);

        canvas.drawPaint(paint);



        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {

                if ((row + col) % 2 == 0) {
                        paint.setColor(Color.WHITE);
                } else {
                    paint.setColor(Color.DKGRAY);
                }

                canvas.drawRect(col * (MainActivity.width / 8), row * (MainActivity.width / 8), (col * (MainActivity.width / 8)) + (MainActivity.width / 8), (row * (MainActivity.width / 8)) + (MainActivity.width / 8), paint);


            }
        }

        for( int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (Board.getInstance().getTable()[x][y] == "1") {
                    paint.setColor(Color.RED);
                    canvas.drawCircle((y * s) + (s / 2), (x * s) + (s / 2), (s * 7) / 18, paint); //if something found, draw a colored square
                }
                if( Board.getInstance().getTable()[x][y] == "2"){
                    paint.setColor(Color.YELLOW);
                    canvas.drawCircle((y * s) + (s / 2), (x * s) + (s / 2), (s * 7) / 18, paint); //if something found, draw a colored square
                }

                if(Board.getInstance().getTable()[x][y] == "3"){
                    paint.setColor(Color.RED);
                    canvas.drawCircle((y * s) + (s / 2), (x * s) + (s / 2), (s * 7) / 18, paint); //if something found, draw a colored square

                    paint.setColor(Color.BLACK);
                    paint.setStrokeWidth(5);

                    canvas.drawLines(new float[]{ (float) (s / 2) + (s * y), (float) ((s / 4) + (s * x) - num),
                            (float)((s / 4) + (s * y)), (float)(((s / 4) * 3) + (s * x) - num),
                            (float)((s / 4) + (s * y)), (float)(((s / 4) * 3) + (s * x) - num),
                            (float)(((s / 4) * 3) + (s * y)), (float)(((s / 4) * 3) + (s * x) - num),
                            (float)(((s / 4) * 3) + (s * y)), (float)(((s / 4) * 3) + (s * x) - num),
                            (float) (s / 2) + (s * y), (float) ((s / 4) + (s * x) - num)
                              }, paint);

                    canvas.drawLines(new float[] {(float)((s / 2) + (s * y)), (float)(((s / 4) * 3) + (s * x) + num),
                            (float)((s / 4) + (s * y)), (float)((s / 4) + (s * x) + num),
                            (float)((s / 4) + (s * y)), (float)((s / 4) + (s * x) + num),
                            (float)(((s / 4) * 3) + (s * y)), (float)((s / 4) + (s * x) + num),
                            (float)(((s / 4) * 3) + (s * y)), (float)((s / 4) + (s * x) + num),
                            (float)((s / 2) + (s * y)), (float)(((s / 4) * 3) + (s * x) + num)
                    }, paint );
                }

                if(Board.getInstance().getTable()[x][y] == "4"){
                    paint.setColor(Color.YELLOW);
                    canvas.drawCircle((y * s) + (s / 2), (x * s) + (s / 2), (s * 7) / 18, paint);

                    paint.setColor(Color.BLACK);
                    paint.setStrokeWidth(5);

                    canvas.drawLines(new float[]{ (float) (s / 2) + (s * y), (float) ((s / 4) + (s * x) - num),
                            (float)((s / 4) + (s * y)), (float)(((s / 4) * 3) + (s * x) - num),
                            (float)((s / 4) + (s * y)), (float)(((s / 4) * 3) + (s * x) - num),
                            (float)(((s / 4) * 3) + (s * y)), (float)(((s / 4) * 3) + (s * x) - num),
                            (float)(((s / 4) * 3) + (s * y)), (float)(((s / 4) * 3) + (s * x) - num),
                            (float) (s / 2) + (s * y), (float) ((s / 4) + (s * x) - num)
                    }, paint);

                    canvas.drawLines(new float[] {(float)((s / 2) + (s * y)), (float)(((s / 4) * 3) + (s * x) + num),
                            (float)((s / 4) + (s * y)), (float)((s / 4) + (s * x) + num),
                            (float)((s / 4) + (s * y)), (float)((s / 4) + (s * x) + num),
                            (float)(((s / 4) * 3) + (s * y)), (float)((s / 4) + (s * x) + num),
                            (float)(((s / 4) * 3) + (s * y)), (float)((s / 4) + (s * x) + num),
                            (float)((s / 2) + (s * y)), (float)(((s / 4) * 3) + (s * x) + num)
                    }, paint );
                }

            }
        }

        paint.setColor(Color.BLACK);
        canvas.drawRect(s * 3, s * 9,( s * 3) + s * 2, (s * 9) + s, paint);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(60);

        canvas.drawText("RESTART", (s * 4) , (float) ((s * 11) - (s * 1.35)), paint);

        paint.setColor(Color.BLACK);

        canvas.drawText(Game.winName, (s * 4), (float) s * 10, paint);
    }

    public void reDraw(){
        this.invalidate();
    }





}
