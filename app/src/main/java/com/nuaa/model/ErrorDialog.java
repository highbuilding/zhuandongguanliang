package com.nuaa.model;

/**
 * Created by where1993 on 2018/3/18.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nuaa.zhuandongforonline.R;

import java.util.List;

/*
 * Created by Za Teper on 2017/5/13.
 */


/**
 * 自定义Dialog基类
 *
 * @author guojinyu
 */
public class ErrorDialog extends Dialog {
    private List<PeriodTime> list;
    private Context context;

    private View.OnClickListener onDefaultClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            cancel();
        }

    };
    private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;
    public ErrorDialog(Context context, List<PeriodTime> list, String group_num) {
        super(context, R.style.Dialog_Fullscreen);
        this.list=list;
        this.context=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bad_checked);
        Button BtnFinish = (Button) findViewById(R.id.button_sure);
        BtnFinish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                restartApplication();
            }
        });

    }
    /*
      重启APP
     */
    private void restartApplication() {
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
    /**
     * 调用完Builder类的create()方法后显示该对话框的方法
     */
    @Override
    public void show() {
        super.show();
       // show(CheckDialog.this);
    }

    private void show(ErrorDialog mDialog) {

    }




}

