package com.nuaa.zhuandongforonline;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.nuaa.model.PeriodTime;
import com.nuaa.model.TableDialog;
import com.nuaa.model.ZhuanDong;
import com.nuaa.utils.Uart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MeasureActivity extends BaseActivity {
    public String group_num;
    public String stu_num;
    private List<PeriodTime> list_data;
    private Uart uart;
    private UartTask task;
    private int Dev_state;
    private int cur_selected=0;//当前选中的物体
    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_1);
        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        group_num = bundle.getString("group_num");
        stu_num = bundle.getString("stu_num");
        list_data=new ArrayList<>();
        EditText edit_text3 = (EditText) findViewById(R.id.editText2);
        edit_text3.setOnKeyListener(onKey);
        //初次设定为摆轮
        TextView textVs = (TextView) findViewById(R.id.textview_s);
        textVs.setText("摆轮");
        ToggleButton mTogBtn1 = (ToggleButton) findViewById(R.id.button_s1); // 获取到控件
        mTogBtn1.setChecked(true);
        mTogBtn1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    //选中
                    TextView textVs = (TextView) findViewById(R.id.textview_s);
                    textVs.setText("摆轮");

                    ToggleButton tp = (ToggleButton) findViewById(R.id.button_s1); // 获取到控件
                    tp.setChecked(true);
                    tp = (ToggleButton) findViewById(R.id.button_s3); // 获取到控件
                    tp.setChecked(false);
                    tp = (ToggleButton) findViewById(R.id.button_s2); // 获取到控件
                    tp.setChecked(false);
                    tp = (ToggleButton) findViewById(R.id.button_s4); // 获取到控件
                    tp.setChecked(false);
                    cur_selected=0;
                }
            }
        });// 添加监听事件
        ToggleButton mTogBtn2 = (ToggleButton) findViewById(R.id.button_s2); // 获取到控件
        mTogBtn2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    //选中
                    TextView textVs = (TextView) findViewById(R.id.textview_s);
                    textVs.setText("摆轮+圆环");
                    ToggleButton tp = (ToggleButton) findViewById(R.id.button_s2); // 获取到控件
                    tp.setChecked(true);
                    tp = (ToggleButton) findViewById(R.id.button_s4); // 获取到控件
                    tp.setChecked(false);
                    tp = (ToggleButton) findViewById(R.id.button_s3); // 获取到控件
                    tp.setChecked(false);
                    tp = (ToggleButton) findViewById(R.id.button_s1); // 获取到控件
                    tp.setChecked(false);
                    cur_selected=1;
                }
            }
        });// 添加监听事件
        ToggleButton mTogBtn3 = (ToggleButton) findViewById(R.id.button_s3); // 获取到控件
        mTogBtn3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    //选中
                    TextView textVs = (TextView) findViewById(R.id.textview_s);
                    textVs.setText("摆轮+飞机模型");
                    ToggleButton tp = (ToggleButton) findViewById(R.id.button_s3); // 获取到控件
                    tp.setChecked(true);
                    tp = (ToggleButton) findViewById(R.id.button_s2); // 获取到控件
                    tp.setChecked(false);
                    tp = (ToggleButton) findViewById(R.id.button_s4); // 获取到控件
                    tp.setChecked(false);
                    tp = (ToggleButton) findViewById(R.id.button_s1); // 获取到控件
                    tp.setChecked(false);
                    cur_selected=2;
                }
            }
        });// 添加监听事件
        ToggleButton mTogBtn4 = (ToggleButton) findViewById(R.id.button_s4); // 获取到控件
        mTogBtn4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    //选中
                    TextView textVs = (TextView) findViewById(R.id.textview_s);
                    textVs.setText("摆轮+两圆柱");
                    ToggleButton tp = (ToggleButton) findViewById(R.id.button_s4); // 获取到控件
                    tp.setChecked(true);
                    tp = (ToggleButton) findViewById(R.id.button_s2); // 获取到控件
                    tp.setChecked(false);
                    tp = (ToggleButton) findViewById(R.id.button_s3); // 获取到控件
                    tp.setChecked(false);
                    tp = (ToggleButton) findViewById(R.id.button_s1); // 获取到控件
                    tp.setChecked(false);
                    cur_selected=3;
                }
            }
        });// 添加监听事件

        Button btn_checkdat = (Button) findViewById(R.id.button2);//查看保存数据的按钮
        btn_checkdat.setOnClickListener(new Button.OnClickListener() {//创建监听
            public void onClick(View v) {
                if(list_data.size()>0) {
                    for(PeriodTime pt:list_data){
                        Log.i("list_data",pt.toString());
                    }
                    TableDialog dialog = new TableDialog(MeasureActivity.this, list_data,group_num);
                    dialog.show();
                }
                else {
                    // 创建退出对话框
                    AlertDialog wrong_1 = new AlertDialog.Builder(MeasureActivity.this).create();
                    // 设置对话框标题
                    wrong_1.setTitle("系统警告");
                    // 设置对话框消息
                    wrong_1.setMessage("还没有保存任何数据，请继续实验！");
                    // 添加选择按钮并注册监听
                    wrong_1.setButton(DialogInterface.BUTTON_NEGATIVE, "确定",listener);
                    // 显示对话框
                    wrong_1.show();
                }

            }

        });
        Button Btn1 = (Button) findViewById(R.id.button1);//发送命令
        Btn1.setOnClickListener(new Button.OnClickListener() {//创建监听
            public void onClick(View v) {
                uart=Uart.getInstance();
                Dev_state=uart.InitUart();
                if(Dev_state>=0){
                    timer=new Timer();
                    EditText edt=(EditText) findViewById(R.id.editText2);
                    Log.i("serial", "periodcnt:"+Integer.parseInt(edt.getText().toString()));
                    int period_cnt=Integer.parseInt(edt.getText().toString());

                    byte[] data=ZhuanDong.StopCmd();
                    //发送停止
                    uart.WriteUart(data);
                    SystemClock.sleep(300);
                    data=ZhuanDong.SetPeriodCnt(period_cnt);
                    //发送周期数命令
                    uart.WriteUart(data);
                    SystemClock.sleep(300);
                    data=ZhuanDong.StartCmd();
                    //发送开始
                    uart.WriteUart(data);

                    TextView period_tv=(TextView) findViewById(R.id.TextView6);//周期数
                    period_tv.setText("0");
                    TextView time_tv=(TextView) findViewById(R.id.TextView7);//时间
                    time_tv.setText("0");
                    Arrays.fill(recv_bufall,(byte)0);
                    read_pos = 0;
                    write_pos = 0;

                    task=new UartTask();
                    timer.schedule(task, 0, 100);//100ms
                }

            }

        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    /**
     5      * byte数组转换成16进制字符串
     6      * @param src
     7      * @return
     8      */
      public static String bytesToHexString(byte[] src){
                   StringBuilder stringBuilder = new StringBuilder();
                    if (src == null || src.length <= 0) {
                            return null;
                       }
                    for (int i = 0; i < src.length; i++) {
                            int v = src[i] & 0xFF;
                            String hv = Integer.toHexString(v);
                            if (hv.length() < 2) {
                                    stringBuilder.append(0);
                               }
                            stringBuilder.append(hv+" ");
                        }
                   return stringBuilder.toString();
                }


    private final int BUFSIZE = 512;
    private byte[] recv_bufall = new byte[BUFSIZE];
    private int read_pos = 0;
    private int write_pos = 0;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            byte[] buf = new byte[BUFSIZE];
            switch (msg.what) {
                case 1:
                    int read_size=uart.ReadUart(buf);
                    if(read_size<=0 && (write_pos-read_pos)==0){
                        return;
                    }
                    for(int i=0;i<read_size;i++){
                        recv_bufall[write_pos++]=buf[i];
                    }
                    while(write_pos-read_pos>=8){
                        if(recv_bufall[read_pos]==(byte)0xfc&&recv_bufall[read_pos+1]==(byte)0xfe){
                            int length=recv_bufall[read_pos+2];
                            byte[] result=new byte[8];
                            for(int i=0;i<8;i++){
                                result[i]=recv_bufall[read_pos+i];
                            }
                            read_pos+=8;
                            int period_cnt=((int)result[4]&0xff)*256+((int)result[5]&0xff);
                            TextView period_tv=(TextView) findViewById(R.id.TextView6);//周期数
                            period_tv.setText(Integer.toString(period_cnt));
                            TextView time_tv=(TextView) findViewById(R.id.TextView7);//时间
                            int time=((int)result[6]&0xff)*256+((int)result[7]&0xff);
                            Log.i("serial","period_cnt:"+period_cnt);
                            Log.i("serial","time:"+time);
                            Float time_fl=(0.0f+time)/1000;
                            DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                            time_tv.setText(decimalFormat.format(time_fl));
                            EditText period_edt=(EditText) findViewById(R.id.editText2);
                            if(Integer.parseInt(period_edt.getText().toString())==period_cnt){//采集完毕
                                timer.cancel();
                                task.cancel();
                                // 创建退出对话框
                                AlertDialog isExit = new AlertDialog.Builder(MeasureActivity.this).create();
                                // 设置对话框标题
                                isExit.setTitle("系统提示");
                                // 设置对话框消息
                                isExit.setMessage("需要保存吗?");
                                // 添加选择按钮并注册监听
                                isExit.setButton(DialogInterface.BUTTON_POSITIVE,"确定", listener);
                                isExit.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",listener);
                                // 显示对话框
                                isExit.show();
                            }

                        }
                        else{
                            read_pos++;
                        }
                    }
                    Log.i("serial_monitor","write_pos:"+write_pos);
                    Log.i("serial_monitor","read_pos:"+ read_pos);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**监听对话框里面的button点击事件*/
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    Log.i("cur_selected",cur_selected+" ");
                    PeriodTime obj=new PeriodTime();
                    obj.setId(cur_selected);
                    TextView time_tv=(TextView) findViewById(R.id.TextView7);//时间
                    obj.setData(Float.parseFloat(time_tv.getText().toString()));
                    obj.setCount(obj.getId2Count(cur_selected)+1);
                    obj.setId2Count(cur_selected,obj.getId2Count(cur_selected)+1);
                    list_data.add(obj);
                    dialog.dismiss();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    Log.i("serial","no save data");
                    dialog.dismiss();

                    break;
                default:
                    break;
            }
        }
    };


    class UartTask extends TimerTask{
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }

    }



    //隐藏键盘
    View.OnKeyListener onKey = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
// TODO Auto-generated method stub
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
                return true;
            }
            return false;
        }
    };
}