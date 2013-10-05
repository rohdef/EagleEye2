package dk.au.cs.EagleEye2.triggers;

import android.hardware.Sensor;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/**
 * Triggers for when to send a GPS for instance a distance trigger or a timed trigger.
 */
public abstract class Trigger implements LocationListener {
  private ArrayList<ITriggerListener> listeners = new ArrayList<ITriggerListener>();
  protected int locationCount;
  private Location lastLocation;

  public abstract void startRegistering();
  public abstract void stopRegistering();

  public void addListener(ITriggerListener listener) {
    listeners.add(listener);
  }
  public void removeListener(ITriggerListener listener) {
    listeners.remove(listener);
  }

  public void fireTriggers(Location location) {
    for (ITriggerListener listener : listeners) {
      listener.fireTrigger(location);
    }

    lastLocation = location;
  }

  protected void setLastLocation(Location location) {
    this.lastLocation = location;
  }

  /**
   * Wrapper for {@link #onLocationChanged(android.location.Location)} to enable testability.
   * @param distanceInMeters calculated distance between {@link #lastLocation} and the new location
   * @param newLocation
   * @param lastLocation
   * @return true if listeners is fired
   */
  protected abstract boolean locationUpdated(float distanceInMeters, Location newLocation, Location lastLocation);

  @Override
  public void onLocationChanged(Location location) {
    locationCount++;
    Log.w("EagleEye", "Location: " + locationCount);

    float distance;

    if(lastLocation == null){
      lastLocation = location;
      // Force first location to be regarded as past threshold so we get a first fix.
      distance = 0;
    }else{
      distance = lastLocation.distanceTo(location);
    }

    locationUpdated(distance, location, lastLocation);
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
