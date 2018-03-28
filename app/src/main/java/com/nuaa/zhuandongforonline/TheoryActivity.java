package com.nuaa.zhuandongforonline;

import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TheoryActivity extends BaseActivity {
    private Button PDFbtn;
    private Button VIDEObtn;
    private Button Returnbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory);
        PDFbtn = (Button)findViewById(R.id.button2);
        PDFbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent=new Intent(TheoryActivity.this,FileSelectActivity.class);
                Bundle bundle=new Bundle();
                //传递name参数为tinyphp
                bundle.putString("dir_path", Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        VIDEObtn = (Button)findViewById(R.id.button3);
        VIDEObtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent=new Intent(TheoryActivity.this,FileSelectActivity.class);
                Bundle bundle=new Bundle();
                //传递name参数为tinyphp
                bundle.putString("dir_path", Environment.getExternalStorageDirectory().getAbsolutePath()+"/Movies");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        Returnbtn = (Button)findViewById(R.id.button1);
        Returnbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent=new Intent(TheoryActivity.this,ListActivity.class);

                startActivity(intent);
            }
        });
    }
}
