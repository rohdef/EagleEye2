package dk.au.cs.EagleEye2.triggers;

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

  public void fireTriggers() {
    for (ITriggerListener listener : listeners) {
      listener.fireTrigger(null);
    }
  }
}
