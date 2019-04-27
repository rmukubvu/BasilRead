package za.co.thamserios.basilread.views;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import za.co.thamserios.basilread.R;
import za.co.thamserios.basilread.services.DataTransferService;

public class NFCActivity extends AppCompatActivity  {

    private NfcAdapter nfcAdapter;
    private FileUriCallback mFileUriCallback;
    private File _fileToTransfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        PackageManager pm = this.getPackageManager();
        // Check whether NFC is available on device
        if (!pm.hasSystemFeature(PackageManager.FEATURE_NFC)) {
            // NFC is not available on the device.
            Toast.makeText(this, "The device does not has NFC hardware.",
                    Toast.LENGTH_SHORT).show();
        }
        // Check whether device is running Android 4.1 or higher
        else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            // Android Beam feature is not supported.
            Toast.makeText(this, "Android Beam is not supported.",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            // NFC and Android Beam file transfer is supported.
            /*
             * Instantiate a new FileUriCallback to handle requests for
             * URIs
             */
            new NFCActivity.TransferFileTask().execute();
            nfcAdapter = NfcAdapter.getDefaultAdapter(this);
            mFileUriCallback = new FileUriCallback();
            // Set the dynamic callback for URI requests.
            nfcAdapter.setBeamPushUrisCallback(mFileUriCallback,this);
        }
        bindViews();
    }

    private void bindViews(){
        findViewById(R.id.btnTransferData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NFCActivity.TransferFileTask().execute();
            }
        });
    }

    private void sendFile(File fileToTransfer) {
        // Check whether NFC is enabled on device
        if(!nfcAdapter.isEnabled()){
            // NFC is disabled, show the settings UI
            // to enable NFC
            Toast.makeText(this, "Please enable NFC.",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
        }
        // Check whether Android Beam feature is enabled on device
        else if(!nfcAdapter.isNdefPushEnabled()) {
            // Android Beam is disabled, show the settings UI
            // to enable Android Beam
            Toast.makeText(this, "Please enable Android Beam.",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_NFCSHARING_SETTINGS));
        }
        else {
            fileToTransfer.setReadable(true, false);
            nfcAdapter.setBeamPushUris(
                    new Uri[]{Uri.fromFile(fileToTransfer)}, this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        // Setting Dialog Title
        alertDialog.setTitle(title);
        // Setting Dialog Message
        alertDialog.setMessage(message);
        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

    private class TransferFileTask extends AsyncTask<Void,Void,String> {
        private ProgressDialog progress;
        public TransferFileTask(){
            progress = new ProgressDialog(NFCActivity.this);
            progress.setMessage("Transferring data...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            return new DataTransferService().startDataTransfer();
        }

        @Override
        protected void onPostExecute(String result) {
            File fileToTransfer = new File(result);
            _fileToTransfer = fileToTransfer;
            //sendFile(fileToTransfer);
            progress.dismiss();
            showAlertDialog(NFCActivity.this,"NFC Transfer",result,true);
        }
    }

    private class FileUriCallback implements
            NfcAdapter.CreateBeamUrisCallback {
        public FileUriCallback() {
        }
        /**
         * Create content URIs as needed to share with another device
         */
        @Override
        public Uri[] createBeamUris(NfcEvent event) {
            // List of URIs to provide to Android Beam
            Uri[] mFileUris = new Uri[1];
            _fileToTransfer.setReadable(true, false);
            // Get a URI for the File and add it to the list of URIs
            Uri fileUri = Uri.fromFile(_fileToTransfer);
            if (fileUri != null) {
                mFileUris[0] = fileUri;
            } else {
                Log.e(NFCActivity.class.getSimpleName(), "No File URI available for file.");
            }
            return mFileUris;
        }
    }
}
