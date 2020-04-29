package com.example.irsis.Activity;

import android.app.Activity;
import android.hardware.camera2.CameraManager;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {

    public static List<Activity> activities =new ArrayList<>();

    //添加一个活动
    public  static void addActivity(Activity activity){
        activities.add(activity);
    }

    //从list中移除一个活动
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    //直接退出程序
    public static void finishAll(){
        for(Activity activity:activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
        activities.clear();
    }
}
