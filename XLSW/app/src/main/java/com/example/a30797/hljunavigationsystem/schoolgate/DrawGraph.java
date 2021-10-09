package com.example.a30797.hljunavigationsystem.schoolgate;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.a30797.hljunavigationsystem.R;

public class DrawGraph extends Thread{

    private BaiduMap mBaiduMap;
    boolean flag;
    private BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.point);
    private int indexFrom,indexTo,walk;

    public DrawGraph(BaiduMap mBaiduMap,Intent twoPoints) {
        this.mBaiduMap = mBaiduMap;
        //接收FromPointToPoint传递的参数
        indexFrom=twoPoints.getIntExtra("indexFrom",0);
        indexTo=twoPoints.getIntExtra("indexTo",0);
        walk=twoPoints.getIntExtra("walk",0);//判断是 步行or骑行
        flag=false;
    }

    public DrawGraph(BaiduMap mBaiduMap,BDLocation location,int indexTo) {
        this.mBaiduMap = mBaiduMap;
        indexFrom=9999;
        this.indexTo=indexTo;
        flag=true;
        MyGraph.points[0].latitude=location.getLatitude();
        MyGraph.points[0].longitude=location.getLongitude();
    }

    @Override
    public void run() {
        mBaiduMap.clear();
        //获取两个点的地理位置坐标点（经纬度是存在points数组中的）
        Point point=MyGraph.points[MyGraph.getArrayIndex(indexFrom)];
        Point point2=MyGraph.points[MyGraph.getArrayIndex(indexTo)];
        LatLng latLng=new LatLng(point.latitude,point.longitude);
        LatLng latLng2=new LatLng(point2.latitude,point2.longitude);

        //建图
        MyGraph g=new MyGraph(indexFrom,indexTo);

        //计算步行最短路
        if(walk==1) {
            Point[] points = g.dijkstra(flag);

            //设定窗口位置
            double midLat = (latLng.latitude + latLng2.latitude) / 2;
            double midLng = (latLng.longitude + latLng2.longitude) / 2;
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(new LatLng(midLat, midLng))
                    .overlook(0)
                    .rotate(0)
                    .zoom((float) (Math.abs(point.longitude - point2.longitude) * (-190.35184285428295) + 19.455606162907486 + (-0.5684567308940842)));
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

            //绘制路径
            Draw.drawLines(mBaiduMap, points);

            //绘制两点
            MarkerOptions option = new MarkerOptions()
                    .icon(bitmap)
                    .scaleX(0.5f)
                    .scaleY(0.5f);
            option.position(latLng);
            mBaiduMap.addOverlay(option);
            option.position(latLng2);
            mBaiduMap.addOverlay(option);
        }
        //骑行最短路
        else if(walk==0){
            Point[] points = g.bikeDijkstra(flag);

            //设定窗口位置
            double midLat = (latLng.latitude + latLng2.latitude) / 2;
            double midLng = (latLng.longitude + latLng2.longitude) / 2;
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(new LatLng(midLat, midLng))
                    .overlook(0)
                    .rotate(0)
                    .zoom((float) (Math.abs(point.longitude - point2.longitude) * (-190.35184285428295) + 19.455606162907486 + (-0.5684567308940842)));
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

            //绘制路径
            Draw.drawLines(mBaiduMap, points);

            //绘制两点
            MarkerOptions option = new MarkerOptions()
                    .icon(bitmap)
                    .scaleX(0.5f)
                    .scaleY(0.5f);
            option.position(latLng);
            mBaiduMap.addOverlay(option);
            option.position(latLng2);
            mBaiduMap.addOverlay(option);
        }

    }
}
