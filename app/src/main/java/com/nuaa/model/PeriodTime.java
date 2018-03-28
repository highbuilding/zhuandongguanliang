package com.nuaa.model;

import android.util.Log;

/*
 * Created by Za Teper on 2017/5/13.
 */

public class PeriodTime implements Comparable<PeriodTime>{
    private int id;
    private int count;
    private static int[] idmapcount=new int[4];
    static {
        Log.i("static","test static block");
        for(int i=0;i<idmapcount.length;i++){
            idmapcount[i]=0;
        }
    }
    private float data;
    static public  void setId2Count(int id,int count){
        idmapcount[id]=count;
    }
    static public  int getId2Count(int id){
        return idmapcount[id];
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }

    @Override
    public int compareTo(PeriodTime pt){
        int result=this.count-pt.getCount();
        int result1= (result == 0 ? this.id-pt.getId():result);
        return result1;
    }
    @Override
    public String toString(){
        return "id:"+id+",count:"+count+",data:"+data;
    }
}
