package dk.au.cs.EagleEye2.triggers;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.util.Log;

public class DistanceWithSpeedTrigger extends DistanceTrigger implements Runnable {
  private LocationManager locationManager;
  private long timeInMilliSeconds;
  private Thread thread;
  private boolean running = false;
  private int tickCount, maxSpeedInMetersPerSecond;

  public DistanceWithSpeedTrigger(int maxSpeedInMetersPerSecond, int thresholdInMeters, Context context) {
    super(thresholdInMeters, context);

    this.maxSpeedInMetersPerSecond = maxSpeedInMetersPerSecond; // Let's start with expected speed from the beginning
    this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    this.timeInMilliSeconds = (long) (thresholdInMeters/maxSpeedInMetersPerSecond)*1000;
  }

  @Override
  public void startRegistering() {
    tickCount = 0;

    thread = new Thread(this);
    thread.start();
  }

  @Override
  public void run() {
    running = true;

    while (running) {
      timerTick();
      try {
        Thread.sleep(timeInMilliSeconds);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void stopRegistering() {
    int tickLocationDifference = tickCount-getLocationsCount();
    int acceptedLocationDifference = getLocationsCount()-getAcceptedLocationCount();

    Log.w("EagleEye", "Ticks:" + tickCount + ", locations:" + getLocationsCount() + ", acceptedLocationCount: " + getAcceptedLocationCount() +
      ", ticks-locations:" + tickLocationDifference + ", locations-acceptedLocations: " + acceptedLocationDifference);

    running = false;
  }

  public void timerTick() {
    tickCount++;

    Log.w("EagleEye", "Tick: " + tickCount);
    // http://stackoverflow.com/questions/7979230/how-to-read-location-only-once-with-locationmanager-gps-and-network-provider-a

    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this, Looper.getMainLooper());
  }

  @Override
  protected boolean locationUpdated(float distanceInMeters, Location newLocation) {
    locationManager.removeUpdates(this);

    if(super.locationUpdated(distanceInMeters, newLocation)) {
      timeInMilliSeconds = (long) ((getThresholdInMeters()/maxSpeedInMetersPerSecond)*1000);
      return true;
    } else {
      float distanceRemaining = getThresholdInMeters()-distanceInMeters;
      timeInMilliSeconds = (long) ((distanceRemaining/maxSpeedInMetersPerSecond)*1000);
      return false;
    }
  }
}
