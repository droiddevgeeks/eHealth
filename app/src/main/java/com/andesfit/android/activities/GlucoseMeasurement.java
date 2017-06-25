package com.andesfit.android.activities;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andesfit.android.R;
import com.andesfit.android.models.GlucoseRecord;
import com.andesfit.android.services.bluetooth.BluetoothLeService;
import com.andesfit.android.util.ApplicationContants;
import com.andesfit.android.util.bluetooth.SampleGattAttributes;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * Created by Vampire on 2017-06-25.
 */

public class GlucoseMeasurement extends AppCompatActivity
{
    private BluetoothAdapter mBluetoothAdapter;
    private String mDeviceName;
    private String mDeviceAddress;
    private ExpandableListView mGattServicesList;
    private BluetoothLeService mBluetoothLeService;
    private boolean mConnected = false;
    private BluetoothGattCharacteristic mNotifyCharacteristic;
    private BluetoothGattCharacteristic mSelectedCharacteristic;
    private boolean mScanning;
    private TextView tempRecievedData;
    private Handler mHandler;
    private BluetoothDevice mDevice;
    private Button btn;

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read or notification operations.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action))
            {
                mConnected = true;
                updateConnectionState(R.string.connected);
                invalidateOptionsMenu();
            }
            else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action))
            {
                mConnected = false;
                updateConnectionState(R.string.disconnected);
                invalidateOptionsMenu();
            }
            else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action))
            {
                // Show all the supported services and characteristics on the user interface.
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
            }
            else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action))
            {
                displayGlucoseData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            }
        }
    };

    private String TAG = getClass().getSimpleName();

    private final ServiceConnection mServiceConnection = new ServiceConnection()
    {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service)
        {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize())
            {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName)
        {
            mBluetoothLeService = null;
        }
    };
    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback()
    {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord)
        {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    if ((!TextUtils.isEmpty(device.getName()) && device.getName().equalsIgnoreCase("Samico GL")))
                    {

                        if (mScanning)
                        {
                            mBluetoothAdapter.stopLeScan(mLeScanCallback);
                            mScanning = false;
                        }
                        if (mDevice == null)
                        {
                            mDevice = device;
                            mDeviceAddress = device.getAddress();
                            connectBTServices();
                        }
                    }
                }
            });
        }
    };

    private static IntentFilter makeGattUpdateIntentFilter()
    {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    private void displayGlucoseData(String stringExtra)
    {

    }

    private void updateConnectionState(int connected)
    {
    }

    private void connectBTServices()
    {
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private void displayGattServices(List<BluetoothGattService> gattServices)
    {
        if (gattServices == null) return;
        String uuid = null;
        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices)
        {
            uuid = gattService.getUuid().toString();
            Log.i("ServiceId", uuid);
            if (uuid.equalsIgnoreCase(SampleGattAttributes.SERVICE_GLUCOSE_MEASUREMENT))
            {

                // Loops through available Characteristics.
                for (BluetoothGattCharacteristic gattCharacteristic : gattService.getCharacteristics())
                {
                    Log.i("charuuid ", gattCharacteristic.getUuid().toString());
                    if (gattCharacteristic.getUuid().toString().equalsIgnoreCase(SampleGattAttributes.CHAR_GLUCOSE_MEASUREMENT))
                    {
                        mSelectedCharacteristic = gattCharacteristic;
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                btn.setBackgroundColor(getResources().getColor(R.color.login));
                                readDataFromChar(mSelectedCharacteristic);
                            }
                        });
                        Log.i("charuuid ", "Found");
                    }
                }
            }
        }
    }

    private void readDataFromChar(final BluetoothGattCharacteristic characteristic)
    {
        /*if (characteristic == null)
            return;
        final int charaProp = characteristic.getProperties();
        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0)
        {
            // If there is an active notification on a characteristic, clear
            // it first so it doesn't update the data field on the user interface.
            if (mNotifyCharacteristic != null)
            {
                mBluetoothLeService.setCharacteristicNotification(mNotifyCharacteristic, false);
                mNotifyCharacteristic = null;
            }
            mBluetoothLeService.readCharacteristic(characteristic);
        }
        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0)
        {
            mNotifyCharacteristic = characteristic;
            mBluetoothLeService.setCharacteristicNotification(characteristic, true);
        }*/

        final UUID uuid = characteristic.getUuid();
        if (SampleGattAttributes.CHAR_GLUCOSE_MEASUREMENT.equals(uuid))
        {

            int offset = 0;
            final int flags = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, offset);
            offset += 1;

            final boolean timeOffsetPresent = (flags & 0x01) > 0;
            final boolean typeAndLocationPresent = (flags & 0x02) > 0;
            final int concentrationUnit = (flags & 0x04) > 0 ? ApplicationContants.UNIT_molpl : ApplicationContants.UNIT_kgpl;
            final boolean sensorStatusAnnunciationPresent = (flags & 0x08) > 0;
            final boolean contextInfoFollows = (flags & 0x10) > 0;

            // create and fill the new record
            final GlucoseRecord record = new GlucoseRecord();
            record.setSequenceNumber(characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT16, offset));
            offset += 2;

            final int year = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT16, offset);
            final int month = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, offset + 2) - 1; // months are 1-based
            final int day = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, offset + 3);
            final int hours = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, offset + 4);
            final int minutes = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, offset + 5);
            final int seconds = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, offset + 6);
            offset += 7;

            final Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, hours, minutes, seconds);
            record.setTime(calendar);

            if (timeOffsetPresent)
            {
                // time offset is ignored in the current release
                record.setTimeOffset(characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_SINT16, offset));
                offset += 2;
            }

            if (typeAndLocationPresent)
            {
                record.setGlucoseConcentration(characteristic.getFloatValue(BluetoothGattCharacteristic.FORMAT_SFLOAT, offset));
                //  setting data to display glucose concentration
                tempRecievedData.setText("" +record.getGlucoseConcentration());
                record.setConcentrationUnit(concentrationUnit);
                final int typeAndLocation = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, offset + 2);
                record.setType((typeAndLocation & 0xF0) >> 4); // TODO this way or around?
                record.setSampleLocation((typeAndLocation & 0x0F));
                offset += 3;
            }

            if (sensorStatusAnnunciationPresent)
            {
                record.setStatus(characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT16, offset));
            }

            // data set modifications must be done in UI thread
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {

                }
            });
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_measurement);
        btn = (Button) findViewById(R.id.button_measurement);
        tempRecievedData = (TextView) findViewById(R.id.temp_data);
        mHandler = new Handler();
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                readDataFromChar(mSelectedCharacteristic);
            }
        });

        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE))
        {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null)
        {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        if (!mBluetoothAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, ApplicationContants.REQUEST_ENABLE_BT);
        }

        scanLeDevice(true);

        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null)
        {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d(TAG, "Connect request result=" + result);
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        scanLeDevice(false);
        unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        try
        {
            unbindService(mServiceConnection);
        }
        catch (Exception e)
        {

        }
        mBluetoothLeService = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // User chose not to enable Bluetooth.
        if (requestCode == ApplicationContants.REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED)
        {
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void scanLeDevice(final boolean enable)
    {
        if (enable)
        {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    invalidateOptionsMenu();
                }
            }, ApplicationContants.SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        }
        else
        {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
        invalidateOptionsMenu();
    }
}
