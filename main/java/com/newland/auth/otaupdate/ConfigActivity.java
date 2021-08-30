package com.newland.auth.otaupdate;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConfigActivity extends AppCompatActivity {

    private static final String TAG = "ConfigActivity";

    private Button confirm;
    private TextView ipcontent;
    private TextView tvPort;
    private String serverIp;
    private String serverPort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        confirm = findViewById(R.id.ipconfirm);
        ipcontent =  findViewById(R.id.ipcontent);
        tvPort =  findViewById(R.id.port);


        ipcontent.setText(Constants.IP);
        tvPort.setText(Constants.PORT);


        confirm.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "ip设置");
                serverIp = ipcontent.getText().toString().trim();
                serverPort = tvPort.getText().toString().trim();
                if (TextUtils.isEmpty(serverIp) || TextUtils.isEmpty(serverPort)) {
                    Toast.makeText(ConfigActivity.this,"地址不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                Constants.IP = serverIp;
                Constants.PORT = serverPort;
                Toast.makeText(ConfigActivity.this,"设置成功",Toast.LENGTH_SHORT).show();

                finish();

            }
        });
    }
}
