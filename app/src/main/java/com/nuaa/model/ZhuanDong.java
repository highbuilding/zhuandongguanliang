package com.nuaa.model;

/**
 * Created by Za Teper on 2017/5/7.
 */

/**
 *
 *  包头 数据长度(1byte) 地址码(1byte) 命令字(  ) 数据域(0byte)
 *
 */

public class ZhuanDong {
    static private byte[] header= {(byte)0xfc,(byte)0xfe};
    private byte cmd;
    public ZhuanDong(){

    }
    public static byte[] StartCmd(){
        byte[] result=new byte[4];
        result[0]=(byte)0xfc;
        result[1]=(byte)0xfe;
        result[2]=(byte)0x04;
        result[3]=(byte)0x02;//2代表开始命令
        return result;
    }
    public static byte[] AckCmd(){
        byte[] result=new byte[4];
        result[0]=(byte)0xfc;
        result[1]=(byte)0xfe;
        result[2]=(byte)0x04;
        result[3]=(byte)0x05;//5代表ACK
        return result;
    }
    public static byte[] StopCmd(){
        byte[] result=new byte[4];
        result[0]=(byte)0xfc;
        result[1]=(byte)0xfe;
        result[2]=(byte)0x04;
        result[3]=(byte)0x04;//4代表开始结束
        return result;
    }
     public static byte[] SetPeriodCnt(int periodCnt){
         byte[] result=new byte[6];
         result[0]=(byte)0xfc;
         result[1]=(byte)0xfe;
         result[2]=(byte)0x06;
         result[3]=(byte)0x01;//1代表设置参数
         result[4]=(byte)(periodCnt/256);
         result[5]=(byte)(periodCnt%256);
         return result;
    }

}
