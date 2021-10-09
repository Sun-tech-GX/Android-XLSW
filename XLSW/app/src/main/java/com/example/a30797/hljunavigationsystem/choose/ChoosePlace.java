package com.example.a30797.hljunavigationsystem.choose;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.a30797.hljunavigationsystem.R;

public class ChoosePlace {
    private static String[] placeNames = {
            "南门", "艺术学院(音乐舞蹈楼)", "知行会堂", "知行楼", "海洋学院", "商学院", "外籍专家公寓(快递收发点)",
            "外卖点", "浴池", "荟园餐厅", "馨园餐厅", "雀园餐厅(大学生活动中心)", "泰园餐厅", "法学院", "图书馆", "数学与统计学院", "玲珑学堂",
            "电子楼(联合理学院)", "文学楼(文化传播学院)", "机电与信息工程学院", "科学实验楼", "闻外楼(翻译学院)", "马克思主义学院",
            "田径场", "东北亚学院", "体育馆", "游泳馆", "闻天楼(空间科学与物理学院)", "网络楼", "天文台",
    };
    /*
    使用AutoCompleteTextView进行动态匹配
    输入内容
     */
    public static AutoCompleteTextView setAutoCompleteTextView(AutoCompleteTextView autoCompleteTextView , Activity activity){
        //创建ArrayAdapter封装数组
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_list_item_multiple_choice,placeNames);

        autoCompleteTextView = (AutoCompleteTextView) activity.findViewById(R.id.auto);
        //设置adapter
        autoCompleteTextView.setAdapter(adapter);
        return autoCompleteTextView;
    }
}
