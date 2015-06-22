package com.cs1530_group1.gardenapp;
import android.graphics.Canvas;

/**
 * Created by root on 6/13/15.
 */
public class GameLoopThread extends Thread {
    private GameView view;
    private boolean running = false;

    public GameLoopThread(GameView v)
    {
        this.view = v;
    }

    public void setRunning(boolean run)
    {
        running = run;
    }

    public void run() {
        while (running) {
            Canvas c = null;

            try {
                c = view.getHolder().lockCanvas();

                synchronized (view.getHolder()) {
                    view.onDraw(c);
                }

            } finally {
                if (c != null)
                {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
        }
    }
}
