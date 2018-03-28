package com.nuaa.pojo;

/**
 * Created by Za Teper on 2017/7/2.
 */

public class SineOscPojo {
    private int status;
    private float v_std;
    private int f_std;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getV_std() {
        return v_std;
    }

    public void setV_std(float v_std) {
        this.v_std = v_std;
    }

    public int getF_std() {
        return f_std;
    }

    public void setF_std(int f_std) {
        this.f_std = f_std;
    }

    @Override
    public String toString(){
        return "status:"+this.status+" "+"amp:"+this.v_std+" "+"frequency:"+this.f_std;
    }
}
