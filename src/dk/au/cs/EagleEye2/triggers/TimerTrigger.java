package dk.au.cs.EagleEye2.triggers;

import android.content.Context;
import android.hardware.Sensor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTrigger extends Trigger implements LocationListener {
  private Context context;
  private Timer timer;

  public TimerTrigger(Context context) {
    this.context = context;
  }

  @Override
  public void startRegistering() {
    timer = new Timer();

    // This has to be done with an anonymous class, since it's an abstract class
    // and we're already extending a class. Just distribute the event to a method
    // which is testable.
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        timerTick();
      }
    }, 0, 0);
  }

  @Override
  public void stopRegistering() {
    timer.cancel();
  }

  public void timerTick() {
    // http://stackoverflow.com/questions/7979230/how-to-read-location-only-once-with-locationmanager-gps-and-network-provider-a
    Log.w("EagleEye", "Tick");
    LocationManager lm = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
    lm.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, Looper.myLooper());
  }

  @Override
  public void onLocationChanged(Location location) {
    Log.w("EagleEye", "GPS");
    this.fireTriggers(location);
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {
  }

  @Override
  public void onProviderEnabled(String provider) {
  }

  @Override
  public void onProviderDisabled(String provider) {
  }
}
