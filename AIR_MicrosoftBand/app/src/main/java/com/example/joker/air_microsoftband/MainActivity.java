package com.example.joker.air_microsoftband;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.band.BandClient;
import com.microsoft.band.BandClientManager;
import com.microsoft.band.BandException;
import com.microsoft.band.BandIOException;
import com.microsoft.band.BandInfo;
import com.microsoft.band.ConnectionState;
import com.microsoft.band.UserConsent;
import com.microsoft.band.sensors.BandAccelerometerEvent;
import com.microsoft.band.sensors.BandAccelerometerEventListener;
import com.microsoft.band.sensors.BandCaloriesEvent;
import com.microsoft.band.sensors.BandCaloriesEventListener;
import com.microsoft.band.sensors.BandContactEvent;
import com.microsoft.band.sensors.BandContactEventListener;
import com.microsoft.band.sensors.BandDistanceEvent;
import com.microsoft.band.sensors.BandDistanceEventListener;
import com.microsoft.band.sensors.BandGyroscopeEvent;
import com.microsoft.band.sensors.BandGyroscopeEventListener;
import com.microsoft.band.sensors.BandHeartRateEvent;
import com.microsoft.band.sensors.BandHeartRateEventListener;
import com.microsoft.band.sensors.BandPedometerEvent;
import com.microsoft.band.sensors.BandPedometerEventListener;
import com.microsoft.band.sensors.BandSkinTemperatureEvent;
import com.microsoft.band.sensors.BandSkinTemperatureEventListener;
import com.microsoft.band.sensors.BandUVEvent;
import com.microsoft.band.sensors.BandUVEventListener;
import com.microsoft.band.sensors.HeartRateConsentListener;
import com.microsoft.band.sensors.SampleRate;

import java.io.File;
import java.io.FileOutputStream;

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
        super.onResume();
        txtStatus.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorService.stop();
    }

    // L
    private ServiceConnection SerConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            sensorService = ((SensorService.Sensor_Binder) service).getService();
            sensorService.setCallback(new SensorResultInterface() {

                @Override
                public void onStatusListener(String s) {
                    appendToUI(Constant.TYPE_STATUS, s);
                }

                @Override
                public void onAccelerometerListener(String s) {
                    appendToUI(Constant.TYPE_ACC, s);
                }

                @Override
                public void onGyroscopeListener(String s) {
                    appendToUI(Constant.TYPE_GYR, s);
                }

                @Override
                public void onDistanceListener(String s) {
                    appendToUI(Constant.TYPE_DIS, s);
                }

                @Override
                public void onHeartRateListener(String s) {
                    appendToUI(Constant.TYPE_HEART_RATE, s);
                }

                @Override
                public void onPedometerListener(String s) {
                    appendToUI(Constant.TYPE_PED, s);
                }

                @Override
                public void onSkinTemperatureListener(String s) {
                    appendToUI(Constant.TYPE_SKIN_TEMP, s);
                }

                @Override
                public void onUltraVioletListener(String s) {
                    appendToUI(Constant.TYPE_UV, s);
                }

                @Override
                public void onBandContactListener(String s) {
                    appendToUI(Constant.TYPE_BAND_CONTACT, s);
                }

                @Override
                public void onCaloriesListener(String s) {
                    appendToUI(Constant.TYPE_CAL, s);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void initView() {
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtDate = (EditText) findViewById(R.id.edtDate);
        edtActType = (EditText) findViewById(R.id.edtActType);

        txtStatus = (TextView) findViewById(R.id.txtStatus);
        txtAcc = (TextView) findViewById(R.id.txtAcc);
        txtGyr = (TextView) findViewById(R.id.txtGyr);
        txtDis = (TextView) findViewById(R.id.txtDis);
        txtHeartRate = (TextView) findViewById(R.id.txtHeartRate);
        txtPed = (TextView) findViewById(R.id.txtPed);
        txtSkinTemp = (TextView) findViewById(R.id.txtSkinTemp);
        txtUV = (TextView) findViewById(R.id.txtUV);
        txtBandContact = (TextView) findViewById(R.id.txtBandContact);
        txtCal = (TextView) findViewById(R.id.txtCal);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.username = edtUsername.getText().toString().replaceAll(" ","");
                Constant.date = edtDate.getText().toString().replaceAll(" ", "");
                Constant.actType = edtActType.getText().toString().replaceAll(" ","");

                if(Constant.checkEditText()) {
                    String filePath = Constant.createDataFolder();
                    if(filePath == null) {
                        appendToUI(Constant.TYPE_STATUS, "Cannot create data folder...");
                        return;
                    }
                    appendToUI(Constant.TYPE_STATUS, filePath);
                    sensorService.startSensors();
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot be ampty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnStop = (Button) findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToUI(Constant.TYPE_STATUS, "stop");
                sensorService.stopSensors();
            }
        });
    }

    private void appendToUI(int type, final String string) {
        switch (type) {
            case Constant.TYPE_STATUS:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtStatus.setText("[Status] " + string);
                    }
                });
                break;
            case Constant.TYPE_ACC:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtAcc.setText("[Accelerometer] " + string);
                    }
                });
                break;
            case Constant.TYPE_GYR:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtGyr.setText("[Gyroscope] " + string);
                    }
                });
                break;
            case Constant.TYPE_DIS:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtDis.setText("[Distance] " + string);
                    }
                });
                break;
            case Constant.TYPE_HEART_RATE:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtHeartRate.setText("[Heart Rate] " + string);
                    }
                });
                break;
            case Constant.TYPE_PED:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtPed.setText("[Pedometer] " + string);
                    }
                });
                break;
            case Constant.TYPE_SKIN_TEMP:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtSkinTemp.setText("[Skin Temp] " + string);
                    }
                });
                break;
            case Constant.TYPE_UV:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtUV.setText("[Ultra Violet] " + string);
                    }
                });
                break;
            case Constant.TYPE_BAND_CONTACT:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtBandContact.setText("[Contact] " + string);
                    }
                });
                break;
            case Constant.TYPE_CAL:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtCal.setText("[Calories] " + string);
                    }
                });
                break;
            default:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtStatus.setText(string);
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
    private Button btnStart;
    private Button btnStop;
    private EditText edtUsername;
    private EditText edtDate;
    private EditText edtActType;
    private TextView txtStatus;
    private TextView txtAcc;
    private TextView txtGyr;
    private TextView txtDis;
    private TextView txtHeartRate;
    private TextView txtPed;
    private TextView txtSkinTemp;
    private TextView txtUV;
    private TextView txtBandContact;
    private TextView txtCal;
}