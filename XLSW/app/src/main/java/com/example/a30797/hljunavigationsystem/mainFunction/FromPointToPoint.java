package com.example.a30797.hljunavigationsystem.mainFunction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a30797.hljunavigationsystem.R;
import com.example.a30797.hljunavigationsystem.schoolgate.MyGraph;

import java.sql.BatchUpdateException;

public class FromPointToPoint extends Activity {
    
    final String[] items=new String[]{
            "南门",
            "艺术学院（音乐舞蹈楼）",
            "知行会堂",
            "知行楼",
            "海洋学院",
            "商学院",
            "外籍专家公寓（快递收发点）",
            "外卖点",
            "浴池",
            "荟园餐厅",
            "馨园餐厅",
            "泰园餐厅",
            "雀园餐厅(大学生活动中心)",
            "法学院",
            "图书馆",
            "数学与统计学院",
            "玲珑学堂",
            "电子楼（联合理学院）",
            "文学楼（文化传播学院）",
            "机电与信息工程学院",
            "科学实验楼",
            "闻外楼（翻译学院、马列部）",
            "田径场",
            "东北亚学院",
            "体育馆",
            "游泳馆",
            "闻天楼（空间科学与物理学院）",
            "网络楼",
            "天文台",
    };
    
    private int arrayIndexFrom=0,arrayIndexTo=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fp2p);

        final TextView textView=findViewById(R.id.text1);
        final TextView textView2=findViewById(R.id.text2);

        Button button=findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(FromPointToPoint.this);
                builder.setTitle("选择出发点");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        arrayIndexFrom=which+1;
                        textView.setText(items[which]);
                    }
                });
                builder.create().show();
            }
        });

        Button button2=findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(FromPointToPoint.this);
                builder.setTitle("选择目的地");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        arrayIndexTo=which+1;
                        textView2.setText(items[which]);
                    }
                });
                builder.create().show();
            }
        });

        Button button3=findViewById(R.id.button3);//步行寻路
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayIndexFrom==arrayIndexTo){
                    Toast.makeText(FromPointToPoint.this,"出发点与目的地不能相同",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent=new Intent();
                //intent.putExtra(A,B) AB键值对，A是键名，B是键值，用来传参。在另一个activity中调用getAExtra()获得
                //此处的传给了drawGraph
                intent.putExtra("indexFrom",MyGraph.points[arrayIndexFrom].index);
                intent.putExtra("indexTo",MyGraph.points[arrayIndexTo].index);
                intent.putExtra("walk",1);
                //设置回传数据，传给调用此activity的activity
                setResult(0x1,intent);
                finish();
            }
        });

        Button button5=findViewById(R.id.button5);//骑行寻路
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayIndexFrom==arrayIndexTo){
                    Toast.makeText(FromPointToPoint.this,"出发点与目的地不能相同",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent=new Intent();
                //intent.putExtra(A,B) AB键值对，A是键名，B是键值，用来传参。在另一个activity中调用getAExtra()获得
                //此处的传给了drawGraph
                intent.putExtra("indexFrom",MyGraph.points[arrayIndexFrom].index);
                intent.putExtra("indexTo",MyGraph.points[arrayIndexTo].index);
                intent.putExtra("walk",0);
                //设置回传数据，传给调用此activity的activity
                setResult(0x1,intent);
                finish();
            }
        });

        Button button4=findViewById(R.id.button4);//取消
        //startActivityForResult( )
        //可以一次性完成这项任务，当程序执行到这段代码的时候，假若从T1Activity跳转到下一个Text2Activity，
        // 而当这个 Text2Activity调用了finish()方法以后，程序会自动跳转回T1Activity，
        // 并调用前一个T1Activity中的 onActivityResult( )方法。
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
