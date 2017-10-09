package com.example.joker.adhd_focus;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;

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

public class SensorService extends Service {

    private SensorResultInterface srInterface;
    public void setCallback(SensorResultInterface srInterface) {
        this.srInterface = srInterface;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return binder;
    }

    public class Sensor_Binder extends Binder {
        public SensorService getService() {
            return SensorService.this;
        }
    }

    public Sensor_Binder binder = new Sensor_Binder();

    public void onCreate() {

    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void startSensors() {
        new AccelerometerSubscriptionTask().execute();
    }

    public void stopSensors() {
        if(client != null) {
            try {
                client.getSensorManager().unregisterAccelerometerEventListener(mAccelerometerEventListener);
                client.getSensorManager().unregisterGyroscopeEventListener(mGyroscopeEventListener);
                client.getSensorManager().unregisterDistanceEventListener(mDistanceEventListener);
                client.getSensorManager().unregisterHeartRateEventListener(mHeartRateEventListener);
                client.getSensorManager().unregisterPedometerEventListener(mPedometerEventListener);
                client.getSensorManager().unregisterSkinTemperatureEventListener(mSkinTemperatureEventListener);
                client.getSensorManager().unregisterUVEventListener(mUVEventListener);
                client.getSensorManager().unregisterContactEventListener(mContactEventListener);
                client.getSensorManager().unregisterCaloriesEventListener(mCaloriesEventListener);
            } catch (BandIOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        stopSensors();
        stopSelf();
    }


    private class AccelerometerSubscriptionTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                if (getConnectedBandClient()) {
                    // srInterface.onStatusListener("Band is connected.\n");
                    client.getSensorManager().registerAccelerometerEventListener(mAccelerometerEventListener, SampleRate.MS128);
                    client.getSensorManager().registerGyroscopeEventListener(mGyroscopeEventListener, SampleRate.MS16);
                    client.getSensorManager().registerDistanceEventListener(mDistanceEventListener);
                    if (client.getSensorManager().getCurrentHeartRateConsent() == UserConsent.GRANTED) {
                        client.getSensorManager().registerHeartRateEventListener(mHeartRateEventListener);
                    } else {
                        // user has not consented yet, request it
                        // client.getSensorManager().requestHeartRateConsent(null, mHeartRateConsentListener);
                    }
                    client.getSensorManager().registerPedometerEventListener(mPedometerEventListener);
                    client.getSensorManager().registerSkinTemperatureEventListener(mSkinTemperatureEventListener);
                    client.getSensorManager().registerUVEventListener(mUVEventListener);
                    client.getSensorManager().registerContactEventListener(mContactEventListener);
                    client.getSensorManager().registerCaloriesEventListener(mCaloriesEventListener);

                } else {
                    srInterface.onStatusListener("Band isn't connected. Please make sure bluetooth is on and the band is in range.\n");
                }
            } catch (BandException e) {
                String exceptionMessage = "";
                switch (e.getErrorType()) {
                    case UNSUPPORTED_SDK_VERSION_ERROR:
                        exceptionMessage = "Microsoft Health BandService doesn't support your SDK Version. Please update to latest SDK.\n";
                        break;
                    case SERVICE_ERROR:
                        exceptionMessage = "Microsoft Health BandService is not available. Please make sure Microsoft Health is installed and that you have the correct permissions.\n";
                        break;
                    default:
                        exceptionMessage = "Unknown error occured: " + e.getMessage() + "\n";
                        break;
                }
                srInterface.onStatusListener(exceptionMessage);

            } catch (Exception e) {
                srInterface.onStatusListener(e.getMessage());
            }
            return null;
        }
    }

    private boolean getConnectedBandClient() throws InterruptedException, BandException {
        if (client == null) {
            BandInfo[] devices = BandClientManager.getInstance().getPairedBands();
            if (devices.length == 0) {
                srInterface.onStatusListener("Band isn't paired with your phone.\n");
                return false;
            }
            client = BandClientManager.getInstance().create(getBaseContext(), devices[0]);
        } else if (ConnectionState.CONNECTED == client.getConnectionState()) {
            return true;
        }

        // srInterface.onStatusListener("Band is connecting...\n");
        return ConnectionState.CONNECTED == client.connect().await();
    }

    /***
     * Create the listeners of all kinds of sensors
     * 1. Accelerometer:
     * 2. Gyroscope:
     * 3. Distance:
     * 4. Heart Rate:
     * 5. Pedometer:
     * 6. Skin Temperature:
     * 7. UV:
     * 8. Band Contact:
     * 9. Calories:
     */

    private BandAccelerometerEventListener mAccelerometerEventListener = new BandAccelerometerEventListener() {
        @Override
        public void onBandAccelerometerChanged(final BandAccelerometerEvent event) {
            if (event != null) {
                float[] values = new float[]{event.getAccelerationX(), event.getAccelerationY(), event.getAccelerationZ()};
                srInterface.onAccelerometerListener(values, System.currentTimeMillis());
            }
        }
    };

    private BandGyroscopeEventListener mGyroscopeEventListener = new BandGyroscopeEventListener() {
        @Override
        public void onBandGyroscopeChanged(final BandGyroscopeEvent event) {
            if (event != null) {
                srInterface.onGyroscopeListener(String.format(" X = %.3f \t Y = %.3f\t Z = %.3f", event.getAngularVelocityX(),
                        event.getAngularVelocityY(), event.getAngularVelocityZ()));
            }
        }
    };

    private BandDistanceEventListener mDistanceEventListener = new BandDistanceEventListener() {
        @Override
        public void onBandDistanceChanged(final BandDistanceEvent event) {
            if (event != null) {
                srInterface.onDistanceListener("CurrentMotion = " + event.getMotionType() + " Pace = " + event.getPace() + " Speed = " + event.getSpeed());
            }
        }
    };

    private BandHeartRateEventListener mHeartRateEventListener = new BandHeartRateEventListener() {
        @Override
        public void onBandHeartRateChanged(final BandHeartRateEvent event) {
            if (event != null) {
                srInterface.onHeartRateListener(event.getHeartRate() + "  " + event.getQuality());
            }
        }
    };

    private HeartRateConsentListener mHeartRateConsentListener = new HeartRateConsentListener() {
        @Override
        public void userAccepted(boolean b) {
            // handle user's heart rate consent decision
            if (b == true) {
                // Consent has been given, start HR sensor event listener
                try {
                    client.getSensorManager().registerHeartRateEventListener(mHeartRateEventListener);
                } catch (BandException e) {
                    srInterface.onStatusListener(e.getMessage());
                }
            } else {
                // Consent hasn't been given
                srInterface.onStatusListener(String.valueOf(b));
            }
        }
    };

    private BandPedometerEventListener mPedometerEventListener = new BandPedometerEventListener() {
        @Override
        public void onBandPedometerChanged(final BandPedometerEvent event) {
            // this is the number of steps taken since the band was last factory-reset
            if (event != null) {
                srInterface.onPedometerListener(event.getTotalSteps() + "");
            }
        }
    };

    private BandSkinTemperatureEventListener mSkinTemperatureEventListener = new BandSkinTemperatureEventListener() {
        @Override
        public void onBandSkinTemperatureChanged(final BandSkinTemperatureEvent event) {
            if (event != null) {
                srInterface.onSkinTemperatureListener(event.getTemperature() + "");
            }
        }
    };

    private BandUVEventListener mUVEventListener = new BandUVEventListener() {
        @Override
        public void onBandUVChanged(final BandUVEvent event) {
            if (event != null) {
                srInterface.onUltraVioletListener(event.getUVIndexLevel() + "");
            }
        }
    };

    private BandContactEventListener mContactEventListener = new BandContactEventListener() {
        @Override
        public void onBandContactChanged(final BandContactEvent event) {
            if (event != null) {
                srInterface.onBandContactListener(event.getContactState() + "");
            }
        }
    };

    private BandCaloriesEventListener mCaloriesEventListener = new BandCaloriesEventListener() {
        @Override
        public void onBandCaloriesChanged(final BandCaloriesEvent event) {
            if (event != null) {
                srInterface.onCaloriesListener(event.getCalories() + "");
            }
        }
    };

    private BandClient client = null;
}