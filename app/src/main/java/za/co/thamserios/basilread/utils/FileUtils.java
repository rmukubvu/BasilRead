package za.co.thamserios.basilread.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import android.os.Environment;
import android.util.Log;

/**
 * Created by Robson.Mukubvu on 6/19/2015.
 */
public class FileUtils {

    private static final String TAG = FileUtils.class.getName();
    private static final String APPLICATION_DIR = Environment.getExternalStorageDirectory() + "/basilread";
    private static final int BUFFER_SIZE = 10240;

    public static List<File> listFiles() {
        return listFiles(null);
    }

    public static List<File> listFiles(final String extension) {
        File appDir = new File(APPLICATION_DIR);
        if (!appDir.exists()) {
            return new ArrayList<>();
        }
        return Arrays.asList(appDir.listFiles(new FileFilter() {

            @Override
            public boolean accept(File file) {
                return extension == null || file.getName().endsWith(extension);
            }
        }));
    }

    private static File getBasilReadFile(String fileName){
        File appDir = new File(APPLICATION_DIR);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        return new File(appDir, fileName);
    }

    public static File getBasilFile() throws IOException {
        String jsonFileName = getFileName();
        return getBasilReadFile(jsonFileName);
    }

    private static String getFileName(){
        return "BASIL_" + uniqueString() + ".txt";
    }

    private static String uniqueString(){
        return UUID.randomUUID().toString().replace("-","_");
    }

    public static void writeJsonToFile(File file,String json) throws IOException {
        FileWriter writer = new FileWriter(file,true);
        writer.append(json);
        writer.flush();
        writer.close();
    }

    public static File writeToFile(String filename, byte[] data) {
        File appDir = new File(APPLICATION_DIR);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }

        File file = new File(appDir, filename);
        try {
            file.createNewFile();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create file.", e);
        }

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            fos.write(data);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to write file.");
        }
        return file;
    }

    public static byte[] readAsBytes(File file) {

        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int read;
            while ((read = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, read);
            }
            return bos.toByteArray();

        } catch (Exception e) {
            Log.e(TAG, "Failed to read file: " + file.getName(), e);
            return new byte[0];

        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    Log.w(TAG, "Failed to close file input stream.", e);
                }
            }

            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    Log.w(TAG, "Failed to close byte array output stream.", e);
                }
            }
        }
    }
}
