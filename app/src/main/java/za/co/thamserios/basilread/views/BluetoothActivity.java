package za.co.thamserios.basilread.views;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.view.View;
import android.widget.TextView;

import java.io.File;

import za.co.thamserios.basilread.ApplicationCache;
import za.co.thamserios.basilread.R;
import za.co.thamserios.basilread.models.CryptoException;
import za.co.thamserios.basilread.services.DataTransferService;
import za.co.thamserios.basilread.utils.CryptoUtils;

public class BluetoothActivity extends AppCompatActivity {

    // duration that the device is discoverable
    private static final int DISCOVER_DURATION = 3000;
    // our request code (must be greater than zero)
    private static final int REQUEST_BLU = 1;
    private TextView rigTopText;
    private TextView shiftTopText;
    private ApplicationCache cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        // inside method
        // Check if bluetooth is supported
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            // Device does not support Bluetooth
            // Inform user that we're done.
            showAlertDialog(BluetoothActivity.this,"File Transfer","Bluetooth is not supported on this device",true);
            finish();
        }
        cache = ApplicationCache.getInstance();
        bindViews();
    }

    private void bindViews(){
        shiftTopText = (TextView)findViewById(R.id.shiftTopText);
        rigTopText = (TextView)findViewById(R.id.rigTopText);
        findViewById(R.id.btnTransferData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BluetoothActivity.TransferFileTask().execute();
            }
        });

        findViewById(R.id.btnCloseBlue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //
        shiftTopText.setText(cache.getCachedIndex().getShiftTopText() + " Shift");
        rigTopText.setText(cache.getCachedIndex().getRigTopText());
    }

    public void enableBluetooth(){
    // enable device discovery - this will automatically enable Bluetooth
        Intent discoveryIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoveryIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,
                DISCOVER_DURATION );
        startActivityForResult(discoveryIntent, REQUEST_BLU);
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
                finish();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

    private void sendFile(File fileToSend){
        // bring up Android chooser
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(fileToSend) );
        /*
        //select bluetooth
        String packageName = null;
        String className = null;
        boolean found = false;
        for(ResolveInfo info: appsList){
          packageName = info.activityInfo.packageName;
          if( packageName.equals("com.android.bluetooth")){
             className = info.activityInfo.name;
             found = true;
             break;// found
          }
        }
        if(! found){
          Toast.makeText(this, R.string.blu_notfound_inlist,
                         Toast.LENGTH_SHORT).show();
          // exit
        }
         */
        startActivity(intent);
        finish();
    }

    private class TransferFileTask extends AsyncTask<Void,Void,String> {
        private ProgressDialog progress;
        public TransferFileTask(){
            progress = new ProgressDialog(BluetoothActivity.this);
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
            progress.dismiss();
            String key = "basilaeskey";
            File fileToTransfer = new File(result);
            File encryptedFile = new File("basil.transport");
            try{
                CryptoUtils.encrypt(key, fileToTransfer, encryptedFile);
            }catch (CryptoException ex){
                showAlertDialog(BluetoothActivity.this,"Bluetooth",ex.getMessage(),false);
            }
            sendFile(encryptedFile);
        }
    }
}
