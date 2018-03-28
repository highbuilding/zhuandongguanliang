package com.nuaa.model;

/*
 * Created by Za Teper on 2017/5/13.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nuaa.pojo.JsonUtil;
import com.nuaa.pojo.ResponsePojo;
import com.nuaa.utils.HttpUtil;
import com.nuaa.utils.MakeUrl;
import com.nuaa.zhuandongforonline.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 自定义Dialog基类
 *
 * @author guojinyu
 */
public class CheckDialog extends Dialog {
    private List<PeriodTime> list;
    private Context context;
    private int count;
    private String group_num;
    private final int STATUS=0;

    private View.OnClickListener onDefaultClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            cancel();
        }

    };
    private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;
    public CheckDialog(Context context, List<PeriodTime> list, String group_num) {
        super(context, R.style.Dialog_Fullscreen);
        this.list=list;
        this.context=context;
        this.group_num=group_num;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking);
        timer.schedule(task, 0, 5000); // 0s后执行task,经过5s再次执行

    }
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            // 需要做的事:发送消息
            Map<String,String> map=new HashMap<>();
            map.put("group_num",group_num);
            String url= MakeUrl.genUrl(MakeUrl.BaseUrl+MakeUrl.Check,map);
            HttpUtil.sendRequestWithHttpURLConnection(url,STATUS,handler);
            Log.i("checkinfo",url);
        }
    };
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STATUS:
                    String str = (String) msg.obj;
                    Log.i("checkinfo",str);
                    ResponsePojo response=(ResponsePojo)JsonUtil.stringToObject(str, ResponsePojo.class);
                    if(response.getStatus()==1){
                        FinishDialog dialog = new FinishDialog(context, list,group_num);
                        dialog.show();
                    }
                    else{
                        if(response.getMsg().equals("审核中")){

                        }
                        else if(response.getMsg().equals("未通过")){
                            ErrorDialog dialog = new ErrorDialog(context, list,group_num);
                            dialog.show();
                        }
                    }

            }
        }
    };

    /**
     * 调用完Builder类的create()方法后显示该对话框的方法
     */
    @Override
    public void show() {
        super.show();
        show(CheckDialog.this);
    }

    private void show(CheckDialog mDialog) {

    }

    private Handler handler_upload = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STATUS:
                    String str = (String) msg.obj;
                    Log.i("response",str);
                    ResponsePojo response=(ResponsePojo) JsonUtil.stringToObject(str, ResponsePojo.class);
                    if(response.getStatus()==1){

                    }
                    else{
                        Toast.makeText(context.getApplicationContext(),"上传失败",
                                Toast.LENGTH_SHORT).show();
                    }

            }
        }
    };


}
