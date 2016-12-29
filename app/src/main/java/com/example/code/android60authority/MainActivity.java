package com.example.code.android60authority;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.btn1);

        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn1:
                checkperm();
                break;
        }
    }

    // 检查在日历中是否有读写权限
    private void checkperm() {
        /**
         * 方法返回值为PackageManager.PERMISSION_DENIED
         * 或者PackageManager.PERMISSION_GRANTED。
         * 当返回DENIED就需要进行申请授权了
         */
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "已被授予权限,continue", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "未权限,try request", Toast.LENGTH_SHORT).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.CAMERA)) {
                // 在这里做需要权限的说明，根据需要来做
                Toast.makeText(MainActivity.this, "需要此权限才可在6.0及以上系统拍照", Toast.LENGTH_SHORT).show();
            } else {
                // 申请权限
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR},
                        1001);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1001: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "权限,apply sucess", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "权限,apply fail", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
