package com.edge.weather.weather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.edge.weather.R;

public class MainActivity extends AppCompatActivity implements LocationListener, View.OnClickListener,TaskContract.View {
    LocationManager locationManager;
    double latitude;
    double longitude;
    TextView latText, lonText, weatherTxt;
    Button button;
    TaskContract.Presenter mPresenter;
    TaskPresenter mTaskPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mTaskPresenter = new TaskPresenter(this);
    }

    private void initView() {
        //뷰세팅
        latText = (TextView) findViewById(R.id.latitude);
        lonText = (TextView) findViewById(R.id.longitude);
        weatherTxt = (TextView) findViewById(R.id.weather);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onLocationChanged(Location location) {
        /*현재 위치에서 위도경도 값을 받아온뒤 우리는 지속해서 위도 경도를 읽어올것이 아니니
        날씨 api에 위도경도 값을 넘겨주고 위치 정보 모니터링을 제거한다.*/
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        //위도 경도 텍스트뷰에 보여주기
        latText.setText(String.valueOf(latitude));
        lonText.setText(String.valueOf(longitude));
        //날씨 가져오기 통신'
        mPresenter.getWeather(latitude,longitude);
        //위치정보 모니터링 제거
        locationManager.removeUpdates(MainActivity.this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //버튼 클릭시 현재위치의 날씨를 가져온다
            case R.id.button:
                if (locationManager != null) {
                    requestLocation();
                }

                break;
        }
    }

    private void requestLocation() {
        //사용자로 부터 위치정보 권한체크
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 1, this);

        }
    }



    @Override
    public void showWeather(String weather) {
        weatherTxt.setText(weather);
    }

    @Override
    public void setPresenter(TaskContract.Presenter presenter) {
            mPresenter = presenter;
    }
}
