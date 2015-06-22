package com.cs1530_group1.gardenapp;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.shapes.OvalShape;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.graphics.drawable.*;
import android.graphics.Picture;
import android.view.SurfaceView;
import android.graphics.Paint;

/**
 * Created by root on 6/13/15.
 */
public class GameView extends SurfaceView {
    private Bitmap bmp;
    private SurfaceHolder holder;
    private float x = 0;
    private float y = 10;
    private int bmp_x = 0;
    private int bmp_y = 0;
    private int start_x = 0;
    private int start_y = 0;
    private Paint paint;
    private GameLoopThread gameLoopThread;
    private ShapeDrawable circle;
    private ShapeDrawable circleArray[];
    private int mode = 0;
    private int height = 0;
    private int width = 0;
    private boolean circleIsDrawn = false;

    public GameView(Context context)
    {
        super(context);
        gameLoopThread = new GameLoopThread(this);

        paint = new Paint();
        paint.setColor(Color.YELLOW);

        circle = new ShapeDrawable(new OvalShape());
        circle.getPaint().setColor(Color.BLUE);



        /*circleArray = new ShapeDrawable[200];

        for (int i = 0; i < 200; i++)
        {
            circleArray[i] = new ShapeDrawable(new OvalShape());
            circleArray[i].getPaint().setColor(Color.YELLOW);
            circleArray[i].setBounds(i*10,i*10, i*10+10, i*10+10 );

        }*/

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x2;
                int y2;
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        start_x = (int)event.getX();
                        start_y = (int)event.getY();

                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = (int)event.getX();
                        y2 = (int)event.getY();

                        float deltaX = x2 - start_x;
                        float deltaY = y2 - start_y;
                        if (!(circle.getBounds().contains(start_x, start_y)) && Math.abs(deltaX) > 0 && Math.abs(deltaY) > 0)
                        {
                            int bmp_x_temp = bmp_x + (int)deltaX;
                            int bmp_y_temp = bmp_y + (int)deltaY;


                            if (bmp_x_temp > 0) {
                                if (circleIsDrawn) x-=bmp_x;
                                bmp_x = 0;
                            }
                            else if (bmp_x_temp < -bmp.getWidth() + width) {
                                if (circleIsDrawn) x += (-bmp.getWidth() + width - bmp_x);
                                bmp_x = -bmp.getWidth() + width;
                            }else {
                                x += deltaX;
                                bmp_x+=deltaX;
                            }


                            if (bmp_y_temp > 0) {
                                if (circleIsDrawn) y-=bmp_y;
                                bmp_y = 0;
                            }
                            else if (bmp_y_temp < -bmp.getHeight() + height) {
                                if (circleIsDrawn) y += (-bmp.getHeight() + height - bmp_y);
                                bmp_y = -bmp.getHeight() + height;
                            }
                            else
                            {
                                y += deltaY;
                                bmp_y+=deltaY;
                            }

                            if (circleIsDrawn) circle.setBounds((int)x - 25,(int)y - 25,(int)x + 50 - 25, (int)y + 50 - 25);
                        }
                        else
                        {
                            // consider as something else - a screen tap for example
                            circleIsDrawn = true;
                            x = event.getX();
                            y = event.getY();
                            circle.setBounds((int) x - 25, (int) y - 25, (int) x + 50 - 25, (int) y + 50 - 25);
                        }
                        break;
                }
                return true;
            }
        });
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            public void surfaceDestroyed(SurfaceHolder h) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }

            }

            public void surfaceCreated(SurfaceHolder h) {
                gameLoopThread.setRunning(true);
                gameLoopThread.start();

            }

            public void surfaceChanged(SurfaceHolder h, int format, int width, int height) {
            }
        });

        try {
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        }catch(Exception e){e.printStackTrace();}

    }

    protected void onDraw(Canvas canvas)
    {
        width = this.getWidth();
        height = this.getHeight();
        try {
            //bmp = Bitmap.createScaledBitmap(bmp, canvas.getWidth(), canvas.getHeight(), true);
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(bmp, bmp_x, bmp_y, null);
            /*for (int i = 0; i < 200; i++)
            {
                circleArray[i].draw(canvas);
            }*/

            circle.draw(canvas);

        }catch(Exception e){e.printStackTrace();}
    }
}
