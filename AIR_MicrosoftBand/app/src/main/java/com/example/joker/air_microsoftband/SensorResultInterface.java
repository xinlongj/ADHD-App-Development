package com.example.joker.air_microsoftband;

import java.io.File;

/**
 * Created by joker on 4/21/16(112).
 */
public interface SensorResultInterface {
    public void onStatusListener(String s);
    public void onAccelerometerListener(String s);
    public void onGyroscopeListener(String s);
    public void onDistanceListener(String s);
    public void onHeartRateListener(String s);
    public void onPedometerListener(String s);
    public void onSkinTemperatureListener(String s);
    public void onUltraVioletListener(String s);
    public void onBandContactListener(String s);
    public void onCaloriesListener(String s);
}