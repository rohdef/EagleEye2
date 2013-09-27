package dk.au.cs.EagleEye2.triggers;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class DistanceTrigger extends Trigger implements LocationListener {
  // Contains the last location that we triggered on (ie. the one we want to know if we're 50 meters away from.)
  private Location lastLocation;
  private int thresholdInMeters;

  public DistanceTrigger(int thresholdInMeters) {
    this.thresholdInMeters = thresholdInMeters;
  }

  public void startWatchingDistances() {
    // From the slides
    // http://developer.android.com/reference/android/location/LocationManager.html#requestLocationUpdates(java.lang.String, long, float, android.location.LocationListener)
    //

//    lm = (LocationManager)
//            context.getSystemService(Context.LOCATION_SERVI
//                    CE);
//    lm.requestLocationUpdates(LocationManager.GPS_PR
//            OVIDER, 0, 0, locationListener);
  }

  public void locationUpdated(float distanceInMeters, Location newLocation, Location lastLocation) {
    // Wrapper for easy testability
    // this should decide if our event should be fired and then call fireTriggers
  }

  @Override
  public void onLocationChanged(Location location) {
    // I think this is our magic method

    float distance = lastLocation.distanceTo(location);
    locationUpdated(distance, location, lastLocation);
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {
    // Don't think we need
  }

  @Override
  public void onProviderEnabled(String provider) {
    // Don't think we need
  }

  @Override
  public void onProviderDisabled(String provider) {
    // Don't think we need
  }
}
