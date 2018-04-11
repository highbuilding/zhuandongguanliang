package com.nuaa.model;

/*
 * Created by Za Teper on 2017/5/13.
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.nuaa.zhuandongforonline.MeasureActivity;
import com.nuaa.zhuandongforonline.R;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * 自定义Dialog基类
 *
 * @author guojinyu
 */
public class TableDialog extends Dialog {
    private TextView tx;
    private List<PeriodTime> list;
    private Context context;
    private int count;
    private String group_num;

    private View.OnClickListener onDefaultClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            cancel();
        }

    };
    private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;
    public TableDialog(Context context,List<PeriodTime> list,String group_num) {
        super(context, R.style.Dialog_Fullscreen);
        this.list=list;
        this.context=context;
        this.group_num=group_num;
    }
private void delListEle(int id){
    TableLayout tableLayout = (TableLayout)findViewById(R.id.tablelayout_1);
    PeriodTime.setId2Count(id,0);
    for(int i=1;i<=count;i++){
        TableRow tableRow=(TableRow)tableLayout.getChildAt(i);
        tx=(TextView)tableRow.getVirtualChildAt(id+1);
        tx.setText("");
    }
    Iterator<PeriodTime> it=list.iterator();
    while(it.hasNext()){
        PeriodTime pt= it.next();
        if(pt.getId()==id){
            it.remove();
        }
    }
}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_2);

        Button BtnReturn = (Button) findViewById(R.id.button_r);
        BtnReturn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                cancel();
            }
        });
        TableLayout tableLayout = (TableLayout)findViewById(R.id.tablelayout_1);
        //全部列自动填充空白处
        tableLayout.setStretchAllColumns(true);
        //生成10行，8列的表格
        PeriodTime max= Collections.max(list);
        count=max.getCount();
        for(int row=0;row<count;row++)
        {
            TableRow tableRow=new TableRow(context);
            for(int col=0;col<5;col++)
            {
                //tv用于显示
                TextView tv=new TextView(context);
                if(col==0){
                    tv.setText((row+1)+"");
                    tv.setBackgroundColor(Color.parseColor("#54A738"));
                }
               else {
                    tv.setText("");
                    tv.setBackgroundColor(Color.parseColor("#AFDF9F"));
                }
                    tv.setTextSize(24);
                    tv.setGravity(Gravity.CENTER);
                    tv.setHeight(50);
                    tableRow.addView(tv);
            }
//新建的TableRow添加到TableLayout
            tableRow.setPadding(5,5,5,5);
            tableLayout.addView(tableRow, new TableLayout.LayoutParams(MP, MP));
        }
        for(PeriodTime pt: list){
            TableRow tableRow=(TableRow)tableLayout.getChildAt(pt.getCount());
            tx=(TextView)tableRow.getVirtualChildAt(pt.getId()+1);
            float data=pt.getData();
            tx.setText(String.valueOf(data));
            tx.setTextColor(Color.parseColor("#000033"));
        }
        TableRow tableRow=new TableRow(context);
        TextView tv=new TextView(context);
        tableRow.addView(tv);

        Button BtnCol1 = new Button(context);
        BtnCol1.setText("删除摆轮");
        BtnCol1.setHeight(50);
        tableRow.addView(BtnCol1);


        Button BtnCol2 = new Button(context);
        BtnCol2.setText("删除摆轮+圆环");
        BtnCol2.setHeight(50);
        tableRow.addView(BtnCol2);

        Button BtnCol3 = new Button(context);
        BtnCol3.setText("删除摆轮+飞机模型");
        BtnCol3.setHeight(50);
        tableRow.addView(BtnCol3);

        Button BtnCol4 = new Button(context);
        BtnCol4.setText("删除摆轮+两圆柱");
        BtnCol4.setHeight(50);
        tableRow.addView(BtnCol4);
        tableLayout.addView(tableRow, new TableLayout.LayoutParams(MP, MP));

        BtnCol1.setOnClickListener(new Button.OnClickListener() {//创建监听
            public void onClick(View v) {
                Log.i("click_delet","按下了 删除摆轮");
                delListEle(0);
            }

        });

        BtnCol2.setOnClickListener(new Button.OnClickListener() {//创建监听
            public void onClick(View v) {
                Log.i("click_delet","按下了 删除摆轮+圆环");
                delListEle(1);
            }

        });

        BtnCol3.setOnClickListener(new Button.OnClickListener() {//创建监听
            public void onClick(View v) {
                Log.i("click_delet","按下了 删除摆轮+飞机模型");
                delListEle(2);
            }

        });

        BtnCol4.setOnClickListener(new Button.OnClickListener() {//创建监听
            public void onClick(View v) {
                Log.i("click_delet","按下了 删除摆轮+两圆柱");
                delListEle(3);
            }
        });

        Button BtnNext = (Button) findViewById(R.id.button_next);
        BtnNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
               // if(PeriodTime.getId2Count(0) == 6 && PeriodTime.getId2Count(1) ==6 && PeriodTime.getId2Count(2) ==6 && PeriodTime.getId2Count(3) ==6)
             //   {
                    MeasureDialog dialog = new MeasureDialog(context,list,group_num);
                    dialog.show();
              //  }

            }
        });



    }

    /**
     * 调用完Builder类的create()方法后显示该对话框的方法
     */
    @Override
    public void show() {
        super.show();
        show(TableDialog.this);
    }

    private void show(TableDialog mDialog) {

    }


}
