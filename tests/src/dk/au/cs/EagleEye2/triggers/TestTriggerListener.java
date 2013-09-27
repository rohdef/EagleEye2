package dk.au.cs.EagleEye2.triggers;

import android.location.Location;

public class TestTriggerListener implements ITriggerListener{
  private int callCount = 0;
  private Object lastGeoPosition = null;

  public int getCallCount() {
    return callCount;
  }

  public Object getLastGeoPosition() {
    return lastGeoPosition;
  }

  @Override
  public void fireTrigger(Location geoPosition) {
    callCount++;
    lastGeoPosition = geoPosition;
  }
}
