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
import android.view.SurfaceView;

/**
 * GardenView : an extension of SurfaceView. Performs the actual drawing of the background
 * garden layout image and the plant circles.
 */
public class GardenView extends SurfaceView {
    private Bitmap background; // The background image

    // A note about bitmaps:
    // The left edge of the screen is the line x = 0. To the left of it, x < 0, and to the right of
    // the left edge, x > 0.
    // The top edge of the screen is y = 0. Below it y > 0, and above the top edge, y < 0.
    private int background_x = 0; // The x-coordinate of the upper left hand corner of the background
    private int background_y = 0; // The y-coordinate of the upper left hand corner of the background


    private SurfaceHolder holder; // Need to register callbacks for the holder of this SurfaceView

    // A circle will visually represent a plant
    private ShapeDrawable circle;
    private int circle_x = 0; // x-coordinate relative to background_x
    private int circle_y = 0; // y-coordinate relative to background_y
    private int circle_r = 50; // The radius of the circle
    private boolean circleIsDrawn = false; // Boolean to tell if the circle has been placed yet

    private DrawingThread drawingThread; // The drawing thread

    // The height and width of the SurfaceView are still 0 when the constuctor is called
    // because the SurfaceView has not been rendered yet. They are currently being updated in
    // onDraw(). They are needed in the GardenTouchListener and they do change (when buttons are
    // added/displayed dynamically.
    private int view_height = 0;
    private int view_width = 0;

    /**
     * GardenView : constructor
     * @param context : passed in automatically
     */
    public GardenView(Context context)
    {
        super(context);

        // Create the drawing thread
        drawingThread = new DrawingThread(this);

        // Create the plant circle
        circle = new ShapeDrawable(new OvalShape());
        circle.getPaint().setColor(Color.GREEN);

        // Set the listener
        this.setOnTouchListener(new GardenTouchListener() );

        // Register for the callbacks
        holder = getHolder();
        holder.addCallback(new GardenHolderCallback());

        // Open the background image as a bitmap
        try {
            background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        }catch(Exception e){e.printStackTrace();}

    }

    /**
     * onDraw : actually performs the drawing calls on the SurfaceView's canvas
     * @param canvas : the canvas that will be drawn on. passed in by the DrawingThread
     */
    protected void onDraw(Canvas canvas)
    {
        // Get the current height and width of the SurfaceView/background that can be seen
        view_width = this.getWidth();
        view_height = this.getHeight();

        // The background must be redrawn first to cover up old circles
        // Then the circle(s) can be drawn on top of the background
        // All circles must be redrawn every time
        try {
            canvas.drawBitmap(background, background_x, background_y, null);
            circle.draw(canvas);

        }catch(Exception e){e.printStackTrace();}
    }

    /**
     * GardenHolderCallback : implements SurfaceHolder.Callback. Needed to stop and start the
     * DrawingThread.
     */
    private class GardenHolderCallback implements SurfaceHolder.Callback
    {
        /**
         * surfaceDestroyed : Stops the drawing thread when the GardenView is destroyed
         * @param h : passed in automatically
         */
        public void surfaceDestroyed(SurfaceHolder h)
        {
            boolean retry = true;

            drawingThread.setRunning(false);
            while (retry) {
                try {
                    drawingThread.join();
                    retry = false;
                } catch (InterruptedException e) {}
            }

    }

        /**
         * surfaceCreated : Creates the drawing thread when the application first starts
          * @param h : passed in automatically
         */
    public void surfaceCreated(SurfaceHolder h)
    {
        drawingThread.setRunning(true);
        drawingThread.start();
        return;
    }

        /**
         * Not used / needed at this point
         */
    public void surfaceChanged(SurfaceHolder h, int format, int width, int height) {}
}

    /**
     * GardenTouchListener : implements OnTouchListener interface. Detects taps for placing plants
     * and also swipes to move the screen around.
     */
    private class GardenTouchListener implements OnTouchListener
    {
        // (x1, y1) is the coordinate when the user presses down on the screen
        // (x2, y2) is the coordinate when the user lifts up his/her finger from the screen
        private int x1 = 0;
        private int x2 = 0;
        private int y1 = 0;
        private int y2 = 0;

        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            // deltaX and deltaY are the difference between the coordinates when the user
            // presses down and when the user lifts up his/her finger
            int deltaX = 0;
            int deltaY = 0;
            boolean collision = false; // determines if the person has touched the circle

            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                {
                    x1 = (int) event.getX();
                    y1 = (int) event.getY();
                    break;
                }
                case MotionEvent.ACTION_UP:
                {
                    x2 = (int) event.getX();
                    y2 = (int) event.getY();

                    // Compute the differences
                    deltaX = x2 - x1;
                    deltaY = y2 - y1;

                    // Determine if the person pressed on the circle
                    collision = circle.getBounds().contains(x1, y1);

                    // IF the person is not touching the circle and there is a positive delta,
                    // the person is trying to drag/scroll the background
                    if (!collision && Math.abs(deltaX) > 0 && Math.abs(deltaY) > 0) {
                        int bmp_x_temp = background_x + deltaX;
                        int bmp_y_temp = background_y + deltaY;

                        // Perform all the x-coordinate adjustments and then the y-coordinate adjustments

                        // The top left corner is being dragged too far right, which would expose the
                        // blank background -- have to prevent this
                        if (bmp_x_temp > 0) {
                            if (circleIsDrawn) circle_x -= background_x;
                            background_x = 0;
                        }
                        // Makes sure they cannot drag the image completely off to the left
                        // There will always be background image on the screen
                        else if (bmp_x_temp < -background.getWidth() + view_width) {
                            if (circleIsDrawn)
                                circle_x += (-background.getWidth() + view_width - background_x);
                            background_x = -background.getWidth() + view_width;
                        }
                        // The happy case
                        else {
                            circle_x += deltaX;
                            background_x += deltaX;
                        }

                        // Same analogous stuff for y
                        if (bmp_y_temp > 0) {
                            if (circleIsDrawn) circle_y -= background_y;
                            background_y = 0;
                        } else if (bmp_y_temp < -background.getHeight() + view_height) {
                            if (circleIsDrawn)
                                circle_y += (-background.getHeight() + view_height - background_y);
                            background_y = -background.getHeight() + view_height;
                        } else {
                            circle_y += deltaY;
                            background_y += deltaY;
                        }
                    }
                    // This would be the case when a plant is being selected to be moved
                    // The person should be able to tap a plant and then move it with the next tap
                    else if (collision)
                    {
                        // Nothing to be done yet
                    }
                    // The user is just tapping to put down a circle
                    else
                    {
                        // consider as something else - a screen tap for example
                        circleIsDrawn = true;
                        circle_x = (int)event.getX();
                        circle_y = (int)event.getY();
                    }
                    break;
                }
            }

            if (circleIsDrawn) {
                // Set the bounds for the circle : offset by half the radius so that
                // the circle is centered around where the user tapped
                circle.setBounds(circle_x - circle_r / 2, circle_y - circle_r / 2, circle_x + circle_r / 2, circle_y + circle_r / 2);
            }
        return true;
    }
    }
}
