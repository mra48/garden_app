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
    public void testFileOpMultiLineString() throws IOException {
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
    public void testFileOpNullString() throws IOException {
        String fileName = "testFile1.txt";
        String text = null;
        try {
            FileOperation.save(fileName, text);
            fail("Exception not thrown for null string.");
        }
        catch (Exception exc) {
            // exception is thrown correctly
        }
        // Read file and check if it is empty
        assertEquals(FileOperation.load(fileName).length(), 0);
    }

    /**
     * Saves a long, single line random string to file, then reads it from file.
     */
    @SmallTest
    public void testFileOpLongString() throws IOException {
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

    @SmallTest
    public void testFileOpOverwrite() throws IOException {
        String fileName = "testFile3.txt";
        String text1 = "1st", text2 = "2nd";
        FileOperation.save(fileName, text1);
        FileOperation.save(fileName, text2);
        assertEquals(text2, FileOperation.load(fileName));
    }

    @SmallTest
    public void testFileOpExists() throws IOException {
        String fileName = "testFile4.txt";
        // File not written yet - it should not exist
        assertFalse(FileOperation.exists(fileName));
        // Write some data
        FileOperation.save(fileName, "some data");
        // File should be created
        assertTrue(FileOperation.exists(fileName));
    }

    @SmallTest
    public void testFileOpDelete() throws IOException {
        String fileName = "testFile5.txt";
        FileOperation.save(fileName, "some data");
        // Delete the file - should return true
        assertTrue(FileOperation.delete(fileName));

        // Check if file still exists
        assertFalse(FileOperation.exists(fileName));
    }

}