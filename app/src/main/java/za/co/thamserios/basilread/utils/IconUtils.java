package za.co.thamserios.basilread.utils;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.github.mjdev.libaums.fs.UsbFile;

import java.io.File;
import java.util.Comparator;
import java.util.HashMap;

import za.co.thamserios.basilread.R;

/**
 * Created by roberto on 23/08/15.
 */
public class IconUtils {

    private static String TAG = "Utils";
    private static boolean DEBUG = false;
    public final static File cachePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/OTGViewer/cache");

    private static HashMap<String, Integer> sMimeIcons = new HashMap();

    private static void add(String mimeType, int resId) {
        if (sMimeIcons.put(mimeType, resId) != null) {
            throw new RuntimeException(mimeType + " already registered!");
        }
    }

    static {
        int icon;

        // Package
        icon = R.drawable.ic_doc_apk;
        add("application/vnd.android.package-archive", icon);

        // Audio
        icon = R.drawable.ic_doc_audio;
        add("application/ogg", icon);
        add("application/x-flac", icon);

        // Certificate
        icon = R.drawable.ic_doc_certificate;
        add("application/pgp-keys", icon);
        add("application/pgp-signature", icon);
        add("application/x-pkcs12", icon);
        add("application/x-pkcs7-certreqresp", icon);
        add("application/x-pkcs7-crl", icon);
        add("application/x-x509-ca-cert", icon);
        add("application/x-x509-user-cert", icon);
        add("application/x-pkcs7-certificates", icon);
        add("application/x-pkcs7-mime", icon);
        add("application/x-pkcs7-signature", icon);

        // Source code
        icon = R.drawable.ic_doc_codes;
        add("application/rdf+xml", icon);
        add("application/rss+xml", icon);
        add("application/x-object", icon);
        add("application/xhtml+xml", icon);
        add("text/css", icon);
        add("text/html", icon);
        add("text/xml", icon);
        add("text/x-c++hdr", icon);
        add("text/x-c++src", icon);
        add("text/x-chdr", icon);
        add("text/x-csrc", icon);
        add("text/x-dsrc", icon);
        add("text/x-csh", icon);
        add("text/x-haskell", icon);
        add("text/x-java", icon);
        add("text/x-literate-haskell", icon);
        add("text/x-pascal", icon);
        add("text/x-tcl", icon);
        add("text/x-tex", icon);
        add("application/x-latex", icon);
        add("application/x-texinfo", icon);
        add("application/atom+xml", icon);
        add("application/ecmascript", icon);
        add("application/json", icon);
        add("application/javascript", icon);
        add("application/xml", icon);
        add("text/javascript", icon);
        add("application/x-javascript", icon);

        // Compressed
        icon = R.drawable.ic_doc_compressed;
        add("application/mac-binhex40", icon);
        add("application/rar", icon);
        add("application/zip", icon);
        add("application/x-apple-diskimage", icon);
        add("application/x-debian-package", icon);
        add("application/x-gtar", icon);
        add("application/x-iso9660-image", icon);
        add("application/x-lha", icon);
        add("application/x-lzh", icon);
        add("application/x-lzx", icon);
        add("application/x-stuffit", icon);
        add("application/x-tar", icon);
        add("application/x-webarchive", icon);
        add("application/x-webarchive-xml", icon);
        add("application/gzip", icon);
        add("application/x-7z-compressed", icon);
        add("application/x-deb", icon);
        add("application/x-rar-compressed", icon);

        // Contact
        icon = R.drawable.ic_doc_contact;
        add("text/x-vcard", icon);
        add("text/vcard", icon);

        // Event
        icon = R.drawable.ic_doc_event;
        add("text/calendar", icon);
        add("text/x-vcalendar", icon);

        // Font
        icon = R.drawable.ic_doc_font;
        add("application/x-font", icon);
        add("application/font-woff", icon);
        add("application/x-font-woff", icon);
        add("application/x-font-ttf", icon);

        // Image
        icon = R.drawable.ic_doc_image;
        add("application/vnd.oasis.opendocument.graphics", icon);
        add("application/vnd.oasis.opendocument.graphics-template", icon);
        add("application/vnd.oasis.opendocument.image", icon);
        add("application/vnd.stardivision.draw", icon);
        add("application/vnd.sun.xml.draw", icon);
        add("application/vnd.sun.xml.draw.template", icon);

        // PDF
        icon = R.drawable.ic_doc_pdf;
        add("application/pdf", icon);

        // Presentation
        icon = R.drawable.ic_doc_presentation;
        add("application/vnd.stardivision.impress", icon);
        add("application/vnd.sun.xml.impress", icon);
        add("application/vnd.sun.xml.impress.template", icon);
        add("application/x-kpresenter", icon);
        add("application/vnd.oasis.opendocument.presentation", icon);

        // Spreadsheet
        icon = R.drawable.ic_doc_spreadsheet;
        add("application/vnd.oasis.opendocument.spreadsheet", icon);
        add("application/vnd.oasis.opendocument.spreadsheet-template", icon);
        add("application/vnd.stardivision.calc", icon);
        add("application/vnd.sun.xml.calc", icon);
        add("application/vnd.sun.xml.calc.template", icon);
        add("application/x-kspread", icon);

        // Text
        icon = R.drawable.ic_doc_text;
        add("application/vnd.oasis.opendocument.text", icon);
        add("application/vnd.oasis.opendocument.text-master", icon);
        add("application/vnd.oasis.opendocument.text-template", icon);
        add("application/vnd.oasis.opendocument.text-web", icon);
        add("application/vnd.stardivision.writer", icon);
        add("application/vnd.stardivision.writer-global", icon);
        add("application/vnd.sun.xml.writer", icon);
        add("application/vnd.sun.xml.writer.global", icon);
        add("application/vnd.sun.xml.writer.template", icon);
        add("application/x-abiword", icon);
        add("application/x-kword", icon);

        // Video
        icon = R.drawable.ic_doc_video;
        add("application/x-quicktimeplayer", icon);
        add("application/x-shockwave-flash", icon);

        // Word
        icon = R.drawable.ic_doc_word;
        add("application/msword", icon);
        add("application/vnd.openxmlformats-officedocument.wordprocessingml.document", icon);
        add("application/vnd.openxmlformats-officedocument.wordprocessingml.template", icon);

        // Excel
        icon = R.drawable.ic_doc_excel;
        add("application/vnd.ms-excel", icon);
        add("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", icon);
        add("application/vnd.openxmlformats-officedocument.spreadsheetml.template", icon);

        // Powerpoint
        icon = R.drawable.ic_doc_powerpoint;
        add("application/vnd.ms-powerpoint", icon);
        add("application/vnd.openxmlformats-officedocument.presentationml.presentation", icon);
        add("application/vnd.openxmlformats-officedocument.presentationml.template", icon);
        add("application/vnd.openxmlformats-officedocument.presentationml.slideshow", icon);
    }

    public static int loadMimeIcon(String mimeType) {

        // Look for exact match first
        Integer resId = sMimeIcons.get(mimeType);
        if (resId != null) {
            return resId;
        }

        if (mimeType == null) {
            // TODO: generic icon?
            return R.drawable.ic_doc_generic;
        }

        // Otherwise look for partial match
        final String typeOnly = mimeType.split("/")[0];
        if ("audio".equals(typeOnly)) {
            return R.drawable.ic_doc_audio;
        } else if ("image".equals(typeOnly)) {
            return R.drawable.ic_doc_image;
        } else if ("text".equals(typeOnly)) {
            return R.drawable.ic_doc_text;
        } else if ("video".equals(typeOnly)) {
            return R.drawable.ic_doc_video;
        } else {
            return R.drawable.ic_doc_generic;
        }
    }

    public static int calculateInSampleSize(File f, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(f.getAbsolutePath(), options);
        // String imageType = options.outMimeType;

        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;

        if(DEBUG) {
            Log.d(TAG, "checkImageSize. X: " + width + ", Y: " + height);
            Log.d(TAG, "Screen is. X: " + reqWidth + ", Y: " + reqHeight);
        }

        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // The directory is now empty or this is a file so delete it
        return dir.delete();
    }

    public static void deleteCache(){
        deleteDir(cachePath);
    }

    public static String getMimetype(File f) {
        if(DEBUG)
            Log.d(TAG, "extension from: " + Uri.fromFile(f).toString().toLowerCase());

        String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri
                .fromFile(f).toString().toLowerCase());

        return getMimetype(extension);
    }

    public static String getMimetype(String extension) {
        String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                extension);

        if(DEBUG)
            Log.d(TAG, "mimetype is: " + mimetype);

        return mimetype;
    }

    public static Comparator<UsbFile> comparator = new Comparator<UsbFile>() {

        @Override
        public int compare(UsbFile lhs, UsbFile rhs) {

            if (lhs.isDirectory() && !rhs.isDirectory()) {
                return -1;
            }

            if (rhs.isDirectory() && !lhs.isDirectory()) {
                return 1;
            }

            return lhs.getName().compareToIgnoreCase(rhs.getName());
        }
    };

    public static boolean isImage(UsbFile entry){
        int index = entry.getName().lastIndexOf(".");

        if(entry.isDirectory())
            return false;

        try{
            String prefix = entry.getName().substring(0, index);
            String ext = entry.getName().substring(index);
            if (ext.equalsIgnoreCase(".jpg")) {
                return true;
            }

        }catch (StringIndexOutOfBoundsException e){
            e.printStackTrace();
        }


        return false;
    }

    public static boolean isImage(File entry){
        int index = entry.getName().lastIndexOf(".");
        String prefix = entry.getName().substring(0, index);
        String ext = entry.getName().substring(index);

        if (ext.equalsIgnoreCase(".jpg")) {
            return true;
        }

        return false;
    }


}
