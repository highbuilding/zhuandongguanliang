package com.nuaa.utils;

import java.util.Map;

/**
 * Created by Za Teper on 2017/4/22.
 */

public class MakeUrl {
    public static String Login= "client=true&action=login&";
    public static String Submit= "client=true&action=submit&";
    public static String Check= "client=true&action=check_info&";
    public static String BaseUrl= "http://120.76.124.44/experiment_manage/client/exp3_moment_inertia.php?";
    public static String genUrl(String base_url,Map<String,String> value){
        StringBuffer result=new StringBuffer(base_url);
        // 第三种：推荐，尤其是容量大时
                 for (Map.Entry<String, String> entry : value.entrySet()) {
                         //Map.entry<Integer,String> 映射项（键-值对）  有几个方法：用上面的名字entry
                         //entry.getKey() ;entry.getValue(); entry.setValue();
                         //map.entrySet()  返回此映射中包含的映射关系的 Set视图。
                     result.append(entry.getKey()+"="+entry.getValue()+"&");
                 }
        return result.substring(0,result.length()-1);

    }
}
