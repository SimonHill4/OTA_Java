package com.newland.assistant.controller.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;


import com.newland.assistant.util.SystemPropertyUtil;
import com.newland.assistant.util.ThreadManager;
import com.newland.testassistant.R;
import com.newland.assistant.api.NdkApi;
import com.newland.assistant.model.AndroidUtils;
import com.newland.assistant.model.Constants;
import com.newland.assistant.model.bean.OtaUpdateBean;
import com.newland.assistant.model.ota.OtaUpdateTask;
import com.newland.assistant.util.LogUtil;
import com.newland.assistant.util.SPUtils;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @author lin
 * @version 2019/5/31
 */
public class OtaUpdateReceiver extends BroadcastReceiver {

    private static final String ACTION_INSTALL_OTA = "android.intent.extra.ota.silent.installation.result";
    String msg = null;
    @Override
    public void onReceive(Context context, Intent intent) {
        // 判断mTMS客户端安装成功，启动BootStartService
        String action = intent.getAction();
        LogUtil.e(action);
        if (ACTION_INSTALL_OTA.equals(action)){
       
            int state = intent.getIntExtra("state", 1);

            if (state == 1){
                int errorCode = intent.getIntExtra("errCode", -100);
                if(errorCode == -100){
                    msg = context.getString(R.string.ota_failed);
                }else if(errorCode == -101){
                    msg = context.getString(R.string.ota_failed_low_battery);
                }else if(errorCode == -102){
                    msg = context.getString(R.string.ota_failed_not_allow_down_version);
                }else if(errorCode == -103){
                    msg = context.getString(R.string.ota_failed_not_allow_permision);
                }
            }else if (state == 0){
                msg = context.getString(R.string.ota_success);
            }else {
                msg = "UnknownError state:" + state;
            }
          
        }
    }

}
