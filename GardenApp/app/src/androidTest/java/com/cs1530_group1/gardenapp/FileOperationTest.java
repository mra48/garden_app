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

    /**
     * Saves a file twice - see if the file is overwritten
     * @throws IOException
     */
    @SmallTest
    public void testFileOpOverwrite() throws IOException {
        String fileName = "testFile3.txt";
        String text1 = "1st", text2 = "2nd";
        FileOperation.save(fileName, text1);
        FileOperation.save(fileName, text2);
        assertEquals(text2, FileOperation.load(fileName));
    }

    /**
     * Creates a file, check if it exists, and deletes the file
     * Checks both exists() and delete()
     * @throws IOException
     */
    @SmallTest
    public void testFileOpDeleteExists() throws IOException {
        String fileName = "testFile4.txt";
        // Write some data
        FileOperation.save(fileName, "some data");
        // File is created - exists should return true
        assertTrue(FileOperation.exists(fileName));
        // Delete the file - delete should return true
        assertTrue(FileOperation.delete(fileName));
        // File is deleted - exists should return false
        assertFalse(FileOperation.exists(fileName));
    }

}