package com.example.joker.adhd_pump;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bmDrop[0] = BitmapFactory.decodeResource(getResources(),
                R.drawable.drop1);
        bmDrop[1] = BitmapFactory.decodeResource(getResources(),
                R.drawable.drop2);
        bmDrop[2] = BitmapFactory.decodeResource(getResources(),
                R.drawable.drop3);
        bmBird[0] = BitmapFactory.decodeResource(getResources(),
                R.drawable.bird1);
        bmBird[1] = BitmapFactory.decodeResource(getResources(),
                R.drawable.bird2);
        bmCenterWater = BitmapFactory.decodeResource(getResources(),
                R.drawable.center_water);
        bmCenter = BitmapFactory.decodeResource(getResources(),
                R.drawable.center);
        bmBigBlock = BitmapFactory.decodeResource(getResources(),
                R.drawable.big_block);

        setContentView(R.layout.activity_main);
        Intent bindservice = new Intent(this, SensorService.class);
        bindService(bindservice, SerConn, BIND_AUTO_CREATE);
        initView();
    }

    // L
    private ServiceConnection SerConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            sensorService = ((SensorService.Sensor_Binder) service).getService();
            sensorService.setCallback(new SensorResultInterface() {

                @Override
                public void onStatusListener(String s) {
                }

                @Override
                public void onAccelerometerListener(float[] values, long timeMs) {

                    float angle = (float) Math.toDegrees(Math.atan2(values[1], values[2]));
                    Log.i("ssss", "y=" + values[1] + " z=" + values[2] + " angle=" + angle);
                    rotatePump(angle);
                }

                @Override
                public void onGyroscopeListener(String s) {
                }

                @Override
                public void onDistanceListener(String s) {
                }

                @Override
                public void onHeartRateListener(String s) {
                }

                @Override
                public void onPedometerListener(String s) {
                }

                @Override
                public void onSkinTemperatureListener(String s) {
                }

                @Override
                public void onUltraVioletListener(String s) {
                }

                @Override
                public void onBandContactListener(String s) {
                }

                @Override
                public void onCaloriesListener(String s) {

                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void initView() {
        // 获得屏幕大小
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(screenSize);
        ivDrop = (ImageView) findViewById(R.id.tv_drop);
        ivBird = (ImageView) findViewById(R.id.tv_bird);
        ivCenter = (ImageView) findViewById(R.id.tv_center);
        tvPercent = (TextView) findViewById(R.id.tv_percent);
        btStart = (Button) findViewById(R.id.bt_start);
        btStop = (Button) findViewById(R.id.bt_stop);
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorService.startSensors();
            }
        });

        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorService.stop();
            }
        });
    }

    private void updateDrop() {

    }

    private void rotatePump(final float angle) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(Math.abs(angle - curWaterAngle) < 10) {
                    ivCenter.setImageBitmap(bmCenterWater);
                    ivCenter.setRotation(curWaterAngle);
                    ivDrop.setImageBitmap(bmBigBlock);
                    ivBird.setImageBitmap(bmBird[(iBird++) % 2]);
                    tvPercent.setText((percent++) / 10 + "%");
                } else {
                    ivCenter.setImageBitmap(bmCenter);
                    ivCenter.setRotation(angle);
                    ivDrop.setImageBitmap(bmDrop[(int)(Math.random() * 3)]);
                    ivDrop.setRotation(curWaterAngle);
                }
                if(System.currentTimeMillis() > CUR_TIME_TARGET) {
                    curWaterAngle = (int)(Math.random() * -3) * 90;
                    CUR_TIME_TARGET = System.currentTimeMillis() + 1000 * (int)(Math.random() * 5 + 5);
                }
            }
        });
    }

    private void appendToUI(int type, final String string) {
        switch (type) {
            case Constant.TYPE_STATUS:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
                break;
            case Constant.TYPE_ACC:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
                break;
            case Constant.TYPE_GYR:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
                break;
            case Constant.TYPE_DIS:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
                break;
            case Constant.TYPE_HEART_RATE:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
                break;
            case Constant.TYPE_PED:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
                break;
            case Constant.TYPE_SKIN_TEMP:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
                break;
            case Constant.TYPE_UV:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
                break;
            case Constant.TYPE_BAND_CONTACT:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
                break;
            case Constant.TYPE_CAL:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
                break;
            default:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //按下键盘上返回按钮
        if(keyCode == KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(this)
                    .setTitle("Close?")
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            finish();
                        }
                    }).show();
            return true;
        }else{
            return super.onKeyDown(keyCode, event);
        }
    }

    private SensorService sensorService = null;

    private ImageView ivDrop;
    private ImageView ivBird;
    private ImageView ivCenter;
    private Button btStart;
    private Button btStop;
    private Bitmap[] bmDrop = new Bitmap[3];
    private Bitmap[] bmBird = new Bitmap[2];
    private Bitmap bmCenterWater;
    private Bitmap bmCenter;
    private Bitmap bmBigBlock;
    private TextView tvPercent;

    Point screenSize = new Point();
    private boolean isOn = false;
    private float curWaterAngle = -90;

    private int iBird = 0;
    private long CUR_TIME_TARGET = 0;
    private int percent = 0;
}