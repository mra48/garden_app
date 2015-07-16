package com.cs1530_group1.gardenapp;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.graphics.drawable.*;
import android.view.SurfaceView;
import java.util.ArrayList;
import android.graphics.Rect;
import android.util.AttributeSet;

/**
 * GardenView : an extension of SurfaceView. Performs the actual drawing of the background
 * garden layout image and the plant circles.
 */
public class GardenView extends SurfaceView {
    protected GardenMode mode;

    protected Bitmap background; // The background image

    // A note about bitmaps:
    // The left edge of the screen is the line x = 0. To the left of it, x < 0, and to the right of
    // the left edge, x > 0.
    // The top edge of the screen is y = 0. Below it y > 0, and above the top edge, y < 0.
    protected int background_x = 0; // The x-coordinate of the upper left hand corner of the background
    protected int background_y = 0; // The y-coordinate of the upper left hand corner of the background

    // The list of circles that corresponds to the list of plants in the garden
    protected ArrayList<ShapeDrawable> plantCircles = null;

    // The garden that we are going to display
    protected Garden garden;


    protected SurfaceHolder holder; // Need to register callbacks for the holder of this SurfaceView

    // A circle used for adding a new plant or moving an existing one
    protected ShapeDrawable tempPlantCircle = null;
    protected Plant tempPlant = null;

    protected DrawingThread drawingThread; // The drawing thread

    // The height and width of the SurfaceView are still 0 when the constuctor is called
    // because the SurfaceView has not been rendered yet. They are currently being updated in
    // onDraw(). They are needed in the GardenTouchListener and they do change (when buttons are
    // added/displayed dynamically.
    protected int view_height = 0;
    protected int view_width = 0;

    // Needed for using a GardenView in an xml layout
    public GardenView(Context context, AttributeSet set, int defStyle)
    {
        super(context, set ,defStyle);
        constructor(context, ((GardenDrawingActivity)context).g);
    }

    // Needed for using a GardenView in an xml layout
    public GardenView(Context context, AttributeSet set)
    {
        super(context, set);
        constructor(context, ((GardenDrawingActivity)context).g);
    }

    // Needed for using a GardenView in an xml layout
    public GardenView(Context context)
    {
        super(context);
        constructor(context, ((GardenDrawingActivity)context).g);
    }

    // Constructor to use when manually creating a GardenView as opposed
    // to when a GardenView is part of an xml layout
    public GardenView(Context context, Garden g)
    {
        super(context);
        constructor(context, g);
    }

    // Does the real work when the class is instantiated
    // This is the code that was previously in 'public GardenView(Context context, Garden g)'
    private void constructor(Context c, Garden g) {
        // set the garden
        garden = g;

        // Create the drawing thread
        createDrawingThread();

        // Set the listener
        this.setOnTouchListener(new GardenTouchListener());

        // Register for the callbacks
        holder = getHolder();
        holder.addCallback(new GardenHolderCallback());

        // Open the background image as a bitmap
        background = loadBitmapImage(R.drawable.background);

        // Initially there should be a list of plants -- produce a list of circles from the plants
        plantCircles = getAllPlantCircles();

    }

    // Sets up the Garden View so that another plant can be added
    public void addAnotherPlant()
    {
        setNewPlantSpecies(tempPlant.s.name);
        mode = GardenMode.ADD;

        // Temp hack to make sure that the plant can be seen while debugging new code
        //if (tempPlant.s.color < 255 && tempPlant.s.color > 0) tempPlant.s.color = Color.GREEN;
    }

    // ConfirmNewPlantLocation : add the plant to the plant list
    // and make it permanent
    public void confirmNewPlantLocation()
    {
        if (mode == GardenMode.ADD) {
            // Add to the library
            garden.addPlant(tempPlant.x, tempPlant.y, tempPlant.s.name);

            // Add to the list of circles
            plantCircles.add(tempPlantCircle);

            // Exit add mode -- this will not cause the panel to disappear, just causes the
            // temporary plant not to be drawn
            setMode(GardenMode.VIEW);
        }
    }

    // Allows the species name of the new plant to be passed in when the
    // GardenDrawingActivity determines if a new plant is being added
    public void setNewPlantSpecies(String speciesName)
    {
        tempPlant = new Plant(0, 0, garden.getSpeciesInfo(speciesName));

        // Temp hack to make sure that the plant can be seen while debugging new code
        //if (tempPlant.s.color < 255 && tempPlant.s.color > 0) tempPlant.s.color = Color.GREEN;

        // Set the new plant to 0,0 and retrieve its species from the garden interface
        tempPlantCircle = plantToCircle(tempPlant);
        Log.d("Garden View", "setNewPlantSpecies: size " + tempPlant.s.size + " color " + tempPlant.s.color + " green " + Color.GREEN + "\n");
    }

    // Sets the mode of the garden -- whether to add new plants or just view, etc
    public void setMode(GardenMode m)
    {
        mode = m;
    }

    /** createDrawingThread :
     *  allows inner classes to create the drawing thread during onCreate()
     */
    protected void createDrawingThread() {
        drawingThread = new DrawingThread(this);
    }

    /**
     * returns a factor by which a plant circle radius should be scaled by based on the device screen
     * to be implemented
     * @return
     */
    protected double getRadiusScaleFactor()
    {
        return 1;
    }

    /**
     * Takes the center of the circle (x, y) and the scaled size/radius and
     * produces the bounds of the circle. Note -- I changed this function to receive
     * the scaled radius instead of scaling the radius inside this function because
     * the scaling only occurs when a new plant is created.
     * When existing plants are being shifted around during scrolling to be consistent with the
     * background, the radius is just half of the width of the existing bounds.
     * @param x
     * @param y
     * @param size
     * @return
     */
    protected Rect positionToBounds(int x, int y, int size)
    {
        Rect bounds = new Rect(x - size, y - size, x + size, y + size); // make (x, y) the center, so offset by the radius

        return bounds;
    }

    /**
     * takes a plant and coverts it to a circular shape drawable with the same position,
     * color and size properties
     * @param p
     * @return
     */
    protected ShapeDrawable plantToCircle(Plant p)
    {
        ShapeDrawable circle = new ShapeDrawable(new OvalShape());

        // Set the color
        circle.getPaint().setColor(p.s.color);

        // Set the proper bounds
        // (p.x, p.y) is the center
        // p.s.size is the species size (unscaled radius)
        circle.setBounds(positionToBounds(p.x, p.y, (int)(p.s.size*getRadiusScaleFactor())));

        return circle;
    }

    /**
     * takes this class's list of plants and returns a list of circles that will graphically
     * represent the list of plants
     * @return
     */
    protected ArrayList<ShapeDrawable> getAllPlantCircles()
    {
        ArrayList<ShapeDrawable> circles = new ArrayList<ShapeDrawable>();
        int i = 0;

        // Go through the plant list and make a new circle to represent each plant
        for (Plant p : garden.getPlantList())
        {
            circles.add(i++ , plantToCircle(p));
        }
        return circles;
    }

    /**
     * make a bitmap out of an image resource
     * @param imageResourceNumber
     * @return
     */
    protected Bitmap loadBitmapImage(int imageResourceNumber)
    {
        try {
            return BitmapFactory.decodeResource(getResources(), imageResourceNumber);
        }catch(Exception e){return null;}
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

            // Draw all of the circles
            for (ShapeDrawable circle : plantCircles) {
                circle.draw(canvas);
            }

            // Only draw the temporary plant in ADD mode
            if (mode == GardenMode.ADD) {
                // Draw the new plant
                tempPlantCircle.draw(canvas);
                Log.d("Garden View", "onDraw: color" + tempPlantCircle.getPaint().getColor() + " x " + tempPlantCircle.getBounds().centerX() + " y " + tempPlantCircle.getBounds().centerY() + "\n");
            }



        }catch(Exception e){e.printStackTrace();}
    }

    /**
     *
     * @param delta : the change in position from finger down to finger up (change in x or
     *              change in y but you must be consistent with the other parameters!)
     * @param background_pos : the current background position ( x or
     *              y but you must be consistent with the other parameters!)
     * @param background_limit : width or height of the background bitmap -- width for x and
     *                         change in x, height for y and change in y
     * @param view_limit : width or height of the SurfaceView -- width for x and
     *                         change in x, height for y and change in y
     * @return : the amount that the background and all the circles need to move (x or y) based
     * on the parameters that were passed
     */
    protected static int getBackgroundChange(int delta, int background_pos, int background_limit, int view_limit)
    {
        int bmp_temp = background_pos + delta;

        // The top left corner is being dragged too far right, which would expose the
        // blank background -- have to prevent this
        if (bmp_temp > 0) {
            return -1 * background_pos;
        }
        // Makes sure they cannot drag the image completely off to the left
        // There will always be background image on the screen
        else if (bmp_temp < -background_limit + view_limit) {

            return (-background_limit + view_limit - background_pos);
        }
        // The happy case
        else {
            return delta;
        }
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
        // When the Drawing Activity is minimized, the thread goes to the Terminated state
        // When it is brought back up, it is still in Terminated state -- just create a new thread
        // and start it
        if (drawingThread.getState() == Thread.State.TERMINATED) createDrawingThread();

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
    class GardenTouchListener implements OnTouchListener
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
                    //collision = tempPlantCircle.getBounds().contains(x1, y1);


                    // IF the person is not touching the circle and there is a positive delta,
                    // the person is trying to drag/scroll the background
                    if (!collision && Math.abs(deltaX) > 0 && Math.abs(deltaY) > 0) {
                        deltaX = getBackgroundChange(deltaX, background_x, background.getWidth(),view_width);
                        deltaY = getBackgroundChange(deltaY, background_y, background.getHeight(), view_height);

                        // Move the background
                        background_x += deltaX;
                        background_y += deltaY;

                        // Move the circle if it is already been drawn
                        if (mode == GardenMode.ADD)
                        {
                            tempPlant.x += deltaX;
                            tempPlant.y += deltaY;
                        }

                        // Make sure that all the plants stay on the same place in the garden relative to the background
                        for (ShapeDrawable circle : plantCircles)
                        {

                            circle.setBounds(positionToBounds(circle.getBounds().centerX() + deltaX, circle.getBounds().centerY() + deltaY, circle.getBounds().width()/2));
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
                        if (mode == GardenMode.ADD) {
                            tempPlant.x = (int) event.getX();
                            tempPlant.y = (int) event.getY();
                        }
                    }
                    break;
                }
            }

            if (mode == GardenMode.ADD) {

                // Set the bounds for the circle centered around where the user tapped
                tempPlantCircle.setBounds(positionToBounds(tempPlant.x, tempPlant.y, (int)(tempPlant.s.size*getRadiusScaleFactor())));
                Log.d("Garden View", "onTap: x " + tempPlant.x + " y" + tempPlant.y + "\n");
            }
            return true;
        }


    }
}
