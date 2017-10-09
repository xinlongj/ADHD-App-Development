package com.example.joker.adhd_focus;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.support.v7.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Point;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.BIND_AUTO_CREATE;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent bindservice = new Intent(this, SensorService.class);
        bindService(bindservice, SerConn, BIND_AUTO_CREATE);
        initView();
    }

    @Override
    protected void onResume() {
        /*
        if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        */
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                    locustJump(values);
                    appendToUI(Constant.TYPE_ACC, "X:" + values[0] + "\nY:" + values[1] + "\nZ:" + values[2]);
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

        tvAcc = (TextView) findViewById(R.id.tv_acc);

        btStart = (Button) findViewById(R.id.bt_start);
        btStop = (Button) findViewById(R.id.bt_stop);
        btLocust = (Button) findViewById(R.id.bt_locust);

        btEasy = (Button) findViewById(R.id.bt_easy);
        btMiddle = (Button) findViewById(R.id.bt_mid);
        btHard = (Button) findViewById(R.id.bt_hard);

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorService.startSensors();
                num_locust = 0;
                isOn = true;
            }
        });
        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorService.stopSensors();
                isOn = false;
            }
        });
        btLocust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOn) {
                    Toast.makeText(getApplicationContext(), "已抓住" + (++num_locust) + "只 蚂蚱！！", Toast.LENGTH_SHORT).show();
                    btLocust.setX(screenSize.x * (float)Math.random() * 0.8f);
                    btLocust.setY(screenSize.y * (float)Math.random() * 0.8f);
                }
            }
        });

        btEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                THRES_HOLD = 1.3f;
            }
        });

        btMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                THRES_HOLD = 1.2f;
            }
        });

        btHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                THRES_HOLD = 1.1f;
            }
        });
    }

    private void locustJump(float[] values) {
        float x = values[0];
        float y = values[1];
        float z = values[2];
        float comp = (float)Math.sqrt(x * x + y * y + z * z);
        if(comp > THRES_HOLD) {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btLocust.setX(screenSize.x * (float)Math.random() * 0.8f);
                    btLocust.setY(screenSize.y * (float)Math.random() * 0.8f);
                }
            });
        }
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
                        tvAcc.setText(string);
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
    private Button btStart;
    private Button btStop;
    private Button btLocust;

    private Button btEasy;
    private Button btMiddle;
    private Button btHard;
    private TextView tvAcc;

    Point screenSize = new Point();
    private int num_locust = 0;

    private float THRES_HOLD = 1.5f;
    private boolean isOn = false;
}