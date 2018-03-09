package com.example.silence.xiyang;

/**
 * Created by Silence on 2018/3/9.
 */

public class HandShow {
    private String name;
    private int imageid;

    public HandShow(String name,int imageid){
        this.name = name;
        this.imageid = imageid;
    }

    public String getName(){
        return name;
    }
    public int getImageid(){
        return imageid;
    }
}
