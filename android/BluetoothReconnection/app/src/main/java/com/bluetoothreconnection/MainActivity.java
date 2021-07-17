package com.bluetoothreconnection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "BLUETOOTH_RECONNECTION_NOTIFICATION_CHANNEL_ID";
    private static final int BLUETOOTH_RECONNECTION_NOTIFICATION_ID = 100;
    private static final String[] PERMISSIONS = {Manifest.permission.BLUETOOTH};
    private static final int PERMISSIONS_REQUEST_CODE = 200;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestPermission();
        createNotification();
        exitApplication();
    }

    private void requestPermission() {
        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);
                break;
            }
        }
    }

    private void createNotification() {
        NotificationCompat.Builder builder = createNotificationBuilder();

        createNotificationChannel();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(BLUETOOTH_RECONNECTION_NOTIFICATION_ID, builder.build());
    }

    private NotificationCompat.Builder createNotificationBuilder() {
        Intent intent = new Intent(this, BluetoothReconnectionBroadcastReceiver.class);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Bluetooth Reconnection (Click RECONNECT)")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(R.drawable.ic_launcher_foreground, "RECONNECT", pendingIntent);
        return builder;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Bluetooth Reconnection", importance);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void exitApplication() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}