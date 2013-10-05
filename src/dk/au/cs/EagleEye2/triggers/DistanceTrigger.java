package dk.au.cs.EagleEye2.triggers;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class DistanceTrigger extends Trigger implements LocationListener {
  // Contains the last location that we triggered on (ie. the one we want to know if we're 50 meters away from.)
  private int thresholdInMeters;
  private LocationManager locationManager;
  private Context context;
  private int tickCount, locationCount;

  public DistanceTrigger(int thresholdInMeters, Context context) {
    this.thresholdInMeters = thresholdInMeters;
    this.context = context;

    locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
  }

  @Override
  public void startRegistering() {
    // From the slides
    // http://developer.android.com/reference/android/location/LocationManager.html#requestLocationUpdates(java.lang.String, long, float, android.location.LocationListener)
    //
    tickCount = 0;
    locationCount = 0;

    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
  }

  @Override
  public void stopRegistering() {
    Log.w("EagleEye", "Ticks: " + tickCount + " locations: " + locationCount + " difference: " + (tickCount-locationCount));
    locationManager.removeUpdates(this);
  }

  public void locationUpdated(float distanceInMeters, Location newLocation, Location lastLocation) {
    // Wrapper for easy testability
    // this should decide if our event should be fired and then call fireTriggers

    Log.w("EagleEye", "distanceInMeters: " + distanceInMeters + " ticks: " + tickCount);

    if(thresholdInMeters <= distanceInMeters){
      locationCount++;
      Log.w("EagleEye", "Locations: " + locationCount);

      fireTriggers(newLocation);
    }
  }
}
