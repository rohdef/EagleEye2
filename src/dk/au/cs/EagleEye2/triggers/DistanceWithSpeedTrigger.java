package dk.au.cs.EagleEye2.triggers;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

public class DistanceWithSpeedTrigger extends Trigger implements LocationListener, Runnable {
  private int maxSpeedInMetersPerSecond, thresholdInMeters;
  private Context context;
  private Location lastLocation;
  private LocationManager locationManager;
  private long timeInMilliSeconds;
  private Thread thread;
  private boolean running = false;
  private int tickCount, locationCount;

  public DistanceWithSpeedTrigger(int maxSpeedInMetersPerSecond, int thresholdInMeters, Context context) {
    this.maxSpeedInMetersPerSecond = maxSpeedInMetersPerSecond;
    this.thresholdInMeters = thresholdInMeters;
    // Let's start with expected speed from the beginning
    this.timeInMilliSeconds = (long) (thresholdInMeters/maxSpeedInMetersPerSecond)*1000;

    this.context = context;
    this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
  }


  @Override
  public void startRegistering() {
    tickCount = locationCount = 0;

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
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }
    }
  }

  @Override
  public void stopRegistering() {
    running = false;

    int tickLocationDifference = tickCount-locationCount;
    Log.w("EagleEye", "Ticks:" + tickCount + ", locations:" + locationCount + ", ticks-locations:" + tickLocationDifference);
  }

  public void timerTick() {
    tickCount++;

    Log.w("EagleEye", "Tick: " + tickCount);
    // http://stackoverflow.com/questions/7979230/how-to-read-location-only-once-with-locationmanager-gps-and-network-provider-a

    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this, Looper.getMainLooper());
  }

  @Override
  public void onLocationChanged(Location location) {
    Log.w("EagleEye", "Location recieved");
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

    Log.w("EagleEye", "distanceInMeters: " + distanceInMeters + ", newLocation: " + newLocation + ", lastLocation: " + lastLocation);

    if(thresholdInMeters <= distanceInMeters){
      fireTriggers(newLocation);

      this.lastLocation = newLocation;
    }

    float distanceRemaining = thresholdInMeters-distanceInMeters;
    locationManager.removeUpdates(this);
    long sleepTime = (long) ((distanceRemaining/maxSpeedInMetersPerSecond)*1000);

    try {
      Thread.sleep(sleepTime);
    } catch (InterruptedException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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
