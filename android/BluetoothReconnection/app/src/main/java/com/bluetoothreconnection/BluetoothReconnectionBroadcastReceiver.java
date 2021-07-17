package com.bluetoothreconnection;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Set;

public class BluetoothReconnectionBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = BluetoothReconnectionBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Set<BluetoothDevice> pairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        Log.d(TAG, "pairedDevices.size: " + pairedDevices.size());

        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);

        for (BluetoothDevice device : pairedDevices) {
            String deviceName = device.getName();
            String deviceHardwareAddress = device.getAddress();

            int deviceConnectionState = bluetoothManager.getConnectionState(device, BluetoothProfile.GATT);
            Log.d(TAG, "deviceName: " + deviceName + ", deviceHardwareAddress: " + deviceHardwareAddress + ", connectionState: " + deviceConnectionState);

//            BluetoothProfile.ServiceListener serviceListener = new BluetoothProfile.ServiceListener() {
//
//                @Override
//                public void onServiceConnected(int profile, BluetoothProfile proxy) {
//                    BluetoothAdapter.getDefaultAdapter().closeProfileProxy(profile, proxy);
//                }
//
//                @Override
//                public void onServiceDisconnected(int profile) {
//                }
//            };
//            BluetoothAdapter.getDefaultAdapter().getProfileProxy(this, serviceListener, BluetoothProfile.GATT);

//            if (bluetoothManager.getConnectionState(device, BluetoothProfile.GATT) == BluetoothProfile.STATE_DISCONNECTED) {
//                BluetoothGatt bluetoothGatt = device.connectGatt(context, false, new BluetoothGattCallback() {
//                    @Override
//                    public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
//                        super.onPhyUpdate(gatt, txPhy, rxPhy, status);
//                        Log.d(TAG, "onPhyUpdated, status: " + status);
//                    }
//                });
//                bluetoothGatt.disconnect();
//                bluetoothGatt.close();
//                device.connectGatt(context, false, new BluetoothGattCallback() {
//                    @Override
//                    public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
//                        super.onPhyUpdate(gatt, txPhy, rxPhy, status);
//                    }
//                });
//                Log.d(TAG, device.getName() + " is reconnected.");
//            }
        }
    }
}
