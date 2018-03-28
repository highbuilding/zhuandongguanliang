package com.nuaa.zhuandongforonline;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.nuaa.adapter.FileAdapter;

import java.io.File;

public class FileSelectActivity extends BaseActivity {
    private String[] data=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_select);
        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        final String dir_path = bundle.getString("dir_path");
        File file = new File(dir_path);
        if(file.isDirectory()){
            File [] fileArray = file.listFiles();
            data=new String[fileArray.length];
            if(null != fileArray && 0 != fileArray.length){
                for(int i = 0; i < fileArray.length; i++){
                    data[i]=fileArray[i].getName();
                }
                FileAdapter adapter = new FileAdapter(FileSelectActivity.this,R.layout.item,data,R.drawable.file_ico2);
                ListView listView = (ListView) findViewById(R.id.list_view);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        final String path=dir_path+"/"+data[arg2];
                        dialog(path);
                        // TODO Auto-generated method stub
                        Toast.makeText(getApplicationContext(),data[arg2],
                                Toast.LENGTH_SHORT).show();
                    }

                });
            }


        }
        else{
            Log.i("file","nonono");
        }

    }
    protected void dialog(final String file_path) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FileSelectActivity.this);
        builder.setMessage("您确定浏览该教程吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent=null;
                if(file_path.contains("Pictures")){
                    intent =new Intent(FileSelectActivity.this,PdfActivity.class);
                }
                else if(file_path.contains("Movies")){
                    intent =new Intent(FileSelectActivity.this,VideoActivity.class);
                }
                Bundle bundle=new Bundle();
                //传递name参数为tinyphp
                bundle.putString("file_path", file_path);
                intent.putExtras(bundle);
                startActivity(intent);
            }
            });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                   }
             });
        builder.create().show();

    }
}

