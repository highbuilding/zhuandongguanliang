package com.nuaa.zhuandongforonline;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ListActivity extends BaseActivity {
    public String group_num;
    public String stu_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        group_num = bundle.getString("group_num");
        stu_num = bundle.getString("stu_num");
        //实验原理
        Button Btn1 = (Button)findViewById(R.id.button1);//获取按钮资源
        Btn1.setOnClickListener(new Button.OnClickListener(){//创建监听
            public void onClick(View v) {
                Intent intent=new Intent(ListActivity.this,TheoryActivity.class);
                Bundle bundle=new Bundle();
                startActivity(intent);
            }

        });

        //实验一
        Button Btn2 = (Button)findViewById(R.id.button2);//获取按钮资源
        Btn2.setOnClickListener(new Button.OnClickListener(){//创建监听
            public void onClick(View v) {
                Intent intent=new Intent(ListActivity.this,Measure_1_Activity.class);
                //用Bundle携带数据
                Bundle bundle=new Bundle();
                //传递name参数为tinyphp
                bundle.putString("group_num",group_num);
                bundle.putString("stu_num",stu_num);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });


        //退出
        Button Btn4 = (Button)findViewById(R.id.button);//获取按钮资源
        Btn4.setOnClickListener(new Button.OnClickListener(){//创建监听
            @Override
            public void onClick(View view) {
                Back_all_activity();

            }

        });

    }
    public void Back_all_activity(){
        new AlertDialog.Builder(this).setTitle("确认退出吗？")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“确认”后的操作
                        ActivityCollector.finishAll();

                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“返回”后的操作,这里不设置没有任何操作
                    }
                }).show();
    }
    public void onBackPressed() {
        Back_all_activity();
    }

}
