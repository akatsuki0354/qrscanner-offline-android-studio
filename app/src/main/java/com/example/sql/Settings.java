package com.example.sql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class Settings extends AppCompatActivity {

    TextView textView;
    SwitchMaterial switchMaterial;

    private static final int CAMERA_PERMISSION_CODE = 112;
    private static final int STORAGE_PERMISSION_CODE = 113;
    Button btnCammera,btnStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_settings);


        btnCammera = findViewById(R.id.btnCamera);
        btnStorage = findViewById(R.id.btnStorage);
        textView = findViewById(R.id.textView);
        switchMaterial = findViewById(R.id.switchMaterial);
        switchMaterial.setChecked(false);
        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    textView.setTextColor(getResources().getColor(R.color.white));
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    textView.setTextColor(getResources().getColor(R.color.black));
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        btnCammera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermession(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);

            }
        });
        btnStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermession(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);

            }
        });
    }
    public void checkPermession(String permession, int cameraPermissionCode){
        if(ContextCompat.checkSelfPermission(Settings.this,permession)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(Settings.this, new String[]{permession}, cameraPermissionCode);
        }else{
            Toast.makeText(Settings.this, "Permession already Granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                btnCammera.setText("Permission Granted");
                Toast.makeText(Settings.this, "Camera permission allow", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Settings.this, "Camera permission Denied", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == STORAGE_PERMISSION_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                btnStorage.setText("Permission Granted");
                Toast.makeText(Settings.this, "Storage permission allow", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText( Settings.this, "Storage permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


}

