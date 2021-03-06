package com.example.a30797.hljunavigationsystem.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationData;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.model.LatLng;
import com.example.a30797.hljunavigationsystem.animation.TopAniHandlerHide;
import com.example.a30797.hljunavigationsystem.animation.TopAniHandlerShow;
import com.example.a30797.hljunavigationsystem.choose.ChoosePlace;
import com.example.a30797.hljunavigationsystem.imageProcessing.ImageProcessing;
import com.example.a30797.hljunavigationsystem.position.LocationSetter;
import com.example.a30797.hljunavigationsystem.position.LocateAnimation;
import com.example.a30797.hljunavigationsystem.position.MyLocationListener;
import com.example.a30797.hljunavigationsystem.position.MyOrientationListener;
import com.example.a30797.hljunavigationsystem.position.ReloacateAnimationForAll;
import com.example.a30797.hljunavigationsystem.R;
import com.example.a30797.hljunavigationsystem.attractions.Attractions_ifo;
import com.example.a30797.hljunavigationsystem.attractions.Scenic;
import com.example.a30797.hljunavigationsystem.mainFunction.FromPointToPoint;
import com.example.a30797.hljunavigationsystem.schoolgate.BottomAniHandler;
import com.example.a30797.hljunavigationsystem.schoolgate.Draw;
import com.example.a30797.hljunavigationsystem.schoolgate.DrawGraph;
import com.example.a30797.hljunavigationsystem.schoolgate.MyGraph;
import com.example.a30797.hljunavigationsystem.schoolgate.Point;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.Arrays;


public class MainActivity extends BaseActivity {
    /**
     * ?????????????????????(x1, y1)???????????????????????????(x2, y2)
     */
    private float x1 = 0;
    private float x2 = 0;
    private float y1 = 0;
    private float y2 = 0;

    public static MainActivity mainActivity;
    //????????????
    public LocationSetter locationSetter;
    //??????????????????
    public MyOrientationListener myOrientationListener;

    private MapView mMapView = null;

    public BaiduMap mBaiduMap;

    private static AutoCompleteTextView autoCompleteTextView;

    LocationClient locationClient;

    private SensorManager manager;

    private MySensorEventListener listener;

    private Sensor magneticSensor, accelerometerSensor;

    public float[] values, r, gravity, geomagnetic;
    /**
     * ????????????
     */
    public View view;

    private FloatingActionsMenu floatingActionsMenu;

    public ImageButton buttonSearch;

    private float azimuth;

    public static boolean flag = false;

    private static boolean threadFlag = false;

    private boolean flag2=false;

    private static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if((Integer)msg.obj==0){
                String destination =MainActivity.autoCompleteTextView.getText().toString();//?????????????????????
                localPositionNavigation(MainActivity.mainActivity.mBaiduMap,
                        destination,MainActivity.mainActivity.locationSetter);
            }
        }
    };
    /**
     * ?????????????????????????????????????????????
    */
    private static Thread thread = new Thread(){//????????????????????????
        @Override                //??????????????????????????????
        public void run() {
            while (true){
                if(threadFlag == true){
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }Log.v("myTag","tsdfsdfdsfsdv");
                    Message message = handler.obtainMessage();
                    message.obj = 0;
                    handler.sendMessage(message);
                }else {

                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SDKInitializer.initialize(getApplicationContext());
        SDKInitializer.setCoordType(CoordType.BD09LL);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//??????layout_main

        //??????GPS??????
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        listener = new MySensorEventListener();
        //??????Sensor
        magneticSensor = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometerSensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //???????????????
        values = new float[3];//???????????????????????????
        gravity = new float[3];//????????????????????????????????????
        r = new float[9];//
        geomagnetic = new float[3];//?????????????????????????????????

        myOrientationListener = new MyOrientationListener(
                getApplicationContext());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mMapView  = findViewById(R.id.bmapView);
        Toast.makeText(MainActivity.this,mBaiduMap+"",Toast.LENGTH_SHORT).show();
        mBaiduMap = mMapView.getMap();

        locationSetter =new LocationSetter(mBaiduMap,azimuth);
        //?????????????????????
        initLocationOption();
        new LocateAnimation(locationSetter,mBaiduMap).start();
        floatingActionsMenu = findViewById(R.id.multiple_actions);
        autoCompleteTextView = findViewById(R.id.auto);
        //?????????????????????????????????
        autoCompleteTextView = ChoosePlace.setAutoCompleteTextView(autoCompleteTextView,this );
        mainActivity = MainActivity.this;

        /**
         *???????????????????????????????????????
         */
        final CardView cardView=findViewById(R.id.cardTop);
        final FloatingActionButton actionC = findViewById(R.id.action_c);
        actionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionsMenu.collapse();
                //????????????
                startActivityForResult(new Intent(MainActivity.this,FromPointToPoint.class),0x1);

                if(flag2) {
                    cardView.setClickable(false);
                    flag2 = false;
                    mBaiduMap.clear();
                    new TopAniHandlerHide(cardView).sendMessage();
                }
            }
        });

        final FloatingActionButton actionB = findViewById(R.id.action_b);//????????????
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionsMenu.collapse();
                //overlook -59.0 rotation 105.0 zoom 18.134295 lat 45.71454845994154 lng 126.62536759271781
                new ReloacateAnimationForAll(mBaiduMap,-59.0f,0,17.2f,37.538068,122.066675).start();
                mBaiduMap.clear();
                //Draw.drawLines(mBaiduMap,MyGraph.edges);
                Draw.drawPoints(mBaiduMap,Arrays.copyOfRange(MyGraph.points,1,30));//array??????[1,7)

                flag2=true;
                CardView cardView=findViewById(R.id.cardTop);
                cardView.setClickable(true);
                new TopAniHandlerShow(cardView).sendMessage();
            }
        });

        final FloatingActionButton actionA = findViewById(R.id.action_a);//????????????
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionsMenu.collapse();
                new ReloacateAnimationForAll(mBaiduMap,0,0,18.5f,mBaiduMap.getLocationData().latitude,mBaiduMap.getLocationData().longitude).start();

                if(flag2) {
                    cardView.setClickable(false);
                    flag2 = false;
                    mBaiduMap.clear();
                    new TopAniHandlerHide(cardView).sendMessage();
                }
            }
        });
        /*
        ??????????????????
         */
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                floatingActionsMenu.collapse();
                if(!flag)return;
                flag=false;
                mainActivity.findViewById(R.id.card).setVisibility(View.INVISIBLE);
                new ReloacateAnimationForAll(mBaiduMap,-59.0f,0,17.2f,37.538067,122.066675).start();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                if(!flag)return false;
                flag=false;
                mainActivity.findViewById(R.id.card).setVisibility(View.INVISIBLE);
                new ReloacateAnimationForAll(mBaiduMap,-59.0f,0,17.2f,37.538067,122.066675).start();
                return false;
            }
        });
        /*
        ????????????????????????
         */
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onMarkerClick(Marker marker){

                CardView cardView=mainActivity.findViewById(R.id.card);
                ScrollView scrollView = cardView.findViewById(R.id.attraction_scroll);
                scrollView.getLayoutParams().height = mainActivity.getWindowManager().getDefaultDisplay().getHeight()/3;
                ImageView imageView   = cardView.findViewById(R.id.attraction_image);
                TextView textView02   = cardView.findViewById(R.id.whole_ifo);
                TextView textView     = cardView.findViewById(R.id.attraction_name);
                imageView.getLayoutParams().width=imageView.getLayoutParams().height=mainActivity.getWindowManager().getDefaultDisplay().getWidth()/3;

                for (Scenic i:Attractions_ifo.Attractions_ifo) {
                    if (i.getAttractionsPoint().longitude == marker.getPosition().longitude &&
                            i.getAttractionsPoint().latitude == marker.getPosition().latitude) {

                        imageView.setImageBitmap(ImageProcessing.ChangeXY(i, mainActivity));
                        textView.setText("\n\n"+ i.getName() + "\n\n" );
                        textView02.setText(i.getIntroduce());
                        break;
                    }
                }
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                flag=true;
                new ReloacateAnimationForAll(mBaiduMap,0,105.0f,18.5f,marker.getPosition().latitude-0.0003882285675,marker.getPosition().longitude+0.0025).start();
                findViewById(R.id.card).setVisibility(View.VISIBLE);
                return true;
            }
        });

        /*
        ????????????
         */
        final ImageButton buttonSearch = findViewById(R.id.search);
        //??????????????????
        Bitmap bitmap01 = BitmapFactory.decodeResource(mainActivity.getResources(),R.drawable.search);
        bitmap01 = ImageProcessing.ChangBitmapSize(bitmap01,10,10);
        buttonSearch.setImageBitmap(bitmap01);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag2) {
                    cardView.setClickable(false);
                    flag2 = false;
                    mBaiduMap.clear();
                    new TopAniHandlerHide(cardView).sendMessage();
                }

                CardView cardView=findViewById(R.id.navigation_button);
                ((TextView)findViewById(R.id.navigation_textTop)).setText("??????????????????");
                new TopAniHandlerShow(cardView).sendMessage();
                cardView.setClickable(true);

                String destination = autoCompleteTextView.getText().toString();//?????????????????????
                localPositionNavigation(mBaiduMap,destination,locationSetter);//??????????????????????????????
                if(threadFlag){
                    threadFlag = false;
                }
            }
        });
        /*
        ??????????????????????????????
         */
        CardView navigationButton = MainActivity.mainActivity.findViewById(R.id.navigation_button);
        navigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!threadFlag){
                    CardView cardView=findViewById(R.id.navigation_button);
                    ((TextView)findViewById(R.id.navigation_textTop)).setText("??????????????????");
                    cardView.setClickable(true);
                    threadFlag = true;
                    thread.start();
                    Toast.makeText(MainActivity.mainActivity,"????????????????????????",Toast.LENGTH_SHORT).show();
                }else {
                    CardView cardView=findViewById(R.id.navigation_button);
                    cardView.setClickable(false);
                    new TopAniHandlerHide(cardView).sendMessage();
                    threadFlag = false;
                    Toast.makeText(MainActivity.mainActivity,"????????????????????????",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //????????????????????????????????????????????????????????????
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setClickable(false);
                flag2=false;
                mBaiduMap.clear();
                new TopAniHandlerHide(cardView).sendMessage();
                findViewById(R.id.card).setVisibility(View.INVISIBLE);
            }
        });

        Toast.makeText(MainActivity.this,"?????????????????????GPS",Toast.LENGTH_SHORT).show();
    }

    /**
     * ????????????????????????
     */
    private void initLocationOption() {
        //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        locationClient = new LocationClient(getApplicationContext());
        //??????LocationClient??????????????????????????????
        MyLocationListener myLocationListener = new MyLocationListener(locationSetter,mBaiduMap,locationClient);
        //??????????????????
        locationClient.registerLocationListener(myLocationListener);
        LocationClientOption locationOption = new LocationClientOption();
        //???????????????gcj02??????????????????????????????????????????????????????????????????????????????????????????bd09ll;
        locationOption.setCoorType("bd09ll");
        locationOption.setOpenGps(true);
        locationOption.setLocationNotify(true);
        locationClient.setLocOption(locationOption);
        //????????????
        locationClient.start();
    }

    /**
     * ?????????????????????????????????
     * @param requestCode
     * @param resultCode
     * @param twoPoints
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent twoPoints){
        if(requestCode!=resultCode) return;
        DrawGraph drawGraph=new DrawGraph(mBaiduMap,twoPoints);
        drawGraph.start();

        while(drawGraph.isAlive()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        CardView cardView=findViewById(R.id.distance_cardView);
        TextView textView=findViewById(R.id.text);
        BottomAniHandler handler=new BottomAniHandler(cardView,textView);
        handler.sendMessage();
    }

    private class MySensorEventListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                geomagnetic = event.values;
            }
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                gravity = event.values;
                getValue();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(listener, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
        manager.registerListener(listener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        manager.unregisterListener(listener);
        super.onPause();
    }

    public void getValue() {
        // r???????????????
        SensorManager.getRotationMatrix(r, null, gravity, geomagnetic);
        //values???????????????
        SensorManager.getOrientation(r, values);
        //????????????
        azimuth = (float) Math.toDegrees(values[0]);
        if (azimuth<0) {
            azimuth=azimuth+360;
        }
        double pitch = Math.toDegrees(values[1]);
        double roll = Math.toDegrees(values[2]);
        locationSetter.setMx(values[0]);
        Log.v("qwe",String.valueOf(azimuth));
        locationSetter.setMx(azimuth);

        try {
            setMapLocationData();
        }catch (Exception e){
            new Thread(){
                @Override
                public void run() {
                    try {
                        double latitude = mBaiduMap.getLocationData().latitude;//?????????????????????????????????????????? ????????????
                    }catch (Exception e){
                        initLocationOption();
                        try {
                            setMapLocationData();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }

    /**
     * ??????????????????
     * @throws Exception
     */
    private void setMapLocationData() throws Exception{
        // ??????????????????
        mBaiduMap.setMyLocationEnabled(true);
        // ??????????????????
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(50)
                // ?????????????????????????????????????????????????????????0-360
                .direction(azimuth)
                .latitude(mBaiduMap.getLocationData().latitude)
                .longitude(mBaiduMap.getLocationData().longitude).build();
        // ??????????????????
        mBaiduMap.setMyLocationData(locData);
        throw new Exception();
    }

    /*
    ??????????????????????????????
     */
    private static void localPositionNavigation(BaiduMap mBaiduMap, String destination ,
                                                LocationSetter locationSetter){
        InputMethodManager imm = (InputMethodManager) MainActivity.mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(MainActivity.mainActivity.getWindow().getDecorView().getWindowToken(), 0);

        int endPlace = 0;
        //?????? destination ??????
        for (int i = 0; i < MyGraph.points.length ; i++){
            if (destination.equals(MyGraph.points[i].name) ){
                endPlace = MyGraph.points[i].index;
            }
        }
        DrawGraph drawGraph = new DrawGraph(mBaiduMap,locationSetter.getLocation(),endPlace);
        drawGraph.start();
        while(drawGraph.isAlive()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        CardView cardView=mainActivity.findViewById(R.id.distance_cardView);
        TextView textView=mainActivity.findViewById(R.id.text);
        BottomAniHandler handler=new BottomAniHandler(cardView,textView);
        handler.sendMessage();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // Toast.makeText(MainActivity.this, "slipe", Toast.LENGTH_SHORT).show();
        // ?????????Activity???onTouchEvent?????????????????????????????????
        switch (ev.getAction() ) {
            case MotionEvent.ACTION_DOWN:
                //????????????????????????
                x1 = ev.getX();
                y1 = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                //????????????????????????
                x2 = ev.getX();
                y2 = ev.getY();
                if(y1 - y2 > 50) {
                    floatingActionsMenu.animate().translationY(-300).setDuration(500);
                } else if(y2 - y1 > 50) {
                    floatingActionsMenu.animate().translationY(0).setDuration(500);
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
