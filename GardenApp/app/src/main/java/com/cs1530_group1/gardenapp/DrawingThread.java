package com.cs1530_group1.gardenapp;
import android.annotation.SuppressLint;
import android.graphics.Canvas;

/**
 * DrawingThread : separate thread for acquiring the SurfaceView and drawing on it
 */
public class DrawingThread extends Thread {
    private GardenView view;
    private boolean running = false;

    /**
     * DrawingThread : only for use in GardenView
     * @param v : GardenView passes itself in
     */
    public DrawingThread(GardenView v)
    {
        this.view = v;
    }

    /**
     * setRunning : start/stop the thread
     * @param run : passed in by GardenView
     */
    public void setRunning(boolean run)
    {
        running = run;
    }

    /**
     * run : the drawing thread
     */
    @SuppressLint("WrongCall")
    public void run() {
        while (running) {
            Canvas c = null;

            try {
                // The SurfaceView must be locked first to get its canvas
                c = view.getHolder().lockCanvas();

                // Perform the drawing
                synchronized (view.getHolder()) {
                    view.onDraw(c);
                }

            } finally {
                if (c != null)
                {
                    // Unlock the SurfaceView and tell Android to render the canvas
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
        }
    }
}
