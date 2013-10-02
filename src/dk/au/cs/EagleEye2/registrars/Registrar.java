package dk.au.cs.EagleEye2.registrars;

import android.location.Location;
import dk.au.cs.EagleEye2.triggers.ITriggerListener;
import dk.au.cs.EagleEye2.triggers.Trigger;

/**
 * Registering service to register GPS fixes to for instance files or similar.
 */
public abstract class Registrar implements ITriggerListener {
  private Trigger trigger;

  public Registrar(Trigger trigger) {
    this.trigger = trigger;
  }

  public void startRegistering() {
    this.trigger.addListener(this);
  }

  public void stopRegistering() {
    this.trigger.removeListener(this);
  }
}
