package com.example.joker.adhd_tmt;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent bindservice = new Intent(this, SensorService.class);
        bindService(bindservice, SerConn, BIND_AUTO_CREATE);
        initView();
        updateNumber();
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
        btStart = (Button) findViewById(R.id.bt_start);
        btStop = (Button) findViewById(R.id.bt_stop);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvError = (TextView) findViewById(R.id.tv_error);
        tvAcc = (TextView) findViewById(R.id.tv_acc);

        bt0  = (Button) findViewById(R.id.bt_0);
        bt1  = (Button) findViewById(R.id.bt_1);
        bt2  = (Button) findViewById(R.id.bt_2);
        bt3  = (Button) findViewById(R.id.bt_3);
        bt4  = (Button) findViewById(R.id.bt_4);
        bt5  = (Button) findViewById(R.id.bt_5);
        bt6  = (Button) findViewById(R.id.bt_6);
        bt7  = (Button) findViewById(R.id.bt_7);
        bt8  = (Button) findViewById(R.id.bt_8);
        bt9  = (Button) findViewById(R.id.bt_9);
        bt10 = (Button) findViewById(R.id.bt_10);
        bt11 = (Button) findViewById(R.id.bt_11);
        bt12 = (Button) findViewById(R.id.bt_12);
        bt13 = (Button) findViewById(R.id.bt_13);
        bt14 = (Button) findViewById(R.id.bt_14);
        bt15 = (Button) findViewById(R.id.bt_15);

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNumber();
                startTime = System.currentTimeMillis();
                curNumber = 1;
                errorNumber = 0;
                isOn = true;
                sensorService.startSensors();
            }
        });

        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOn = false;
                sensorService.stopSensors();
            }
        });


        bt0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOn) {
                    Toast.makeText(getApplicationContext(), "请先点击'开始'", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(bt0.getText().toString()) != curNumber) {
                    Toast.makeText(getApplicationContext(), "该点击：" + curNumber, Toast.LENGTH_SHORT).show();
                    errorNumber ++;
                    tvError.setText("错误" + errorNumber + "次");
                } else {
                    curNumber++;
                }
                if(curNumber > 16) {
                    curTestStop();
                }
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOn) {
                    Toast.makeText(getApplicationContext(), "请先点击'开始'", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(bt1.getText().toString()) != curNumber) {
                    Toast.makeText(getApplicationContext(), "该点击：" + curNumber, Toast.LENGTH_SHORT).show();
                    errorNumber ++;
                    tvError.setText("错误" + errorNumber + "次");
                } else {
                    curNumber++;
                }
                if(curNumber > 16) {
                    curTestStop();
                }
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOn) {
                    Toast.makeText(getApplicationContext(), "请先点击'开始'", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(bt2.getText().toString()) != curNumber) {
                    Toast.makeText(getApplicationContext(), "该点击：" + curNumber, Toast.LENGTH_SHORT).show();
                    errorNumber ++;
                    tvError.setText("错误" + errorNumber + "次");
                } else {
                    curNumber++;
                }
                if(curNumber > 16) {
                    curTestStop();
                }
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOn) {
                    Toast.makeText(getApplicationContext(), "请先点击'开始'", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(bt3.getText().toString()) != curNumber) {
                    Toast.makeText(getApplicationContext(), "该点击：" + curNumber, Toast.LENGTH_SHORT).show();
                    errorNumber ++;
                    tvError.setText("错误" + errorNumber + "次");
                } else {
                    curNumber++;
                }
                if(curNumber > 16) {
                    curTestStop();
                }
            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOn) {
                    Toast.makeText(getApplicationContext(), "请先点击'开始'", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(bt4.getText().toString()) != curNumber) {
                    Toast.makeText(getApplicationContext(), "该点击：" + curNumber, Toast.LENGTH_SHORT).show();
                    errorNumber ++;
                    tvError.setText("错误" + errorNumber + "次");
                } else {
                    curNumber++;
                }
                if(curNumber > 16) {
                    curTestStop();
                }
            }
        });

        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOn) {
                    Toast.makeText(getApplicationContext(), "请先点击'开始'", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(bt5.getText().toString()) != curNumber) {
                    Toast.makeText(getApplicationContext(), "该点击：" + curNumber, Toast.LENGTH_SHORT).show();
                    errorNumber ++;
                    tvError.setText("错误" + errorNumber + "次");
                } else {
                    curNumber++;
                }
                if(curNumber > 16) {
                    curTestStop();
                }
            }
        });

        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOn) {
                    Toast.makeText(getApplicationContext(), "请先点击'开始'", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(bt6.getText().toString()) != curNumber) {
                    Toast.makeText(getApplicationContext(), "该点击：" + curNumber, Toast.LENGTH_SHORT).show();
                    errorNumber ++;
                    tvError.setText("错误" + errorNumber + "次");
                } else {
                    curNumber++;
                }
                if(curNumber > 16) {
                    curTestStop();
                }
            }
        });

        bt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOn) {
                    Toast.makeText(getApplicationContext(), "请先点击'开始'", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(bt7.getText().toString()) != curNumber) {
                    Toast.makeText(getApplicationContext(), "该点击：" + curNumber, Toast.LENGTH_SHORT).show();
                    errorNumber ++;
                    tvError.setText("错误" + errorNumber + "次");
                } else {
                    curNumber++;
                }
                if(curNumber > 16) {
                    curTestStop();
                }
            }
        });

        bt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOn) {
                    Toast.makeText(getApplicationContext(), "请先点击'开始'", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(bt8.getText().toString()) != curNumber) {
                    Toast.makeText(getApplicationContext(), "该点击：" + curNumber, Toast.LENGTH_SHORT).show();
                    errorNumber ++;
                    tvError.setText("错误" + errorNumber + "次");
                } else {
                    curNumber++;
                }
                if(curNumber > 16) {
                    curTestStop();
                }
            }
        });

        bt9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOn) {
                    Toast.makeText(getApplicationContext(), "请先点击'开始'", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(bt9.getText().toString()) != curNumber) {
                    Toast.makeText(getApplicationContext(), "该点击：" + curNumber, Toast.LENGTH_SHORT).show();
                    errorNumber ++;
                    tvError.setText("错误" + errorNumber + "次");
                } else {
                    curNumber++;
                }
                if(curNumber > 16) {
                    curTestStop();
                }
            }
        });

        bt10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOn) {
                    Toast.makeText(getApplicationContext(), "请先点击'开始'", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(bt10.getText().toString()) != curNumber) {
                    Toast.makeText(getApplicationContext(), "该点击：" + curNumber, Toast.LENGTH_SHORT).show();
                    errorNumber ++;
                    tvError.setText("错误" + errorNumber + "次");
                } else {
                    curNumber++;
                }
                if(curNumber > 16) {
                    curTestStop();
                }
            }
        });

        bt11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOn) {
                    Toast.makeText(getApplicationContext(), "请先点击'开始'", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(bt11.getText().toString()) != curNumber) {
                    Toast.makeText(getApplicationContext(), "该点击：" + curNumber, Toast.LENGTH_SHORT).show();
                    errorNumber ++;
                    tvError.setText("错误" + errorNumber + "次");
                } else {
                    curNumber++;
                }
                if(curNumber > 16) {
                    curTestStop();
                }
            }
        });

        bt12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOn) {
                    Toast.makeText(getApplicationContext(), "请先点击'开始'", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(bt12.getText().toString()) != curNumber) {
                    Toast.makeText(getApplicationContext(), "该点击：" + curNumber, Toast.LENGTH_SHORT).show();
                    errorNumber ++;
                    tvError.setText("错误" + errorNumber + "次");
                } else {
                    curNumber++;
                }
                if(curNumber > 16) {
                    curTestStop();
                }
            }
        });

        bt13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOn) {
                    Toast.makeText(getApplicationContext(), "请先点击'开始'", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(bt13.getText().toString()) != curNumber) {
                    Toast.makeText(getApplicationContext(), "该点击：" + curNumber, Toast.LENGTH_SHORT).show();
                    errorNumber ++;
                    tvError.setText("错误" + errorNumber + "次");
                } else {
                    curNumber++;
                }
                if(curNumber > 16) {
                    curTestStop();
                }
            }
        });

        bt14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOn) {
                    Toast.makeText(getApplicationContext(), "请先点击'开始'", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(bt14.getText().toString()) != curNumber) {
                    Toast.makeText(getApplicationContext(), "该点击：" + curNumber, Toast.LENGTH_SHORT).show();
                    errorNumber ++;
                    tvError.setText("错误" + errorNumber + "次");
                } else {
                    curNumber++;
                }
                if(curNumber > 16) {
                    curTestStop();
                }
            }
        });

        bt15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOn) {
                    Toast.makeText(getApplicationContext(), "请先点击'开始'", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(bt15.getText().toString()) != curNumber) {
                    Toast.makeText(getApplicationContext(), "该点击：" + curNumber, Toast.LENGTH_SHORT).show();
                    errorNumber ++;
                    tvError.setText("错误" + errorNumber + "次");
                } else {
                    curNumber++;
                }
                if(curNumber > 16) {
                    curTestStop();
                }
            }
        });
    }

    private void curTestStop(){
        Toast.makeText(getApplicationContext(), "结束", Toast.LENGTH_SHORT).show();
        tvTime.setText("共用时:" + (System.currentTimeMillis() - startTime) / 1000 + "秒");
        sensorService.stopSensors();
        isOn = false;
    }

    private void updateNumber() {
        if(numbers == null) {
            numbers = new int[16];
            for(int i = 0; i < numbers.length; i++){
                numbers[i] = i + 1;
            }
        }

        Random random = new Random();
        for(int i = 0; i < numbers.length; i++){
            int p = random.nextInt(numbers.length);
            int tmp = numbers[i];
            numbers[i] = numbers[p];
            numbers[p] = tmp;
        }
        random = null;

        bt0.setText(numbers[0] + "");
        bt1.setText(numbers[1] + "");
        bt2.setText(numbers[2] + "");
        bt3.setText(numbers[3] + "");
        bt4.setText(numbers[4] + "");
        bt5.setText(numbers[5] + "");
        bt6.setText(numbers[6] + "");
        bt7.setText(numbers[7] + "");
        bt8.setText(numbers[8] + "");
        bt9.setText(numbers[9] + "");
        bt10.setText(numbers[10] + "");
        bt11.setText(numbers[11] + "");
        bt12.setText(numbers[12] + "");
        bt13.setText(numbers[13] + "");
        bt14.setText(numbers[14] + "");
        bt15.setText(numbers[15] + "");
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
                        tvTime.setText("已用时:" + (System.currentTimeMillis() - startTime) / 1000 + "秒");
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

    private Button bt0;
    private Button bt1;
    private Button bt2;
    private Button bt3;
    private Button bt4;
    private Button bt5;
    private Button bt6;
    private Button bt7;
    private Button bt8;
    private Button bt9;
    private Button bt10;
    private Button bt11;
    private Button bt12;
    private Button bt13;
    private Button bt14;
    private Button bt15;

    private Button btStart;
    private Button btStop;
    private TextView tvTime;
    private TextView tvError;
    private TextView tvAcc;

    private int[] numbers = null;

    private int curNumber = 1;
    private long startTime;
    private int errorNumber = 0;
    private boolean isOn = false;
}