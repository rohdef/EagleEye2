package dk.au.cs.EagleEye2.triggers;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

public class TimerTrigger extends Trigger implements Runnable {
  private Context context;
  private LocationManager locationManager;
  private long timeInMilliSeconds;
  private Thread thread;
  private boolean running = false;
  private int tickCount;

  public TimerTrigger(int timeInSeconds, Context context) {
    this.context = context;
    this.locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
    this.timeInMilliSeconds = timeInSeconds*1000;
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
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }
    }
  }

  @Override
  public void stopRegistering() {
    int tickLocationDifference = tickCount-locationCount;
    Log.w("EagleEye", "Ticks:" + tickCount + ", locations:" + locationCount + ", ticks-locations:" + tickLocationDifference);

    running = false;
  }

  public void timerTick() {
    tickCount++;
    Log.w("EagleEye", "Tick: " + tickCount);
    // http://stackoverflow.com/questions/7979230/how-to-read-location-only-once-with-locationmanager-gps-and-network-provider-a

    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this, Looper.getMainLooper());
  }

  @Override
  protected boolean locationUpdated(float distanceInMeters, Location newLocation, Location lastLocation) {
    locationManager.removeUpdates(this);
    this.fireTriggers(newLocation);
    return true;
  }
}
