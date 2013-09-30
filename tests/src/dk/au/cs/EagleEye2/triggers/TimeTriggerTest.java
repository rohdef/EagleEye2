package dk.au.cs.EagleEye2.triggers;

import android.location.Location;
import android.test.AndroidTestCase;

/**
 * Timer interval is 1 second as specified.
 */
public class TimeTriggerTest extends AndroidTestCase {
  private TimerTrigger timerTrigger;
  private TestTriggerListener timerTriggerListener;

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    timerTrigger = new TimerTrigger(this.getContext());
    timerTriggerListener = new TestTriggerListener();
    timerTrigger.addListener(timerTriggerListener);
  }

  public void testShouldActivate10TimesIn10Seconds() {
    Location location;
    for (int i=0; i<10; i++) {
//      timerTrigger.timerTick();
      // Should've worked with location mocking, might not work irl

      location = new Location("Test");
      location.setLongitude(10.0);
      location.setLatitude(20.0);
      timerTrigger.onLocationChanged(location);
    }

    assertEquals(10, timerTriggerListener.getCallCount());
    assertNotNull(timerTriggerListener.getLastGeoPosition());
  }
}
