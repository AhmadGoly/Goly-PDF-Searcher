package com.example.golypdfsearcher;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String [] permission = {READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE};
    ActivityResultLauncher<Intent> activityResultLauncher;
    Button getPermissionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActivityResultLauncher();
        getPermissionButton = findViewById(R.id.grant_permission_button);
        getPermissionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(checkFilePermission())
                    Toast.makeText(getApplicationContext(),"we have Access to files.",Toast.LENGTH_SHORT);
                else requestFilePermission();
            }
        });

    }

    void setActivityResultLauncher(){
        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == Activity.RESULT_OK){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                        if(Environment.isExternalStorageManager()){
                            Toast.makeText(getApplicationContext(),"Permission Granted.",Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(getApplicationContext(),"Permission Denied.",Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });
    }
    void requestFilePermission(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(
                        Uri.parse(String.format("package:%s",new Object[]{getApplicationContext().getPackageName()})));
                activityResultLauncher.launch(intent);

            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                activityResultLauncher.launch(intent);
            }
        } else{
            ActivityCompat.requestPermissions(this,permission,30);
        }

    }
    boolean checkFilePermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        {
            return Environment.isExternalStorageManager();
        }
        else{
            int readcheck = ContextCompat.checkSelfPermission(getApplicationContext(),READ_EXTERNAL_STORAGE);
            int writecheck = ContextCompat.checkSelfPermission(getApplicationContext(),WRITE_EXTERNAL_STORAGE);
            return readcheck == PackageManager.PERMISSION_GRANTED && writecheck== PackageManager.PERMISSION_GRANTED;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 30:
                if(grantResults.length>0){
                    boolean readper = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean writeper = grantResults[1]==PackageManager.PERMISSION_GRANTED;
                    if(readper && writeper){
                        Toast.makeText(getApplicationContext(),"Permission Granted.",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Permission Denied.",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"You Denied Permission.",Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }
}