package dk.au.cs.EagleEye2.triggers;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

public class DistanceWithSpeedTrigger extends Trigger implements Runnable {
  private int maxSpeedInMetersPerSecond, thresholdInMeters;
  private Context context;
  private Location lastLocation;
  private LocationManager locationManager;
  private long timeInMilliSeconds;
  private Thread thread;
  private boolean running = false;
  private int tickCount, locationCount, acceptedLocationCount;

  public DistanceWithSpeedTrigger(int maxSpeedInMetersPerSecond, int thresholdInMeters, Context context) {
    this.maxSpeedInMetersPerSecond = maxSpeedInMetersPerSecond;
    this.thresholdInMeters = thresholdInMeters;
    // Let's start with expected speed from the beginning
    this.context = context;
    this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    this.timeInMilliSeconds = (long) (thresholdInMeters/maxSpeedInMetersPerSecond)*1000;
  }


  @Override
  public void startRegistering() {
    locationCount = tickCount = 0;

    thread = new Thread(this);
    thread.start();;
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
    int tickLocationDifference = tickCount-locationCount;
    int acceptedLocationDifference = locationCount-acceptedLocationCount;
    Log.w("EagleEye", "Ticks:" + tickCount + ", locations:" + locationCount + ", acceptedLocationCount: " + acceptedLocationCount +
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
  public void onLocationChanged(Location location) {
    locationCount++;
    Log.w("EagleEye", "Location: " + locationCount);

    locationManager.removeUpdates(this);

    float distance;

    if(lastLocation == null){
      lastLocation = location;
      // Force first location to be regarded as past threshold so we get a first fix.
      distance = thresholdInMeters;
    }else{
      distance = lastLocation.distanceTo(location);
    }

    locationUpdated(distance, location, lastLocation);
  }

  public void locationUpdated(float distanceInMeters, Location newLocation, Location lastLocation) {
    // Wrapper for easy testability
    // this should decide if our event should be fired and then call fireTriggers

    Log.w("EagleEye", "distanceInMeters: " + distanceInMeters + " locationCount: " + locationCount);

    if(thresholdInMeters <= distanceInMeters) {
      acceptedLocationCount++;
      Log.w("EagleEye", "Accepted location count: " + acceptedLocationCount);
      fireTriggers(newLocation);

      this.lastLocation = newLocation;

      timeInMilliSeconds = (long) ((thresholdInMeters/maxSpeedInMetersPerSecond)*1000);
    } else {
      float distanceRemaining = thresholdInMeters-distanceInMeters;
      timeInMilliSeconds = (long) ((distanceRemaining/maxSpeedInMetersPerSecond)*1000);
    }

    locationManager.removeUpdates(this);
  }
}
