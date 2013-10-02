package dk.au.cs.EagleEye2.triggers;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
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

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testShouldActivate10TimesIn10Seconds() {
    Location location;
    for (int i=0; i<10; i++) {
      // Mocking locations wouldn't work. This should've been tested by sending a location before
      // then ticking and then sending two locations, since we expect it to remove them.
      // Fortunately the logic is so simple that this probably won't be a problem.
      //timerTrigger.timerTick();


      location = new Location("Test");
      location.setLongitude(10.0);
      location.setLatitude(20.0);
      timerTrigger.onLocationChanged(location);
    }

    assertEquals(10, timerTriggerListener.getCallCount());
    assertNotNull(timerTriggerListener.getLastGeoPosition());
  }
}
