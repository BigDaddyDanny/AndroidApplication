package com.example.danny.checkers;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    public static int width;
    private int height;
    public static int RADIUS = 0;
    MyCanvas mycanvas;
    private int tempX;
    private int tempY;

    //((TextView)findViewById(R.id.editText2)).setText(height + ", " + width);

    Button multi;
    Button AI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mycanvas = new MyCanvas(this);

        showMain();

        width = getScreenWidth();
        height = getScreenHeight();
        RADIUS = width / 3;

    }

    public boolean onTouchEvent(MotionEvent event) {
        int y = (int)event.getX();
        int x = (int)event.getY();




        x = x - 200;//GUI adjustment

        y = y / (width / 8);
        x = x / (width / 8);

        if(x == 9 && (y == 3 || y == 4)) showMain();



        if(y == tempY && x == tempX){
            return false;
        }

        tempX = x;
        tempY = y;

        if(y > 7 || y < 0 || x > 7 || x < 0){
            return false;
        }

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                //((TextView)findViewById(R.id.editText2)).setText(String.format("%d, %d", x, y));
                Board.getInstance().methodCaller(x, y);
                Log.i("222","X="+x+"===Y="+y);

        }
        String temp = "";
        for (int i = 0; i < 8; i++) {

            for (int z = 0; z < 8; z++) {
                temp = temp + Board.getInstance().getTable()[i][z] + " ";
            } // end of for
            Log.i("911", temp);
            temp = "";
        } // end of for

        mycanvas.reDraw();
        return false;
    }

    private void showMain(){

        setContentView(R.layout.activity_main);

        multi = (Button) findViewById(R.id.b_multi);
        AI = (Button) findViewById(R.id.b_AI);

        multi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Game.getInstance().setAI(false);
                Board.getInstance().resetBoard();

                setContentView(mycanvas);



            }
        });
        AI.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Game.getInstance().setAI(true);
                Board.getInstance().resetBoard();

                setContentView(mycanvas);



            }
        });


    }

/*
    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            ((TextView)findViewById(R.id.editText2)).setText("no");

            // save the X,Y coordinates
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                double x = event.getX();
                double y = event.getY();
                Log.i("222","X-"+x+"===Y="+y);



            }

            // let the touch event pass on to whoever needs it
            return false;
        }
    };

*/
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    @Override
    public void onClick(View v) {


        Log.i("000", "button clicked");
    }
}
