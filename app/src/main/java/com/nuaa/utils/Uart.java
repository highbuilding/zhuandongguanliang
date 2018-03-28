package com.nuaa.utils;

import android.util.Log;

import com.friendlyarm.AndroidSDK.HardwareControler;

/*
 * Created by Za Teper on 2017/4/27.
 */

public class Uart {
    private final static int BUFFERSIZE=1000;
    private static  String devName = "/dev/ttySAC3";
    private static  int speed = 115200;
    private static  int dataBits = 8;
    private static  int stopBits = 1;
    private int devfd;

    // 定义私有构造方法（防止通过 new SingletonTest()去实例化）
    private Uart(){
        devfd=-1;
    }
    // 定义一个SingletonTest类型的变量（不初始化，注意这里没有使用final关键字）
    private static Uart instance;
    // 定义一个静态的方法（调用时再初始化SingletonTest，使用synchronized 避免多线程访问时，可能造成重的复初始化问题）
    public static synchronized  Uart getInstance() {
        if (instance == null)
            instance = new Uart();
        return instance;
    }
    public static  synchronized void setPar(String Name,int Speed,int DataBits,int StopBits){
        devName = Name;
        speed = Speed;
        dataBits = DataBits;
        stopBits = StopBits;
    }
    public synchronized int WriteUart(byte[] data) {
        return HardwareControler.write(devfd, data);
    }
    public synchronized int CloseInit() {
        if (devfd != -1) {
            HardwareControler.close(devfd);
            devfd = -1;
            return 1;
        }
        return 0;
    }
    public synchronized int InitUart() {
        if (devfd >= 1) {
            Log.i("serial", "uart already open");
            return 1;
        }
        devfd = HardwareControler.openSerialPort(devName, speed, dataBits, stopBits);
        if (devfd >= 0) {
            Log.i("serial", "uart open success");
            return 0;
        } else {
            devfd = -1;
            Log.i("serial", "uart open fail");
            return -1;
        }
    }
    public int ReadUart(byte[] buffer){
        if(HardwareControler.select(devfd, 0, 0) == 1){
            int retSize =  HardwareControler.read(devfd, buffer, BUFFERSIZE);
            return  retSize;
        }
        return -1;
    }


}
