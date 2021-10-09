package com.example.a30797.hljunavigationsystem.schoolgate;

import android.graphics.Color;
import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.a30797.hljunavigationsystem.R;

import java.util.ArrayList;
import java.util.List;

public class Draw {

    public static void drawLines(BaiduMap mBaiduMap, Point[] place) {//接收传入数组
        if (place.length > 1) {
            LatLng latLng;//将传入的点转换为 LatLng 类型 在传入 points 中
            List<LatLng> points = new ArrayList<LatLng>();
            ;//存储路径上的坐标点集合
            for (int i = 0; i < place.length; i++) {
                latLng = new LatLng(place[i].latitude, place[i].longitude);
                points.add(latLng);
            }
            OverlayOptions ooPolyline = new PolylineOptions().width(10)
                    .color(0xffff0000).points(points);//红色
            Polyline mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
        } else {
            new Exception("输入两个不同点");
        }
    }

    public static void drawLines(BaiduMap mBaiduMap, Edge[] edges) {//接收传入数组
        Point[] ps = new Point[2];
        for (Edge e : edges) {
            ps[0] = MyGraph.points[MyGraph.getArrayIndex(e.from)];
            ps[1] = MyGraph.points[MyGraph.getArrayIndex(e.to)];
            drawLines(mBaiduMap, ps);
        }
    }

    public static void drawPoints(BaiduMap mBaiduMap,Point[] points){
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.point);
        MarkerOptions option = new MarkerOptions()
                .icon(bitmap)
                .scaleX(0.5f)
                .scaleY(0.5f)
                .animateType(MarkerOptions.MarkerAnimateType.grow)
                .perspective(false);

        for(Point p:points){
            LatLng latLng=new LatLng(p.latitude,p.longitude);//设置经纬度位置
            option.position(latLng);
            Marker marker=(Marker)mBaiduMap.addOverlay(option);//设置marker
            Bundle bundle=new Bundle();
            bundle.putInt("index",p.index);//传递index的值
            marker.setExtraInfo(bundle);
        }
    }

}