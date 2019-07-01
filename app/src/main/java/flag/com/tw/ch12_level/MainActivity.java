package flag.com.tw.ch12_level;

import androidx.appcompat.app.AppCompatActivity;


import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sm;
    Sensor sr;
    TextView txvInfo;
    ImageView igv;
    RelativeLayout layout;
    double mx = 0, my = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        sr = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        txvInfo = (TextView) findViewById(R.id.txvInfo);
        igv = (ImageView) findViewById(R.id.igvMove);
        layout = (RelativeLayout) findViewById(R.id.layout);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(mx == 0){
            mx = (layout.getWidth() - igv.getWidth()) / 20.0;
            my = (layout.getHeight() - igv.getHeight()) / 20.0;
        }

        RelativeLayout.LayoutParams parms = (RelativeLayout.LayoutParams) igv.getLayoutParams();

        parms.leftMargin = (int) ((10 - sensorEvent.values[0]) * mx);
        parms.topMargin = (int) ((10 - sensorEvent.values[1]) * my);

        igv.setLayoutParams(parms);

        txvInfo.setText(String.format("X軸： %1.2f, Y軸：%1.2f, Z軸：%1.2f", sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume(){
        super.onResume();
        sm.registerListener(this, sr, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }
}
