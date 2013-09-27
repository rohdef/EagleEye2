package dk.au.cs.EagleEye2.triggers;

import android.location.Location;

public interface ITriggerListener {
  void fireTrigger(Location geoPosition);
}
