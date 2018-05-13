package com.example.deepakrattan.runtimepermissiondemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//Runtime Permission Steps
/*
 * 1. Check the platform
 * 2. Check the Permission
 * 3. Explain the permission
 * 4. Request the permission
 * 5. Handle the response
 *
 *
 *
 * */

public class MainActivity extends AppCompatActivity {
    private Button btnchkPermissions;
    public static final int RequestPermissionCode = 999;
    public static final String TAG = "TEST";
    public static final String PRE_MARSHMALLOW = "PreMarshMallow";
    public static final String MARSHMALLOW = "Marshmallow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnchkPermissions = findViewById(R.id.btnChkPermission);
        btnchkPermissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Step 1. check the platform
                String platform = checkPlatform();
                if (platform.equals("Marshmallow")) {
                    Log.d(TAG, "Runtime permission required");
                    //Step 2. check the permission
                    boolean permissionStatus = checkPermission();
                    if (permissionStatus) {
                        //Permission already granted
                        Log.d(TAG, "Permission already granted");
                    } else {
                        //Permission not granted
                        //Step 3. Explain permission i.e show an explanation
                        Log.d(TAG, "Explain permission");
                        explainPermission();
                        //Step 4. Request Permissions
                        Log.d(TAG, "Request Permission");
                        requestPermission();
                    }

                } else {
                    Log.d(TAG, "onClick: Runtime permission not required");
                }


            }
        });
    }

    //Checking the Camera and Read_Contacts permissions
    public boolean checkPermission() {
        int cameraPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        int readContactPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS);
        return cameraPermissionResult == PackageManager.PERMISSION_GRANTED && readContactPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    //Requesting the Camera and Read_Contacts permissions
    public void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_CONTACTS
        }, RequestPermissionCode);
    }

    //Step 5. Handle the Response
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission was granted
                    Log.d(TAG, "onRequestPermissionsResult: Permission Granted");
                    Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    //Permission Denied
                    Log.d(TAG, "onRequestPermissionsResult: Permission Denied");
                    Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    //Check the platfrom
    public String checkPlatform() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return MARSHMALLOW;

        } else {
            return PRE_MARSHMALLOW;
        }
    }

    //Explain Permission required
    public void explainPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)) {
            Log.d(TAG, "explainPermission:Camera Permission required ");
            Toast.makeText(MainActivity.this, "Camera Permission required", Toast.LENGTH_SHORT).show();
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS)) {
            Log.d(TAG, "explainPermission:Read Contacts Permission Required ");
            Toast.makeText(MainActivity.this, "Read Contacts Permission Required", Toast.LENGTH_SHORT).show();
        }
    }


}
