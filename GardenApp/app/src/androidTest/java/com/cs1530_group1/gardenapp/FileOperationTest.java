package com.cs1530_group1.gardenapp;

import junit.framework.TestCase;

/**
 * Created by wenchao on 7/2/2015.
 */
public class FileOperationTest extends TestCase {

    public void testFileIO() throws Exception {
        String fileName = "testFile.txt";
        String text = "some text here\n::01234::abcd\nlast line of test file\n";

        // Save the file
        FileOperation.save(fileName, text);

        // Read the file
        String textRead = FileOperation.load(fileName);

        // Check if texts are equal
        assertEquals(text, textRead);

    }

}