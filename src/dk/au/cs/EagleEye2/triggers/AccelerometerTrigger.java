package dk.au.cs.EagleEye2.triggers;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class AccelerometerTrigger extends Trigger implements SensorEventListener {
  public AccelerometerTrigger() {

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

  @Override
  public void onSensorChanged(Sensor sensor, int accuracy, long timestamp, float[] values) {
    fireTriggers(null);
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int i) {

  }
}
