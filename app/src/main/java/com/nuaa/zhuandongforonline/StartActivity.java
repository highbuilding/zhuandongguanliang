package com.nuaa.zhuandongforonline;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nuaa.utils.HttpUtil;
import com.nuaa.utils.MakeUrl;

import java.util.HashMap;
import java.util.Map;


public class StartActivity extends BaseActivity {
    public static final int SHOW_RESPONSE = 0;
    public static char group_name;
    public String group_num;
    public String stu_num;
    public String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button Btn1 = (Button)findViewById(R.id.button);//获取按钮资源
        EditText edit_text1 = (EditText) findViewById(R.id.edit_text1);
        edit_text1.setOnKeyListener(onKey);
        Btn1.setOnClickListener(new Button.OnClickListener(){//创建监听
            public void onClick(View v) {
                EditText edit_text1=(EditText)findViewById(R.id.edit_text1);
                EditText edit_text2=(EditText)findViewById(R.id.edit_text2);
                EditText edit_text3=(EditText)findViewById(R.id.edit_text3);
                group_num=edit_text1.getText().toString();
                stu_num=edit_text2.getText().toString();
                name=edit_text3.getText().toString();
                Map<String,String> par=new HashMap<>();
                par.put("group_num",group_num);
                par.put("stu_num",stu_num);
                par.put("stu_name",name);
                String URL= MakeUrl.genUrl(MakeUrl.BaseUrl+MakeUrl.Login,par);
                Log.i("url",URL);
                HttpUtil.sendRequestWithHttpURLConnection(URL,SHOW_RESPONSE,handler);

            }

        });


    }
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
                    Toast.makeText(getApplicationContext(),response,
                            Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(StartActivity.this,ListActivity.class);
                    //用Bundle携带数据
                    Bundle bundle=new Bundle();
                    //传递name参数为tinyphp
                    bundle.putString("group_num",group_num);
                    bundle.putString("stu_num",stu_num);
                    intent.putExtras(bundle);

                    startActivity(intent);
            }
        }
    };

    //隐藏键盘
    View.OnKeyListener onKey=new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
// TODO Auto-generated method stub
            if(keyCode == KeyEvent.KEYCODE_ENTER)
            {

                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm.isActive()){
                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0 );
                }
                return true;
            }
            return false;
        }
    };
}



