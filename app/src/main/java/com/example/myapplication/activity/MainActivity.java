package com.example.myapplication.activity;

public class MainActivity {


//    @BindView(R.id.nav) BottomNavigationBar mNav;
//    @BindView(R.id.mapview) MapView mapView;
//    private LocationManager mLocationManager;
//    private OnLocationChangedListener listener;
//
//    @Override
//    public int getLayoutID() {
//        return R.layout.activity_main;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mapView.onCreate(savedInstanceState);// 此方法必须重写
//
//
//    }
//
//    @Override
//    public void initView() {
//        BadgeItem badgeItem = new BadgeItem();
////        AMap aMap = mapView.getMap();
////        UiSettings mUiSettings = aMap.getUiSettings();//实例化UiSettings类
////        aMap.setTrafficEnabled(true);// 显示实时交通状况
////        mUiSettings.setCompassEnabled(true);
////        mUiSettings.setScaleControlsEnabled(true);//显示比例尺控件
////        aMap.setLocationSource(this);// 设置定位监听
////        mUiSettings.setMyLocationButtonEnabled(true); // 显示默认的定位按钮
////        aMap.setMyLocationEnabled(true);// 可触发定位并显示定位层
////        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
////        aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 卫星地图模式
////设置BottomNavigationBar的模式，会在下面详细讲解
//        mNav.setMode(BottomNavigationBar.MODE_DEFAULT);
//        //设置BottomNavigationBar的背景风格，后面详细讲解
//        int drawables[] = {R.drawable.mine_press, R.drawable.home_press, R.mipmap.ic_launcher};
//        int drawables2[] = {R.drawable.mine_norm, R.drawable.home_norm, R.mipmap.ic_launcher};
//        String titles[] = {"首页", "发现", "音乐"};
//        BottomNavigationItem item;
//        for (int i = 0; i < 3; i++) {
//            item = new BottomNavigationItem(drawables[i], titles[i]).setInactiveIcon(getResources().getDrawable(drawables2[i]));
//            if (i==2){
//                item.setBadgeItem(badgeItem);
//            }
//            mNav.addItem(item);
//        }
//        mNav.setInActiveColor(darker_gray).setActiveColor(holo_green_dark)
//                .setFirstSelectedPosition(0)  //设置默认
//                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
//
//                .initialise();  //完事
//        badgeItem.setText("3");
//
//        badgeItem.setHideOnSelect(true);
////        mNav.setBackgroundColor(holo_red_dark);
//
//        mNav.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(int position) {
//                Toaster.show("" + position);
//            }
//
//            @Override
//            public void onTabUnselected(int position) {
//
//            }
//
//            @Override
//            public void onTabReselected(int position) {
//
//            }
//        });
//    }
//
//    @Override
//    public void activate(OnLocationChangedListener listener) {
//        this.listener = listener;
//        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        LocationProvider gpsProvider = mLocationManager.getProvider(LocationManager.GPS_PROVIDER);
//        if (gpsProvider != null) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            mLocationManager.requestLocationUpdates(gpsProvider.getName(), 0L, 10, this);
//        }
//
//        LocationProvider networkProvider = mLocationManager.getProvider(LocationManager.NETWORK_PROVIDER);
//
//        if (networkProvider != null) {
//            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 60 * 5, 0, this);
//        }
//    }
//
//    @Override
//    public void deactivate() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        mLocationManager.removeUpdates(this);
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//        if (listener != null) {
//            listener.onLocationChanged(location);
//        }
//    }
//
//    @Override
//    public void onStatusChanged(String s, int i, Bundle bundle) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String s) {
//
//    }
//
//    @Override
//    public void onProviderDisabled(String s) {
//
//    }
}
