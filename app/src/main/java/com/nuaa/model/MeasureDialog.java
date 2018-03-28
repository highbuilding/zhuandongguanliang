package com.nuaa.model;

/*
 * Created by Za Teper on 2017/5/13.
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nuaa.pojo.JsonUtil;
import com.nuaa.pojo.ResponsePojo;
import com.nuaa.utils.HttpUtil;
import com.nuaa.utils.MakeUrl;
import com.nuaa.zhuandongforonline.Measure_1_Activity;
import com.nuaa.zhuandongforonline.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 自定义Dialog基类
 *
 * @author guojinyu
 */
public class MeasureDialog extends Dialog {
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
    public MeasureDialog(Context context, List<PeriodTime> list, String group_num) {
        super(context, R.style.Dialog_Fullscreen);
        this.list=list;
        this.context=context;
        this.group_num=group_num;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);
        Button BtnReturn = (Button) findViewById(R.id.button_r);
        BtnReturn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                cancel();
            }
        });
        Button BtnUpload = (Button) findViewById(R.id.button_upload);
        BtnUpload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Map par=new HashMap<>();
                par.put("group_num",group_num);
                par.put("d1",((EditText)findViewById(R.id.tableData00)).getText().toString());
                par.put("d2",((EditText)findViewById(R.id.tableData01)).getText().toString());
                par.put("d3",((EditText)findViewById(R.id.tableData02)).getText().toString());
                par.put("d4",((EditText)findViewById(R.id.tableData03)).getText().toString());
                par.put("d5",((EditText)findViewById(R.id.tableData04)).getText().toString());
                par.put("d6",((EditText)findViewById(R.id.tableData05)).getText().toString());
                par.put("x",((EditText)findViewById(R.id.tableData06)).getText().toString());
                par.put("dn_1",((EditText)findViewById(R.id.tableData12)).getText().toString());
                par.put("dn_2",((EditText)findViewById(R.id.tableData13)).getText().toString());
                par.put("dn_3",((EditText)findViewById(R.id.tableData14)).getText().toString());
                par.put("dn_4",((EditText)findViewById(R.id.tableData15)).getText().toString());
                par.put("dn_5",((EditText)findViewById(R.id.tableData16)).getText().toString());
                par.put("dn_6",((EditText)findViewById(R.id.tableData17)).getText().toString());
                par.put("dw_1",((EditText)findViewById(R.id.tableData18)).getText().toString());
                par.put("dw_2",((EditText)findViewById(R.id.tableData19)).getText().toString());
                par.put("dw_3",((EditText)findViewById(R.id.tableData20)).getText().toString());
                par.put("dw_4",((EditText)findViewById(R.id.tableData21)).getText().toString());
                par.put("dw_5",((EditText)findViewById(R.id.tableData22)).getText().toString());
                par.put("dw_6",((EditText)findViewById(R.id.tableData23)).getText().toString());
                par.put("m1",((EditText)findViewById(R.id.tableData24)).getText().toString());
                par.put("m2",((EditText)findViewById(R.id.tableData30)).getText().toString());
                for(PeriodTime item :list)
                {
                    par.put("t"+item.getId()+"_"+item.getCount(), Float.toString(item.getData()));
                }
                String url= MakeUrl.genUrl(MakeUrl.BaseUrl+MakeUrl.Submit,par);
                Log.i("submit",url);
                HttpUtil.sendRequestWithHttpURLConnection(url,STATUS,handler_upload);
            }
        });
    }

    /**
     * 调用完Builder类的create()方法后显示该对话框的方法
     */
    @Override
    public void show() {
        super.show();
        show(MeasureDialog.this);
    }

    private void show(MeasureDialog mDialog) {

    }

    private Handler handler_upload = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STATUS:
                    String str = (String) msg.obj;
                    Log.i("response",str);
                    ResponsePojo response=(ResponsePojo) JsonUtil.stringToObject(str, ResponsePojo.class);
                    if(response.getStatus()==1){
                        //进入检测界面
                        CheckDialog dialog = new CheckDialog(context, list,group_num);
                        dialog.show();

                    }
                    else{
                        Toast.makeText(context.getApplicationContext(),"上传失败",
                                Toast.LENGTH_SHORT).show();
                    }

            }
        }
    };


}
