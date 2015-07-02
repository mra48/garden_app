package com.cs1530_group1.gardenapp;

import java.io.*;
import android.os.Environment;
import android.util.Log;

/**
 * Created by wenchao on 7/2/2015.
 */
public class FileOperation {

    private static final String _FOLDERPATH = "/garden_app/files";

    /**
     * @param fileName Name of the file to save to
     * @param string String to be saved
     * @throws IOException
     */
    public static void save(String fileName, String string) throws IOException {
        File dir = getFolderOnSDCard();
        // Create file storage folder if it does not exist
        if (!dir.exists()) dir.mkdirs();

        // Create output stream for the file
        FileOutputStream f = new FileOutputStream(getFullFilePath(dir, fileName));
        // Write string and flush
        f.write(string.getBytes());
        f.flush();
        f.close();
    }

    /**
     * @param fileName Name of the file to read from
     * @return String read from the file
     * @throws IOException
     */
    public static String load(String fileName) throws IOException {
        File dir = getFolderOnSDCard();

        // Use a string builder to build the read string
        StringBuilder sb = new StringBuilder();
        FileInputStream f = new FileInputStream(getFullFilePath(dir, fileName));
        // Create a buffered reader using the input stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(f));

        // Read line by line
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }

        // Close reader
        reader.close();

        // Return read string
        return sb.toString();
    }

    /**
     * @return The folder where the files are stored
     * @throws IOException
     */
    private static File getFolderOnSDCard() throws IOException {
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File (sdCard.getAbsolutePath() + _FOLDERPATH);
        return dir;
    }

    /**
     * @param dir The folder where the files are stored
     * @param fileName Name of the file
     * @return
     */
    private static String getFullFilePath(File dir, String fileName)
    {
        return dir.getPath() + "/" + fileName;
    }
}
