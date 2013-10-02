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

public class AccelerometerTrigger extends Trigger implements SensorEventListener, LocationListener {
  private Context context;
  private SensorManager mSensorManager;
  private Sensor mAccelerometer;
  private LocationManager locationManager;
  private Location lastLocation;

  private float lastX, lastY, lastZ, minThresholdMovement;
  private long lastTimestamp, timeThreshold;
  private boolean running = false;
  private int thresholdInMeters;

  public AccelerometerTrigger(float minThresholdMovement, int timeThreshold, int thresholdInMeters, Context context) {
    this.thresholdInMeters = thresholdInMeters;
    this.minThresholdMovement = minThresholdMovement;
    this.context = context;

    this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    this.timeThreshold = timeThreshold*1000000000;
    this.lastTimestamp = 0;
  }

  public void startRegistering() {
    mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    running = false;
  }

  public void stopRegistering() {
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

    float differenceX = x-lastX;
    float differenceY = y-lastY;
    float differenceZ = z-lastZ;

    lastX = x;
    lastY = y;
    lastZ = z;

    if ((minThresholdMovement <= differenceX || minThresholdMovement <= differenceY || minThresholdMovement <= differenceZ) &&
            !running) {
      Log.w("EagleEye", "Movent accepted at " + x + " " + y + " " + z);
      running = true;
      lastTimestamp = timestamp+timeThreshold;

      locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
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
  public void onLocationChanged(Location location) {
    // Would enable us to add checks on accuracy, to prevent inaccurate early fixes due to a long down time for the gps.

    Log.w("EagleEye", "Location recieved");
    if (!running) locationManager.removeUpdates(this);

    float distance;

    if(lastLocation == null){
      lastLocation = location;
      distance = 0;
    }else{
      distance = lastLocation.distanceTo(location);
    }

    locationUpdated(distance, location, lastLocation);
  }

  public void locationUpdated(float distanceInMeters, Location newLocation, Location lastLocation) {
    // Wrapper for easy testability
    // this should decide if our event should be fired and then call fireTriggers

    Log.w("EagleEye", "distanceInMeters: " + distanceInMeters + ", newLocation: " + newLocation + ", lastLocation: " + lastLocation);

    if(thresholdInMeters <= distanceInMeters){
      fireTriggers(newLocation);

      this.lastLocation = newLocation;
    }
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {
    // Ignore
  }

  @Override
  public void onProviderEnabled(String provider) {
    // Ignore
  }

  @Override
  public void onProviderDisabled(String provider) {
    // Ignore
  }
}
