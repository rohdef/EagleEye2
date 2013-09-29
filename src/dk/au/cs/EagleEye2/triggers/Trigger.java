package dk.au.cs.EagleEye2.triggers;

import android.hardware.Sensor;
import android.location.Location;

import java.util.ArrayList;

/**
 * Triggers for when to send a GPS for instance a distance trigger or a timed trigger.
 */
public abstract class Trigger {
  private ArrayList<ITriggerListener> listeners = new ArrayList<ITriggerListener>();

  public abstract void startRegistering();
  public abstract void stopRegistering();

  public void addListener(ITriggerListener listener) {
    listeners.add(listener);
  }
  public void removeListener(ITriggerListener listener) {
    listeners.remove(listener);
  }

  public void fireTriggers(Location geoPosition) {
    for (ITriggerListener listener : listeners) {
      listener.fireTrigger(geoPosition);
    }
  }
}
