package com.cs1530_group1.gardenapp;
import android.graphics.Canvas;


public class DrawingThread extends Thread {
    private GardenView view;
    private boolean running = false;

    public DrawingThread(GardenView v)
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
