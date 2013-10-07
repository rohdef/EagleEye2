package dk.au.cs.EagleEye2.triggers;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

public class DistanceTrigger extends Trigger implements LocationListener {
  // Contains the last location that we triggered on (ie. the one we want to know if we're 50 meters away from.)
  private int thresholdInMeters;
  private LocationManager locationManager;
  private Context context;
  private int locationsCount, acceptedLocationCount;

  public DistanceTrigger(int thresholdInMeters, Context context) {
    this.thresholdInMeters = thresholdInMeters;
    this.context = context;

    locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
  }

  public int getLocationsCount() {
    return locationsCount;
  }

  public int getAcceptedLocationCount() {
    return acceptedLocationCount;
  }

  public int getThresholdInMeters() {
    return thresholdInMeters;
  }

  @Override
  public void startRegistering() {
    locationsCount = 0;
    acceptedLocationCount = 0;

    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
  }

  @Override
  public void stopRegistering() {
    Log.w("EagleEye", "Ticks: " + locationsCount + " locations: " + acceptedLocationCount + " difference: " + (locationsCount - acceptedLocationCount));
    locationManager.removeUpdates(this);
  }

  @Override
  protected boolean locationUpdated(float distanceInMeters, Location newLocation) {
    locationsCount++;

    Log.w("EagleEye", "distanceInMeters: " + distanceInMeters + " ticks: " + locationsCount);
    Log.w("EagleEye", "" + lastLocation);

    if(thresholdInMeters <= distanceInMeters || lastLocation == null){
      acceptedLocationCount++;
      Log.w("EagleEye", "Locations: " + acceptedLocationCount);

      fireTriggers(newLocation);

      return true;
    } else {
      return false;
    }
  }
}
