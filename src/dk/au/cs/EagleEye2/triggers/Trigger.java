package dk.au.cs.EagleEye2.triggers;

import android.hardware.Sensor;

import java.util.ArrayList;

/**
 * Triggers for when to send a GPS for instance a distance trigger or a timed trigger.
 */
public abstract class Trigger {
  private ArrayList<ITriggerListener> listeners = new ArrayList<ITriggerListener>();

  public void addListener(ITriggerListener listener) {
    listeners.add(listener);
  }
  public void removeListener(ITriggerListener listener) {
    listeners.remove(listener);
  }

  public void fireTriggers(Object geoPosition) {
    for (ITriggerListener listener : listeners) {
      listener.fireTrigger(geoPosition);
    }
  }

  /**
   * Due the private constructor in SensorEvent this wrapper is needed to be able to test.
   * The actual SensorEvent should be unwrapped and send to this method.
   * @param sensorEvent
   */
  public abstract void onSensorChanged(Sensor sensor, int accuracy, long timestamp, float[] values);
}
