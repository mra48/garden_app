package com.cs1530_group1.gardenapp;

import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

import java.io.IOException;
import java.util.Random;

/**
 * @author : wenchao on 7/2/2015.
 */
public class FileOperationTest extends TestCase {

    /**
     * Saves a multi line string to file, then reads it from file.
     */
    @SmallTest
    public void testFileSaveLoadMultiLineString() throws IOException {
        String fileName = "testFile0.txt";
        String text = "some text here\ntext on line2\n0123:::ffff:::";

        // Save to file
        FileOperation.save(fileName, text);
        // Read from file
        String textRead = FileOperation.load(fileName);
        // Check if texts are equal
        assertEquals(text, textRead);
    }

    /**
     * Attempts to save a null string.
     */
    @SmallTest
    public void testFileSaveNullString() {
        String fileName = "testFile1.txt";
        String text = null;
        try {
            FileOperation.save(fileName, text);
            fail("Exception not thrown for null string.");
        }
        catch (Exception exc)
        {
            // exception is thrown correctly
        }
    }

    /**
     * Saves a long, single line random string to file, then reads it from file.
     */
    @SmallTest
    public void testFileSaveLoadLongString() throws IOException {
        String fileName = "testFile2.txt";
        String text;
        // Construct long string with 2048 random chars
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 2048; i++) {
            char c = (char)(rnd.nextInt(26) + 'a');
            sb.append(c);
        }
        text = sb.toString();
        // Save to file
        FileOperation.save(fileName, text);
        // Read from file
        String textRead = FileOperation.load(fileName);
        // Check if texts are equal
        assertEquals(text, textRead);
    }

}