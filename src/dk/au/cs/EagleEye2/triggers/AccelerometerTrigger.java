package dk.au.cs.EagleEye2.triggers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelerometerTrigger extends Trigger implements SensorEventListener {
  private float minThresholdMovement;
  private Context context;
  private SensorManager mSensorManager;
  private Sensor mAccelerometer;

  public AccelerometerTrigger(float minThresholdMovement, Context context) {
    this.minThresholdMovement = minThresholdMovement;
    this.context = context;
  }

  public void startRegistering() {
    mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
  }

  public void stopRegistering() {
    mSensorManager.unregisterListener(this);
  }

  /**
   * Due to the interface not being public, this method should never contain
   * any implementation, since testing will be impossible. In stead use the
   * override onSensorChanged(Sensor sensor, int accuracy, long timestamp, float[] values)
   * @param sensorEvent
   */
  @Override
  public void onSensorChanged(SensorEvent sensorEvent) {
       onSensorChanged(sensorEvent.sensor, sensorEvent.accuracy, sensorEvent.timestamp, sensorEvent.values);
  }

  /**
   * Due the private constructor in SensorEvent this wrapper is needed to be able to test.
   * The actual SensorEvent should be unwrapped and send to this method.
   */
  public void onSensorChanged(Sensor sensor, int accuracy, long timestamp, float[] values) {
    float x = values[0];
    float y = values[1];
    float z = values[2];

    if(minThresholdMovement <= x || minThresholdMovement <= y || minThresholdMovement <= z){
      fireTriggers(null);
    }
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int i) {
    // Ignore
  }
}
