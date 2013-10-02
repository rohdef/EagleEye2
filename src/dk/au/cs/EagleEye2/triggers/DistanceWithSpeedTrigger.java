package dk.au.cs.EagleEye2.triggers;

import android.content.Context;
import android.hardware.Sensor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class DistanceWithSpeedTrigger extends Trigger implements LocationListener {
  private int maxSpeedInMetersPerSecond, thresholdInMeters;
  private Context context;
  private Location lastLocation;
  private LocationManager locationManager;

  public DistanceWithSpeedTrigger(int maxSpeedInMetersPerSecond, int thresholdInMeters, Context context) {
    this.maxSpeedInMetersPerSecond = maxSpeedInMetersPerSecond;
    this.thresholdInMeters = thresholdInMeters;

    this.context = context;
    this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
  }


  @Override
  public void startRegistering() {
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    // Start timer
  }

  @Override
  public void stopRegistering() {
    locationManager.removeUpdates(this);
    // Kill timer
  }

  public void timerEvent() {
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
  }

  @Override
  public void onLocationChanged(Location location) {
    Log.w("EagleEye", "Location recieved");
    locationManager.removeUpdates(this);

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
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void onProviderEnabled(String provider) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void onProviderDisabled(String provider) {
    //To change body of implemented methods use File | Settings | File Templates.
  }
}
