package com.cs1530_group1.gardenapp;

import java.io.*;
import android.os.Environment;

/**
 * FileOperation : a helper class that contains methods to save and load strings to/from extern storage device
 * @author : wenchao
 */
public class FileOperation {

    private static final String _FOLDERPATH = "/garden_app/files";

    //region Public methods

    /**
     * Saves a string to file
     * @param fileName Name of the file to save to
     * @param string String to be saved
     * @throws IOException
     */
    public static void save(String fileName, String string) throws IOException {
        setUpSdCardFolder();
        // Create output stream for the file
        FileOutputStream f = new FileOutputStream(getFile(fileName));
        // Write string and flush
        f.write(string.getBytes());
        f.flush();
        f.close();
    }

    /**
     * Loads a string from file
     * @param fileName Name of the file to read from
     * @return String read from the file
     * @throws IOException
     */
    public static String load(String fileName) throws IOException {
        // Use a string builder to build the read string
        StringBuilder sb = new StringBuilder();
        // Create a buffered reader using the input stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(getFile(fileName))));
        // Read line by line
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        // Close reader
        reader.close();
        // Return read string trimmed
        return sb.toString().trim();
    }

    /**
     * Checks if the file exists
     * @param fileName Name of the file
     * @return Returns true if file exists
     */
    public static boolean exists(String fileName)
    {
        return getFile(fileName).exists();
    }

    /**
     * Deletes a file
     * @param fileName Name of the file
     * @return Returns true if file is deleted
     */
    public static boolean delete(String fileName)
    {
        return getFile(fileName).delete();
    }

    //endregion


    //region Private methods

    /**
     * Gets the storage folder on SD card
     * @return The folder where the files are stored
     */
    private static File getFolderOnSDCard() {
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File (sdCard.getAbsolutePath() + _FOLDERPATH);
        return dir;
    }

    /**
     * Gets the full file path string for the file
     * @param dir The folder where the files are stored
     * @param fileName Name of the file
     * @return Full file path string
     */
    private static String getFullFilePath(File dir, String fileName) {
        return dir.getPath() + "/" + fileName;
    }

    /**
     * Gets the File reference to the file
     * @param fileName Name of the file
     * @return File reference to the file
     */
    private static File getFile(String fileName)
    {
        return new File(getFullFilePath(getFolderOnSDCard(), fileName));
    }

    /**
     * Creates storage folder on SD card if such folder does not exist.
     */
    private static void setUpSdCardFolder()
    {
        // Create file storage folder if it does not exist
        File dir = getFolderOnSDCard();
        if (!dir.exists()) dir.mkdirs();
    }

    //endregion
}
