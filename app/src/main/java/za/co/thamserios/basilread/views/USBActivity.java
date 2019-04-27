package za.co.thamserios.basilread.views;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mjdev.libaums.UsbMassStorageDevice;
import com.github.mjdev.libaums.fs.FileSystem;
import com.github.mjdev.libaums.fs.UsbFile;
import com.github.mjdev.libaums.fs.UsbFileOutputStream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import za.co.thamserios.basilread.ApplicationCache;
import za.co.thamserios.basilread.R;
import za.co.thamserios.basilread.services.DataTransferService;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class USBActivity extends AppCompatActivity {

    private static final String TAG = USBActivity.class.getSimpleName();
    private TextView deviceText;
    private UsbManager mUsbManager ;
    public static final String ACTION_USB_DEVICE_ATTACHED = "za.co.thamserios.ACTION_USB_DEVICE_ATTACHED";
    public static final String ACTION_USB_DEVICE_DETTACHED = "za.co.thamserios.ACTION_USB_DEVICE_DETTACHED";
    private PendingIntent mPermissionIntent;
    private TextView rigTopText;
    private TextView shiftTopText;
    private ApplicationCache cache;
    private UsbMassStorageDevice mSelectedDevice;
    private List<UsbFile> dirs = new ArrayList<>();
    private Button transferButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usb);
        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_DEVICE_ATTACHED), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_DEVICE_ATTACHED);
        registerReceiver(mUsbReceiver, filter);
        cache = ApplicationCache.getInstance();
        bindViews();
    }

    private void initUsb(){
        mSelectedDevice = null;
        UsbMassStorageDevice[] devices = UsbMassStorageDevice.getMassStorageDevices(this);
        if(devices.length > 0 ) {
            mSelectedDevice = devices[0];
            transferButton.setVisibility(View.VISIBLE);
            try {
                mSelectedDevice.init();
                // we always use the first partition of the device
                FileSystem fs = mSelectedDevice.getPartitions().get(0).getFileSystem();
                UsbFile root = fs.getRootDirectory();
                dirs.add(root);
                Log.d(TAG, "root: " + root.getName());
                deviceText.setText(mSelectedDevice.getUsbDevice().getDeviceName());
                //usbArrayAdapter = new ArrayAdapter<>(USBActivity.this, android.R.layout.simple_spinner_item, devices);
                //usbArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //deviceSpinner.setAdapter(usbArrayAdapter);
            } catch (Exception e) {
                deviceText.setText(e.getMessage());
            }
        }
    }

    private void bindViews(){

        shiftTopText = (TextView)findViewById(R.id.shiftTopText);
        rigTopText = (TextView)findViewById(R.id.rigTopText);
        deviceText = (TextView)findViewById(R.id.deviceSpinner);
        findViewById(R.id.refreshDevices).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initUsb();//
            }
        });

        findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        transferButton = (Button)findViewById(R.id.btnTransferData);
        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TransferDataTask().execute();
            }
        });
        shiftTopText.setText(cache.getCachedIndex().getShiftTopText() + " Shift");
        rigTopText.setText(cache.getCachedIndex().getRigTopText());
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Intent intent = getIntent();
        if (intent == null) return;
        if (intent.getAction() != null && intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED)) {
            Parcelable usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
            // Create a new intent and put the usb device in as an extra
            Intent broadcastIntent = new Intent(ACTION_USB_DEVICE_ATTACHED);
            broadcastIntent.putExtra(UsbManager.EXTRA_DEVICE, usbDevice);
            // Broadcast this event so we can receive it
            sendBroadcast(broadcastIntent);
        }

        if (intent.getAction() != null && intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_DETACHED)) {
            // Create a new intent and put the usb device in as an extra
            Intent broadcastIntent = new Intent(ACTION_USB_DEVICE_DETTACHED);
            // Broadcast this event so we can receive it
            sendBroadcast(broadcastIntent);
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mSelectedDevice = null;
    }

    private void writeData(String data){
        try {
            String fileName = String.format("%s.txt",UUID.randomUUID().toString().replace("-","_"));
            UsbFile root = dirs.get(0);
            if (root != null) {
                UsbFile dumpDirectory = root.search("BasilReadData");
                if (dumpDirectory == null)
                    dumpDirectory = root.createDirectory("BasilReadData");

                UsbFile file = dumpDirectory.createFile(fileName);
                OutputStream os = new UsbFileOutputStream(file);
                os.write(data.getBytes());
                os.close();
                showAlertDialog(USBActivity.this, "USB Transfer", "Successfully transfered to USB", true);
                transferButton.setVisibility(View.INVISIBLE);
            }
        } catch (IOException e) {
            showAlertDialog(USBActivity.this,"USB Transfer",e.getMessage(),false);
        }
    }

    private void removedUSB(){
        deviceText.setText("Device removed");
        transferButton.setVisibility(View.INVISIBLE);
    }

    private void connectDevice(){
        initUsb();
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            try {
                String action = intent.getAction();
                if (ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                    synchronized (this) {
                        UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                        //if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            //call method to set up device communication
                            //mUsbManager.requestPermission(device, mPermissionIntent);
                            connectDevice();
                        }
                    /*}
                    else {
                        showAlertDialog(USBActivity.this,"USB","permission denied for device " + device.getDeviceName(),false);
                    }*/
                    }
                }
                //detach
                if (ACTION_USB_DEVICE_DETTACHED.equals(action)) {
                    removedUSB();
                }
            }catch (Exception e){
                showAlertDialog(USBActivity.this,"USB Transfer",e.getMessage(),false);
            }
        }
    };

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


    private class TransferDataTask extends AsyncTask<Void,Void,String> {
        private ProgressDialog progress;
        public TransferDataTask(){
            progress = new ProgressDialog(USBActivity.this);
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
            String fileContent;
            try {
                File f = new File(result);
                FileInputStream inp = new FileInputStream(f);
                byte[] bf = new byte[(int)f.length()];
                inp.read(bf);
                fileContent = new String(bf, "UTF-8");
                writeData(fileContent);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                progress.dismiss();
            }
        }
    }
}
