package dk.au.cs.EagleEye2.triggers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class AccelerometerTrigger extends DistanceTrigger implements SensorEventListener {
  private Context context;
  private SensorManager mSensorManager;
  private Sensor mAccelerometer;
  private LocationManager locationManager;

  private float minThresholdMovement;
  private long lastTimestamp, timeThreshold;
  private boolean running = false;
  private int movementTicks, locationTicks, acceptedLocationTicks;

  public AccelerometerTrigger(float minThresholdMovement, int timeThreshold, int thresholdInMeters, Context context) {
    super(thresholdInMeters, context);

    this.minThresholdMovement = minThresholdMovement;
    this.context = context;

    this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    this.timeThreshold = timeThreshold*1000000000;
    this.lastTimestamp = 0;
  }

  public void startRegistering() {
    movementTicks = locationTicks = acceptedLocationTicks = 0;

    mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    running = false;
  }

  public void stopRegistering() {
    int movementLocationDifference = movementTicks-locationTicks;
    int acceptedLocationDifference = locationTicks-acceptedLocationTicks;

    Log.w("EagleEye", "MovementTicks:" + movementTicks + ", locations:" + locationTicks + ", acceptedLocationCount: " + acceptedLocationTicks +
      ", movements-locations:" + movementLocationDifference + ", locations-acceptedLocations: " + acceptedLocationDifference);

    mSensorManager.unregisterListener(this);
    locationManager.removeUpdates(this);
    running = false;
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
    if (timestamp < lastTimestamp) return;

    float x = values[0];
    float y = values[1];
    float z = values[2];

    float gravity = 9.81f;
    float movementChange = (float) Math.sqrt(x*x+y*y+z*z)-gravity;
    Log.w("EagleEye", "dMov" + movementChange);


    if (minThresholdMovement <= movementChange) {
      Log.w("EagleEye", "Movent accepted at " + x + " " + y + " " + z);
      lastTimestamp = timestamp+timeThreshold;

      if (!running)
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

      running = true;
    } else if (running) {
      Log.w("EagleEye", "Stop running");
      running = false;
      lastTimestamp = timestamp+timeThreshold;
    }
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int i) {
    // Ignore
  }

  @Override
  public boolean locationUpdated(float distanceInMeters, Location newLocation, Location lastLocation) {
    if (!running) locationManager.removeUpdates(this);

    return super.locationUpdated(distanceInMeters, newLocation, lastLocation);
  }
}
