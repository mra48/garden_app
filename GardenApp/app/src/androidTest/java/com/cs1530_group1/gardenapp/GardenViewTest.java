package com.cs1530_group1.gardenapp;

import android.graphics.Color;
import android.graphics.Rect;
import android.test.ActivityTestCase;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Date;
import android.graphics.drawable.ShapeDrawable;
import android.test.suitebuilder.annotation.SmallTest;

/**
 * Created by root on 7/4/15.
 */
public class GardenViewTest extends ActivityTestCase {


    /**
     * Test to see if an existent image resource can be successfully loaded as a bitmap
     * R.mimp.ic_launcher is the little android dude icon
     * @throws Exception
     */
    public void testLoadBitmapImage() throws Exception {
        GardenView gv = new GardenView(getInstrumentation().getTargetContext(), new Garden());
        Bitmap bmp = gv.loadBitmapImage(R.mipmap.ic_launcher);
        assertNotNull(bmp);
    }

    /**
     * Tests the function that returns a factor by which to scale the size of the circles based
     * on the device screen.
     * @throws Exception
     */
    public void testGetRadiusScaleFactor() throws  Exception {
        GardenView gv = new GardenView(getInstrumentation().getTargetContext(), new Garden());

        // For right now, this is just 1 -- it has not actually been fully implemented but
        // it is useful to have the function now
        assertTrue(gv.getRadiusScaleFactor() == 1);
    }

    /**
     * Tests the conversion of a center position x,y + size to
     * a bounding box centered on x, y. The width of the box is 2*size*scalingFactor
     * The height of the box is 2*size*scalingFactor
     */
    public void testPositionToBounds() throws Exception {
        GardenView gv = new GardenView(getInstrumentation().getTargetContext(), new Garden());
        int x = 100;
        int y = 100;
        int size = 50;
        Rect correctBounds = new Rect(x - (int)(size*gv.getRadiusScaleFactor()), y - (int)(size*gv.getRadiusScaleFactor()), x + (int)(size*gv.getRadiusScaleFactor()), y + (int)(size*gv.getRadiusScaleFactor()));
        Rect actualBounds = gv.positionToBounds(x, y, size);

        assertTrue(actualBounds.left == correctBounds.left);
        assertTrue(actualBounds.right == correctBounds.right);
        assertTrue(actualBounds.top == correctBounds.top);
        assertTrue(actualBounds.bottom == correctBounds.bottom);
    }

    /**
     * Tests conversion from a plant to a circle that graphically represents that plant
     * Color should be the same, location should be centered around x and y (offset by
     * the radius, which corresponds to the size scaled according to the display -- scaling yet to be implemented)
     */
    public void testPlantToCircle() throws Exception {
        GardenView gv = new GardenView(getInstrumentation().getTargetContext(), new Garden());
        int color = Color.RED;
        int size = 50;
        Species s = new Species("Tomato", "A plump red fruit", "Gracious sunbeams", "annual", new Date(2015, 5, 1), new Date(2015, 6, 1), color, size);
        int x = 100;
        int y = 100;
        Plant p = new Plant(x, y, s);
        // positionToBounds() is already tested
        Rect correctBounds = gv.positionToBounds(x, y, size);
        ShapeDrawable circle = gv.plantToCircle(p);
        Rect actualBounds = circle.getBounds();

        // Make sure the circle has the correct color, and that
        // the bounds are correct -- this accounts for the radius being correct
        assertTrue(circle.getPaint().getColor() == color);
        assertTrue(actualBounds.left == correctBounds.left);
        assertTrue(actualBounds.right == correctBounds.right);
        assertTrue(actualBounds.top == correctBounds.top);
        assertTrue(actualBounds.bottom == correctBounds.bottom);

    }

    /**
     * Tests that all the plants have been converted to circles correctly
     * @throws Exception
     */
    public void testGetAllPlantCircles() throws Exception {
        GardenView gv = new GardenView(getInstrumentation().getTargetContext(), new Garden());
        ArrayList<ShapeDrawable> plantCircles;
        int tomatoColor = Color.RED;
        int tomatoSize = 5;
        int tomatoX = 0;
        int tomatoY = 0;
        Rect tomatoBounds = gv.positionToBounds(tomatoX, tomatoY, tomatoSize);

        int sunflowerColor = Color.YELLOW;
        int sunflowerSize = 8;
        int sunflowerX = 0;
        int sunflowerY = 20;
        Rect sunflowerBounds = gv.positionToBounds(sunflowerX, sunflowerY, sunflowerSize);

        // Create two species to put in the garden
        gv.garden.addSpecies("Tomato");
        gv.garden.setColor("Tomato", tomatoColor);
        gv.garden.setSize("Tomato", tomatoSize);

        gv.garden.addSpecies("Sunflower");
        gv.garden.setColor("Sunflower", sunflowerColor);
        gv.garden.setSize("Sunflower", sunflowerSize);

        // Add some a tomato and a sunflower to the garden
        gv.garden.addPlant(tomatoX, tomatoY, "Tomato");

        gv.garden.addPlant(sunflowerX, sunflowerY, "Sunflower");

        // Get the list of plant circles
        plantCircles = gv.getAllPlantCircles();

        // Make sure there are exactly 2 circles
        assertTrue(plantCircles.size() == 2);

        // Make sure that the first plant corresponds to the tomato and the second to the sunflower
        // Make sure the circle has the correct color, and that
        // the bounds are correct -- this accounts for the radius being correct
        // the tomato
        assertTrue(plantCircles.get(0).getPaint().getColor() == tomatoColor);
        assertTrue(plantCircles.get(0).getBounds().left == tomatoBounds.left);
        assertTrue(plantCircles.get(0).getBounds().right == tomatoBounds.right);
        assertTrue(plantCircles.get(0).getBounds().top == tomatoBounds.top);
        assertTrue(plantCircles.get(0).getBounds().bottom == tomatoBounds.bottom);
        // the sunflower
        assertTrue(plantCircles.get(1).getPaint().getColor() == sunflowerColor);
        assertTrue(plantCircles.get(1).getBounds().left == sunflowerBounds.left);
        assertTrue(plantCircles.get(1).getBounds().right == sunflowerBounds.right);
        assertTrue(plantCircles.get(1).getBounds().top == sunflowerBounds.top);
        assertTrue(plantCircles.get(1).getBounds().bottom == sunflowerBounds.bottom);

    }

    /**
     * Test a change of ten in the negative x direction when the background is at x = 0 and the background
     * is 1155 wide and the screen is 1080 wide
     * Change should be -10, this is the happy case
     * @throws Exception
     */
    @SmallTest
    public void testGetBackgroundChange1() throws Exception {
        GardenView gv = new GardenView(getInstrumentation().getTargetContext(), new Garden());
        int change = gv.getBackgroundChange(-10, 0, 1155, 1080);

        assertTrue(change == -10);
    }

    /**
     * Tests trying to move the background positive 10 units when the background is at 0
     * The background should not move in this case.
     * @throws Exception
     */
    @SmallTest
    public void testGetBackgroundChange2() throws Exception {
        GardenView gv = new GardenView(getInstrumentation().getTargetContext(), new Garden());
        int change = gv.getBackgroundChange(10, 0, 1155, 1080);

        assertTrue(change == 0);
    }

    /**
     * Tests trying to move the background positive -10 units when the right edge of the background
     * is at the right edge of the screen. It should not be able to move negative here.
     * The background should not move in this case.
     * @throws Exception
     */
    @SmallTest
    public void testGetBackgroundChange3() throws Exception {
        GardenView gv = new GardenView(getInstrumentation().getTargetContext(), new Garden());
        int change = gv.getBackgroundChange(-10, -75, 1155, 1080);

        assertTrue(change == 0);
    }

}